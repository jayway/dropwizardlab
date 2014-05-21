package com.jayway.labs.dropwizard;

import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;

public class AppITest {


    @ClassRule
    public static final DropwizardAppRule<StackExchangeViewConfiguration> RULE =
            new DropwizardAppRule<StackExchangeViewConfiguration>(StackExchangeViewApplication.class, "stackexchangeview.yml");

}
