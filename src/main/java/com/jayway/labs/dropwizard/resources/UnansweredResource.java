package com.jayway.labs.dropwizard.resources;

import com.jayway.labs.dropwizard.core.Question;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

@Path("/unanswered")
@Produces(MediaType.APPLICATION_JSON)
public class UnansweredResource {

    public UnansweredResource() {
    }

    @GET
    public List<Question> questions() {
        return Arrays.asList(new Question("1", "dummy"));
    }
}