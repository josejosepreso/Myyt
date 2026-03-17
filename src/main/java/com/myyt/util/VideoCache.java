package com.myyt.util;

import java.io.File;
import java.util.stream.Stream;
import java.nio.file.*;
import java.io.*;

import static com.myyt.util.Configuration.*;

public class VideoCache {
    public static String getPathByName(String videoId) throws IOException {
        final File file = new File(VIDEO_CACHE_PATH);

        if (!file.exists())
            file.mkdir();

        try (Stream<Path> paths = Files.walk(Paths.get(VIDEO_CACHE_PATH))) {
            final String cachedVideoPath = String.format("%s%s.mkv", VIDEO_CACHE_PATH, videoId);

            return paths.filter(Files::isRegularFile)
                .filter(path -> cachedVideoPath.equals(path.toString()))
                .findFirst()
                .map(Path::toString)
                .orElseThrow(Exception::new);
        } catch(Exception e) {
            return null;
        }
    }
}
