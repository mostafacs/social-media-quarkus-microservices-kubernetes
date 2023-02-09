package org.social.caching;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.config.IndexType;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastJsonValue;
import com.hazelcast.map.IMap;
import com.hazelcast.sql.SqlResult;
import com.hazelcast.sql.SqlRow;
import org.social.form.PostFeedCache;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author Mostafa
 * On 12/18/22
 */
@ApplicationScoped
public class HazelcastFeedCacheManager implements FeedCacheManager {

    @Inject
    HazelcastInstance hazelcast;

    @Inject
    ObjectMapper objectMapper;

    enum SortDirection {
        asc, desc
    }

    @Override
    public int size(Long userId) {
        String mapName = USER_FEED_PREFIX+userId;
        IMap<Long, HazelcastJsonValue> map = hazelcast.getMap(mapName);
        return map.size();
    }

    @Override
    public boolean isFull(Long userId) {
        String mapName = USER_FEED_PREFIX+userId;
        IMap<Long, HazelcastJsonValue> map = hazelcast.getMap(mapName);
        int mapSize = map.size();
        return mapSize >= MAX_POST_PER_CLIENT;
    }

    @Override
    public void addToUserFeed(Long userId, PostFeedCache post) throws Exception {
        String mapName = USER_FEED_PREFIX+userId;
        // init the map
        // create the map for
        hazelcast.getSql().execute("CREATE MAPPING IF NOT EXISTS "+ mapName +" \n" +
                "TYPE IMap \n" +
                "OPTIONS (\n" +
                "    'keyFormat'='bigint',\n" +
                "    'valueFormat'='json'\n" +
                ")");
        IMap<Long, HazelcastJsonValue> map = hazelcast.getMap(mapName);
        map.addIndex(IndexType.SORTED, "priority");
        map.addIndex(IndexType.SORTED, "updatedOnTimeStamp");

        if(map.size() >= MAX_POST_PER_CLIENT) {
            List<PostFeedCache> toDeletePosts =  getUserFeed(userId, COUNT_TO_DELETE_ON_FULL, SortDirection.asc, SortDirection.asc);
            for(PostFeedCache deletePost : toDeletePosts) {
                map.remove(deletePost.getPostId());
            }
        }
        map.put(post.getPostId(), new HazelcastJsonValue(objectMapper.writeValueAsString(post)));
    }

    @Override
    public List<PostFeedCache> getUserFeed(Long userId, int limit) throws Exception{
        return getUserFeed(userId, limit, SortDirection.desc, SortDirection.asc);
    }

    private List<PostFeedCache> getUserFeed(Long userId, int limit, SortDirection prioritySortDirection, SortDirection updateDateSortDirection) throws JsonProcessingException {
        List<PostFeedCache> postForms = new ArrayList<>();
        String mapName = USER_FEED_PREFIX+userId;

        Long startTime = System.currentTimeMillis();
        SqlResult sqlResult = hazelcast.getSql().execute("SELECT JSON_QUERY(this, '$') as post FROM "+ mapName +
                " order by JSON_VALUE(this, '$.priority' RETURNING int) " + prioritySortDirection.toString() +
                ",  JSON_VALUE(this, '$.updatedOnTimeStamp' RETURNING BIGINT) " + updateDateSortDirection.toString() +
                " LIMIT "+limit); // map.values(Predicates.pagingPredicate(Predicates.alwaysTrue(), 22));
        System.out.println("Executed in "+ (System.currentTimeMillis()-startTime) );
        Iterator<SqlRow> itr =  sqlResult.iterator();
        while (itr.hasNext()) {
            SqlRow row = itr.next();
            HazelcastJsonValue hazelcastJsonValue = row.getObject("post");
            System.out.println((HazelcastJsonValue) row.getObject("post"));
            postForms.add(objectMapper.readValue(hazelcastJsonValue.getValue(), PostFeedCache.class));
        }
        return postForms;
    }
}
