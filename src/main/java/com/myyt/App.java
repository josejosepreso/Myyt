package com.myyt;

import com.myyt.exception.NoVideosFoundException;
import com.myyt.yt.YoutubeSearcher;
import com.myyt.entity.Video;
import com.myyt.util.*;

import static com.myyt.util.Configuration.*;

import java.io.IOException;
import java.util.*;

import static java.lang.System.exit;
import static java.lang.System.out;
import static java.util.Objects.nonNull;

public class App {
    public static void main(String[] args) throws IOException, NoVideosFoundException {
        if (args.length == 0) {
            out.println(CommandCaller.shellCommand(String.format("mpv %s%s", YT, NIGHTZ)));
            exit(0);
        }

        final String videoName = String.join("_", args);

        checkCachedVideo(videoName);

        final YoutubeSearcher yt = new YoutubeSearcher(getQuery(args));

        final Video video = "-a".equals(args[0]) ? yt.getFirstResult() : yt.getResult();

        if ("-f".equals(args[0]))
            out.println(CommandCaller.shellCommand(String.format("yt-dlp -F \"%s%s\"", YT, video.getVideoId())));
        else
            out.println(CommandCaller.shellCommand(String.format("mpv --stream-record=%s%s.mkv %s%s", VIDEO_CACHE_PATH, videoName, YT, video.getVideoId())));
    }

    static void checkCachedVideo(String videoName) throws IOException {
        final String cachedVideoPath = VideoCache.getPathByName(videoName);

        if (nonNull(cachedVideoPath)) {
            out.println(CommandCaller.shellCommand(String.format("mpv %s", cachedVideoPath)));
            exit(0);
        }
    }

    static String getQuery(String[] args) {
        return String.join("+", args)
                .replace("-a+", "")
                .replace("-f+", "");
    }
}