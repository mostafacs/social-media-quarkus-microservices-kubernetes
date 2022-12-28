package org.social.messaging;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.caching.FeedCacheManager;
import org.social.caching.PostCacheManager;
import org.social.form.PostFeedCache;
import org.social.form.PostForm;
import org.social.services.PostService;
import org.social.services.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * @Author Mostafa
 * On 12/23/22
 */
@ApplicationScoped
public class PostUpdateConsumerHandler {

    @Inject
    FeedCacheManager feedCacheManager;

    @Inject
    PostCacheManager postCacheManager;

    @Inject
    UserService userService;

    @Inject
    EntityManager em;

    Logger logger = LoggerFactory.getLogger(PostUpdateConsumerHandler.class);

    /**
     * When a new post is created, existing one updated update the post owner friends.
     * @param postForm
     */
    // update post priority and set to cache
    @Incoming("posts-in")
    public void consumeAddPost(PostForm postForm) {
        // process post.
        try {
            postCacheManager.cachePost(postForm);
        } catch (Exception e) {
            logger.error(String.format("Error while cahing post [%d]", postForm.getId()), e);
        }
        int page = 0;
        int pageSize = 100;
        Long postOwnerId = postForm.getUser().id();
        List<Long> friends;
        do {
            friends = userService.getFriends(postOwnerId, page, pageSize);
            for(Long friend : friends) {
                try {
                    feedCacheManager.addToUserFeed(friend, PostFeedCache.fromPostForm(postForm, postForm.getPriority()));
                } catch (Exception e) {
                    logger.error(String.format("Error while adding post [%d] to user [%d] Feed", postForm.getId(), postForm.getUser().id()), e);
                }
            }
        } while (friends.size() >= pageSize);
    }

    @Incoming("zero-priority-post")
    //TODO update the wall for consumed user only
    // TODO create a new Pojo for this consumer
    public PostForm zeroPriorityPost(PostForm post) {
        //TODO
        post.setPriority(0);
        return post;
    }
}
