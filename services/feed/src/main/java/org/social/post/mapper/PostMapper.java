package org.social.post.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.social.post.form.PostForm;
import org.social.model.Post;

/**
 * @Author Mostafa
 * On 12/11/22
 */
@Mapper
public interface PostMapper {

    PostMapper mapper = Mappers.getMapper(PostMapper.class);

    PostForm toForm(Post post);

    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "user", ignore = true)
    Post toEntity(PostForm post);
}
