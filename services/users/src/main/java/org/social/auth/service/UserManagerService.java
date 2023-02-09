package org.social.auth.service;

import org.jboss.logmanager.Level;
import org.keycloak.representations.idm.UserRepresentation;
import org.social.form.UserForm;
import org.social.model.FriendRequest;
import org.social.model.Friendship;
import org.social.model.User;
import org.social.services.UserService;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.Date;
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

    @Inject
    UserService userService;

    @Transactional
    public User addNewUser(UserForm form) throws Exception{
        User user = form.toEntity();
        user.setJoinedOn(new Date());
        user.setUpdatedOn(new Date());
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
            // add role here due to the keycloak api bug
            keycloakService.updateRealmRole(userRep.getId());
            user.setKeycloakId(userRep.getId());
            em.persist(user);
        }
        return user;
    }

    @Transactional
    public Long sendFriendRequest(Long toId) {


        FriendRequest fr = new FriendRequest();
        fr.setFrom(em.find(User.class, userService.currentLoginUserId()));
        fr.setTo(em.find(User.class, toId));
        fr.setRequestDate(new Date());
        fr.setStatus(FriendRequest.FriendRequestStatus.pending);
        em.persist(fr);
        return fr.getId();
    }

    @Transactional
    public void confirmFriendRequest(Long friendRequestId) {
        FriendRequest fr = em.find(FriendRequest.class, friendRequestId);
        if(fr.getTo().getId().equals(userService.currentLoginUserId())) {
            fr.setStatus(FriendRequest.FriendRequestStatus.confirmed);
            // update friendship
            Friendship friendship = new Friendship();
            friendship.setUser1(fr.getFrom());
            friendship.setUser2(fr.getTo());
            friendship.setFriendshipDate(new Date());
            em.persist(fr);
            em.persist(friendship);
        }
        else {
            throw new IllegalArgumentException("Invalid friend request");
        }
    }

    @Transactional
    public void cancelFriendRequest(Long friendRequestId) {
        FriendRequest fr = em.find(FriendRequest.class, friendRequestId);
        if(fr.getTo().getId().equals(userService.currentLoginUserId())) {
            fr.setStatus(FriendRequest.FriendRequestStatus.canceled);
            em.persist(fr);
        }
        else {
            throw new IllegalArgumentException("Invalid friend request");
        }
    }


}
