package org.social.post.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.collection.IQueue;
import com.hazelcast.collection.ISet;
import com.hazelcast.config.IndexConfig;
import com.hazelcast.config.IndexType;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MultiMapConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.HazelcastJsonValue;
import com.hazelcast.map.IMap;
import com.hazelcast.query.Predicates;
import org.social.model.Post;
import org.social.post.form.PostForm;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

        //MapConfig mapConfig = new MapConfig();
        //mapConfig.add
        //hazelcast.getConfig().addMapConfig()

        IQueue queue = hazelcast.getQueue("hello");
        IMap<Long, HazelcastJsonValue> map = hazelcast.getMap("wall-user-xyz2");
        map.addIndex(IndexType.SORTED, "priority");


        ISet set = hazelcast.getSet("wall-set");

        for (Long j = 22l; j > 0; j--) {
                System.out.println("Put = "+String.valueOf(j));
                PostForm post= new PostForm(); post.setPriority(j.intValue());
            System.out.println(objectMapper.writeValueAsString(post));
                map.put(j, new HazelcastJsonValue(objectMapper.writeValueAsString(post)));
                queue.add(post);
            }
        //}

            new Thread(  () -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //for (int i = 0; i < 5; i++) {
                System.out.println("Size="+map.size());

                Collection<HazelcastJsonValue> jsonValues = map.values(Predicates.pagingPredicate(Predicates.alwaysTrue(), 22));
                for (HazelcastJsonValue postJson : jsonValues) {
                    PostForm value = null;
                    try {
                        value = objectMapper.readValue(postJson.toString(), PostForm.class);
                        System.out.println("Priority >> "+value.getPriority());
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("-------------");
                //}
            }).start();
        map.flush();

        return Response.ok(new PostForm()).build();
    }
}
