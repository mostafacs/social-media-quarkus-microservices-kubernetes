package org.social.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @Author Mostafa
 * On 11/28/22
 */
@Entity
@Table(name = "user")
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "profile_image_url")
    String profileImageUrl;

    @Column
    String firstname;

    @Column
    String lastname;

    @Column
    String username;

    @Column
    String email;

    @Column(name = "keycloak_id")
    String keycloakId;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "joined_on")
    Date joinedOn;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "updated_on")
    Date updatedOn;

}
