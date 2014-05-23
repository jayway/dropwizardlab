package com.jayway.labs.dropwizard;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

    @Valid
    @NotNull
    @JsonProperty
    private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return httpClient;
    }
}