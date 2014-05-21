package com.jayway.labs.dropwizard.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class QuestionTest {

    @Test
    public void serializesToJSON() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        final Question question = new Question("123", "What is love?");
        assertThat("a Question can be serialized to JSON",
                mapper.writeValueAsString(question),
                is(equalTo(mapper.writeValueAsString(mapper.readValue(fixture("fixtures/question.json"), JsonNode.class)))));
    }
}
