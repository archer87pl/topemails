package application;


import domain.Email;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainCounterService {

    private final Map<String, Integer> domainCounts = new HashMap<>();

    public void processEmail(String rawInput) {
        try {
            Email email = new Email(rawInput);
            String domain = email.getDomain();
            domainCounts.put(domain, domainCounts.getOrDefault(domain, 0) + 1);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid email: " + rawInput);
        }
    }

    public List<Map.Entry<String, Integer>> getTopDomains(int limit) {
        return domainCounts.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .toList();
    }
}
