package com.jayway.labs.dropwizard;

import com.jayway.labs.dropwizard.resources.UnansweredResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class StackExchangeViewApplication extends Application<StackExchangeViewConfiguration> {
    public static void main(String[] args) throws Exception {
        new StackExchangeViewApplication().run(args);
    }

    @Override
    public String getName() {
        return "StackExchange Viewer";
    }

    @Override
    public void initialize(Bootstrap<StackExchangeViewConfiguration> bootstrap) {
    }

    @Override
    public void run(StackExchangeViewConfiguration configuration,
                    Environment environment) {
        environment.jersey().register(new UnansweredResource());
    }

}