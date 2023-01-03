package org.social.post.controller;

import io.quarkus.security.identity.SecurityIdentity;
import org.social.form.PostForm;
import org.social.post.service.FeedService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Principal;

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

    @Inject
    SecurityIdentity identity;

    @RolesAllowed({"user", "admin"})
    @POST
    public Response addNewPost(PostForm postForm) {
        Principal principal = identity.getPrincipal();
        feedService.submitPost(postForm);
        return Response.ok().build();
    }

    @RolesAllowed({"user", "admin"})
    @GET
    @Path("/{id}")
    public Response getPost(@PathParam("id") Long postId) throws Exception {
        PostForm postForm = feedService.getPost(postId);
        return Response.ok(postForm).build();
    }
}
