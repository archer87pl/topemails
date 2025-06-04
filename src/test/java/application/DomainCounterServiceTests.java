package application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class DomainCounterServiceTests {

    private DomainCounterService service;

    @BeforeEach
    public void setup() {
        service = new DomainCounterService();
    }

    @Test
    public void testProcessValidEmailsCountsDomains() {
        service.processEmail("alice@gmail.com");
        service.processEmail("bob@gmail.com");
        service.processEmail("carol@yahoo.com");

        List<Map.Entry<String, Integer>> top = service.getTopDomains(2);
        Map<String, Integer> map = toMap(top);

        assertEquals(2, map.get("gmail.com"));
        assertEquals(1, map.get("yahoo.com"));
    }

    @Test
    public void testProcessInvalidEmailsAreSkipped() {
        service.processEmail("bad-email");
        service.processEmail("valid@domain.com");

        List<Map.Entry<String, Integer>> top = service.getTopDomains(1);
        assertEquals(1, top.size());
        assertEquals("domain.com", top.get(0).getKey());
        assertEquals(1, top.get(0).getValue());
    }

    @Test
    public void testTopDomainsAreOrderedDescending() {
        service.processEmail("a@one.com");
        service.processEmail("b@two.com");
        service.processEmail("c@one.com");

        List<Map.Entry<String, Integer>> top = service.getTopDomains(2);

        assertEquals("one.com", top.get(0).getKey());
        assertEquals(2, top.get(0).getValue());

        assertEquals("two.com", top.get(1).getKey());
        assertEquals(1, top.get(1).getValue());
    }

    @Test
    public void testLimitExceedsDomainCountReturnsAll() {
        service.processEmail("x@abc.com");
        List<Map.Entry<String, Integer>> top = service.getTopDomains(5);

        assertEquals(1, top.size());
        assertEquals("abc.com", top.get(0).getKey());
    }

    private Map<String, Integer> toMap(List<Map.Entry<String, Integer>> entries) {
        return entries.stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}