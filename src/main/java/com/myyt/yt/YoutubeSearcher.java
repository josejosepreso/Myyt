package com.myyt.yt;

import static com.myyt.util.Configuration.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myyt.entity.Video;
import com.myyt.exception.NoVideosFoundException;
import com.myyt.util.FileHandler;
import com.myyt.util.HttpHandler;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

import static com.myyt.util.Configuration.YT_KEY_PATH;
import static java.lang.System.out;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public class YoutubeSearcher {
    private final FileHandler fileHandler;
    private final HttpHandler httpHandler;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String query;

    public YoutubeSearcher(String query, FileHandler fileHandler, HttpHandler httpHandler) {
        this.query = query;
        this.fileHandler = fileHandler;
        this.httpHandler = httpHandler;
    }

    public YoutubeSearcher(FileHandler fileHandler, HttpHandler httpHandler) {
        this.query = null;
        this.fileHandler = fileHandler;
        this.httpHandler = httpHandler;
    }

    public YoutubeSearcher(String query) {
        this.query = query;
        this.fileHandler = new FileHandler();
        this.httpHandler = new HttpHandler();
    }

    public void setQuery(String query) {
        this.query = query;
    }

    Optional<Video> getVideo(int idx, JsonNode videoNode) {
        final JsonNode snippetNode = videoNode.path("snippet");

        if (snippetNode.isMissingNode())
            return empty();

        final Video video = Video.builder()
            .id(idx)
            .videoId(videoNode.path("id").path("videoId").asText())
            .title(snippetNode.path("title").asText())
            .channel(snippetNode.path("channelTitle").asText())
            .date(snippetNode.path("publishTime").asText())
            .build();

        return of(video);
    }

    Video[] parseResult(String json) throws JsonProcessingException {
        final JsonNode items = this.objectMapper.readTree(json).path("items");

        final Optional<Video>[] videos = new Optional[items.size()];

        int i = 0;

        for (JsonNode node : items)
            videos[i] = this.getVideo(i++, node);

        return Arrays.stream(videos)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toArray(Video[]::new);
    }

    Video[] getVideos(String query) throws NoVideosFoundException {
        try {
            String result = this.fileHandler.getContent(YT_KEY_PATH)
                .map(key -> YT_URL + query + YT_URL_1 + key)
                .flatMap(this.httpHandler::GETRequest)
                .flatMap(this.httpHandler::getResponseBody)
                .orElseThrow(Exception::new);

            Video[] videos = this.parseResult(result);

            if (videos.length == 0)
                throw new NoVideosFoundException("No results for search " + this.query);

            return videos;
        } catch(NoVideosFoundException e) {
            throw e;
        } catch(Exception e) {
            e.printStackTrace();
            return new Video[0];
        }
    }

    public Video getResult() throws NoVideosFoundException {
        var videos = this.getVideos(this.query);

        out.println("Select a video:");

        Arrays.stream(videos)
                .map(Video::toString)
                .forEach(out::println);

        out.print("> ");

        try (Scanner scanner = new Scanner(System.in)) {
            return videos[ scanner.nextInt() ];
        }
    }

    public Video getFirstResult() throws NoVideosFoundException {
        return this.getVideos(this.query)[0];
    }
}