package org.social.post.service;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.social.caching.FeedCacheManager;
import org.social.caching.PostCacheManager;
import org.social.form.PostFeedCache;
import org.social.form.PostForm;
import org.social.form.UserForm;
import org.social.model.Post;
import org.social.model.User;
import org.social.post.mapper.PostMapper;
import org.social.services.PostService;
import org.social.services.UserService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Mostafa
 * On 12/27/22
 */
@Singleton
public class FeedService {

    public static final int DEFAULT_POST_COMMENTS_CACHE = 5;

    @ConfigProperty(name = "new-posts-priority")
    int newPostsPriority;

    @ConfigProperty(name = "user-feed-page-size")
    int userFeedPageSize;

    @Inject
    PostService postService;

    @Inject
    UserService userService;

    @Inject
    FeedCacheManager feedCacheManager;

    @Inject
    PostCacheManager postCacheManager;

    @Inject
    @Channel("feeds-out")
    Emitter<PostForm> postEmitter;

    @Inject
    @Channel("zero-priority-out")
    Emitter<PostForm> zeroPriorityPostsEmitter;

    PostMapper postMapper = PostMapper.mapper;

    public void submitPost(PostForm postForm, Long userId) {
        Post post = postMapper.toEntity(postForm);
        User user = userService.getUser(userId);
        post.setUser(user);
        postService.save(post);
        postForm.setId(post.getId());
        postForm.setUser(UserForm.fromEntity(user));
        postForm.setPriority(newPostsPriority);
        postEmitter.send(Message.of(postForm));
    }

    public PostForm getPost(Long postId) throws Exception {
        PostForm post = postCacheManager.getPost(postId);
        if(post == null) {
            post = postService.getPostForm(postId, DEFAULT_POST_COMMENTS_CACHE);
        }
        return post;
    }


    public List<PostForm> getFeed(long userId) throws Exception {
        List<PostFeedCache> feeds = feedCacheManager.getUserFeed(userId, userFeedPageSize);
        List<PostForm> posts = new ArrayList<>(feeds.size());
        for(PostFeedCache pfc : feeds) {
            PostForm post = postCacheManager.getPost(pfc.getPostId());
            if(post == null) {
                post = postService.getPostForm(pfc.getPostId(), DEFAULT_POST_COMMENTS_CACHE);
            }
            posts.add(post);
            zeroPriorityPostsEmitter.send(post);
        }
        return posts;
    }
}
