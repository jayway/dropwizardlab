package com.jayway.labs.dropwizard;

import com.jayway.labs.dropwizard.client.StackExchangeClient;
import com.jayway.labs.dropwizard.health.SiteHealthCheck;
import com.jayway.labs.dropwizard.resources.FullQuestionResource;
import com.jayway.labs.dropwizard.resources.UnansweredResource;
import com.sun.jersey.api.client.Client;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.client.JerseyClientBuilder;
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
        bootstrap.addBundle(new AssetsBundle("/assets/", "/assets/"));
    }

    @Override
    public void run(StackExchangeViewConfiguration configuration,
                    Environment environment) {
        final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration())
                .using(environment)
                .build("jerseyClient");

        final StackExchangeClient stackExchangeClient = new StackExchangeClient(client, configuration.getSite());

        environment.jersey().register(new UnansweredResource(stackExchangeClient));
        environment.jersey().register(new FullQuestionResource(stackExchangeClient));

        environment.healthChecks().register("site", new SiteHealthCheck());

    }

}