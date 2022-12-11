package org.social.post.form;

import lombok.Getter;
import lombok.Setter;
import org.social.form.UserForm;

/**
 * @Author Mostafa
 * On 12/11/22
 */
@Getter @Setter
public class CommentForm {

    Integer id;
    UserForm user;
    String commentBody;

}
