///**
// * Copyright 2020 American Well Systems
// * All rights reserved.
// *
// * It is illegal to use, reproduce or distribute
// * any part of this Intellectual Property without
// * prior written authorization from American Well.
// */
//
//package com.americanwell.autotest.soa.servicename.service;
//
//import com.americanwell.autotest.soa.servicename.BaseTest;
//import com.americanwell.autotest.soa.servicename.service.resource.HealthResource;
//import io.restassured.response.Response;
//import lombok.extern.slf4j.Slf4j;
//import org.testng.Assert;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import java.io.IOException;
//import java.util.List;
//
//import static java.net.HttpURLConnection.HTTP_OK;
//
///**
// * @author corey.zwart on 11/3/2020
// */
//@Slf4j
//public class HealthResourceTest extends BaseTest {
//
//    private static TestData testData;
//
//    public HealthResourceTest() throws IOException {
//    }
//
//    private static class TestData {
//
//        HealthResource healthResource = new HealthResource();
//        String statusUp = "UP";
//        String livenessGroup = "liveness";
//        String readinessGroup = "readiness";
//
//        //Constructor for fixture
//        private TestData() throws Exception {
//        }
//    }
//
//    @BeforeClass
//    private void testSetup() throws Exception {
//
//        testData = new TestData();
//
//    }
//
//    /**
//     * GIVEN a user invokes http services with endpoint healthcheck
//     * WHEN user sees Healthcheck Status as 200 OK
//     * THEN Status Is Up
//     *
//     * @see <a href="https://amwell.atlassian.net/browse/VMS-7">VMS Health Check is HTTP_OK and UP</a>
//     */
//    @Test(description = "VMS-7, Healthcheck is Status 200 OK When Service Is Up")
//    private void verifyStatusIsUpForVideoManagementService() {
//
//        log.info("Running " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()...");
//
//        Response resp = testData.healthResource.getHealthCheck();
//
//        log.info("Asserting that /healthcheck response status code comes back \"{}\"...", HTTP_OK);
//        Assert.assertEquals(resp.statusCode(), HTTP_OK, "Status code is NOT " + HTTP_OK + "!");
//
//        log.info("Asserting that /healthcheck status is \"{}\"!", testData.statusUp);
//        Assert.assertEquals(testData.healthResource.getStatus(), testData.statusUp, "Service status is not " + testData.statusUp + "!");
//
//    }
//
//    /**
//     * GIVEN a user invokes http services with endpoint healthcheck
//     * WHEN user sees Healthcheck Status as 200 OK
//     * THEN the groups key contains ["liveness", "readiness"]
//     *
//     * @see <a href="https://amwell.atlassian.net/browse/VMS-18">VMS Health Check (check groups list)</a>
//     */
//    @Test(description = "VMS-18, VMS health check for groups list to contain expected values")
//    private void verifyGroupsForVideoManagementService() {
//
//        log.info("Running " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()...");
//
//        testData.healthResource.getHealthCheck();
//        List list = testData.healthResource.getGroups();
//
//        log.info("Asserting that /healthcheck groups list contains \"{}\"...", testData.livenessGroup);
//        Assert.assertTrue(list.contains(testData.livenessGroup), "Groups list does not contain " + testData.livenessGroup + "!");
//
//        log.info("Asserting that /healthcheck groups list contains \"{}\"...", testData.readinessGroup);
//        Assert.assertTrue(list.contains(testData.readinessGroup), "Groups list does not contain " + testData.readinessGroup + "!");
//
//    }
//
//}
