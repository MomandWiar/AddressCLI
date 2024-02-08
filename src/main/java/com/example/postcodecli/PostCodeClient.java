package com.example.postcodecli;

import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Scanner;

@Component
public class PostCodeClient implements CommandLineRunner {

    PostCodeService postCodeService;

    public PostCodeClient (PostCodeService postCodeService) {
        this.postCodeService = postCodeService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("(type 'exit' to stop)");

        while(true) {
            String postcode = getAddressInfo("Postcode: ", scanner);
            String houseNumber = getAddressInfo("houseNumber: ", scanner);

            if ("exit".equalsIgnoreCase(postcode) || "exit".equalsIgnoreCase(houseNumber)) {
                System.exit(0);
            }

            try {
                ResponseEntity<String> response = postCodeService.getAddress(postcode, houseNumber);
                System.out.println(response.getBody());
            } catch (HttpClientErrorException | HttpServerErrorException e) {
                System.out.println("Something went wrong, " + e.getStatusCode());
            }
        }
    }

    private String getAddressInfo(String prompt, Scanner scanner) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
