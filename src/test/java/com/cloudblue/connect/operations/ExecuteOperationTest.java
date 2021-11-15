/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.operations;

import com.boomi.connector.api.DynamicPropertyMap;
import com.boomi.connector.api.ObjectData;

import com.cloudblue.connect.ConnectConnection;
import com.cloudblue.connect.test.utils.ConnectTestContext;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExecuteOperationTest {
    private final ConnectTestContext context = mock(ConnectTestContext.class);
    ExecuteOperation operation = new ExecuteOperation(new ConnectConnection(context));

    @Test
    public void testGetPathForGet() {

        when(context.getObjectTypeId()).thenReturn("REQUEST");
        when(context.getCustomOperationType()).thenReturn("GET");

        DynamicPropertyMap propertyMap = mock(DynamicPropertyMap.class);
        when(propertyMap.getProperty("request_id", null)).thenReturn("PR-9480-2709-4408-004");

        ObjectData objectData = mock(ObjectData.class);
        when(objectData.getDynamicOperationProperties()).thenReturn(propertyMap);

        String path = operation.getPath(objectData);

        assertEquals("requests/PR-9480-2709-4408-004", path);
    }

    @Test
    public void testGetPathForUpdate() {

        when(context.getObjectTypeId()).thenReturn("REQUEST");
        when(context.getCustomOperationType()).thenReturn("UPDATE");

        DynamicPropertyMap propertyMap = mock(DynamicPropertyMap.class);
        when(propertyMap.getProperty("request_id", null)).thenReturn("PR-9480-2709-4408-004");

        ObjectData objectData = mock(ObjectData.class);
        when(objectData.getDynamicOperationProperties()).thenReturn(propertyMap);

        String path = operation.getPath(objectData);

        assertEquals("requests/PR-9480-2709-4408-004", path);
    }

    @Test
    public void testGetPathForUpload() {

        when(context.getObjectTypeId()).thenReturn("USAGE_REPORT");
        when(context.getCustomOperationType()).thenReturn("UPLOAD");

        DynamicPropertyMap propertyMap = mock(DynamicPropertyMap.class);
        when(propertyMap.getProperty("usage_report_id", null)).thenReturn("UF-2021-08-0000-0000");

        ObjectData objectData = mock(ObjectData.class);
        when(objectData.getDynamicOperationProperties()).thenReturn(propertyMap);

        String path = operation.getPath(objectData);

        assertEquals("usage/files/UF-2021-08-0000-0000/upload", path);
    }

    @Test
    public void testGetPathForUploadCollectionAction() {

        when(context.getObjectTypeId()).thenReturn("USAGE_RECONCILIATION");
        when(context.getCustomOperationType()).thenReturn("UPLOAD");

        ObjectData objectData = mock(ObjectData.class);

        String path = operation.getPath(objectData);

        assertEquals("usage/reconciliations", path);
    }

    @Test
    public void testGetPathForBulkCloseCollectionAction() {

        when(context.getObjectTypeId()).thenReturn("USAGE_RECORD");
        when(context.getCustomOperationType()).thenReturn("BULK_CLOSE");

        ObjectData objectData = mock(ObjectData.class);

        String path = operation.getPath(objectData);

        assertEquals("usage/records/close-records", path);
    }

    @Test
    public void testGetPathForCreate() {
        when(context.getObjectTypeId()).thenReturn("PURCHASE_REQUEST");
        when(context.getCustomOperationType()).thenReturn("CREATE");

        String path = operation.getPath(null);

        assertEquals("requests", path);
    }

    @Test
    public void testGetHeadersForCreate() {
        when(context.getObjectTypeId()).thenReturn("PURCHASE_REQUEST");
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

        for (Map.Entry<String, String> header : headers) {
            counter++;
        }

        assertEquals(0, counter);
    }
}
