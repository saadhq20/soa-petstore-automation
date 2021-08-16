/**
 * Copyright 2020 American Well Systems
 * All rights reserved.
 *
 * It is illegal to use, reproduce or distribute
 * any part of this Intellectual Property without
 * prior written authorization from American Well.
 */

package com.americanwell.autotest.soa.petstore.service;

import com.americanwell.autotest.soa.petstore.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chan.suom on 7/22/20
 */
@Slf4j
public class RequestHeaders {

	private HashMap<String, String> requestHeadersMap = new HashMap<>();
	private TokenUtil tokenUtil;
	private boolean isPatient;

	/**
	 * Constructor
	 *
	 * @param roomSourceId String
	 * @param participantSourceId String
	 * @param isPatient boolean true : patient; false : provider
	 * @throws IOException exception
	 */
	public RequestHeaders(String roomSourceId, String participantSourceId, boolean isPatient) throws IOException {

		this.isPatient = isPatient;
		this.tokenUtil = new TokenUtil(roomSourceId, participantSourceId);
		setDefaultRequestHeaders();

	}

	/**
	 * Set the persistent request headers map to the default for the class instance
	 */
	private void setDefaultRequestHeaders() throws IOException {

		log.debug("Setting the default request headers map...");

		String token = (isPatient) ? tokenUtil.getPatientAuthorizationToken() : tokenUtil.getProviderAuthorizationToken();

		this.requestHeadersMap = new HashMap<>();
		addRequestHeader(HttpHeaders.AUTHORIZATION, token);

		log.debug("default requestHeadersMap: {}", this.requestHeadersMap);

	}

	/**
	 * Get the authenticated request headers map
	 *
	 * @return HashMap String, String headers
	 */
	public Map<String, String> getRequestHeaders() {

		log.debug("Getting the authenticated request headers map: {}...", this.requestHeadersMap);

		return this.requestHeadersMap;

	}

	/**
	 * Add a request header to the instance map
	 *
	 * @param headerKey String
	 * @param headerValue String
	 */
	public void addRequestHeader(String headerKey, String headerValue) {

		log.debug("Adding request header \"{}: {}\"...", headerKey, headerValue);

		this.requestHeadersMap.put(headerKey, headerValue);

	}

	/**
	 * Get the Authorization header value
	 *
	 * @return String auth header
	 */
	public String getAuthorizationHeader() {

		log.debug("Getting the request \"{}\" header...", HttpHeaders.AUTHORIZATION);

		log.debug("{}: {}", HttpHeaders.AUTHORIZATION, this.requestHeadersMap.get(HttpHeaders.AUTHORIZATION));

		return this.requestHeadersMap.get(HttpHeaders.AUTHORIZATION);

	}

	/**
	 * Set the Authorization header value
	 *
	 * @param authHeader String Authorization header
	 */
	public void setAuthorizationHeader(String authHeader) {

		log.debug("Setting the request \"{}\" header to \"{}\"...", HttpHeaders.AUTHORIZATION, authHeader);

		this.requestHeadersMap.replace(HttpHeaders.AUTHORIZATION, authHeader);

	}

	/**
	 * Remove the Authorization header
	 */
	public void removeAuthorizationHeader() {

		log.debug("Removing the request \"{}\" header...", HttpHeaders.AUTHORIZATION);

		this.requestHeadersMap.remove(HttpHeaders.AUTHORIZATION);

	}

}
