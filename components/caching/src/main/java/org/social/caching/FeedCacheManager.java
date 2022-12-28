package org.social.caching;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.social.form.PostFeedCache;
import org.social.form.PostForm;
import org.social.form.UserForm;

import java.util.List;

/**
 * @Author Mostafa
 * On 12/18/22
 */
public interface FeedCacheManager {

    String USER_FEED_PREFIX = "UserFeed";
    Integer MAX_POST_PER_CLIENT = 200;
    Integer COUNT_TO_DELETE_ON_FULL = 5;

    int size(Long userId);

    boolean isFull(Long userId);

    void addToUserFeed(Long userId, PostFeedCache userForm) throws Exception;

    List<PostFeedCache> getUserFeed(Long userId, int limit) throws Exception;
}
