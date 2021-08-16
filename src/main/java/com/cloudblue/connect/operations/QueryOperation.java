package com.cloudblue.connect.operations;

import com.boomi.common.apache.http.response.HttpResult;
import com.boomi.common.rest.RestClient;
import com.boomi.connector.api.*;
import com.boomi.connector.util.BaseQueryOperation;

import com.boomi.util.URLUtil;
import com.cloudblue.connect.ConnectConnection;
import com.cloudblue.connect.browser.ResourceType;
import com.cloudblue.connect.utils.FilterUtil;
import org.apache.http.client.methods.RequestBuilder;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


public class QueryOperation extends BaseQueryOperation {

    public QueryOperation(ConnectConnection connection) {
        super(connection);
    }

    @Override
    protected void executeQuery(QueryRequest queryRequest, OperationResponse operationResponse) {
        RestClient client = this.getConnection().getClient();
        FilterData input = queryRequest.getFilter();

        try {
            RequestBuilder httpRequest = client.createRequestBuilder(this.getUri(input));

            Iterable<HttpResult> results = client.execute(httpRequest);
            if (results == null) {
                throw new ConnectorException("unable to execute request");
            }

            for (HttpResult result : results) {
                ResponseUtil.addResult(operationResponse, input, result);
            }
        } catch (Exception e) {
            ResponseUtil.addExceptionFailure(operationResponse, input, e);
        }
    }

    URI getUri(FilterData data) throws MalformedURLException, URISyntaxException {
        URL baseURL = this.getConnection().getUrl();
        String path = this.getPath(data);
        return URLUtil.makeUrl(baseURL, path).toURI();
    }

    protected String getPath(FilterData data) {
        ResourceType resourceType = ResourceType.valueOf(
                getContext().getObjectTypeId().toUpperCase());
        String basePath = resourceType.getPath();

        String filterQueryString;

        if (data != null) {
            filterQueryString = FilterUtil.convertToQueryString(
                    data.getFilter(),
                    this.getConnection().getLimit(),
                    this.getConnection().getOffset());

        } else {
            filterQueryString = FilterUtil.convertToQueryString(
                    null,
                    this.getConnection().getLimit(),
                    this.getConnection().getOffset());

        }

        return String.format("%s?%s", basePath, filterQueryString);
    }

    @Override
    public ConnectConnection getConnection() {
        return (ConnectConnection)super.getConnection();
    }
}
