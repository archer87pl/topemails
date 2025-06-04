package integration;

import org.junit.jupiter.api.Test;
import org.kahoot.Main;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class MainIntegrationTests {

    @Test
    public void testIntegrationWithValidEmails() throws Exception {
        String inputEmails = String.join("\n", List.of(
                "alice@gmail.com",
                "bob@yahoo.com",
                "carol@gmail.com",
                "dave@outlook.com",
                "eve@gmail.com",
                "frank@yahoo.com",
                "grace@openai.com"
        ));

        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        ByteArrayInputStream in = new ByteArrayInputStream(inputEmails.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printOut = new PrintStream(out);

        System.setIn(in);
        System.setOut(printOut);

        // Act: Run main
        Main.main(new String[]{});

        // Restore streams
        System.setIn(originalIn);
        System.setOut(originalOut);

        // Analyze output
        List<String> outputLines = out.toString().lines().collect(Collectors.toList());

        assertFalse(outputLines.isEmpty(), "Output should not be empty");

        // Example: gmail.com should be at the top with count 3
        assertTrue(outputLines.get(0).startsWith("gmail.com 3"), "Expected gmail.com count at top");

        // Optional: Check all expected domains exist
        assertTrue(outputLines.stream().anyMatch(line -> line.startsWith("yahoo.com 2")));
        assertTrue(outputLines.stream().anyMatch(line -> line.startsWith("outlook.com 1")));
        assertTrue(outputLines.stream().anyMatch(line -> line.startsWith("openai.com 1")));
    }

    @Test
    public void testIntegrationSkipsInvalidEmails() throws Exception {
        String inputEmails = String.join("\n", List.of(
                "invalid-email",
                "jane@company.com"
        ));

        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        ByteArrayInputStream in = new ByteArrayInputStream(inputEmails.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printOut = new PrintStream(out);

        System.setIn(in);
        System.setOut(printOut);

        Main.main(new String[]{});

        System.setIn(originalIn);
        System.setOut(originalOut);

        String output = out.toString();

        // Should skip invalid-email and only count company.com
        assertTrue(output.contains("company.com 1"));
        assertFalse(output.contains("invalid-email"));
    }
}