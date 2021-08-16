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
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * @author corey.zwart on 11/3/20
 */
@Slf4j
public class VersionResource extends BaseResource {

    private static final String VERSION_ENDPOINT = "/api/version";
    private Optional<String> checkNameStatus = Optional.empty();
    private Optional<String> checkVersionStatus = Optional.empty();
    private Optional<String> checkApiVersionStatus = Optional.empty();

    /**
     * Constructor
     *
     * @throws IOException exception
     */
    public VersionResource() throws IOException {
    }

    /**
     *  Request to get version status
     *
     *  Successful server response example:
     *
     * {
     *     "name": "Tenant Service",
     *     "version": "0.1-SNAPSHOT",
     *     "apiVersion": "1.0.0"
     * }
     *
     *  @return Response 200/OK
     */
    public Response getVersionResponse() {

        log.debug("Sending request for version status...");

        Response resp = given().log().all().
                when().get(VERSION_ENDPOINT);

        if (resp.statusCode() == HTTP_OK) {

            this.checkNameStatus = Optional.of(resp.path("name"));
            this.checkVersionStatus = Optional.of(resp.path("version"));
            this.checkApiVersionStatus = Optional.of(resp.path("apiVersion"));

        }

        return resp;

    }

    /**
     * Get the application name
     *
     * @return String
     */
    public String getName() {

        log.debug("Getting the name...");
        return this.checkNameStatus.orElseThrow(() -> new IllegalArgumentException("Name is empty!"));

    }

    /**
     * Get the application version
     *
     * @return String
     */
    public String getVersion(){

        log.debug("Getting the version...");
        return this.checkVersionStatus.orElseThrow(() -> new IllegalArgumentException("Version is empty!"));

    }

    /**
     * Get the application apiVersion
     *
     * @return String
     */
    public String getApiVersion(){

        log.debug("Getting the apiVersion...");
        return this.checkApiVersionStatus.orElseThrow(() -> new IllegalArgumentException("apiVersion is empty!"));

    }

}
