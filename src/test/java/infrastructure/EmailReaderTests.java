package infrastructure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.kahoot.infrastructure.EmailReader;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmailReaderTests {

    private final InputStream originalIn = System.in;

    @AfterEach
    public void restoreSystemIn() {
        System.setIn(originalIn);
    }

    @Test
    public void testReadsMultipleEmails() throws Exception {
        String input = String.join("\n",
                "alice@example.com",
                "bob@gmail.com",
                "carol@company.org"
        );
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailReader reader = new EmailReader();
        List<String> emails = reader.readEmailsFromStdin();

        assertEquals(3, emails.size());
        assertEquals("alice@example.com", emails.get(0));
        assertEquals("bob@gmail.com", emails.get(1));
        assertEquals("carol@company.org", emails.get(2));
    }

    @Test
    public void testStopsReadingAfterFirstEmptyLine() throws Exception {
        String input = "jane@domain.com\n\njohn@domain.com\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailReader reader = new EmailReader();
        List<String> emails = reader.readEmailsFromStdin();

        // Should only contain the email before the first empty line
        assertEquals(1, emails.size());
        assertEquals("jane@domain.com", emails.get(0));
    }

    @Test
    public void testHandlesNoInput() throws Exception {
        System.setIn(new ByteArrayInputStream(new byte[0]));

        EmailReader reader = new EmailReader();
        List<String> emails = reader.readEmailsFromStdin();

        assertTrue(emails.isEmpty());
    }

    @Test
    void testMaxLineLengthExceeded() {
        // Create a string longer than 254 characters
        String longLine = "a".repeat(255) + "\n";
        System.setIn(new ByteArrayInputStream(longLine.getBytes()));

        EmailReader reader = new EmailReader();
        IOException exception = assertThrows(IOException.class, reader::readEmailsFromStdin);
        assertTrue(exception.getMessage().contains("Line too long"));
    }

    @Test
    void testMaxEmailsExceeded() {
        // 1001 valid email lines + 1 empty line to stop reading
        StringBuilder input = new StringBuilder();
        for (int i = 0; i <= 2000000; i++) {
            input.append("user").append(i).append("@example.com\n");
        }
        System.setIn(new ByteArrayInputStream(input.toString().getBytes()));

        EmailReader reader = new EmailReader();
        IOException exception = assertThrows(IOException.class, reader::readEmailsFromStdin);
        assertTrue(exception.getMessage().contains("Too many emails"));
    }
}