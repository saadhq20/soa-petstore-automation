/**
 * Copyright 2020 American Well Systems
 * All rights reserved.
 *
 * It is illegal to use, reproduce or distribute
 * any part of this Intellectual Property without
 * prior written authorization from American Well.
 */

package com.americanwell.autotest.soa.petstore.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * @author chan.suom on 7/14/20
 */
@Slf4j
public class Helper {

	private Helper() {

	}

	/**
	 * Transform an object to a JsonPath object
	 *
	 * @param object Object
	 * @return JsonPath
	 */
	public static JsonPath convertObjectToJsonPath(Object object) {

		String jsonString = convertObjectToJsonString(object);

		log.debug("Converting object to JsonPath object...");

		return new JsonPath(jsonString);

	}

	/**
	 * Convert object to Json String
	 *
	 * @param object Object an entity, e.g. ConsumerEnrollmentEntity
	 * @return String
	 */
	public static String convertObjectToJsonString(Object object) {

		log.debug("Converting object to JSON String...");

		Gson gson = new Gson();
		String json = gson.toJson(object);

		log.debug("json: {}", json);

		return json;

	}

	/**
	 * Get a map from passing in an entity; with this map, we
	 * can add/remove the object attributes for negative testing.
	 *
	 * @param entity Object an entity, e.g. ConsumerEnrollmentEntity
	 * @return Map
	 */
	public static Map<String, Object> mapEntity(Object entity) {

		log.debug("Converting object to map...");

		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.convertValue(entity, Map.class);

	}

	/**
	 * Create date in yyyy-MM-dd format for today +/- years
	 *
	 * @param years int
	 * @return String yyyy-MM-dd format
	 */
	public static String createDateStringPlusYears(int years) {

		log.debug("Creating a date string \"{}\" years from today...", years);

		LocalDateTime dateTime = LocalDateTime.now().plusYears(years);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);

		String date = formatter.format(dateTime);

		log.debug("Date string: {}", date);

		return formatter.format(dateTime);

	}

	/**
	 * Get Test Class Name
	 *
	 * @return String representing name of test calling method
	 * @author Chris Roberts
	 */
	public static String getTestClassName() {

		log.debug("Reading test class name from stack trace...");

		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		Optional<String> testClassName = Optional.empty();

		for (int i = 0; i < stackTraceElements.length; i++) {

			String className = stackTraceElements[i].getClassName();

			if (className.contains("Test") && !className.contains("$")) {

				testClassName = Optional.of(className.substring(className.lastIndexOf(".") + 1));
				break;

			}

		}

		return testClassName.orElseThrow(() -> new IllegalStateException("Unable to find test class name from stack trace."));

	}

}
