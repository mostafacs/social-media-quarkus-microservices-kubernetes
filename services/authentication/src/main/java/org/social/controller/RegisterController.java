package org.social.controller;

import org.social.constants.SecurityConstants;
import org.social.form.UserForm;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;

/**
 * @Author Mostafa
 * On 11/28/22
 */
@Path("/register")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegisterController {

    @Inject
    Keycloak keycloak;

    @GET
    public Response ping() {
        return Response.ok("Welcome", MediaType.TEXT_PLAIN).build();
    }

    @POST
    public Response register(UserForm userForm) {
        UserRepresentation user = new UserRepresentation();
        user.setEmail(userForm.email());
        user.setFirstName(userForm.firstname());
        user.setLastName(userForm.lastname());
        user.setUsername(userForm.username());

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userForm.password());
        credential.setTemporary(false);

        user.setCredentials(Collections.singletonList(credential));
        user.setRealmRoles(Collections.singletonList(SecurityConstants.ROLE_USER));
        user.setEnabled(true);
        user.setEmailVerified(true); // for now

        Response response = keycloak.realm(SecurityConstants.REALM).users().create(user);

       return Response.status(response.getStatus()).entity(response.getEntity()).build();
    }
}
