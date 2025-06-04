package org.kahoot;

import application.DomainCounterService;
import infrastructure.EmailReader;

import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        EmailReader reader = new EmailReader();
        DomainCounterService service = new DomainCounterService();

        for (String emailLine : reader.readEmailsFromStdin()) {
            service.processEmail(emailLine);
        }

        List<Map.Entry<String, Integer>> topDomains = service.getTopDomains(10);
        for (Map.Entry<String, Integer> entry : topDomains) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}