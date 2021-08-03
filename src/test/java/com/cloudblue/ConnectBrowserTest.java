package com.cloudblue;

import com.boomi.connector.api.ObjectDefinitions;
import com.boomi.connector.api.ObjectType;
import com.boomi.connector.api.OperationType;

import com.cloudblue.connect.browser.ConnectBrowser;
import com.cloudblue.util.ConnectTestContext;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConnectBrowserTest {
    private final ConnectTestContext context = mock(ConnectTestContext.class);
    private final ConnectBrowser browser = new ConnectBrowser(context);

    @Test
    public void testGetOperationObjectTypes() {
        when(context.getOperationType()).thenReturn(OperationType.GET);
        List<ObjectType> types = browser.getObjectTypes().getTypes();
        assertEquals(6, types.size());
    }

    @Test
    public void testExecuteOperationObjectTypes() {
        when(context.getOperationType()).thenReturn(OperationType.EXECUTE);
        List<ObjectType> types = browser.getObjectTypes().getTypes();
        assertEquals(0, types.size());
    }

    @Test
    public void testGetOperationObjectDefinitions() {
        when(context.getOperationType()).thenReturn(OperationType.GET);
        ObjectDefinitions objectDefinitions = browser.getObjectDefinitions("REQUEST", null);
        assertEquals(1, objectDefinitions.getDefinitions().size());
    }

    @Test
    public void testExecuteOperationObjectDefinitions() {
        when(context.getOperationType()).thenReturn(OperationType.EXECUTE);
        ObjectDefinitions objectDefinitions = browser.getObjectDefinitions("REQUEST", null);
        assertEquals(0, objectDefinitions.getDefinitions().size());
    }
}
