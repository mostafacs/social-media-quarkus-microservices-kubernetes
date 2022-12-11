package org.social.post.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.durableexecutor.DurableExecutorService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.social.post.form.PostForm;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

/**
 * @Author Mostafa
 * On 12/4/22
 */
@Singleton
public class PostService {

    //@Inject
    EntityManager em;

    @ConfigProperty(name = "post-exec-service")
    String wallUpdateService;

    @Inject
    HazelcastInstance hazelcast;

    public void addPost(PostForm post) {

         //   hazelcast.getQueue("").take();
        DurableExecutorService durableExecutorService = hazelcast.getDurableExecutorService("");
//        durableExecutorService.submit(() -> {}).getTaskId();
//        durableExecutorService.retrieveResult(11l);
    }
}
