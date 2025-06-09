package org.kahoot.application;


import org.kahoot.domain.Email;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainCounterService {

    private final Map<String, Integer> domainCounts = new HashMap<>();

    public void processEmail(String rawInput) throws IllegalArgumentException {
        Email email = new Email(rawInput);
        String domain = email.getDomain();
        domainCounts.merge(domain, 1, Integer::sum);
    }

    public List<Map.Entry<String, Integer>> getTopDomains(int limit) {
        return domainCounts.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .toList();
    }
}
