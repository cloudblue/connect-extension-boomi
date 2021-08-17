package com.cloudblue.connect;

import com.boomi.connector.api.Browser;

import com.boomi.connector.api.Operation;

import com.boomi.connector.util.BaseConnection;
import com.cloudblue.connect.browser.ConnectBrowser;
import com.cloudblue.connect.operations.ExecuteOperation;
import com.cloudblue.connect.operations.QueryOperation;
import com.cloudblue.connect.test.utils.ConnectTestContext;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;

public class ConnectConnectorTest {
    private final ConnectTestContext context = mock(ConnectTestContext.class);
    private final ConnectConnector connector = new ConnectConnector();

    @Test
    public void createBrowserTest() {
        Browser browser = connector.createBrowser(context);

        assertTrue(browser instanceof ConnectBrowser);
    }

    @Test
    public void createExecuteOperationTest() {
        Operation executeOperation = connector.createExecuteOperation(context);

        assertTrue(executeOperation instanceof ExecuteOperation);
    }

    @Test
    public void createQueryOperationTest() {
        Operation queryOperation = connector.createQueryOperation(context);

        assertTrue(queryOperation instanceof QueryOperation);

        BaseConnection connection = ((QueryOperation)queryOperation).getConnection();

        assertTrue(connection instanceof ConnectConnection);
    }
}
