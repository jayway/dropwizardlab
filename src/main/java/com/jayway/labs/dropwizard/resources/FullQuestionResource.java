package com.jayway.labs.dropwizard.resources;

import com.jayway.labs.dropwizard.client.StackExchangeClient;
import com.jayway.labs.dropwizard.core.FullQuestion;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/questions")
@Produces(MediaType.APPLICATION_JSON)
public class FullQuestionResource {

    private final StackExchangeClient client;

    public FullQuestionResource(StackExchangeClient client) {
        this.client = client;
    }

    @GET
    @Path("{id}")
    public FullQuestion getQuestion(@PathParam("id") LongParam id) {
        return client.getFullQuestion(id.get());
    }
}