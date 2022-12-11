package org.social.form;

import org.social.model.User;

/**
 * @Author Mostafa
 * On 11/28/22
 */
public record UserForm (Long id, String firstname, String lastname,
                        String username, String email,
                        String password){

    public User toEntity(User user) {
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    public User toEntity() {
        return toEntity(new User());
    }
}
