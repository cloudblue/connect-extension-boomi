/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.test.utils;

import com.boomi.connector.api.Connector;
import com.boomi.connector.testutil.ConnectorTestContext;
import com.cloudblue.connect.ConnectConnector;

public class ConnectTestContext extends ConnectorTestContext {
    @Override
    protected Class<? extends Connector> getConnectorClass() {
        return ConnectConnector.class;
    }
}
