package com.jayway.labs.dropwizard.core;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Question {
    private String id;
    private String title;

    public Question() {
        // Jackson deserialization
    }

    public Question(String id, String title) {
        this.id = id;
        this.title = title;
    }

    @JsonProperty
    public String getId() {
        return id;
    }

    @JsonProperty
    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (id != null ? !id.equals(question.id) : question.id != null) return false;
        if (title != null ? !title.equals(question.title) : question.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}