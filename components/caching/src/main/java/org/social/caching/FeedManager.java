package org.social.caching;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.social.form.PostForm;
import org.social.form.UserForm;

import java.util.List;

/**
 * @Author Mostafa
 * On 12/18/22
 */
public interface FeedManager {

    void addToUserFeed(Long userId, PostForm userForm) throws JsonProcessingException;

    List<PostForm> getUserFeed(Long userId, int limit);
}
