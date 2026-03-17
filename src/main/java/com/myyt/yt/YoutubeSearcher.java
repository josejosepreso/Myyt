package com.myyt.yt;

import static com.myyt.util.Configuration.*;
import static java.util.Collections.emptyList;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.function.Function;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myyt.entity.Video;
import com.myyt.util.FileHandler;
import com.myyt.util.HttpHandler;

import static com.myyt.util.Configuration.YT_KEY_PATH;;

public class YoutubeSearcher {
    private static Video getVideo(int idx, JsonNode videoNode) {
        final JsonNode snippetNode = videoNode.path("snippet");

        if (snippetNode instanceof MissingNode)
            return null;

        final String videoId = videoNode.path("id")
            .path("videoId")
            .asText();

        final String title = snippetNode.path("title").asText();

        final String channel = snippetNode.path("channelTitle").asText();

        final String date = snippetNode.path("publishTime").asText();

        return Video.builder()
            .id(idx)
            .videoId(videoId)
            .title(title)
            .channel(channel)
            .date(date)
            .build();
    }

    private static List<Video> parseResult(String json) throws Exception {
        final List<Video> videos = new ArrayList<>();

        final JsonNode items = new ObjectMapper()
            .readTree(json)
            .path("items");

        int i = 0;

        for (JsonNode node : items)
            videos.add(YoutubeSearcher.getVideo(i++, node));

        return videos.stream()
            .filter(Objects::nonNull)
            .toList();
    }

    public static List<Video> search(String query) {
        final Function<String, String> getFullYouTubeUrl = key ->
            YT_URL + query + YT_URL_1 + key;

        try {
            final String result = FileHandler.getContent(YT_KEY_PATH)
                .map(getFullYouTubeUrl)
                .flatMap(HttpHandler::GETRequest)
                .flatMap(HttpHandler::getResponseBody)
                .orElseThrow(Exception::new);

            return parseResult(result);
        } catch(Exception e) {
            return emptyList();
        }
    }
}
