package com.cloudblue.connect.operations;

import com.boomi.connector.api.ObjectData;
import com.cloudblue.connect.ConnectConnection;
import com.cloudblue.connect.utils.ConnectTestContext;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExecuteOperationTest {
    private final ConnectTestContext context = mock(ConnectTestContext.class);

    @Test
    public void testPathForGet() throws FileNotFoundException {
        ExecuteOperation operation = new ExecuteOperation(new ConnectConnection(context));
        when(context.getObjectTypeId()).thenReturn("REQUEST");
        when(context.getCustomOperationType()).thenReturn("GET");

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("GetResourceRequest.json").getFile());
        InputStream inputStream = new FileInputStream(file);
        ObjectData objectData = mock(ObjectData.class);
        when(objectData.getData()).thenReturn(inputStream);

        String path = operation.getPath(objectData);

        assertEquals("requests/PR-9480-2709-4408-004", path);
    }

    @Test
    public void testPathForCreate() throws FileNotFoundException {
        ExecuteOperation operation = new ExecuteOperation(new ConnectConnection(context));
        when(context.getObjectTypeId()).thenReturn("REQUEST");
        when(context.getCustomOperationType()).thenReturn("CREATE");

        String path = operation.getPath(null);

        assertEquals("requests", path);
    }
}
