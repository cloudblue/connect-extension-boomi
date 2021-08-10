package com.cloudblue.connect.operations;

import com.boomi.common.rest.RestOperation;
import com.boomi.common.rest.RestOperationConnection;
import com.boomi.connector.api.ObjectData;

import com.cloudblue.connect.browser.ResourceOperationType;
import com.cloudblue.connect.browser.ResourceType;
import com.cloudblue.connect.utils.JsonUtil;

import java.util.HashMap;
import java.util.Map;

public class ExecuteOperation extends RestOperation {

    public ExecuteOperation(RestOperationConnection connection) {
        super(connection);
    }

    @Override
    protected String getPath(ObjectData data) {
        ResourceType resourceType = ResourceType.valueOf(getContext().getObjectTypeId().toUpperCase());
        ResourceOperationType operationType = ResourceOperationType
                .valueOf(getContext().getCustomOperationType().toUpperCase());

        if (operationType == ResourceOperationType.GET) {
            String idValue = JsonUtil.getFieldStringValue(data.getData(), "id");
            return resourceType.getPath() + "/" + idValue;
        } else {
            return resourceType.getPath();
        }
    }

    @Override
    protected Iterable<Map.Entry<String, String>> getHeaders(ObjectData data) {
        Map<String, String> headers = new HashMap<>();

        ResourceOperationType operationType = ResourceOperationType
                .valueOf(getContext().getCustomOperationType().toUpperCase());
        if (operationType == ResourceOperationType.CREATE) {
            headers.put("Content-Type", "application/json");
        }

        return headers.entrySet();
    }
}
