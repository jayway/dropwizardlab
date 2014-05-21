package com.jayway.labs.dropwizard;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AppITest {


    @ClassRule
    public static final DropwizardAppRule<StackExchangeViewConfiguration> RULE =
            new DropwizardAppRule<StackExchangeViewConfiguration>(StackExchangeViewApplication.class, "stackexchangeview.yml");


    @Test
    public void getUnansweredQuestions() {
        Client client = new Client();

        ClientResponse response = client.resource(
                String.format("http://localhost:%d/unanswered", RULE.getLocalPort()))
                .get(ClientResponse.class);

        assertThat(response.getStatus(), is(200));
    }
}
