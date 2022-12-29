package org.social.post.controller;

import org.social.form.PostForm;
import org.social.post.service.FeedService;

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
public class PostController {

    @Inject
    FeedService feedService;


    @POST
    public Response addNewPost(PostForm postForm) {
        feedService.submitPost(postForm);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    public Response getPost(Long postId) throws Exception {
        PostForm postForm = feedService.getPost(postId);
        return Response.ok(postForm).build();
    }
}
