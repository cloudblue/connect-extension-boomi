/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect;

import com.boomi.common.rest.authentication.AuthenticationType;
import com.boomi.connector.api.PropertyMap;

import com.cloudblue.connect.test.utils.ConnectTestContext;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConnectConnectionTest {
    private final ConnectTestContext context = mock(ConnectTestContext.class);
    private final ConnectConnection connection = new ConnectConnection(context);

    @Test
    public void getAuthenticationTypeTest() {
        assertEquals(AuthenticationType.CUSTOM, connection.getAuthenticationType());
    }

    @Test
    public void getHttpMethodTest() {
        PropertyMap propertyMap = mock(PropertyMap.class);
        when(propertyMap.getProperty(ConnectConnection.HTTP_METHOD_PROPERTY)).thenReturn("GET");
        when(context.getOperationProperties()).thenReturn(propertyMap);

        assertEquals("GET", connection.getHttpMethod());
    }

    @Test
    public void getLimitTest() {
        PropertyMap propertyMap = mock(PropertyMap.class);
        when(propertyMap.getLongProperty(ConnectConnection.LIMIT_PROPERTY)).thenReturn(100L);
        when(context.getOperationProperties()).thenReturn(propertyMap);

        assertEquals(100L, (long)connection.getLimit());
    }

    @Test
    public void getOffsetTest() {
        PropertyMap propertyMap = mock(PropertyMap.class);
        when(propertyMap.getLongProperty(ConnectConnection.OFFSET_PROPERTY)).thenReturn(10L);
        when(context.getOperationProperties()).thenReturn(propertyMap);

        assertEquals(10L, (long)connection.getOffset());
    }
}
