/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.utils;

import com.boomi.util.json.JSONUtil;
import org.junit.Test;
import org.mockito.MockedStatic;

import java.io.*;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;


public class JsonUtilTest {

    @Test
    public void testGetFieldStringValue() throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(
                Objects.requireNonNull(classLoader.getResource("GetResourceRequest.json"))
                        .getFile());
        InputStream inputStream = new FileInputStream(file);
        String id = JsonUtil.getFieldStringValue(inputStream, "request_id");

        assertEquals("PR-9480-2709-4408-004", id);
    }

    @Test
    public void testGetFieldStringValueNoIdJson() throws FileNotFoundException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(
                Objects.requireNonNull(classLoader.getResource("NoIdJson.json"))
                        .getFile());
        InputStream inputStream = new FileInputStream(file);
        String id = JsonUtil.getFieldStringValue(inputStream, "id");

        assertNull(id);
    }

    @Test
    public void testGetFieldStringValueFailed() {
        try (MockedStatic<JSONUtil> jsonUtilMockedStatic = mockStatic(JSONUtil.class)) {
            jsonUtilMockedStatic.when(
                    () -> JSONUtil.newInitializedParser(any())
            ).thenThrow(IOException.class);

            String id = JsonUtil.getFieldStringValue(null, "id");

            assertNull(id);
        }

    }
}
