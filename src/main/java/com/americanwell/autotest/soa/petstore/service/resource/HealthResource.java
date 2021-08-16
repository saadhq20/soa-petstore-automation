/**
 * Copyright 2020 American Well Systems
 * All rights reserved.
 *
 * It is illegal to use, reproduce or distribute
 * any part of this Intellectual Property without
 * prior written authorization from American Well.
 */

package com.americanwell.autotest.soa.servicename.service.resource;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * @author corey.zwart on 10/30/20
 */
@Slf4j
public class HealthResource extends BaseResource {

    private static final String HEALTH_CHECK_ENDPOINT = "/actuator/health";
    private Optional<String> healthCheckStatus = Optional.empty();
    private Optional<List<String>> groups = Optional.empty();

    public HealthResource() throws IOException {
    }

    /**
     * Request to get health status of tenant service
     * Success server response example:
     *
     * {
     *     "status": "UP",
     *     "groups": [
     *         "liveness",
     *         "readiness"
     *     ]
     * }
     *
     * @return Response 200/OK
     */
    public Response getHealthCheck() {

        log.debug("Sending request for service health status...");

        Response resp = given().log().all().
                when().get(HEALTH_CHECK_ENDPOINT);

        if (resp.statusCode() == HTTP_OK) {

            this.healthCheckStatus = Optional.of(resp.path("status"));
            this.groups = Optional.of(resp.path("groups"));

        }

        return resp;

    }

    /**
     * Get the health status of the tenant service
     *
     * @return String
     */
    public String getStatus() {

        log.debug("Getting the health status...");
        return this.healthCheckStatus.orElseThrow(() -> new IllegalArgumentException("Health status is NOT set!"));

    }

    /**
     * Get the groups list of the tenant service
     *
     * @return List of String
     */
    public List<String> getGroups() {

        log.debug("Getting the groups list...");
        return this.groups.orElseThrow(() -> new IllegalArgumentException("Groups list is empty!"));

    }

}
