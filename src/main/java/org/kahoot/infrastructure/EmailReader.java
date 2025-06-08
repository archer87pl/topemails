package org.kahoot.infrastructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EmailReader {
    private static final int MAX_EMAILS = 1000000;
    private static final int MAX_LINE_LENGTH = 254;

    public List<String> readEmailsFromStdin() throws IOException {

        List<String> emails = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) {
                break; // stop reading on empty line
            }

            if (line.length() > MAX_LINE_LENGTH) {
                throw new IOException("Line too long (max " + MAX_LINE_LENGTH + " characters).");
            }

            if (emails.size() >= MAX_EMAILS) {
                throw new IOException("Too many emails (max " + MAX_EMAILS + ").");
            }

            emails.add(line);
        }

        return emails;
    }
}