package org.social.post.service;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.social.caching.FeedCacheManager;
import org.social.form.PostForm;
import org.social.model.Post;
import org.social.post.mapper.PostMapper;
import org.social.services.PostService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * @Author Mostafa
 * On 12/27/22
 */
@Singleton
public class FeedService {

    @ConfigProperty(name = "new-posts-priority")
    int newPostsPriority;

    @ConfigProperty(name = "user-feed-page-size")
    int userFeedPageSize;

    @Inject
    PostService postService;

    @Inject
    FeedCacheManager feedCacheManager;

    @Inject
    @Channel("posts-out")
    Emitter<PostForm> postEmitter;

    PostMapper postMapper = PostMapper.mapper;

    public void submitPost(PostForm postForm) {
        Post post = postMapper.toEntity(postForm);
        postService.save(post);
        postForm.setId(post.getId());
        postForm.setPriority(newPostsPriority);
        postEmitter.send(Message.of(postForm));
    }


    public List<PostForm> getFeed(long userId) throws Exception {
        return feedCacheManager.getUserFeed(userId, userFeedPageSize);
    }
}
