package org.social.form;

import org.social.model.User;

/**
 * I used record for training only.
 * @Author Mostafa
 * On 11/28/22
 */
public record UserForm (Long id, String firstname, String lastname,
                        String username, String email,
                        String password, String profileImageUrl){

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
