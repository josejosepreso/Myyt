package com.myyt.util;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class CommandCallerTest {
    @Test
    public void shouldReturnCommandOutput() throws IOException {
        Assert.assertTrue(CommandCaller.shellCommand("echo hola").contains("hola"));
    }
}