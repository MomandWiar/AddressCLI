package com.example.postcodecli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Scanner;

@Component
public class PostCodeClient implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(PostCodeClient.class);

    PostCodeService postCodeService;

    public PostCodeClient (PostCodeService postCodeService) {
        this.postCodeService = postCodeService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("(type 'exit' to stop)");

        while(true) {
            // Prompt user for 'postcode' and 'houseNumber'
            String postcode = getAddressInfo("Postcode: ", scanner);
            String houseNumber = getAddressInfo("houseNumber: ", scanner);

            // Exit condition
            if ("exit".equalsIgnoreCase(postcode) || "exit".equalsIgnoreCase(houseNumber)) {
                System.exit(0);
            }

            try {
                // Call PostCodeService to get address information
                ResponseEntity<String> response = postCodeService.getAddress(postcode, houseNumber);
                System.out.println(response.getBody());
            } catch (HttpClientErrorException | HttpServerErrorException e) {
                // Error handling
                logger.error("Whoops, something went wrong. {}", e.getStatusCode());
            }
        }
    }

    // Helper method to get user input
    private String getAddressInfo(String prompt, Scanner scanner) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
