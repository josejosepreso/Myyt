package com.myyt;

import com.myyt.yt.YoutubeSearcher;
import com.myyt.entity.Video;
import com.myyt.util.*;

import static com.myyt.util.Configuration.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import static java.util.stream.Collectors.joining;
import static java.util.Objects.nonNull;

public class App {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        if (args.length == 0) {
            CommandCaller.shellCommand(String.format("mpv %s%s", YT, NIGHTZ));
            return;
        }

        final String videoName = String.join("_", args);

        final String cachedVideoPath = VideoCache.getPathByName(videoName);

        if (nonNull(cachedVideoPath)) {
            CommandCaller.shellCommand(String.format("mpv %s", cachedVideoPath));
            return;
        }

        final String query = Arrays.stream(args)
                .collect(joining("+"))
                .replace("-a+", "")
                .replace("-f+", "");

        final List<Video> videos = YoutubeSearcher.search(query);

        if (videos.isEmpty()) {
            System.out.println(String.format("No results for search %s.", query));
            return;
        }

        int idx = 0;
        Video video = videos.get(idx);

        if (!"-a".equals(args[0])) {
            System.out.println("Select a video:");

            videos.stream()
                    .map(Video::toString)
                    .forEach(System.out::println);

            System.out.print("> ");

            try (Scanner scanner = new Scanner(System.in)) {
                idx = scanner.nextInt();
            }

            video = videos.get(idx);

            if ("-f".equals(args[0])) {
                CommandCaller.shellCommand(String.format("yt-dlp -F \"%s%s\"", YT, video.getVideoId()));
                return;
            }
        }

        CommandCaller.shellCommand(String.format("mpv --stream-record=%s%s.mkv %s%s", VIDEO_CACHE_PATH, videoName, YT, video.getVideoId()));
    }
}
