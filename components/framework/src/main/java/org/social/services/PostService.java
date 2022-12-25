package org.social.services;

import org.social.form.PostForm;
import org.social.mapper.CommentMapper;
import org.social.mapper.PostMapper;
import org.social.model.Comment;
import org.social.model.Post;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Mostafa
 * On 12/25/22
 */
@Singleton
public class PostService {

    PostMapper postMapper = PostMapper.mapper;
    CommentMapper commentMapper = CommentMapper.mapper;

    @Inject
    EntityManager em;

    public PostForm getPost(Long postId, int commentsCount) {
        Post post = em.find(Post.class, postId);
        List<Comment> comments = em.createQuery("Select comment from Comment comment where comment.post=:post", Comment.class)
                .setParameter("post", post)
                .setMaxResults(commentsCount)
                .getResultList();

        PostForm postForm = postMapper.toForm(post);
        postForm.setComments(new ArrayList<>(comments.size()));
        for(Comment comment : comments) {
            postForm.getComments().add(commentMapper.toForm(comment));
        }
        return postForm;
    }
}
