package com.myyt.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.*;

public class FileHandler {
    public static final Optional<String> getContent(String path) throws IOException {
        try (BufferedReader file = new BufferedReader(new FileReader(path))) {
            return of(file.lines().collect(Collectors.joining("\n")));
        } catch(Exception e) {
            return empty();
        }
    }
}
