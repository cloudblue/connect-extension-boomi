/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.operations;

import com.boomi.common.apache.http.response.HttpResult;
import com.boomi.common.rest.RestClient;
import com.boomi.connector.api.*;

import com.boomi.connector.api.result.ConnectorResult;
import com.cloudblue.connect.ConnectConnection;
import com.cloudblue.connect.browser.metadata.Key;
import com.cloudblue.connect.test.common.BaseFilterTest;
import com.cloudblue.connect.test.utils.ConnectTestContext;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class QueryOperationTest extends BaseFilterTest {

    private final RestClient client = mock(RestClient.class);
    private final ConnectTestContext context = mock(ConnectTestContext.class);
    private final ConnectConnection connection = new ConnectConnection(context) {
        @Override
        public RestClient getClient() {
            return client;
        }
    };
    private final QueryOperation operation = new QueryOperation(connection);


    private void mockOperationProperties() {
        PropertyMap propertyMap = mock(PropertyMap.class);
        when(propertyMap.getLongProperty(ConnectConnection.LIMIT_PROPERTY)).thenReturn(100L);
        when(propertyMap.getLongProperty(ConnectConnection.OFFSET_PROPERTY)).thenReturn(0L);
        when(context.getOperationProperties()).thenReturn(propertyMap);
    }

    private void mockDynamicOperationProperties(FilterData data, boolean mockDynamicProperty) {
        DynamicPropertyMap propertyMap = mock(DynamicPropertyMap.class);

        if (mockDynamicProperty)
            when(propertyMap.getProperty(Key.PRODUCT_ID.getField(), null)).thenReturn("PRD-000-000");

        when(data.getDynamicOperationProperties()).thenReturn(propertyMap);
    }

    private void mockContextPropertiesForRequest() {
        when(context.getObjectTypeId()).thenReturn("REQUEST");
        when(context.getCustomOperationType()).thenReturn("LIST");
    }

    private void mockContextPropertiesForProductItem() {
        when(context.getObjectTypeId()).thenReturn("PRODUCT_ITEM");
        when(context.getCustomOperationType()).thenReturn("LIST");
    }

    private void mockConnectionProperties() {
        PropertyMap connectionProperties = mock(PropertyMap.class);
        when(connectionProperties.getProperty("url"))
                .thenReturn("https://api.connect.cloud.im/public/v1");
        when(context.getConnectionProperties()).thenReturn(connectionProperties);
    }

    private void mockForRequest() {
        mockOperationProperties();
        mockConnectionProperties();
        mockContextPropertiesForRequest();
    }

    private FilterData mockForProductItem(boolean mockDynamicProperty) {
        FilterData data = mock(FilterData.class);

        mockOperationProperties();
        mockConnectionProperties();
        mockContextPropertiesForProductItem();
        mockDynamicOperationProperties(data, mockDynamicProperty);

        return data;
    }

    private void mockRestCall() throws IOException {
        CloseableHttpResponse response = mock(CloseableHttpResponse.class);
        StatusLine statusLineGet = mock(StatusLine.class);
        HttpEntity httpEntity = mock(HttpEntity.class);
        when(response.getStatusLine()).thenReturn(statusLineGet);
        when(statusLineGet.getStatusCode()).thenReturn(200);
        when(response.getEntity()).thenReturn(httpEntity);

        HttpResult result = new HttpResult(response) {
            @Override
            public Iterable<? extends Payload> getPayloads() throws IOException {
                List<Payload> payloads = new ArrayList<>();
                payloads.add(new BasePayload());
                return payloads;
            }
        };
        List<HttpResult> results = new ArrayList<>();
        results.add(result);

        when(client.execute(null)).thenReturn(results);
    }

    @Test
    public void getPathTest() {
        mockOperationProperties();
        mockContextPropertiesForRequest();

        FilterData filterData = this.getFilterData(
                new QueryFilter()
                        .withExpression(
                                this.createGroupingExp(
                                        GroupingOperator.OR,
                                        this.createSimpleExp("eq", "id", "PR-9827-0915-3938-001"),
                                        this.createSimpleExp("eq", "id", "PR-9827-0915-3938-002")
                                )
                        ).withSort(
                                new Sort().withSortOrder("asc").withProperty("id"),
                                new Sort().withSortOrder("desc").withProperty("name")
                ));

        String path = operation.getPath(filterData);

        assertEquals(
                "requests?(eq(id,PR-9827-0915-3938-001)|eq(id,PR-9827-0915-3938-002))" +
                        "&ordering(-id,name)" +
                        "&limit=100" +
                        "&offset=0",
                path);

    }

    @Test
    public void getPathForSubCollectionTest() {
        FilterData data = mockForProductItem(true);

        String path = operation.getPath(data);

        assertEquals(
                "products/PRD-000-000/items?limit=100" +
                        "&offset=0",
                path);
    }

    @Test(expected = ConnectorException.class)
    public void getPathForSubCollectionTestErrorForNull() {
        FilterData data = mockForProductItem(false);

        operation.getPath(data);
    }

    @Test(expected = ConnectorException.class)
    public void getPathForSubCollectionTestErrorForBlank() {
        FilterData data = mockForProductItem(false);
        when(data.getDynamicOperationProperties().getProperty(Key.PRODUCT_ID.getField(), null)).thenReturn("");
        operation.getPath(data);
    }

    @Test
    public void getPathNullDataTest() {
        mockOperationProperties();
        mockContextPropertiesForRequest();

        String path = operation.getPath(null);

        assertEquals(
                "requests?limit=100" +
                        "&offset=0",
                path);

    }

    @Test
    public void getUriTest() throws MalformedURLException, URISyntaxException {
        mockForRequest();

        URI uri = operation.getUri(null);

        assertEquals(
                "https://api.connect.cloud.im/public/v1/requests?limit=100&offset=0",
                uri.toString());
    }

    @Test(expected = Test.None.class)
    public void executeQuerySuccessTest() throws IOException {
        mockForRequest();
        mockRestCall();

        try (MockedStatic<ResponseUtil> schemaUtilMockedStatic = mockStatic(ResponseUtil.class)) {
            schemaUtilMockedStatic.when(
                    () -> ResponseUtil.addResult(any(), any(), (ConnectorResult)any())
            ).thenAnswer((Answer<Void>) invocation -> null);

            operation.executeQuery(
                    () -> null,
                    null
            );
        }
    }

    @Test(expected = Test.None.class)
    public void executeQueryFailedTest() throws IOException {
        mockForRequest();

        when(client.execute(null)).thenReturn(null);

        try (MockedStatic<ResponseUtil> schemaUtilMockedStatic = mockStatic(ResponseUtil.class)) {
            schemaUtilMockedStatic.when(
                    () -> ResponseUtil.addExceptionFailure(any(), any(), any())
            ).thenAnswer((Answer<Void>) invocation -> null);

            operation.executeQuery(
                    () -> null,
                    null
            );
        }
    }
}
