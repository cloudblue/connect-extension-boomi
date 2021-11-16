/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.browser;

import com.boomi.connector.api.BrowseContext;
import com.boomi.connector.api.ConnectorException;
import com.boomi.connector.api.ContentType;
import com.boomi.connector.api.ObjectDefinition;
import com.boomi.connector.api.ObjectDefinitionRole;
import com.boomi.connector.api.ObjectDefinitions;
import com.boomi.connector.api.ObjectTypes;
import com.boomi.connector.api.ui.BrowseField;
import com.boomi.connector.api.ui.DataType;
import com.boomi.connector.util.BaseBrowser;

import com.cloudblue.connect.browser.metadata.Action;
import com.cloudblue.connect.browser.metadata.ActionMetadata;
import com.cloudblue.connect.browser.metadata.Key;
import com.cloudblue.connect.browser.metadata.Metadata;
import com.cloudblue.connect.browser.metadata.MetadataUtil;
import com.cloudblue.connect.utils.FileUtil;

import java.io.IOException;
import java.util.Arrays;
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
                                    ActionMetadata actionMetadata) throws IOException {
        ObjectDefinition objectDefinition = new ObjectDefinition();

        definitions.getDefinitions().add(objectDefinition
                .withInputType(ContentType.JSON)
                .withOutputType(ContentType.NONE)
                .withJsonSchema(FileUtil.readJsonSchema(actionMetadata.getInput()))
                .withElementName(""));
    }

    private void addOutputDefinition(ObjectDefinitions definitions,
                                     Metadata metadata,
                                     ActionMetadata actionMetadata) throws IOException {
        ObjectDefinition objectDefinition = new ObjectDefinition();

        if (isDownloadAction()) {
            definitions.getDefinitions().add(objectDefinition
                    .withInputType(ContentType.NONE)
                    .withOutputType(ContentType.BINARY));
        } else if (actionMetadata.getOutput() == null && metadata.getSchema() != null) {
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

    private void addField(ObjectDefinitions definitions, Key key) {
        addField(definitions, key.getField(), key.getLabel());

    }

    private void addField(ObjectDefinitions definitions, String keyId, String keyName) {
        BrowseField keyField = new BrowseField();

        keyField.setId(keyId);
        keyField.setLabel(keyName);
        keyField.setType(DataType.STRING);
        keyField.setOverrideable(true);

        definitions.getOperationFields().add(keyField);

    }

    private void addOperationFields(ObjectDefinitions definitions,
                                    Metadata metadata,
                                    ActionMetadata actionMetadata) {
        if (metadata.isSubCollection()) {
            addField(definitions, metadata.getParentId());
        }

        if (!actionMetadata.isCollectionAction()) {
            addField(definitions, metadata.getId());
        }

        if (isUploadAction()) {
            addField(definitions, actionMetadata.getFileName(), "File");

            for (Key key : actionMetadata.getFormAttributes()) {
                addField(definitions, key);
            }
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
                        addInputDefinition(definitions, actionMetadata);
                    } else if (ObjectDefinitionRole.OUTPUT == role) {
                        addOutputDefinition(definitions, metadata, actionMetadata);
                    }
                }

                addOperationFields(definitions, metadata, actionMetadata);
            }
        } catch (IOException e) {
            throw new ConnectorException(e);
        }

        return definitions;
    }

    private Action getResourceOperationType() {
        return Action.valueOf(getContext().getCustomOperationType().toUpperCase());
    }

    private boolean isUploadAction() {
        return Arrays.stream(Action.getUploadActions()).anyMatch(x -> x == getAction());
    }

    private boolean isDownloadAction() {
        return Arrays.stream(Action.getDownloadActions()).anyMatch(x -> x == getAction());
    }

    private Action getAction() {
        return Action.valueOf(getContext().getCustomOperationType().toUpperCase());
    }
}
