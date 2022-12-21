package org.social.form;

import lombok.Getter;
import lombok.Setter;
import org.social.form.UserForm;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Mostafa
 * On 12/4/22
 */
@Getter @Setter
public class PostForm implements Serializable {

    private Long id;

    private Integer priority;

    private String postBody;

    private UserForm user;

    private List<String> images; // image urls

    private List<CommentForm> comments;

    private Integer likesCount;

    private Integer commentsCount;
}
