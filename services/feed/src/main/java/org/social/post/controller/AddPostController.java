package org.social.post.controller;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.social.form.PostForm;
import org.social.model.Post;
import org.social.post.mapper.PostMapper;
import org.social.services.PostService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @Author Mostafa
 * On 12/4/22
 */
@Path("/post")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AddPostController {

//    @Inject
//    ObjectMapper objectMapper;

    @Inject
    PostService postService;

    PostMapper postMapper = PostMapper.mapper;



    @POST
    public Response addPost(PostForm postForm) {

        return Response.ok().build();
    }

    @GET
    public Response getPost() throws Exception {

        return Response.ok(new PostForm()).build();
    }
}
