package org.social.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * @Author Mostafa
 * On 12/23/22
 */
@ApplicationScoped
public class UserService {

    @Inject
    EntityManager em;


    public List<Long> getFriends(Long userId) {
        return em.createQuery("select if(fs.user1.id = :userId, fs.user2.id, fs.user1.id) as friendId from Friendship fs where fs.user1.id=:userId or fs.user2.id=:userId", Long.class)
                .setParameter("userId", userId)
                .getResultList();
    }

}
