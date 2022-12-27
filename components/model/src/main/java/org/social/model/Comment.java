package org.social.model;

import lombok.Getter;
import lombok.Setter;
import org.social.converter.ImageAttributeConverter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * @Author Mostafa
 * On 12/4/22
 */
@Table(name = "comment")
@Entity
@Getter @Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_fk")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "post_fk")
    private Post post;

    @Column(name = "comment_text")
    private String commentText;

    @Column(name = "`images`")
    @Convert(converter = ImageAttributeConverter.class)
    private List<String> images;

}
