package com.jayway.labs.dropwizard.health;

import com.codahale.metrics.health.HealthCheck;

public class SiteHealthCheck extends HealthCheck {

    public SiteHealthCheck() {
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}