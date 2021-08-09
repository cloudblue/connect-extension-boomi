package com.cloudblue.connect.browser;

import com.boomi.connector.api.*;
import com.boomi.connector.util.BaseBrowser;

import com.cloudblue.connect.utils.SchemaUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.logging.Logger;

public class ConnectBrowser extends BaseBrowser {

    public static final Logger LOGGER = Logger.getLogger("ConnectBrowser");


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
                                .withJsonSchema(readJsonSchema(schemaInfo.getInput()))
                                .withElementName(""));
                    } else if (ObjectDefinitionRole.OUTPUT == role && schemaInfo.getOutput() != null) {
                        definitions.getDefinitions().add(objectDefinition
                                .withInputType(ContentType.NONE)
                                .withOutputType(ContentType.JSON)
                                .withJsonSchema(readJsonSchema(schemaInfo.getOutput()))
                                .withElementName(""));
                    }
                }
            }
        } catch (IOException e) {
            throw new ConnectorException(e);
        }

        return definitions;
    }

    private static String readJsonSchema(String fileName) throws IOException {
        try (InputStream is = ConnectBrowser.class.getResourceAsStream("/schemas/" + fileName)) {
            return toString(is, StandardCharsets.UTF_8.toString());
        }
    }

    private static String toString(InputStream in, String charsetName) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        for( int len; ( len = in.read(buf) ) != -1; ) {
            bout.write(buf, 0, len);
        }
        return bout.toString(charsetName);
    }

    private ResourceOperationType getResourceOperationType() {
        return ResourceOperationType
                .valueOf(getContext().getCustomOperationType().toUpperCase());
    }
}
