package com.jayway.labs.dropwizard.core;


import com.fasterxml.jackson.annotation.JsonProperty;

public class FullQuestion {
    private String id;
    private String title;
    private String body;

    public FullQuestion() {
        // Jackson deserialization
    }

    public FullQuestion(String id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    @JsonProperty
    public String getId() {
        return id;
    }

    @JsonProperty
    public String getTitle() {
        return title;
    }

    @JsonProperty
    public String getBody() {
        return body;
    }
}
