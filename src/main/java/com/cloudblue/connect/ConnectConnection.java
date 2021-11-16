/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect;

import com.boomi.common.rest.RestOperationConnection;
import com.boomi.common.rest.authentication.AuthenticationType;
import com.boomi.connector.api.OperationContext;

public class ConnectConnection extends RestOperationConnection {
    public static final String HTTP_METHOD_PROPERTY = "httpMethod";
    public static final String LIMIT_PROPERTY = "limit";
    public static final String OFFSET_PROPERTY = "offset";

    public ConnectConnection(OperationContext context) {
        super(context);
    }

    @Override
    public String getHttpMethod() {
        return this.getContext().getOperationProperties().getProperty(HTTP_METHOD_PROPERTY);
    }

    public Long getLimit() {
        return this.getContext().getOperationProperties().getLongProperty(LIMIT_PROPERTY);
    }

    public Long getOffset() {
        return this.getContext().getOperationProperties().getLongProperty(OFFSET_PROPERTY);
    }

    @Override
    public AuthenticationType getAuthenticationType() {
        return AuthenticationType.CUSTOM;
    }
}
