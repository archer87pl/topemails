package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmailTests {

    @Test
    public void testValidEmailIsCreated() {
        Email email = new Email("user@example.com");
        assertEquals("user@example.com", email.value());
    }

    @Test
    public void testEmailIsLowercased() {
        Email email = new Email("User@Example.COM");
        assertEquals("user@example.com", email.value());
    }

    @Test
    public void testInvalidEmailThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Email("invalid-email");
        });
    }

    @Test
    public void testGetDomain() {
        Email email = new Email("person@mydomain.org");
        assertEquals("mydomain.org", email.getDomain());
    }

    @Test
    public void testEquality() {
        Email email1 = new Email("test@Example.com");
        Email email2 = new Email("test@example.com");
        assertEquals(email1, email2);
    }

    @Test
    public void testHashCodeConsistency() {
        Email email1 = new Email("abc@xyz.com");
        Email email2 = new Email("ABC@XYZ.COM");
        assertEquals(email1.hashCode(), email2.hashCode());
    }
}