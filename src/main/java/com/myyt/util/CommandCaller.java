package com.myyt.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class CommandCaller {
    public static String shellCommand(String command) throws IOException {
        final Process p = (new ProcessBuilder("sh", "-c", command)).start();

        try (BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            return b.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
