package com.jayway.labs.dropwizard.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FullQuestionTest {

    @Test
    public void serializesToJSON() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        final FullQuestion question = new FullQuestion("123", "What is love?", "Don't hurt me.");
        assertThat("a FullQuestion can be serialized to JSON",
                mapper.writeValueAsString(question),
                is(equalTo(mapper.writeValueAsString(mapper.readValue(fixture("fixtures/fullquestion.json"), JsonNode.class)))));
    }
}
