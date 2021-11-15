/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.browser;

import com.boomi.connector.api.*;

import com.cloudblue.connect.test.utils.ConnectTestContext;
import com.cloudblue.connect.utils.FileUtil;

import org.junit.Test;

import org.mockito.MockedStatic;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

import static org.mockito.ArgumentMatchers.any;
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
        assertEquals(20, types.size());
    }

    @Test
    public void testExecuteOperationObjectTypes() {
        when(context.getCustomOperationType()).thenReturn("DELETE");
        List<ObjectType> types = browser.getObjectTypes().getTypes();
        assertEquals(1, types.size());
    }

    @Test
    public void testExecuteOperationObjectDefinitions() {
        String[] actions = {"GET", "PENDING", "CREATE", "DELETE", "CREATE"};
        String[] resources = {"REQUEST", "REQUEST", "PURCHASE_REQUEST", "REQUEST", "REQUEST"};
        int[] expected = {1, 1, 2, 0, 0};

        for (int count = 0; count < actions.length; count++) {
            when(context.getOperationType()).thenReturn(OperationType.EXECUTE);
            when(context.getCustomOperationType()).thenReturn(actions[count]);
            ObjectDefinitions objectDefinitions = browser.getObjectDefinitions(
                    resources[count], Arrays.asList(
                            ObjectDefinitionRole.INPUT, ObjectDefinitionRole.OUTPUT));
            assertEquals(expected[count], objectDefinitions.getDefinitions().size());
        }
    }

    @Test(expected = ConnectorException.class)
    public void testGetObjectDefinitionsFailed() {
        when(context.getCustomOperationType()).thenReturn("CREATE");

        try (
                MockedStatic<FileUtil> fileUtilMockedStatic = mockStatic(FileUtil.class)
        ) {

            fileUtilMockedStatic.when(
                    () -> FileUtil.readJsonSchema(any())
            ).thenThrow(IOException.class);

            browser.getObjectDefinitions("PURCHASE_REQUEST", Arrays.asList(
                            ObjectDefinitionRole.INPUT, ObjectDefinitionRole.OUTPUT));
        }
    }
}
