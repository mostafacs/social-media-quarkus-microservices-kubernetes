package org.social.form;

import lombok.Getter;
import lombok.Setter;
import org.social.model.User;

/**
 * @Author Mostafa
 * On 11/28/22
 */
@Getter @Setter
public class UserForm {

    Long id;
    String firstname;
    String lastname;
    String username;
    String email;
    String password;
    String profileImageUrl;

    public static UserForm fromEntity(User user) {
        UserForm form = new UserForm();
        form.id = user.getId();
        form.firstname = user.getFirstname();
        form.lastname = user.getLastname();
        form.username = user.getUsername();
        form.email = user.getEmail();
        form.profileImageUrl = user.getProfileImageUrl();
        return form;
    }

    public User toEntity(User user) {
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setUsername(username);
        user.setEmail(email);
        user.setProfileImageUrl(profileImageUrl);
        return user;
    }

    public User toEntity() {
        return toEntity(new User());
    }
}
