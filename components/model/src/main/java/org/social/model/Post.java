package org.social.model;

import lombok.Getter;
import lombok.Setter;
import org.social.converter.ImageAttributeConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author Mostafa
 * On 12/4/22
 */
@Table(name = "post", indexes = {@Index(columnList = "created_on"), @Index(columnList = "updated_on")})
@Entity
@Getter @Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_body")
    private String postBody;

    @ManyToOne
    @JoinColumn(name = "user_fk")
    private User user;

    @Column(name = "`images`")
    @Convert(converter = ImageAttributeConverter.class)
    private List<String> images; // image urls

    // to keep performance
//    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
//    private List<Comment> comments;

    @Column(name = "likes_count")
    private Integer likesCount;

    @Column(name = "comments_count")
    private Integer commentsCount;

    @Column(name = "set_to_wall")
    private Boolean setToWall;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    private Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on")
    private Date updatedOn;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id.equals(post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
