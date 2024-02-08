package com.example.postcodecli;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class PostCodeClient implements CommandLineRunner {

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
        }
    }

    private String getAddressInfo(String prompt, Scanner scanner) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
