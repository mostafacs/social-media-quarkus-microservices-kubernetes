package org.social.post.controller;

import org.social.post.form.PostForm;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @Author Mostafa
 * On 12/4/22
 */
@Path("/post")
public class AddPostController {

    @POST
    public Response addPost(PostForm post) {

        return Response.ok().build();
    }
}
