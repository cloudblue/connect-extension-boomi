package com.cloudblue.connect;

import com.boomi.common.rest.RestConnector;
import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.api.Browser;
import com.boomi.connector.api.Operation;
import com.boomi.connector.api.OperationContext;

import com.cloudblue.connect.browser.ConnectBrowser;
import com.cloudblue.connect.operations.ExecuteOperation;
import com.cloudblue.connect.operations.QueryOperation;

public class ConnectConnector extends RestConnector {
    @Override
    public Browser createBrowser(BrowseContext browseContext) {
        return new ConnectBrowser(browseContext);
    }

    @Override
    protected Operation createExecuteOperation(OperationContext context) {
        return new ExecuteOperation(new ConnectConnection(context));
    }

    @Override
    protected Operation createQueryOperation(OperationContext context) {
        return new QueryOperation(new ConnectConnection(context));
    }
}
