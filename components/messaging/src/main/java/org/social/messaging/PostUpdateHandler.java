package org.social.messaging;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.social.caching.FeedCacheManager;
import org.social.form.PostForm;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @Author Mostafa
 * On 12/23/22
 */
@ApplicationScoped
public class PostUpdateHandler {

    @Inject
    FeedCacheManager feedManager;

    // update post priority and set to cache
    @Incoming("post-updated")
    public void consumeAddPost(Long postId) {
        // process post.
    }

}
