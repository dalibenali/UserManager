package com.example.api.cucumbersteps;

import io.cucumber.java.en.*;

import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.example.api.utils.ResponseValidator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Map;
import static org.junit.Assert.*;

public class UserSteps {

	private RestTemplate restTemplate;
	private String baseUrl = "https://fakestoreapi.com/users";
	private Map<String, Object> payload;
	private ResponseEntity<Map> response;
	private Integer userId;

	public UserSteps() {
		restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter()));
	}

	/**
	 * Custom step that create a new user with json file payload
	 * 
	 * @param jsonFileName: the json file name
	 * @throws IOException
	 **/
	@Given("I create the user from {string}")
	public void loadUserPayload(String jsonFileName) throws IOException {
		try {
			File jsonFile = new File("src/test/java/resources/data/Create/" + jsonFileName + ".json");
			String json = new String(Files.readAllBytes(jsonFile.toPath()));

			// Create Content-Type application/json header
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			// Create HttpEntity for send json payload & headers
			HttpEntity<String> entity = new HttpEntity<>(json, headers);

			// New RestTemplate instance for post http
			RestTemplate restTemplate = new RestTemplate();

			// POST request
			response = restTemplate.postForEntity(baseUrl, entity, Map.class);
		} catch (IOException e) {
			throw new IOException(e); // Catch Exception
		}

	}

	/** Custom step that get user by ID **/
	@Given("I GET the user by id {int}")
	public void getUserById(int userId) {
		this.userId = userId;
		response = restTemplate.getForEntity(baseUrl + "/" + userId, Map.class);
	}

	/** Reusable step that checks the expected status code **/
	@Then("I check the API statusCode {int}")
	public void checkApiStatusCode(int expectedStatusCode) {
		assertEquals(expectedStatusCode, response.getStatusCode().value());
	}

	/** Reusable step that checks if a field in the JSON response is not null **/
	@And("The {string} field of body response is not null")
	public void the_field_of_body_response_is_not_null(String field) {
		ResponseValidator.assertFieldNotNull(response.getBody(), field);
	}

	/**
	 * Reusable step that checks if a field in a JSON object of the response is not
	 * null
	 **/
	@And("The {string} object of body response contains the not null {string} field")
	public void the_field_of_body_response_is_not_null(String key, String field) {
		ResponseValidator.assertNestedFieldNotNull(response.getBody(), key, field);
		ResponseValidator.assertNestedFieldNotNull(response.getBody(), key, field);
	}

}
