/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.operations;

import com.boomi.common.rest.RestOperation;
import com.boomi.common.rest.RestOperationConnection;
import com.boomi.connector.api.ObjectData;

import com.cloudblue.connect.browser.metadata.Action;
import com.cloudblue.connect.browser.metadata.ActionMetadata;
import com.cloudblue.connect.browser.metadata.Metadata;
import com.cloudblue.connect.browser.metadata.MetadataUtil;
import com.cloudblue.connect.utils.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class ExecuteOperation extends RestOperation {

    public ExecuteOperation(RestOperationConnection connection) {
        super(connection);
    }

    @Override
    protected String getPath(ObjectData data) {
        Action action = Action.valueOf(getContext().getCustomOperationType().toUpperCase());
        Metadata metadata = MetadataUtil.getMetadata(getContext().getObjectTypeId());

        String parentIdValue = null;

        if (metadata.isSubCollection()) {
            parentIdValue = JsonUtil.getFieldStringValue(data.getData(), metadata.getParentId().getField());
        }

        if (action.isDetailOperation()) {
            String idValue = JsonUtil.getFieldStringValue(data.getData(), metadata.getId().getField());

            return metadata.getPath(idValue, parentIdValue);
        } else {
            return metadata.getPath(null, parentIdValue);
        }
    }

    @Override
    protected Iterable<Map.Entry<String, String>> getHeaders(ObjectData data) {
        Map<String, String> headers = new HashMap<>();

        ActionMetadata actionMetadata = MetadataUtil.getActionMetadata(
                getContext().getObjectTypeId(), getContext().getCustomOperationType());

        if (actionMetadata.isIncludePayload()) {
            headers.put("Content-Type", "application/json");
        }

        return headers.entrySet();
    }
}
