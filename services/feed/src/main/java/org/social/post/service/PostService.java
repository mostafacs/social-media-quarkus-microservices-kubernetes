package org.social.post.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.durableexecutor.DurableExecutorService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.social.post.form.PostForm;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @Author Mostafa
 * On 12/4/22
 */
@Singleton
public class PostService {

    @ConfigProperty(name = "post-exec-service")
    String wallUpdateService;

    @Inject
    HazelcastInstance hazelcast;

    public void addPost(PostForm post) {

        DurableExecutorService durableExecutorService = hazelcast.getExecutorService("");
        durableExecutorService.submit(() -> {}).getTaskId();
        durableExecutorService.retrieveResult(11l);
    }
}
