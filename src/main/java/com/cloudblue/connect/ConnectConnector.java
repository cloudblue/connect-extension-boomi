package com.cloudblue.connect;

import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.api.Browser;
import com.boomi.connector.util.BaseConnector;
import com.cloudblue.connect.browser.ConnectBrowser;

public class ConnectConnector extends BaseConnector {
    @Override
    public Browser createBrowser(BrowseContext browseContext) {
        return new ConnectBrowser(browseContext);
    }
}
