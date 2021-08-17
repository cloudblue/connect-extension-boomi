package com.cloudblue.connect.browser;

import com.boomi.connector.api.*;
import com.boomi.connector.util.BaseBrowser;

import com.cloudblue.connect.utils.FileUtil;
import com.cloudblue.connect.utils.SchemaUtil;

import java.io.IOException;
import java.util.Collection;

public class ConnectBrowser extends BaseBrowser {

    public ConnectBrowser(BrowseContext context) {
        super(context);
    }

    @Override
    public ObjectTypes getObjectTypes() {
        ObjectTypes types = new ObjectTypes();

        for (ResourceInfo resourceInfo : SchemaUtil.getResourceMap().values()) {
            if (resourceInfo.getOperationSchemas().containsKey(getResourceOperationType()))
                types.getTypes().add(resourceInfo.objectType());
        }
        return types;
    }

    @Override
    public ObjectDefinitions getObjectDefinitions(String objectTypeId, Collection<ObjectDefinitionRole> roles) {
        ObjectDefinitions definitions = new ObjectDefinitions();
        OperationSchemaInfo schemaInfo = SchemaUtil.getSchemaInfo(objectTypeId, getContext().getCustomOperationType());

        try {
            if (schemaInfo != null) {
                for(ObjectDefinitionRole role : roles) {
                    ObjectDefinition objectDefinition = new ObjectDefinition();
                    if (ObjectDefinitionRole.INPUT == role && schemaInfo.getInput() != null) {
                        definitions.getDefinitions().add(objectDefinition
                                .withInputType(ContentType.JSON)
                                .withOutputType(ContentType.NONE)
                                .withJsonSchema(FileUtil.readJsonSchema(schemaInfo.getInput()))
                                .withElementName(""));
                    } else if (ObjectDefinitionRole.OUTPUT == role && schemaInfo.getOutput() != null) {
                        definitions.getDefinitions().add(objectDefinition
                                .withInputType(ContentType.NONE)
                                .withOutputType(ContentType.JSON)
                                .withJsonSchema(FileUtil.readJsonSchema(schemaInfo.getOutput()))
                                .withElementName(""));
                    }
                }
            }
        } catch (IOException e) {
            throw new ConnectorException(e);
        }

        return definitions;
    }

    private ResourceOperationType getResourceOperationType() {
        return ResourceOperationType
                .valueOf(getContext().getCustomOperationType().toUpperCase());
    }
}
