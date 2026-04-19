package com.myyt.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.myyt.util.TestConstants.FAIL_TEST_FILE_PATH;
import static com.myyt.util.TestConstants.TEST_FILE_PATH;
import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {
    FileHandler fileHandler;

    @BeforeEach
    void setUp() {
        this.fileHandler = new FileHandler();
    }

    @Test
    void shouldGetFileContent() {
        Optional<String> fileContent = this.fileHandler.getContent(TEST_FILE_PATH);

        assertTrue(fileContent.isPresent());
        assertEquals("Hello", fileContent.get());
    }

    @Test
    void shouldNotGetNonExistingFileContent() {
        assertTrue(this.fileHandler.getContent(FAIL_TEST_FILE_PATH).isEmpty());
    }
}