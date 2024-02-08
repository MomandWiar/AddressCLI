package com.example.addresscli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    private final String baseURL;
    private final HttpEntity<String> entity;
    private final RestTemplate restTemplate;

    @Autowired
    public AddressService(@Value("${application.baseURL}") String baseURL,
                          @Value("${application.accessToken}") String accessToken) {

        // Set baseURL
        this.baseURL = baseURL;

        // Initialize headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");

        // Initialize HTTP entity
        this.entity = new HttpEntity<>("parameters", headers);

        // Initialize RestTemplate
        this.restTemplate = new RestTemplate();
    }

    // Method to get address information with external API
    public ResponseEntity<String> getAddress(String postcode, String houseNumber) {
        // Construct URI with postcode and houseNumber
        String URI = UriComponentsBuilder.fromUriString(baseURL)
                .queryParam("postcode", postcode)
                .queryParam("number", houseNumber)
                .build().toUriString();

        // Log the retrieval from external API
        logger.info("Response retrieved from external API");

        // Make the get request and return response
        return restTemplate.exchange(URI, HttpMethod.GET, entity, String.class);
    }
}
