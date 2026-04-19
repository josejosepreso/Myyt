package com.myyt.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

import static com.myyt.util.TestConstants.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class HttpHandlerTest {
    HttpHandler httpHandler;

    @BeforeEach
    void setUp() {
        this.httpHandler = new HttpHandler();
    }

    @Test
    void shouldGetSuccessfulResponse() {
        assertTrue(this.httpHandler.GETRequest(EXAMPLE_DOMAIN_URL).isPresent());
    }

    @Test
    void shouldGetResponseBodyOnSuccessfulResponse() {
        var response = getMockHttpResponse();

        when(response.statusCode())
                .thenReturn(HTTP_OK_STATUS_CODE);

        when(response.body())
                .thenReturn(HTTP_OK_MESSAGE);

        assertTrue(this.httpHandler.getResponseBody(response).isPresent());
    }

    @Test
    void shouldNotGetResponseBodyOnFailedRequest() {
        var response = getMockHttpResponse();

        when(response.statusCode())
                .thenReturn(HTTP_BAD_REQUEST_STATUS_CODE);

        when(response.body())
                .thenReturn(EMPTY_STRING);

        assertTrue(this.httpHandler.getResponseBody(response).isEmpty());
    }

    public static HttpResponse<String> getMockHttpResponse() {
        return mock(HttpResponse.class);
    }
}