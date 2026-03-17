package com.myyt.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandCaller {
    public static void shellCommand(String command) throws IOException {
        final Process p = (new ProcessBuilder("sh", "-c", command)).start();

        try (BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            b.lines().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
