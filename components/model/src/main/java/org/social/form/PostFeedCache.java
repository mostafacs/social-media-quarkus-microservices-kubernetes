package org.social.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.social.model.Post;

/**
 * Instead of repeated PostForm per user feed(wall) store this light Object(PostFeedCache) and load the post from another cache
 * @Author Mostafa
 * On 12/28/22
 */

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class PostFeedCache {

    private Long postId;
    private Integer priority;
    private Long updatedOnTimeStamp;

    public static PostFeedCache fromPost(Post post, Integer priority) {
        PostFeedCache pc = new PostFeedCache();
        pc.setPostId(post.getId());
        pc.setPriority(priority);
        pc.setUpdatedOnTimeStamp(post.getUpdatedOn().getTime());
        return pc;
    }

    public static PostFeedCache fromPostForm(PostForm postForm, Integer priority) {
        PostFeedCache pc = new PostFeedCache();
        pc.setPostId(postForm.getId());
        pc.setPriority(priority);
        pc.setUpdatedOnTimeStamp(postForm.getUpdatedOn().getTime());
        return pc;
    }
}
