package org.social.post.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @Author Mostafa
 * On 1/15/23
 */
@Path("/")
@Produces(MediaType.TEXT_HTML)
public class PingController {

    @GET
    public String hello() {
        System.out.println("hello .......");
        return "hello";
    }

    @Path("/ping")
    @GET
    public String ping() {
        System.out.println("Ping .......");
        return "ping";
    }
}
