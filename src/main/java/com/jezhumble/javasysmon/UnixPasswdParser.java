package com.jezhumble.javasysmon;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

class UnixPasswdParser {

    public Map<String, String> parse(BufferedReader reader) {
        if (reader == null) {
            System.err.println("Error parsing password file: reader is null");
            return new HashMap<String, String>();
        }

        Map<String, String> users = new HashMap<String, String>();

        try {
            String line;
            String[] fields;

            while ((line = reader.readLine()) != null) {
                fields = line.split(":");

                if (fields.length >= 2) {
                    users.put(fields[2], fields[0]);
                }
            }

            return users;
        } catch (IOException e) {
            System.err.println("Error parsing password file: " + e.getMessage());
            return new HashMap<String, String>();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println("Error closing reader: " + e.getMessage());
            }
        }
    }

    public Map<String, String> parse() {
        try {
            final FileInputStream passwdFile = new FileInputStream("/etc/passwd");
            BufferedReader reader = new BufferedReader(new InputStreamReader(passwdFile, "UTF-8"));
            return parse(reader);
        } catch (IOException e) {
            System.err.println("Error reading password file: " + e.getMessage());
            return new HashMap<String, String>();
        }
    }
}
