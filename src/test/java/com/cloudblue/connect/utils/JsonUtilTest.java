package com.cloudblue.connect.utils;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class JsonUtilTest {

    @Test
    public void testGetFieldStringValue() throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("GetResourceRequest.json").getFile());
        InputStream inputStream = new FileInputStream(file);
        String id = JsonUtil.getFieldStringValue(inputStream, "id");

        assertEquals("PR-9480-2709-4408-004", id);
    }

    @Test
    public void testNoIdJson() throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("NoIdJson.json").getFile());
        InputStream inputStream = new FileInputStream(file);
        String id = JsonUtil.getFieldStringValue(inputStream, "id");

        assertNull(id);
    }
}
