package com.jayway.labs.dropwizard.resources;

import com.jayway.labs.dropwizard.client.StackExchangeClient;
import com.jayway.labs.dropwizard.core.Question;
import com.sun.jersey.api.client.GenericType;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UnansweredResourceITest {

    private static final StackExchangeClient client = mock(StackExchangeClient.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new UnansweredResource(client))
            .build();

    private final List<Question> questions = Arrays.asList(new Question("123", "Are we there yet?"));

    @Before
    public void setup() {
        when(client.unansweredQuestions()).thenReturn(questions);
    }

    @Test
    public void testGetQuestions() {
        assertThat(resources.client().resource("/unanswered").get(new GenericType<List<Question>>() {
        })).containsAll(questions);
    }


}
