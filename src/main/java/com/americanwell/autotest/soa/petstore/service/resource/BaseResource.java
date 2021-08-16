/**
 * Copyright 2020 American Well Systems
 * All rights reserved.
 *
 * It is illegal to use, reproduce or distribute
 * any part of this Intellectual Property without
 * prior written authorization from American Well.
 */

package com.americanwell.autotest.soa.petstore.service.resource;

import com.americanwell.autotest.soa.petstore.utils.Config;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author chan.suom on 7/22/20
 */
@Slf4j
class BaseResource {

	/**
	 * Constructor
	 *
	 * @throws IOException exception
	 */
	BaseResource() throws IOException {

		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.registerParser("text/plain", Parser.JSON);
		RestAssured.baseURI = Config.getInstance().getServiceUrl();

	}

}
