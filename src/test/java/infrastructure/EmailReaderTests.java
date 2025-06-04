package infrastructure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void testSkipsEmptyLines() throws Exception {
        String input = "\n\njohn@domain.com\n\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailReader reader = new EmailReader();
        List<String> emails = reader.readEmailsFromStdin();

        assertEquals(1, emails.size());
        assertEquals("john@domain.com", emails.get(0));
    }

    @Test
    public void testHandlesNoInput() throws Exception {
        System.setIn(new ByteArrayInputStream(new byte[0]));

        EmailReader reader = new EmailReader();
        List<String> emails = reader.readEmailsFromStdin();

        assertTrue(emails.isEmpty());
    }
}