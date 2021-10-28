/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.operations;

import com.boomi.connector.api.ObjectData;

import com.cloudblue.connect.ConnectConnection;
import com.cloudblue.connect.test.utils.ConnectTestContext;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExecuteOperationTest {
    private final ConnectTestContext context = mock(ConnectTestContext.class);
    ExecuteOperation operation = new ExecuteOperation(new ConnectConnection(context));

    @Test
    public void testGetPathForGet() throws FileNotFoundException {

        when(context.getObjectTypeId()).thenReturn("REQUEST");
        when(context.getCustomOperationType()).thenReturn("GET");

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(
                classLoader.getResource("GetResourceRequest.json")).getFile());
        InputStream inputStream = new FileInputStream(file);
        ObjectData objectData = mock(ObjectData.class);
        when(objectData.getData()).thenReturn(inputStream);

        String path = operation.getPath(objectData);

        assertEquals("requests/PR-9480-2709-4408-004", path);
    }

    @Test
    public void testGetPathForUpdate() throws FileNotFoundException {

        when(context.getObjectTypeId()).thenReturn("REQUEST");
        when(context.getCustomOperationType()).thenReturn("UPDATE");

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(
                classLoader.getResource("UpdateRequest.json")).getFile());
        InputStream inputStream = new FileInputStream(file);
        ObjectData objectData = mock(ObjectData.class);
        when(objectData.getData()).thenReturn(inputStream);

        String path = operation.getPath(objectData);

        assertEquals("requests/PR-9480-2709-4408-004", path);
    }

    @Test
    public void testGetPathForCreate() {
        when(context.getObjectTypeId()).thenReturn("REQUEST");
        when(context.getCustomOperationType()).thenReturn("CREATE_PURCHASE_REQUEST");

        String path = operation.getPath(null);

        assertEquals("requests", path);
    }

    @Test
    public void testGetHeadersForCreate() {
        when(context.getObjectTypeId()).thenReturn("REQUEST");
        when(context.getCustomOperationType()).thenReturn("CREATE_PURCHASE_REQUEST");

        Iterable<Map.Entry<String, String>> headers = operation.getHeaders(null);

        int counter = 0;

        for (Map.Entry<String, String> header : headers) {
            assertEquals("Content-Type", header.getKey());
            assertEquals("application/json", header.getValue());

            counter++;
        }

        assertEquals(1, counter);
    }

    @Test
    public void testGetHeadersForGet() {
        when(context.getObjectTypeId()).thenReturn("REQUEST");
        when(context.getCustomOperationType()).thenReturn("GET");

        Iterable<Map.Entry<String, String>> headers = operation.getHeaders(null);

        int counter = 0;

        while (headers.iterator().hasNext()) {
            counter++;
        }

        assertEquals(0, counter);
    }
}
