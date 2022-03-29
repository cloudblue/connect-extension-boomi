/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.operations;

import com.boomi.connector.api.ObjectDefinitionRole;
import com.boomi.connector.api.OperationType;
import com.boomi.connector.testutil.ConnectorTester;
import com.boomi.connector.testutil.SimpleOperationResult;
import com.boomi.connector.testutil.SimpleTrackedData;

import com.cloudblue.connect.ConnectConnection;
import com.cloudblue.connect.ConnectConnector;
import com.cloudblue.connect.browser.metadata.Key;
import com.cloudblue.connect.browser.metadata.MetadataUtil;
import com.cloudblue.connect.common.UsageFileGenerator;
import com.cloudblue.connect.utils.FileUtil;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsageOperationIntegrationTest extends AuthenticatedIntegrationTest {
    private static String usageReportId = null;

    private InputStream getPayload(String fileName) {
        return getClass().getClassLoader().getResourceAsStream(fileName);
    }

    @Test
    public void test1GetOperation() throws IOException {
        ConnectConnector connector = new ConnectConnector();
        ConnectorTester tester = new ConnectorTester(connector);

        ConnectConnectorTestContext testContext = new ConnectConnectorTestContext();

        testContext.addOperationProperty(ConnectConnection.HTTP_METHOD_PROPERTY, "GET");

        testContext.addCookie(
                ObjectDefinitionRole.OUTPUT,
                FileUtil.readJsonSchema(MetadataUtil.FULFILLMENT_REQUEST_SCHEMA));

        testContext.setOperationType(OperationType.EXECUTE);
        testContext.setObjectTypeId("REQUEST");
        testContext.setOperationCustomType("GET");

        addVendorConnProperties(testContext);

        tester.setOperationContext(testContext);

        SimpleTrackedData data = new SimpleTrackedData(0, new ByteArrayInputStream("{}".getBytes()));
        data.getDynamicOperationProperties().addProperty(Key.REQUEST_ID.getField(), "PR-4761-9205-6537-001");

        List<SimpleTrackedData> inputs = new ArrayList<>();
        inputs.add(data);

        List<SimpleOperationResult> actual = tester.executeExecuteOperationWithTrackedData(inputs);

        assertEquals("200",actual.get(0).getStatusCode());
        assertEquals(1, actual.get(0).getPayloads().size());
    }

    @Test
    public void test2CreateOperation() throws IOException {
        ConnectConnector connector = new ConnectConnector();
        ConnectorTester tester = new ConnectorTester(connector);

        ConnectConnectorTestContext testContext = new ConnectConnectorTestContext();

        testContext.addOperationProperty(ConnectConnection.HTTP_METHOD_PROPERTY, "POST");

        testContext.addCookie(
                ObjectDefinitionRole.INPUT,
                FileUtil.readJsonSchema(MetadataUtil.NEW_USAGE_REPORT_SCHEMA));
        testContext.addCookie(
                ObjectDefinitionRole.OUTPUT,
                FileUtil.readJsonSchema(MetadataUtil.USAGE_REPORT_SCHEMA));

        testContext.setOperationType(OperationType.EXECUTE);
        testContext.setObjectTypeId("USAGE_REPORT");
        testContext.setOperationCustomType("CREATE");
        addVendorConnProperties(testContext);
        tester.setOperationContext(testContext);

        SimpleTrackedData data = new SimpleTrackedData(0, getPayload("CreateUsagePayload.json"));

        List<SimpleTrackedData> inputs = new ArrayList<>();
        inputs.add(data);

        List<SimpleOperationResult> actual = tester.executeExecuteOperationWithTrackedData(inputs);

        assertEquals("201",actual.get(0).getStatusCode());
        assertEquals(1, actual.get(0).getPayloads().size());

        String responseString = new String(actual.get(0).getPayloads().get(0));

        usageReportId = JsonUtil.getFieldStringValue(
                new ByteArrayInputStream(responseString.getBytes(StandardCharsets.UTF_8)),
                "id");
    }

    @Test
    public void test3UploadOperation() throws IOException {
        ConnectConnector connector = new ConnectConnector();
        ConnectorTester tester = new ConnectorTester(connector);

        ConnectConnectorTestContext testContext = new ConnectConnectorTestContext();

        testContext.addOperationProperty(ConnectConnection.HTTP_METHOD_PROPERTY, "POST");

        testContext.addCookie(
                ObjectDefinitionRole.OUTPUT,
                FileUtil.readJsonSchema(MetadataUtil.USAGE_REPORT_SCHEMA));

        testContext.setOperationType(OperationType.EXECUTE);
        testContext.setObjectTypeId("USAGE_REPORT");
        testContext.setOperationCustomType("UPLOAD");
        addVendorConnProperties(testContext);
        tester.setOperationContext(testContext);

        SimpleTrackedData data = new SimpleTrackedData(0, new ByteArrayInputStream("{}".getBytes()));
        data.getDynamicOperationProperties().addProperty(
                Key.USAGE_REPORT_ID.getField(),
                usageReportId);
        data.getDynamicOperationProperties().addProperty(
                "usage_file",
                UsageFileGenerator.getNewUsageFileLocation());

        List<SimpleTrackedData> inputs = new ArrayList<>();
        inputs.add(data);

        List<SimpleOperationResult> actual = tester.executeExecuteOperationWithTrackedData(inputs);

        assertEquals("201",actual.get(0).getStatusCode());
        assertEquals(1, actual.get(0).getPayloads().size());
    }

    private void waitTillStatus(String status) throws IOException {
        ConnectConnector connector = new ConnectConnector();
        ConnectorTester tester = new ConnectorTester(connector);

        ConnectConnectorTestContext testContext = new ConnectConnectorTestContext();

        testContext.addOperationProperty(ConnectConnection.HTTP_METHOD_PROPERTY, "GET");

        testContext.addCookie(
                ObjectDefinitionRole.OUTPUT,
                FileUtil.readJsonSchema(MetadataUtil.USAGE_REPORT_SCHEMA));

        testContext.setOperationType(OperationType.EXECUTE);
        testContext.setObjectTypeId("USAGE_REPORT");
        testContext.setOperationCustomType("GET");
        addVendorConnProperties(testContext);
        tester.setOperationContext(testContext);

        SimpleTrackedData data = new SimpleTrackedData(0, new ByteArrayInputStream("{}".getBytes()));
        data.getDynamicOperationProperties().addProperty(
                Key.USAGE_REPORT_ID.getField(),
                usageReportId);

        List<SimpleTrackedData> inputs = new ArrayList<>();
        inputs.add(data);

        List<SimpleOperationResult> actual = tester.executeExecuteOperationWithTrackedData(inputs);

        assertEquals("200",actual.get(0).getStatusCode());
        assertEquals(1, actual.get(0).getPayloads().size());

        String responseString = new String(actual.get(0).getPayloads().get(0));

        String currentStatus = JsonUtil.getFieldStringValue(
                new ByteArrayInputStream(responseString.getBytes(StandardCharsets.UTF_8)),
                "status");
        if (currentStatus == null || !currentStatus.equalsIgnoreCase(status)) {
            waitTillStatus(status);
        }
    }

    @Test
    public void test4SubmitOperation() throws IOException {
        waitTillStatus("ready");

        ConnectConnector connector = new ConnectConnector();
        ConnectorTester tester = new ConnectorTester(connector);

        ConnectConnectorTestContext testContext = new ConnectConnectorTestContext();

        testContext.addOperationProperty(ConnectConnection.HTTP_METHOD_PROPERTY, "POST");

        testContext.addCookie(
                ObjectDefinitionRole.OUTPUT,
                FileUtil.readJsonSchema(MetadataUtil.USAGE_REPORT_SCHEMA));

        testContext.setOperationType(OperationType.EXECUTE);
        testContext.setObjectTypeId("USAGE_REPORT");
        testContext.setOperationCustomType("SUBMIT");
        addVendorConnProperties(testContext);
        tester.setOperationContext(testContext);

        SimpleTrackedData data = new SimpleTrackedData(0, new ByteArrayInputStream("{}".getBytes()));
        data.getDynamicOperationProperties().addProperty(
                Key.USAGE_REPORT_ID.getField(),
                usageReportId);

        List<SimpleTrackedData> inputs = new ArrayList<>();
        inputs.add(data);

        List<SimpleOperationResult> actual = tester.executeExecuteOperationWithTrackedData(inputs);

        assertEquals("201",actual.get(0).getStatusCode());
        assertEquals(1, actual.get(0).getPayloads().size());
    }

    @Test
    public void test5RejectOperation() throws IOException {
        waitTillStatus("pending");

        ConnectConnector connector = new ConnectConnector();
        ConnectorTester tester = new ConnectorTester(connector);

        ConnectConnectorTestContext testContext = new ConnectConnectorTestContext();

        testContext.addOperationProperty(ConnectConnection.HTTP_METHOD_PROPERTY, "POST");

        testContext.addCookie(
                ObjectDefinitionRole.INPUT,
                FileUtil.readJsonSchema(MetadataUtil.REJECT_REPORT_SCHEMA));
        testContext.addCookie(
                ObjectDefinitionRole.OUTPUT,
                FileUtil.readJsonSchema(MetadataUtil.USAGE_REPORT_SCHEMA));

        testContext.setOperationType(OperationType.EXECUTE);
        testContext.setObjectTypeId("USAGE_REPORT");
        testContext.setOperationCustomType("REJECT");
        addDistConnProperties(testContext);
        tester.setOperationContext(testContext);

        SimpleTrackedData data = new SimpleTrackedData(0, new ByteArrayInputStream("{\"rejection_note\" : \"Test Rejection\"}".getBytes()));
        data.getDynamicOperationProperties().addProperty(
                Key.USAGE_REPORT_ID.getField(),
                usageReportId);

        List<SimpleTrackedData> inputs = new ArrayList<>();
        inputs.add(data);

        List<SimpleOperationResult> actual = tester.executeExecuteOperationWithTrackedData(inputs);

        assertEquals("201",actual.get(0).getStatusCode());
        assertEquals(1, actual.get(0).getPayloads().size());
    }

    @Test
    public void test6AcceptOperation() throws IOException {
        test2CreateOperation();
        test3UploadOperation();
        test4SubmitOperation();

        waitTillStatus("pending");

        ConnectConnector connector = new ConnectConnector();
        ConnectorTester tester = new ConnectorTester(connector);

        ConnectConnectorTestContext testContext = new ConnectConnectorTestContext();

        testContext.addOperationProperty(ConnectConnection.HTTP_METHOD_PROPERTY, "POST");

        testContext.addCookie(
                ObjectDefinitionRole.INPUT,
                FileUtil.readJsonSchema(MetadataUtil.ACCEPT_REPORT_SCHEMA));
        testContext.addCookie(
                ObjectDefinitionRole.OUTPUT,
                FileUtil.readJsonSchema(MetadataUtil.USAGE_REPORT_SCHEMA));

        testContext.setOperationType(OperationType.EXECUTE);
        testContext.setObjectTypeId("USAGE_REPORT");
        testContext.setOperationCustomType("ACCEPT");
        addDistConnProperties(testContext);
        tester.setOperationContext(testContext);

        SimpleTrackedData data = new SimpleTrackedData(0, new ByteArrayInputStream("{\"acceptance_note\" : \"Test Acceptance\"}".getBytes()));
        data.getDynamicOperationProperties().addProperty(
                Key.USAGE_REPORT_ID.getField(),
                usageReportId);

        List<SimpleTrackedData> inputs = new ArrayList<>();
        inputs.add(data);

        List<SimpleOperationResult> actual = tester.executeExecuteOperationWithTrackedData(inputs);

        assertEquals("201",actual.get(0).getStatusCode());
        assertEquals(1, actual.get(0).getPayloads().size());
    }
}
