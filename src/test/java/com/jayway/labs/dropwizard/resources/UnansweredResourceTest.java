package com.jayway.labs.dropwizard.resources;

import com.jayway.labs.dropwizard.client.StackExchangeClient;
import com.jayway.labs.dropwizard.core.Question;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UnansweredResourceTest {
    private final StackExchangeClient client = mock(StackExchangeClient.class);
    private final UnansweredResource resource = new UnansweredResource(client);


    @Test
    public void getsReturnsQuestions() {
        final List<Question> questions = mock(List.class);
        when(client.unansweredQuestions()).thenReturn(questions);

        final List<Question> list = resource.questions();
        assertThat(list, is(questions));
    }


}
