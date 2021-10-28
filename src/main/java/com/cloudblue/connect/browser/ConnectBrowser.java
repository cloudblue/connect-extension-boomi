/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.browser;

import com.boomi.connector.api.*;
import com.boomi.connector.util.BaseBrowser;

import com.cloudblue.connect.browser.metadata.Action;
import com.cloudblue.connect.browser.metadata.ActionMetadata;
import com.cloudblue.connect.browser.metadata.Metadata;
import com.cloudblue.connect.browser.metadata.MetadataUtil;
import com.cloudblue.connect.utils.FileUtil;

import java.io.IOException;
import java.util.Collection;

public class ConnectBrowser extends BaseBrowser {

    public ConnectBrowser(BrowseContext context) {
        super(context);
    }

    @Override
    public ObjectTypes getObjectTypes() {
        ObjectTypes types = new ObjectTypes();

        MetadataUtil.getMetadataStore().forEach((key, metadata) -> {
            if (metadata.getActionMetadata().containsKey(getResourceOperationType()))
                types.getTypes().add(key.objectType());
        });
        return types;
    }

    private void addInputDefinition(ObjectDefinitions definitions,
                                    Metadata metadata,
                                    ActionMetadata actionMetadata) throws IOException {
        ObjectDefinition objectDefinition = new ObjectDefinition();

        if (actionMetadata.getInput().equals(MetadataUtil.BASE_SCHEMA)) {
            String schema = FileUtil.readJsonSchema(MetadataUtil.DETAIL_SCHEMA);
            if (metadata.isSubCollection()) {
                schema = FileUtil.readJsonSchema(MetadataUtil.SUB_COLLECTION_DETAILS_SCHEMA);

                schema = schema.replace("parent_id", metadata.getParentId().getField());
            }
            definitions.getDefinitions().add(objectDefinition
                    .withInputType(ContentType.JSON)
                    .withOutputType(ContentType.NONE)
                    .withJsonSchema(schema.replace("id", metadata.getId().getField()))
                    .withElementName(""));
        } else {
            definitions.getDefinitions().add(objectDefinition
                    .withInputType(ContentType.JSON)
                    .withOutputType(ContentType.NONE)
                    .withJsonSchema(FileUtil.readJsonSchema(actionMetadata.getInput()))
                    .withElementName(""));
        }
    }

    private void addOutputDefinition(ObjectDefinitions definitions,
                                    Metadata metadata,
                                    ActionMetadata actionMetadata) throws IOException {
        ObjectDefinition objectDefinition = new ObjectDefinition();

        if (actionMetadata.getOutput() == null && metadata.getSchema() != null) {
            definitions.getDefinitions().add(objectDefinition
                    .withInputType(ContentType.NONE)
                    .withOutputType(ContentType.JSON)
                    .withJsonSchema(FileUtil.readJsonSchema(metadata.getSchema()))
                    .withElementName(""));
        } else if (actionMetadata.getOutput() != null
                && !actionMetadata.getOutput().equals(MetadataUtil.NO_OUTPUT_SCHEMA)) {
            definitions.getDefinitions().add(objectDefinition
                    .withInputType(ContentType.NONE)
                    .withOutputType(ContentType.JSON)
                    .withJsonSchema(FileUtil.readJsonSchema(actionMetadata.getOutput()))
                    .withElementName(""));
        }
    }

    @Override
    public ObjectDefinitions getObjectDefinitions(String objectTypeId, Collection<ObjectDefinitionRole> roles) {
        ObjectDefinitions definitions = new ObjectDefinitions();

        Metadata metadata = MetadataUtil.getMetadata(objectTypeId);
        ActionMetadata actionMetadata = MetadataUtil.getActionMetadata(
                objectTypeId, getContext().getCustomOperationType());
        try {
            if (actionMetadata != null) {
                for(ObjectDefinitionRole role : roles) {
                    if (ObjectDefinitionRole.INPUT == role && actionMetadata.getInput() != null) {
                        addInputDefinition(definitions, metadata, actionMetadata);
                    } else if (ObjectDefinitionRole.OUTPUT == role) {
                        addOutputDefinition(definitions, metadata, actionMetadata);
                    }
                }
            }
        } catch (IOException e) {
            throw new ConnectorException(e);
        }

        return definitions;
    }

    private Action getResourceOperationType() {
        return Action.valueOf(getContext().getCustomOperationType().toUpperCase());
    }
}
