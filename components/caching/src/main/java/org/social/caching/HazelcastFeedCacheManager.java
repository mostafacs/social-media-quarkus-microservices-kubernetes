package org.social.caching;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.config.IndexType;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastJsonValue;
import com.hazelcast.map.IMap;
import com.hazelcast.sql.SqlResult;
import com.hazelcast.sql.SqlRow;
import org.social.form.PostForm;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author Mostafa
 * On 12/18/22
 */
@Singleton
public class HazelcastFeedCacheManager implements FeedCacheManager {

    @Inject
    HazelcastInstance hazelcast;

    @Inject
    ObjectMapper objectMapper;

    @Override
    public void addToUserFeed(Long userId, PostForm post) throws JsonProcessingException {
        String mapName = "UserWall"+userId;
        hazelcast.getSql().execute("CREATE IF NOT EXIST MAPPING "+ mapName +" \n" +
                "TYPE IMap \n" +
                "OPTIONS (\n" +
                "    'keyFormat'='bigint',\n" +
                "    'valueFormat'='json'\n" +
                ")");
        IMap<Long, HazelcastJsonValue> map = hazelcast.getMap(mapName);
        map.addIndex(IndexType.SORTED, "priority");
        map.put(post.getId(), new HazelcastJsonValue(objectMapper.writeValueAsString(post)));
    }

    @Override
    public List<PostForm> getUserFeed(Long userId, int limit) {
        List<PostForm> postForms = new ArrayList<>();
        String mapName = "UserWall"+userId;

        Long startTime = System.currentTimeMillis();
        SqlResult sqlResult = hazelcast.getSql().execute("SELECT JSON_QUERY(this, '$') as post FROM "+ mapName +" order by JSON_VALUE(this, '$.priority' RETURNING int) LIMIT "+limit); // map.values(Predicates.pagingPredicate(Predicates.alwaysTrue(), 22));
        System.out.println("Executed in "+ (System.currentTimeMillis()-startTime) );
        Iterator<SqlRow> itr =  sqlResult.iterator();
        while (itr.hasNext()) {
            SqlRow row = itr.next();
            HazelcastJsonValue hazelcastJsonValue = (HazelcastJsonValue) row.getObject("post");
            System.out.println((HazelcastJsonValue) row.getObject("post"));
        }
        return postForms;
    }
}
