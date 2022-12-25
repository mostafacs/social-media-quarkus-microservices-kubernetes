package org.social.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.social.form.CommentForm;
import org.social.form.PostForm;
import org.social.model.Comment;
import org.social.model.Post;

/**
 * @Author Mostafa
 * On 12/25/22
 */
@Mapper
public interface CommentMapper {

    CommentMapper mapper = Mappers.getMapper(CommentMapper.class);

    CommentForm toForm(Comment comment);

    @Mapping(target = "post", ignore = true)
    @Mapping(target = "user", ignore = true)
    Comment toEntity(Comment comment);
}
