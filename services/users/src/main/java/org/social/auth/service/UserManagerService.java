package org.social.auth.service;

import org.jboss.logmanager.Level;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.social.constants.SecurityConstants;
import org.social.form.UserForm;
import org.social.model.User;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * @author Mostafa
 */
@Singleton
public class UserManagerService {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Inject
    EntityManager em;

    @Inject
    KeycloakService keycloakService;

    @Transactional
    public User addNewUser(UserForm form) throws Exception{
        User user = form.toEntity();
        em.persist(user);
        // save to keycloak
        Response response = keycloakService.addUser(form, user.getId());

        if(!response.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL)) {
            logger.log(Level.ERROR, response.toString());
            throw new Exception("Error creating new user");
        }
        // response.getKeycloakUserId
        UserRepresentation userRep = keycloakService.getUserByUsername(form.getUsername());
        if(userRep != null) {
            user.setKeycloakId(userRep.getId());
            em.persist(user);
        }
        return user;
    }
}
