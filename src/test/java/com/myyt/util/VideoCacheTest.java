package com.myyt.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

import static com.myyt.util.TestConstants.FAIL_TEST_FILE_PATH;

public class VideoCacheTest {
    @Test
    void shouldNotGetPathOnNonExistingCachedVideo() {
        assertNull(VideoCache.getPathByName(FAIL_TEST_FILE_PATH));
    }
}