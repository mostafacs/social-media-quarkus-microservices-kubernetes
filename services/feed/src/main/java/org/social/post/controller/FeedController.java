package org.social.post.controller;

import org.social.form.PostForm;
import org.social.post.service.FeedService;
import org.social.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @Author Mostafa
 * On 12/29/22
 */
@Path("/get")
public class FeedController {

    @Inject
    FeedService feedService;

    @Inject
    UserService userService;

    @GET
    public Response getFeed() throws Exception {
        List<PostForm> feedPosts = feedService.getFeed(userService.currentLoginUserId());
        return Response.ok(feedPosts).build();
    }
}
