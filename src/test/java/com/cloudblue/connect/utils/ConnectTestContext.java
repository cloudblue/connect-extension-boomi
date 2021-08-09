package com.cloudblue.connect.utils;

import com.boomi.connector.api.Connector;
import com.boomi.connector.testutil.ConnectorTestContext;
import com.cloudblue.connect.ConnectConnector;

public class ConnectTestContext extends ConnectorTestContext {
    @Override
    protected Class<? extends Connector> getConnectorClass() {
        return ConnectConnector.class;
    }
}
