package com.cloudblue.connect.operations;

import com.boomi.connector.api.ObjectData;
import com.cloudblue.connect.ConnectConnection;
import com.cloudblue.connect.utils.ConnectTestContext;

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
    public void testPathForGet() throws FileNotFoundException {

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
    public void testPathForCreate() {
        when(context.getObjectTypeId()).thenReturn("REQUEST");
        when(context.getCustomOperationType()).thenReturn("CREATE");

        String path = operation.getPath(null);

        assertEquals("requests", path);
    }

    @Test
    public void testGetHeadersForCreate() {
        when(context.getObjectTypeId()).thenReturn("REQUEST");
        when(context.getCustomOperationType()).thenReturn("CREATE");

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
