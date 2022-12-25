package org.social.post.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.collection.IQueue;
import com.hazelcast.collection.ISet;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.IndexType;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizePolicy;
import com.hazelcast.config.MultiMapConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastJsonValue;
import com.hazelcast.map.IMap;
import com.hazelcast.sql.SqlResult;
import com.hazelcast.sql.SqlRow;
import org.social.caching.FeedCacheManager;
import org.social.form.PostForm;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @Author Mostafa
 * On 12/4/22
 */
@Path("/post")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AddPostController {

    @Inject
    HazelcastInstance hazelcast;

    @Inject
    ObjectMapper objectMapper;

    @Inject
    FeedCacheManager feedCacheManager;

    @POST
    public Response addPost(PostForm post) {

        return Response.ok().build();
    }

    @GET
    public Response getPost() throws InterruptedException, JsonProcessingException {

//        ClientConfig clientConfig = new ClientConfig();
        // clientConfig.getNetworkConfig().setSmartRouting(false);
        // String[] members = ips.split(",");
        //clientConfig.getNetworkConfig().addAddress(members);
//        HazelcastInstance hazelcast = HazelcastClient.newHazelcastClient(clientConfig);
        //hazelcast.getQueue(String.valueOf(0)).clear();
        //for (int i = 0; i < 5; i++) {
        MultiMapConfig multiMapConfig = new MultiMapConfig();
        multiMapConfig.setName("user-11").setValueCollectionType(MultiMapConfig.ValueCollectionType.SET);
        hazelcast.getConfig().addMultiMapConfig(multiMapConfig);

        MapConfig mapConfig = new MapConfig();
        mapConfig.setEvictionConfig(new EvictionConfig().setMaxSizePolicy(MaxSizePolicy.ENTRY_COUNT));

        //mapConfig.add
        //hazelcast.getConfig().addMapConfig()

//        IQueue queue = hazelcast.getQueue("hello");
//        IMap<Long, HazelcastJsonValue> map = hazelcast.getMap("WallUserXyz5");
//        map.addIndex(IndexType.SORTED, "priority");
//
//
//        ISet set = hazelcast.getSet("wall-set");

        Long userId = 123456l;
        for (Long j = 100l; j > 0; j--) {
                System.out.println("Put = "+String.valueOf(j));
                PostForm post= new PostForm();
                post.setId(j);
                post.setPriority(j.intValue());
                post.setUpdatedOn(new Date());
            // System.out.println(objectMapper.writeValueAsString(post));
                feedCacheManager.addToUserFeed(userId, post);
            }


            new Thread(  () -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //for (int i = 0; i < 5; i++) {
                System.out.println("Size="+feedCacheManager.size(userId));

                List<PostForm> wall = feedCacheManager.getUserFeed(userId, 50);

                for(PostForm post : wall) {
                    System.out.println(post.getPriority() +" - "+post.getUpdatedOn());
                }

//                hazelcast.getSql().execute("CREATE IF NOT EXIST MAPPING WallUserXyz5 \n" +
//                        "TYPE IMap \n" +
//                        "OPTIONS (\n" +
//                        "    'keyFormat'='bigint',\n" +
//                        "    'valueFormat'='json'\n" +
//                        ")");
//                Long startTime = System.currentTimeMillis();
//                SqlResult sqlResult = hazelcast.getSql().execute("SELECT JSON_QUERY(this, '$') as post FROM WallUserXyz5 order by JSON_VALUE(this, '$.priority' RETURNING int);"); // map.values(Predicates.pagingPredicate(Predicates.alwaysTrue(), 22));
//                System.out.println("Executed in "+ (System.currentTimeMillis()-startTime) );
//                Iterator<SqlRow> itr =  sqlResult.iterator();
//                while (itr.hasNext()) {
//                    SqlRow row = itr.next();
//                    System.out.println((HazelcastJsonValue) row.getObject("post"));
//                }

//                for (HazelcastJsonValue postJson : jsonValues) {
//                    PostForm value = null;
//                    try {
//                        value = objectMapper.readValue(postJson.toString(), PostForm.class);
//                        System.out.println("Priority >> "+value.getPriority());
//                    } catch (JsonProcessingException e) {
//                        e.printStackTrace();
//                    }
//                }
                System.out.println("-------------");
                //}
            }).start();
        // map.flush();

        return Response.ok(new PostForm()).build();
    }
}
