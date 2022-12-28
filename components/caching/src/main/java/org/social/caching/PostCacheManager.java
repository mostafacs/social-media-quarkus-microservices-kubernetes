package org.social.caching;

import org.social.form.PostForm;

/**
 * @Author Mostafa
 * On 12/28/22
 */
public interface PostCacheManager {

    String POSTS_CACHE_NAME = "posts";

    boolean contains(Long postId);

    void cachePost(PostForm post) throws Exception;

    PostForm getPost(Long postId) throws Exception;
}
