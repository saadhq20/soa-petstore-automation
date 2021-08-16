/**
 * Copyright 2020 American Well Systems
 * All rights reserved.
 *
 * It is illegal to use, reproduce or distribute
 * any part of this Intellectual Property without
 * prior written authorization from American Well.
 */

package com.americanwell.autotest.soa.servicename.service;

import com.americanwell.autotest.soa.servicename.BaseTest;
import com.americanwell.autotest.soa.servicename.service.resource.VersionResource;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Update the "appName" value so that your tests will pass
 *
 * @author corey.zwart on 11/3/2020
 */
@Slf4j
public class VersionResourceTest extends BaseTest {

    private static TestData testData;

    public VersionResourceTest() throws IOException {
    }

    private static class TestData {

        VersionResource versionResource = new VersionResource();
        String appName = "Tenant Service";

        //Constructor for fixture
        private TestData() throws Exception {
        }

    }

    @BeforeClass
    private void testSetup() throws Exception {

        testData = new TestData();

    }

    /**
     * GIVEN a request to the /version endpoint
     * WHEN the server sends a response
     * THEN an HTTP_OK status is seen
     *
     * @see <a href="https://amwell.atlassian.net/browse/VMS-9">Verify /version endpoint is HTTP_OK</a>
     */
    @Test(description = "VMS-9, Verify that the /version endpoint returns HTTP_OK")
    private void verifyVersionEndpointIsUp() {

        log.info("Running " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()...");

        Response resp = testData.versionResource.getVersionResponse();

        log.info("Asserting that /version response status code comes back \"{}\"...", HTTP_OK);
        Assert.assertEquals(resp.statusCode(), HTTP_OK, "Status code is NOT " + HTTP_OK + "!");

    }

    /**
     * GIVEN a request to the /version endpoint
     * WHEN user sees an HTTP_OK server response
     * THEN The "name" displays "video-management-service"
     *
     * @see <a href="https://amwell.atlassian.net/browse/VMS-16">VMS Service: Verify /version endpoint is working (verifyNameIsCorrect() method)</a>
     */
    @Test(description = "VMS-16, Verify that the \"name\" value displays the expected string")
    private void verifyNameIsCorrect() {

        log.info("Running " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()...");

        testData.versionResource.getVersionResponse();

        log.info("Asserting that \"name\" value is equal to \"{}\"...", testData.appName);
        Assert.assertEquals(testData.versionResource.getName(), testData.appName, "Value of \"name\" is not " + testData.appName);

    }

    /**
     * GIVEN a request to the /version endpoint
     * WHEN user sees an HTTP_OK server response
     * THEN The "version" and "apiVersion" keys have associated values
     *
     * @see <a href="https://amwell.atlassian.net/browse/VMS-17">VMS Service: Verify /version endpoint is working (verifyVersionKeysHaveValues() method)</a>
     */
    @Test(description = "VMS-17, Verify that the \"version\" and \"apiVersion\" keys have associated values")
    private void verifyVersionKeysHaveValues() {

        log.info("Running " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()...");

        testData.versionResource.getVersionResponse();

        log.info("Asserting that \"version\" is not null.");
        Assert.assertNotNull(testData.versionResource.getVersion(), "\"version\" value is null in JSON response!");

        log.info("Asserting that \"apiVersion\" is not null.");
        Assert.assertNotNull(testData.versionResource.getApiVersion(), "\"apiVersion\" value is null in JSON response!");

    }

}


