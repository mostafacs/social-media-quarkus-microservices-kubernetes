package org.social.post.controller;

import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal;
import io.smallrye.jwt.auth.principal.ParseException;
import org.social.constants.SecurityConstants;
import org.social.form.PostForm;
import org.social.post.service.FeedService;
import org.social.services.UserService;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.JsonNumber;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

    @Inject
    UserService userService;

    @RolesAllowed({"user", "admin"})
    @POST
    public Response addNewPost(PostForm postForm, @HeaderParam("Authorization") String tokenStr) throws ParseException {
        feedService.submitPost(postForm, userService.currentLoginUserId());
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
