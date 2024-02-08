package com.example.postcodecli;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PostCodeService {

    @Value("${application.baseURL}")
    private String baseURL;

    @Value("${application.accessToken}")
    private String accessToken;

    // Method to get address information with external API
    public ResponseEntity<String> getAddress(String postcode, String houseNumber) {
        RestTemplate restTemplate = new RestTemplate();

        // Construct URI for with postcode and houseNumber
        String URI = UriComponentsBuilder.fromUriString(baseURL)
                .queryParam("postcode", postcode)
                .queryParam("number", houseNumber)
                .build().toUriString();

        // Set HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");

        // Set HTTP entity
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        // Make the get request and return response
        return restTemplate.exchange(URI, HttpMethod.GET, entity, String.class);
    }
}
