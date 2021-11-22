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
import com.cloudblue.connect.ConnectConnection;
import com.cloudblue.connect.ConnectConnector;
import com.cloudblue.connect.browser.metadata.Key;
import com.cloudblue.connect.browser.metadata.MetadataUtil;
import com.cloudblue.connect.utils.FileUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class QueryOperationIntegrationTest extends AuthenticatedIntegrationTest {

    @Test
    public void testListOperation() throws IOException {
        ConnectConnector connector = new ConnectConnector();
        ConnectorTester tester = new ConnectorTester(connector);

        ConnectConnectorTestContext testContext = new ConnectConnectorTestContext();

        testContext.addOperationProperty(ConnectConnection.LIMIT_PROPERTY, 1L);
        testContext.addOperationProperty(ConnectConnection.OFFSET_PROPERTY, 0L);
        testContext.addOperationProperty(ConnectConnection.HTTP_METHOD_PROPERTY, "GET");

        testContext.addCookie(
                ObjectDefinitionRole.OUTPUT,
                FileUtil.readJsonSchema(MetadataUtil.FULFILLMENT_REQUEST_SCHEMA));

        testContext.setOperationType(OperationType.QUERY);
        testContext.setObjectTypeId("REQUEST");
        testContext.setOperationCustomType("LIST");

        addVendorConnProperties(testContext);

        tester.setOperationContext(testContext);

        List<SimpleOperationResult> actual = tester.executeQueryOperation(null);

        assertEquals("200",actual.get(0).getStatusCode());
        assertEquals(1, actual.get(0).getPayloads().size());
    }
}
