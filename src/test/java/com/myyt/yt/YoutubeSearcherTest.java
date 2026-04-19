package com.myyt.yt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myyt.entity.Video;
import com.myyt.exception.NoVideosFoundException;
import com.myyt.util.FileHandler;
import com.myyt.util.HttpHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.myyt.util.HttpHandlerTest.getMockHttpResponse;
import static com.myyt.util.TestConstants.*;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static java.util.Optional.of;

@ExtendWith(MockitoExtension.class)
public class YoutubeSearcherTest {

    @Mock FileHandler fileHandler;
    @Mock HttpHandler httpHandler;

    YoutubeSearcher youtubeSearcher;

    ObjectMapper mapper;

    @BeforeEach
    void init() {
        this.youtubeSearcher = new YoutubeSearcher(this.fileHandler, this.httpHandler);
        this.mapper = new ObjectMapper();
    }

    @Test
    void testShouldMapVideoCorrectly() throws Exception {
        Video[] videos = this.youtubeSearcher.parseResult(MOCK_JSON_RESPONSE);

        assertEquals(2, videos.length);

        Video video = videos[0];

        assertAll(
                () -> assertNotNull(video),
                () -> assertEquals("bladee - Nike Just Do It (เกาะเสม็ด)", video.getTitle()),
                () -> assertEquals("7WQDckhcF9k", video.getVideoId()),
                () -> assertEquals("drain gang", video.getChannel())
        );
    }

    @Test
    void shouldReturnNullWhenSnippetMissing() throws JsonProcessingException {
        JsonNode node = mapper.readTree(MOCK_JSON);

        Optional<Video> video = this.youtubeSearcher.getVideo(0, node);

        assertTrue(video.isEmpty());
    }

    @Test
    void shouldReturnVideosWhenEverythingWorks() throws NoVideosFoundException {
        when(this.fileHandler.getContent(any()))
                .thenReturn(of(API_KEY));

        when(this.httpHandler.GETRequest(any()))
                .thenReturn(of(getMockHttpResponse()));

        when(this.httpHandler.getResponseBody(any()))
                .thenReturn(of(MOCK_JSON_RESPONSE));

        Video[] videos = this.youtubeSearcher.getVideos(ANYTHING_SEARCH_QUERY);

        assertEquals(2, videos.length);
        assertEquals(1, videos[1].getId());
    }

    @Test
    void shouldReturnEmptyArrayWhenKeyMissing() throws NoVideosFoundException {
        when(this.fileHandler.getContent(any()))
                .thenReturn(empty());

        Video[] videos = this.youtubeSearcher.getVideos(ANYTHING_SEARCH_QUERY);

        assertEquals(0, videos.length);
    }

    @Test
    void shouldReturnEmptyArrayWhenHttpFails() throws NoVideosFoundException {
        when(this.fileHandler.getContent(any()))
                .thenReturn(of(API_KEY));

        when(this.httpHandler.GETRequest(any()))
                .thenReturn(empty());

        Video[] videos = this.youtubeSearcher.getVideos(ANYTHING_SEARCH_QUERY);

        assertEquals(0, videos.length);
    }

    @Test
    void shouldThrowExceptionWhenNoVideosFound() {
        when(this.fileHandler.getContent(any()))
                .thenReturn(of(API_KEY));

        when(this.httpHandler.GETRequest(any()))
                .thenReturn(of(getMockHttpResponse()));

        when(this.httpHandler.getResponseBody(any()))
                .thenReturn(of(EMPTY_STRING));

        this.youtubeSearcher.setQuery(MOCK_NO_RESULTS_QUERY);

        assertThrows(NoVideosFoundException.class, () -> this.youtubeSearcher.getFirstResult());
    }

    @Test
    void shouldReturnEmptyArrayWhenJsonResponseIsInvalid() throws NoVideosFoundException {
        when(this.fileHandler.getContent(any()))
                .thenReturn(of(API_KEY));

        when(this.httpHandler.GETRequest(any()))
                .thenReturn(of(getMockHttpResponse()));

        when(this.httpHandler.getResponseBody(any()))
                .thenReturn(of("{\"invalid\":\"json}"));

        Video[] videos = this.youtubeSearcher.getVideos(ANYTHING_SEARCH_QUERY);

        assertEquals(0, videos.length);
    }
}