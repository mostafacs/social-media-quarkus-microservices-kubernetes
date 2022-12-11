package org.social.post.controller;

import com.hazelcast.core.HazelcastInstance;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
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

    @POST
    public Response addPost(PostForm post) {

        return Response.ok().build();
    }

    @GET
    public Response getPost() throws InterruptedException {

        //hazelcast.getQueue(String.valueOf(0)).clear();
        //for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 15; j++) {
                System.out.println("Put = "+String.valueOf(j));
                hazelcast.getQueue(String.valueOf(0)).add("Put = "+String.valueOf(j));
            }
        //}

            new Thread(  () -> {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //for (int i = 0; i < 5; i++) {
                List<String> data = new ArrayList<>();
                while (data.size() != 14) {
                    Object value = null;
                    try {
                        value = hazelcast.getQueue(String.valueOf(0)).take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    data.add(String.valueOf(value));
                }
                System.out.println(String.join(", ", data));
                System.out.println("-------------");
                //}
            }).start();

        return Response.ok(new PostForm()).build();
    }
}
