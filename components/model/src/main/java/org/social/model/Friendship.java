package org.social.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @Author Mostafa
 * On 12/24/22
 */
@Entity
@Table(name = "friendship", indexes = {@Index(columnList = "user1, user2")})
@Getter @Setter
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user1")
    User user1;

    @ManyToOne
    @JoinColumn(name = "user2")
    User user2;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "friendship_date")
    Date friendshipDate;

}
