/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.operations;

import com.boomi.common.apache.http.entity.RepeatableInputStreamEntity;
import com.boomi.common.rest.RestOperation;
import com.boomi.common.rest.RestOperationConnection;
import com.boomi.connector.api.ObjectData;

import com.cloudblue.connect.browser.metadata.Action;
import com.cloudblue.connect.browser.metadata.ActionMetadata;
import com.cloudblue.connect.browser.metadata.Metadata;
import com.cloudblue.connect.browser.metadata.MetadataUtil;
import com.cloudblue.connect.utils.Common;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ExecuteOperation extends RestOperation {

    public ExecuteOperation(RestOperationConnection connection) {
        super(connection);
    }

    @Override
    protected String getPath(ObjectData data) {
        Action action = getAction();
        Metadata metadata = getMetadata();
        ActionMetadata actionMetadata = getActionMetadata();

        String parentIdValue = null;
        String actionUrl = null;

        if (metadata.isSubCollection()) {
            parentIdValue = Common.getDynamicPropertyValue(
                    data.getDynamicOperationProperties(),
                    metadata.getParentId().getField(),
                    true);
        }

        if (actionMetadata.isCustomAction()) {
            actionUrl = actionMetadata.getAction() == null || actionMetadata.getAction().isEmpty()
                    ? action.name().toLowerCase() : actionMetadata.getAction();
        }

        if (action.isDetailOperation() && !actionMetadata.isCollectionAction()) {
            String idValue = Common.getDynamicPropertyValue(
                    data.getDynamicOperationProperties(),
                    metadata.getId().getField(),
                    true);

            return metadata.getPath(idValue, parentIdValue, actionUrl);
        } else {
            return metadata.getPath(null, parentIdValue, actionUrl);
        }
    }

    private boolean isUploadAction() {
        return Arrays.stream(Action.getUploadActions()).anyMatch(x -> x == getAction());
    }

    @Override
    protected Iterable<Map.Entry<String, String>> getHeaders(ObjectData data) {
        Map<String, String> headers = new HashMap<>();

        ActionMetadata actionMetadata = MetadataUtil.getActionMetadata(
                getContext().getObjectTypeId(), getContext().getCustomOperationType());

        if (actionMetadata.isIncludePayload() && !isUploadAction()) {
            headers.put("Content-Type", "application/json");
        }

        return headers.entrySet();
    }

    private Metadata getMetadata() {
        return MetadataUtil.getMetadata(getContext().getObjectTypeId());
    }

    private ActionMetadata getActionMetadata() {
        return MetadataUtil.getActionMetadata(
                getContext().getObjectTypeId(),
                getContext().getCustomOperationType().toUpperCase());
    }

    private Action getAction() {
        return Action.valueOf(getContext().getCustomOperationType().toUpperCase());
    }

    @Override
    protected HttpEntity getEntity(ObjectData data) throws IOException {

        if (isUploadAction()) {
            ActionMetadata actionMetadata = getActionMetadata();

            MultipartEntityBuilder builder = MultipartEntityBuilder
                    .create()
                    .setMode(HttpMultipartMode.STRICT);

            String file  = Common.getDynamicPropertyValue(
                    data.getDynamicOperationProperties(),
                    actionMetadata.getFileName(),
                    true);

            builder.addBinaryBody(
                    actionMetadata.getFileName(),
                    new FileInputStream(file),
                    ContentType.DEFAULT_BINARY,
                    actionMetadata.getFileName() + ".xlsx");

            return builder.build();

        } else if (data.getDataSize() <= 0L) {
            return null;
        } else {
            return new RepeatableInputStreamEntity(
                    data.getData(),
                    data.getDataSize(),
                    this.getConnection().getEntityContentType());
        }
    }

    @Override
    protected boolean isRequestBodyRequired() {
        return getActionMetadata().isIncludePayload();
    }
}
