package org.kahoot;

import org.kahoot.application.DomainCounterService;
import org.kahoot.infrastructure.EmailReader;

import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        EmailReader reader = new EmailReader();
        DomainCounterService service = new DomainCounterService();

        for (String emailLine : reader.readEmailsFromStdin()) {
            try {
                service.processEmail(emailLine);
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid email: " + emailLine);
            }
        }

        List<Map.Entry<String, Integer>> topDomains = service.getTopDomains(10);
        for (Map.Entry<String, Integer> entry : topDomains) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}