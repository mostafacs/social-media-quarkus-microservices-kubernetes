package org.social.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
 * @author Mostafa
 */
@Entity
@Table(name = "friend_request", indexes = {@Index(unique = true, columnList = "from_user_fk, to_user_fk"), @Index(columnList = "status"), @Index(columnList = "request_date")})
@Getter @Setter
public class FriendRequest {

    public enum FriendRequestStatus {pending, canceled, confirmed};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_user_fk")
    User from;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_user_fk")
    User to;

    @Enumerated(EnumType.STRING)
    FriendRequestStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "request_date")
    Date requestDate;

}
