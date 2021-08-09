package com.cloudblue.connect;

import com.boomi.common.rest.authentication.AuthenticationType;
import com.boomi.connector.api.PropertyMap;
import com.cloudblue.connect.utils.ConnectTestContext;
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
}
