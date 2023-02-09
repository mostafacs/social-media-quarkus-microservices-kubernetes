package org.social.auth.controller;

import org.social.auth.service.UserManagerService;
import org.social.form.UserForm;
import org.social.model.User;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @Author Mostafa
 * On 11/28/22
 */
@Path("/register")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegisterController {

    @Inject
    UserManagerService userManagerService;

    @POST
    public Response register(UserForm userForm) throws Exception{
       User user = userManagerService.addNewUser(userForm);
       return Response.ok(user.getId()).build();
    }

//    @GET
//    @Path("/login")
//    public Response login() {
//        AccessTokenResponse response = keycloak.tokenManager().getAccessToken();
//        return Response.ok(response).build();
//    }
}
