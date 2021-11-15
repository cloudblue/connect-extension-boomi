/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.operations;

import com.boomi.common.apache.http.response.HttpResult;
import com.boomi.common.rest.RestClient;
import com.boomi.connector.api.*;
import com.boomi.connector.util.BaseQueryOperation;
import com.boomi.util.URLUtil;
import com.boomi.util.json.splitter.JsonRootArraySplitter;

import com.cloudblue.connect.ConnectConnection;
import com.cloudblue.connect.browser.metadata.Metadata;
import com.cloudblue.connect.browser.metadata.MetadataUtil;
import com.cloudblue.connect.utils.Common;
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
                for (Payload payload : result.getPayloads()) {
                    ResponseUtil.addSplitSuccess(
                            operationResponse,
                            input,
                            result.getStatus().getStatusCode(),
                            new JsonRootArraySplitter(payload.readFrom()));
                }
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
        Metadata metadata = MetadataUtil.getMetadata(getContext().getObjectTypeId());

        String parentIdValue = null;

        if (metadata.isSubCollection()) {
            parentIdValue = Common.getDynamicPropertyValue(
                    data.getDynamicOperationProperties(),
                    metadata.getParentId().getField(),
                    true);
        }
        String basePath = metadata.getPath(null, parentIdValue, null);

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
