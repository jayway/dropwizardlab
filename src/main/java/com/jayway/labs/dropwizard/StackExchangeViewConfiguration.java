package com.jayway.labs.dropwizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class StackExchangeViewConfiguration extends Configuration {
    @NotEmpty
    private String site;

    @JsonProperty
    public String getSite() {
        return site;
    }

    @JsonProperty
    public void setSite(String site) {
        this.site = site;
    }
}