/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.browser.metadata;


import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Metadata {
    private String collection;
    private Key id;

    private boolean isSubCollection = false;
    private Key parentId;
    private String parentCollection;

    private String schema;

    private final EnumMap<Action, ActionMetadata> actionMetadata = new EnumMap<>(Action.class);

    public Metadata collection(String collection) {
        this.collection = collection;

        return this;
    }

    public Metadata id(Key id) {
        this.id = id;

        return this;
    }

    public Metadata isSubCollection(boolean isSubCollection) {
        this.isSubCollection = isSubCollection;

        return this;
    }

    public Metadata parentId(Key parentId) {
        this.parentId = parentId;

        return this;
    }

    public Metadata parentCollection(String parentCollection) {
        this.parentCollection = parentCollection;

        return this;
    }

    public Metadata addActionMetaData(Action action, ActionMetadata actionMetadata) {
        if (actionMetadata.getOutput() == null) {
            actionMetadata.output(schema);
        }
        this.actionMetadata.put(action, actionMetadata);

        return this;
    }

    public Metadata schema(String schema) {
        this.schema = schema;

        return this;
    }

    public Metadata includeListAction(Key... filters) {
        this.addActionMetaData(Action.LIST,
                new ActionMetadata()
                        .output(schema)
                        .customAction(false)
                        .collectionAction(true)
                        .includePayload(false)
                        .filter(filters));

        return this;
    }

    public Metadata includeGetAction(Key... filters) {
        this.addActionMetaData(Action.GET,
                new ActionMetadata()
                        .output(schema)
                        .customAction(false)
                        .includePayload(false)
                        .filter(filters));

        return this;
    }

    public String getCollection() {
        return collection;
    }

    public Key getId() {
        return id;
    }

    public boolean isSubCollection() {
        return isSubCollection;
    }

    public Key getParentId() {
        return parentId;
    }

    public String getParentCollection() {
        return parentCollection;
    }

    public Map<Action, ActionMetadata> getActionMetadata() {
        return actionMetadata;
    }

    public String getSchema() {
        return schema;
    }


    private boolean addIfNotBlank(List<String> pathParts, String pathPart) {
        if (pathPart != null && !pathPart.isEmpty()) {
            pathParts.add(pathPart);
            return true;
        } else
            return false;
    }

    public String getPath(String id, String parentId, String action) {
        List<String> pathParts = new ArrayList<>();

        if (parentId != null && !parentId.isEmpty()) {
            boolean parentCollectionAdded = addIfNotBlank(pathParts, getParentCollection());

            if (parentCollectionAdded)
                addIfNotBlank(pathParts, parentId);
        }

        addIfNotBlank(pathParts, getCollection());
        addIfNotBlank(pathParts, id);

        addIfNotBlank(pathParts, action);

        return String.join("/", pathParts);
    }
}
