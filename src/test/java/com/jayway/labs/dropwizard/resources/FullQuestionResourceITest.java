package com.jayway.labs.dropwizard.resources;

import com.jayway.labs.dropwizard.client.StackExchangeClient;
import com.jayway.labs.dropwizard.core.FullQuestion;
import com.sun.jersey.api.client.ClientResponse;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FullQuestionResourceITest {

    private static final StackExchangeClient client = mock(StackExchangeClient.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new FullQuestionResource(client))
            .build();

    private final FullQuestion fullQuestion = new FullQuestion("123", "Are we there yet?", "I need to pee.");

    @Before
    public void setup() {
        when(client.getFullQuestion(any())).thenReturn(fullQuestion);
    }

    @Test
    public void testGetQuestions() {
        assertThat(resources.client().resource("/questions/123").get(FullQuestion.class).equals(fullQuestion));
    }

    @Test
    public void getsWithInvalidLongGivesMessage() {
        assertThat(resources.client().resource("/questions/abc").get(ClientResponse.class).getEntity(String.class)).contains("\"abc\" is not a number.");
    }

}
