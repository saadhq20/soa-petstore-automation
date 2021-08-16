/**
 * Copyright 2020 American Well Systems
 * All rights reserved.
 *
 * It is illegal to use, reproduce or distribute
 * any part of this Intellectual Property without
 * prior written authorization from American Well.
 */

package com.americanwell.autotest.soa.servicename.service.entity;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * This is an example template for creating an entity (payload) for a POST/PUT request. We want a valid and dynamic
 * object each time the class is instantiated so that you may have a successful request. For example, you may omit
 * fields by setting a property to null to perform negative test cases.
 *
 * @author Corey.Zwart on 08/20/2020
 */
@Slf4j
public class ParticipantResourceEntity {

    private String conferenceVendorId;
    private String displayName;
    private String ehrId;
    private String id;
    private String participantSourceId;
    private String roomSourceId;
    private String sourceId;
    private String tenantKey;

    /**
     * Constructor
     * Contains JSON keys used for participant request
     */
    public ParticipantResourceEntity() {

        long timestamp = System.currentTimeMillis() % 100000;
        String randomString = RandomStringUtils.randomAlphabetic(6).toLowerCase().concat(String.valueOf(timestamp));

        this.conferenceVendorId = "twilio";
        this.displayName = "displayNameAutomated" + randomString;
        this.ehrId = "epic";
        this.id = "idAutomated" + randomString;
        this.participantSourceId = "participantSourceIdAutomated" + randomString;
        this.roomSourceId = "roomSourceIdAutomated" + randomString;
        this.sourceId = "sourceIdAutomated" + randomString;
        this.tenantKey = "AMWL";

    }

    /**
     * Get the conferenceVendorId
     *
     * @return String conferenceVendorId
     */
    public String getConferenceVendorId() {

        log.debug("conferenceVendorId: \"{}\"", this.conferenceVendorId);
        return this.conferenceVendorId;

    }

    /**
     * Set the conferenceVendorId
     *
     * @param conferenceVendorId String
     */
    public void setConferenceVendorId(String conferenceVendorId) {

        this.conferenceVendorId = conferenceVendorId;
        log.debug("Setting conferenceVendorId to \"{}\"", conferenceVendorId);

    }

    /**
     * Get the displayName
     *
     * @return String displayName
     */
    public String getDisplayName() {

        log.debug("displayName: \"{}\"", this.displayName);
        return this.displayName;

    }

    /**
     * Set the displayName
     *
     * @param displayName String
     */
    public void setDisplayName(String displayName) {

        log.debug("Setting displayName to \"{}\"", displayName);
        this.displayName = displayName;
    }

    /**
     * Get the ehrId
     *
     * @return String ehrId
     */
    public String getEhrId() {

        log.debug("ehrId: \"{}\"", this.ehrId);
        return this.ehrId;

    }

    /**
     * Set the ehrId
     *
     * @param ehrId String
     */
    public void setEhrId(String ehrId) {

        this.ehrId = ehrId;
        log.debug("Setting ehrId to \"{}\"", ehrId);
    }

    /**
     * Get the id
     *
     * @return String id
     */
    public String getId() {

        log.debug("id: \"{}\"", this.id);
        return this.id;

    }

    /**
     * Set the id
     *
     * @param id String
     */
    public void setId(String id) {

        log.debug("Setting id to \"{}\"", id);
        this.id = id;

    }

    /**
     * Get the participantSourceId
     *
     * @return String participantSourceId
     */
    public String getParticipantSourceId() {

        log.debug("participantSourceId: \"{}\"", this.participantSourceId);
        return this.participantSourceId;

    }

    /**
     * Set the participantSourceId
     *
     * @param participantSourceId String
     */
    public void setParticipantSourceId(String participantSourceId) {

        this.participantSourceId = participantSourceId;
        log.debug("Setting participantSourceId to \"{}\"", participantSourceId);
    }

    /**
     * Get the roomSourceId
     *
     * @return String roomSourceId
     */
    public String getRoomSourceId() {

        log.debug("roomSourceId: \"{}\"", this.roomSourceId);
        return roomSourceId;

    }

    /**
     * Set the roomSourceId
     *
     * @param roomSourceId String
     */
    public void setRoomSourceId(String roomSourceId) {

        this.roomSourceId = roomSourceId;
        log.debug("Setting roomSourceId to \"{}\"", roomSourceId);
    }

    /**
     * Get the sourceId
     *
     * @return String sourceId
     */
    public String getSourceId() {

        log.debug("sourceId: \"{}\"", this.sourceId);
        return this.sourceId;
    }

    /**
     * Set the sourceId
     *
     * @param sourceId String
     */
    public void setSourceId(String sourceId) {

        log.debug("Setting sourceId to \"{}\"", sourceId);
        this.sourceId = sourceId;
    }

    /**
     * Get the tenantKey
     *
     * @return String tenantKey
     */
    public String getTenantKey() {

        log.debug("tenantKey: \"{}\"", this.tenantKey);
        return this.tenantKey;
    }

    /**
     * Set the tenantKey
     *
     * @param tenantKey String
     */
    public void setTenantKey(String tenantKey) {

        log.debug("Setting visitId to \"{}\"", tenantKey);
        this.tenantKey = tenantKey;
    }

}
