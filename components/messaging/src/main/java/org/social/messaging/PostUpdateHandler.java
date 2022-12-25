package org.social.messaging;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.social.caching.FeedCacheManager;
import org.social.form.PostForm;
import org.social.mapper.PostMapper;
import org.social.model.Post;
import org.social.services.PostService;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * @Author Mostafa
 * On 12/23/22
 */
@ApplicationScoped
public class PostUpdateHandler {

    @Inject
    FeedCacheManager feedCacheManager;

//    @Inject
//    PostService postService;

    @Inject
    EntityManager em;

    // update post priority and set to cache
    @Incoming("post-updated")
    public void consumeAddPost(Long postId) {
        // process post.
    }

    @Incoming("zero-priority-post")
    public void zeroPriorityPost(Long postId) {
        Post post = em.find(Post.class, postId);
        PostForm form = PostMapper.mapper.toForm(post);
        form.setPriority(0);
    }
}
