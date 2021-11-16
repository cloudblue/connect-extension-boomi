/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.operations;

import com.boomi.common.apache.http.entity.RepeatableInputStreamEntity;
import com.boomi.connector.api.DynamicPropertyMap;
import com.boomi.connector.api.ObjectData;

import com.cloudblue.connect.ConnectConnection;
import com.cloudblue.connect.test.utils.ConnectTestContext;

import org.apache.http.HttpEntity;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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
    public void testGetPathForSubCollectionAction() {

        when(context.getObjectTypeId()).thenReturn("PRODUCT_ITEM");
        when(context.getCustomOperationType()).thenReturn("GET");

        DynamicPropertyMap propertyMap = mock(DynamicPropertyMap.class);
        when(propertyMap.getProperty("product_id", null)).thenReturn("PRD-000-000-000");
        when(propertyMap.getProperty("item_id", null)).thenReturn("PRD-000-000-000-0001");

        ObjectData objectData = mock(ObjectData.class);
        when(objectData.getDynamicOperationProperties()).thenReturn(propertyMap);

        String path = operation.getPath(objectData);

        assertEquals("products/PRD-000-000-000/items/PRD-000-000-000-0001", path);
    }

    @Test
    public void testGetPathForCustomAction() {

        when(context.getObjectTypeId()).thenReturn("USAGE_RECORD");
        when(context.getCustomOperationType()).thenReturn("BULK_CLOSE");

        DynamicPropertyMap propertyMap = mock(DynamicPropertyMap.class);

        ObjectData objectData = mock(ObjectData.class);
        when(objectData.getDynamicOperationProperties()).thenReturn(propertyMap);

        String path = operation.getPath(objectData);

        assertEquals("usage/records/close-records", path);
    }

    @Test
    public void testGetPathForFilters() {

        when(context.getObjectTypeId()).thenReturn("PRODUCT_ACTION_LINK");
        when(context.getCustomOperationType()).thenReturn("GET");

        DynamicPropertyMap propertyMap = mock(DynamicPropertyMap.class);
        when(propertyMap.getProperty("asset_id", null)).thenReturn("AS-000-000-000");
        when(propertyMap.getProperty("action_id", null)).thenReturn("AC-000-000-000");
        when(propertyMap.getProperty("product_id", null)).thenReturn("PRD-000-000-000");

        ObjectData objectData = mock(ObjectData.class);
        when(objectData.getDynamicOperationProperties()).thenReturn(propertyMap);

        String path = operation.getPath(objectData);

        assertEquals("products/PRD-000-000-000/actions/AC-000-000-000?asset_id=AS-000-000-000", path);
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

    @Test
    public void testGetHeadersForUpload() {
        when(context.getObjectTypeId()).thenReturn("USAGE_REPORT");
        when(context.getCustomOperationType()).thenReturn("UPLOAD");

        Iterable<Map.Entry<String, String>> headers = operation.getHeaders(null);

        int counter = 0;

        for (Map.Entry<String, String> header : headers) {
            counter++;
        }

        assertEquals(0, counter);
    }

    @Test
    public void testGetEntityEmpty() throws IOException {

        when(context.getObjectTypeId()).thenReturn("USAGE_RECORD");
        when(context.getCustomOperationType()).thenReturn("BULK_CLOSE");

        ObjectData objectData = mock(ObjectData.class);
        when(objectData.getDataSize()).thenReturn(0L);

        assertNull(operation.getEntity(objectData));
    }

    @Test
    public void testGetEntityJsonEntity() throws IOException {

        when(context.getObjectTypeId()).thenReturn("USAGE_RECORD");
        when(context.getCustomOperationType()).thenReturn("CLOSE");

        InputStream inputStream = new ByteArrayInputStream("Test".getBytes(StandardCharsets.UTF_8));

        ObjectData objectData = mock(ObjectData.class);
        when(objectData.getData()).thenReturn(inputStream);
        when(objectData.getDataSize()).thenReturn(4L);

        HttpEntity httpEntity = operation.getEntity(objectData);

        assertTrue(httpEntity instanceof RepeatableInputStreamEntity);
    }

    @Test
    public void testGetEntityFileUploadEntity() throws IOException {

        when(context.getObjectTypeId()).thenReturn("USAGE_RECONCILIATION");
        when(context.getCustomOperationType()).thenReturn("UPLOAD");

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(
                classLoader.getResource("connector-config.xml")).getFile());

        DynamicPropertyMap propertyMap = mock(DynamicPropertyMap.class);
        when(propertyMap.getProperty("recon_file", null)).thenReturn(file.getAbsolutePath());
        when(propertyMap.getProperty("upload_note", null)).thenReturn("Test Upload");

        ObjectData objectData = mock(ObjectData.class);
        when(objectData.getDynamicOperationProperties()).thenReturn(propertyMap);

        HttpEntity httpEntity = operation.getEntity(objectData);

        assertTrue(httpEntity.getContentType().getValue().startsWith("multipart/form-data"));
    }

    @Test
    public void testIsRequestBodyRequiredTrue() {
        when(context.getObjectTypeId()).thenReturn("USAGE_RECORD");
        when(context.getCustomOperationType()).thenReturn("CLOSE");

        assertTrue(operation.isRequestBodyRequired());
    }

    @Test
    public void testIsRequestBodyRequiredFalse() {
        when(context.getObjectTypeId()).thenReturn("USAGE_RECORD");
        when(context.getCustomOperationType()).thenReturn("GET");

        assertFalse(operation.isRequestBodyRequired());
    }
}
