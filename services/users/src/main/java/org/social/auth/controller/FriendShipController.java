package org.social.auth.controller;

import org.social.auth.service.UserManagerService;
import org.social.constants.SecurityConstants;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * TODO implement send friend request and confirm or cancel friend request
 * @author Mostafa
 */

@Path("/friend")
public class FriendShipController {

    @Inject
    UserManagerService userManagerService;


    @RolesAllowed(SecurityConstants.ROLE_USER)
    @POST
    @Path("/send/{to}")
    public Response newFriendRequest(@PathParam("to") Long to) {
        Long friendRequestId = userManagerService.sendFriendRequest(to);
        return Response.ok(friendRequestId).build();
    }

    @RolesAllowed(SecurityConstants.ROLE_USER)
    @PUT
    @Path("/confirm/{friendRequestId}")
    public Response confirmFriendRequest(@PathParam("friendRequestId") Long friendRequestId) {
        userManagerService.confirmFriendRequest(friendRequestId);
        return Response.ok().build();
    }

    @RolesAllowed(SecurityConstants.ROLE_USER)
    @PUT
    @Path("/cancel/{friendRequestId}")
    public Response cancelFriendRequest(@PathParam("friendRequestId") Long friendRequestId) {
        userManagerService.cancelFriendRequest(friendRequestId);
        return Response.ok().build();
    }
}
