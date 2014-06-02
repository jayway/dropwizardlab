package com.jayway.labs.dropwizard.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.labs.dropwizard.core.FullQuestion;
import com.jayway.labs.dropwizard.core.Question;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import org.eclipse.jetty.io.RuntimeIOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StackExchangeClient {
    private static final Logger logger = LoggerFactory.getLogger(StackExchangeClient.class);
    private final String site;
    private final Client client;
    private final String baseUri;

    public StackExchangeClient(Client client, String site) {
        this.client = client;
        this.site = site;
        this.baseUri ="https://api.stackexchange.com/2.2/";
    }

    public List<Question> unansweredQuestions() {
        URI uri = URI.create(baseUri + "questions/unanswered?order=desc&sort=activity&site=" + site);
        List<Question> questions = new ArrayList<Question>();
        requestQuestions(uri, (String s) -> parseQuestions(questions, s));
        return questions;
    }

    public FullQuestion getFullQuestion(Long id) {
        URI uri = URI.create(baseUri + "questions/" + id + "?filter=withbody&site=" + site);
        List<FullQuestion> questions = new ArrayList<FullQuestion>();
        requestQuestions(uri, (String s) -> parseFullQuestion(questions, s));
        if (!questions.isEmpty()) {
            return questions.iterator().next();
        }
        return null;
    }

    protected void requestQuestions(URI uri, Consumer<String> responseProcessor) {
        logger.debug("Requesting external resource {}.", uri);
        ClientResponse response = client.resource(uri).accept(MediaType.APPLICATION_JSON_TYPE).
                type(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
        if (response.getStatus() == 200) {
            responseProcessor.accept(response.getEntity(String.class));
        } else {
            logger.error("Invalid status code from request: " + response.getStatus());
            if (response.hasEntity()) {
                String stringResponse = response.getEntity(String.class);
                logger.error("Message: " + stringResponse);
            }
            throw new RuntimeIOException("Error when getting data from remote server.");
        }
    }

    protected void parseQuestions(List<Question> questions, String stringResponse) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonRootNode = mapper.readTree(stringResponse);
            JsonNode items = jsonRootNode.findPath("items");
            items.elements().forEachRemaining(node -> {
                JsonNode titleNode = node.findPath("title");
                JsonNode idNode = node.findPath("question_id");
                questions.add(new Question(idNode.toString(), titleNode.textValue()));
            });
        } catch (IOException e) {
            logger.error("Error when parsing data.", e);
            throw new RuntimeException(e);
        }
    }

    protected void parseFullQuestion(List<FullQuestion> questions, String stringResponse) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonRootNode = mapper.readTree(stringResponse);

            JsonNode items = jsonRootNode.findPath("items");

            items.elements().forEachRemaining(node -> {
                JsonNode titleNode = node.findPath("title");
                JsonNode idNode = node.findPath("question_id");
                JsonNode bodyNode = node.findPath("body");
                questions.add(new FullQuestion(idNode.toString(), titleNode.textValue(), bodyNode.textValue()));
            });

        } catch (IOException e) {
            logger.error("Error when parsing data.", e);
            throw new RuntimeException(e);
        }
    }
}
