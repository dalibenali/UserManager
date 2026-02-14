package com.example.api.utils;

import static org.junit.Assert.*;
import java.util.Map;

public class ResponseValidator {

	/**
	 * Ensure field within the JSON body is not null
	 * 
	 * @param body:  the response body converted to an object
	 * @param field: the field to check
	 * 
	 *               { "id": 1}
	 * @throws: AssertionError
	 */
	public static void assertFieldNotNull(Map<String, Object> body, String field) {
		Object value = body.get(field);
		assertNotNull("The '" + field + "' must not be null", value);
	}

	/**
	 * Ensure nested field within the JSON body is not null
	 * 
	 * @param body:  the response body converted to an object
	 * @param key:   key of nested object
	 * @param field: the field to check (ex: "firstname")
	 * 
	 * ex: { "name": { "firstname": "john", "lastname": "doe" } }
	 * 
	 * @throws: AssertionError
	 */
	public static void assertNestedFieldNotNull(Map<String, Object> body, String key, String field) {

		Object nestedObject = body.get(key); // key is the "name" field in our test case (see console)

		assertNotNull("The key '" + key + "' is null", nestedObject); // check if nested object is not null

		assertTrue("The key '" + key + "'  is not a JSON object", nestedObject instanceof Map); // check if nested object is a JSON object
		
		Map<String, Object> nestedMap = (Map<String, Object>) nestedObject; // Cast nestedObject from Object to Map<String, Object>

		assertNotNull("The '" + field + "' field in the '" + key + "' object must not be null", nestedMap.get(field)); // check if field in the nested object is not null
	
	}
}
