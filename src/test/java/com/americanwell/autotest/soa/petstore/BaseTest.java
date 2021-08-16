/**
 * Copyright 2020 American Well Systems
 * All rights reserved.
 *
 * It is illegal to use, reproduce or distribute
 * any part of this Intellectual Property without
 * prior written authorization from American Well.
 */

package com.americanwell.autotest.soa.petstore;

import com.americanwell.autotest.soa.petstore.service.resource.HealthResource;
import com.americanwell.autotest.soa.petstore.utils.Config;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * @author chan.suom on 8/12/20
 */
@Slf4j
public class BaseTest {

	protected static Config config;

	public BaseTest() throws IOException {

		config = Config.getInstance();

	}
	/**
	 * Initial health check to determine if service layer can be tested
	 */
 	@BeforeSuite
	public void baseTestStart() throws Exception {

		log.info("This is the BaseTest BeforeSuite...");
		String statusUp = "UP";
		HealthResource healthResource = new HealthResource();
		Response resp = healthResource.getHealthCheck();

		log.info("Asserting that /healthcheck response status code comes back \"{}\"...", HTTP_OK);
		Assert.assertEquals(resp.statusCode(), HTTP_OK, "Status code is NOT " + HTTP_OK + ".  Skipping all tests...");

		log.info("Asserting that /healthcheck status is \"{}\"!", statusUp);
		Assert.assertEquals(healthResource.getStatus(), statusUp, "Service status is not " + statusUp + ".  Skipping all tests...");

	}

	@AfterSuite
	protected void baseTestTearDown() {

		log.info("This is the BaseTest AfterSuite...");

	}

}
