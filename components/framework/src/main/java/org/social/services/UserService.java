package org.social.services;

import io.quarkus.oidc.runtime.OidcJwtCallerPrincipal;
import io.quarkus.security.identity.SecurityIdentity;
import org.social.constants.SecurityConstants;
import org.social.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonNumber;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author Mostafa
 * On 12/23/22
 */
@ApplicationScoped
public class UserService {

    @Inject
    EntityManager em;

    @Inject
    SecurityIdentity identity;

    public Long currentLoginUserId() {
        OidcJwtCallerPrincipal principal = (OidcJwtCallerPrincipal) identity.getPrincipal();
        JsonNumber id = principal.getClaim(SecurityConstants.USER_ID_ATTRIBUTE);
        return id.longValue();
    }

    public User getUser(Long id) {
        return em.find(User.class, id);
    }

    @Transactional
    public Set<Long> getFriends(Long userId, int page, int pageSize) {
        Set<Long> friends = new HashSet<>();
        List<Object[]> rawData = em.createQuery("select fs.user2.id, fs.user1.id from Friendship fs where fs.user1.id=:userId or fs.user2.id=:userId")
                .setParameter("userId", userId)
                .setFirstResult(page * pageSize)
                .setMaxResults(pageSize)
                .getResultList();

        for(Object[] objects : rawData) {
            friends.add((long) objects[0]);
            friends.add((long) objects[0]);
        }
        friends.remove(userId);
        return friends;
    }

}
