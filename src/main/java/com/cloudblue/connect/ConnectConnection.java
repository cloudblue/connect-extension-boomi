package com.cloudblue.connect;

import com.boomi.common.rest.RestOperationConnection;
import com.boomi.common.rest.authentication.AuthenticationType;
import com.boomi.connector.api.OperationContext;

public class ConnectConnection extends RestOperationConnection {
    public static final String HTTP_METHOD_PROPERTY = "httpMethod";

    public ConnectConnection(OperationContext context) {
        super(context);
    }

    @Override
    public String getHttpMethod() {
        return this.getContext().getOperationProperties().getProperty(HTTP_METHOD_PROPERTY);
    }

    @Override
    public AuthenticationType getAuthenticationType() {
        return AuthenticationType.CUSTOM;
    }
}
