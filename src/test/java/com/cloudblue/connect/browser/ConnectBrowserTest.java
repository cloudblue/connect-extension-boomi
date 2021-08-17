package com.cloudblue.connect.browser;

import com.boomi.connector.api.*;

import com.cloudblue.connect.test.utils.ConnectTestContext;
import com.cloudblue.connect.utils.SchemaUtil;

import org.junit.Test;

import org.mockito.MockedStatic;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class ConnectBrowserTest {
    private final ConnectTestContext context = mock(ConnectTestContext.class);
    private final ConnectBrowser browser = new ConnectBrowser(context);

    @Test
    public void testGetOperationObjectTypes() {
        when(context.getCustomOperationType()).thenReturn("GET");
        List<ObjectType> types = browser.getObjectTypes().getTypes();
        assertEquals(6, types.size());
    }

    @Test
    public void testExecuteOperationObjectTypes() {
        when(context.getCustomOperationType()).thenReturn("DELETE");
        List<ObjectType> types = browser.getObjectTypes().getTypes();
        assertEquals(0, types.size());
    }

    @Test
    public void testExecuteOperationObjectDefinitionsForGet() {
        when(context.getOperationType()).thenReturn(OperationType.EXECUTE);
        when(context.getCustomOperationType()).thenReturn("GET");
        ObjectDefinitions objectDefinitions = browser.getObjectDefinitions(
                "REQUEST", Arrays.asList(
                        ObjectDefinitionRole.INPUT, ObjectDefinitionRole.OUTPUT));
        assertEquals(2, objectDefinitions.getDefinitions().size());
    }

    @Test
    public void testExecuteOperationObjectDefinitionsForCreate() {
        when(context.getOperationType()).thenReturn(OperationType.EXECUTE);
        when(context.getCustomOperationType()).thenReturn("CREATE");
        ObjectDefinitions objectDefinitions = browser.getObjectDefinitions(
                "REQUEST", Arrays.asList(
                        ObjectDefinitionRole.INPUT, ObjectDefinitionRole.OUTPUT));
        assertEquals(2, objectDefinitions.getDefinitions().size());
    }

    @Test
    public void testUpdateOperationObjectDefinitionsForCreate() {
        when(context.getOperationType()).thenReturn(OperationType.UPDATE);
        when(context.getCustomOperationType()).thenReturn("DELETE");
        ObjectDefinitions objectDefinitions = browser.getObjectDefinitions(
                "REQUEST", Arrays.asList(
                        ObjectDefinitionRole.INPUT, ObjectDefinitionRole.OUTPUT));
        assertEquals(0, objectDefinitions.getDefinitions().size());
    }

    @Test
    public void testGetObjectDefinitionsInputSchemaNotPresent() {
        when(context.getCustomOperationType()).thenReturn("CREATE");

        OperationSchemaInfo schemaInfo = new OperationSchemaInfo();

        try (MockedStatic<SchemaUtil> schemaUtilMockedStatic = mockStatic(SchemaUtil.class)) {
            schemaUtilMockedStatic.when(
                    () -> SchemaUtil.getSchemaInfo("REQUEST", "CREATE")
            ).thenReturn(schemaInfo);

            ObjectDefinitions objectDefinitions = browser.getObjectDefinitions(
                    "REQUEST", Arrays.asList(
                            ObjectDefinitionRole.INPUT, ObjectDefinitionRole.OUTPUT));
            assertEquals(0, objectDefinitions.getDefinitions().size());
        }
    }

    @Test(expected = ConnectorException.class)
    public void testGetObjectDefinitionsSchemaFileLocked() throws IOException {
        when(context.getCustomOperationType()).thenReturn("CREATE");

        OperationSchemaInfo schemaInfo = new OperationSchemaInfo()
                .input("Asset-schema.json");

        final RandomAccessFile raFile = new RandomAccessFile(
                Objects.requireNonNull(this.getClass()
                        .getResource("/schemas/Asset-schema.json"))
                        .getFile(),
                "rw");
        FileLock fileLock = raFile.getChannel().lock();

        try (MockedStatic<SchemaUtil> schemaUtilMockedStatic = mockStatic(SchemaUtil.class)) {
            schemaUtilMockedStatic.when(
                    () -> SchemaUtil.getSchemaInfo("REQUEST", "CREATE")
            ).thenReturn(schemaInfo);

            browser.getObjectDefinitions("REQUEST", Arrays.asList(
                            ObjectDefinitionRole.INPUT, ObjectDefinitionRole.OUTPUT));
        }finally {
            fileLock.release();
            fileLock.close();
            raFile.getChannel().close();
        }
    }
}
