package org.social.post.controller;

import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal;
import io.quarkus.security.credential.Credential;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.social.form.PostForm;
import org.social.post.service.FeedService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Set;

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
    public Response addNewPost(PostForm postForm, @HeaderParam("Authorization") String tokenStr) throws ParseException {
        Set<Credential> credentialSet = identity.getCredentials();

        OidcJwtCallerPrincipal principal = (OidcJwtCallerPrincipal) identity.getPrincipal();

        System.out.println(principal.getName());

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
