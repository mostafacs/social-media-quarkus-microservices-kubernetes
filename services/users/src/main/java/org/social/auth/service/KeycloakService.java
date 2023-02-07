package org.social.auth.service;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.social.constants.SecurityConstants;
import org.social.form.UserForm;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Mostafa
 */
@Singleton
public class KeycloakService {

    @Inject
    Keycloak keycloak;

    protected Response addUser(UserForm form, Long userId) {
        UserRepresentation userRep = new UserRepresentation();
        fillUserRepresentation(userRep, form);

        // client credential
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(form.getPassword());
        credential.setTemporary(false);

        userRep.setCredentials(Collections.singletonList(credential));
        userRep.setRealmRoles(Collections.singletonList(SecurityConstants.ROLE_USER));
        userRep.setClientRoles(Collections.singletonMap(SecurityConstants.ROLE_USER, new ArrayList<>()));
        userRep.setEnabled(true);
        userRep.setEmailVerified(true); // for now

        userRep.setAttributes(Collections.singletonMap(SecurityConstants.USER_ID_ATTRIBUTE, Arrays.asList(userId.toString())));

        Response response = keycloak.realm(SecurityConstants.REALM).users().create(userRep);
        return response;
    }

    public UserRepresentation getUserByUsername(String username) {
        List<UserRepresentation> users = keycloak.realm(SecurityConstants.REALM).users().search(username, true);
        if(users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    public void update(String keycloakUserId, UserForm userForm) {
        UserResource userResource = keycloak.realm(SecurityConstants.REALM).users().get(keycloakUserId);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        fillUserRepresentation(userRepresentation, userForm);
        userResource.update(userRepresentation);
    }

    private void fillUserRepresentation(UserRepresentation userRep, UserForm form) {
        userRep.setEmail(form.getEmail());
        userRep.setFirstName(form.getFirstname());
        userRep.setLastName(form.getLastname());
        userRep.setUsername(form.getUsername());
    }
}
