package com.cloudblue.connect.browser;

import com.boomi.connector.api.*;
import com.boomi.connector.util.BaseBrowser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.EnumMap;

public class ConnectBrowser extends BaseBrowser {
    private static final EnumMap<ResourceType, ResourceInfo> RESOURCE_INFO_MAP = new EnumMap<>(ResourceType.class);

    static {
        RESOURCE_INFO_MAP.put(
                ResourceType.REQUEST,
                new ResourceInfo()
                        .id(ResourceType.REQUEST.name())
                        .name(ResourceType.REQUEST.getName())
                        .operationSchema(
                                OperationType.GET,
                                new OperationSchemaInfo().input("Request-schema.json")
                        )
        );
        RESOURCE_INFO_MAP.put(
                ResourceType.ASSET,
                new ResourceInfo()
                        .id(ResourceType.ASSET.name())
                        .name(ResourceType.ASSET.getName())
                        .operationSchema(
                                OperationType.GET,
                                new OperationSchemaInfo().input("Asset-schema.json")
                        )
        );
        RESOURCE_INFO_MAP.put(
                ResourceType.TIER_ACCOUNT,
                new ResourceInfo()
                        .id(ResourceType.TIER_ACCOUNT.name())
                        .name(ResourceType.TIER_ACCOUNT.getName())
                        .operationSchema(
                                OperationType.GET,
                                new OperationSchemaInfo().input("TierAccount-schema.json")
                        )
        );
        RESOURCE_INFO_MAP.put(
                ResourceType.TIER_ACCOUNT_REQUEST,
                new ResourceInfo()
                        .id(ResourceType.TIER_ACCOUNT_REQUEST.name())
                        .name(ResourceType.TIER_ACCOUNT_REQUEST.getName())
                        .operationSchema(
                                OperationType.GET,
                                new OperationSchemaInfo().input("TierAccountRequest-schema.json")
                        )
        );
        RESOURCE_INFO_MAP.put(
                ResourceType.TIER_CONFIG,
                new ResourceInfo()
                        .id(ResourceType.TIER_CONFIG.name())
                        .name(ResourceType.TIER_CONFIG.getName())
                        .operationSchema(
                                OperationType.GET,
                                new OperationSchemaInfo().input("TierConfig-schema.json")
                        )
        );
        RESOURCE_INFO_MAP.put(
                ResourceType.TIER_CONFIG_REQUEST,
                new ResourceInfo()
                        .id(ResourceType.TIER_CONFIG_REQUEST.name())
                        .name(ResourceType.TIER_CONFIG_REQUEST.getName())
                        .operationSchema(
                                OperationType.GET,
                                new OperationSchemaInfo().input("TierConfigRequest-schema.json")
                        )
        );
    }

    public ConnectBrowser(BrowseContext context) {
        super(context);
    }

    @Override
    public ObjectTypes getObjectTypes() {
        ObjectTypes types = new ObjectTypes();
        OperationType operationType = getContext().getOperationType();

        for (ResourceInfo resourceInfo : RESOURCE_INFO_MAP.values()) {
            if (resourceInfo.getOperationSchemas().containsKey(operationType))
                types.getTypes().add(resourceInfo.objectType());
        }
        return types;
    }

    @Override
    public ObjectDefinitions getObjectDefinitions(String objectTypeId, Collection<ObjectDefinitionRole> collection) {
        ObjectDefinitions definitions = new ObjectDefinitions();
        OperationType operationType = getContext().getOperationType();
        OperationSchemaInfo schemaInfo = getSchemaInfo(objectTypeId, operationType);

        try {
            if (schemaInfo != null) {
                if (schemaInfo.getInput() != null) {
                    definitions.getDefinitions().add(
                            new ObjectDefinition()
                                    .withInputType(ContentType.NONE)
                                    .withOutputType(ContentType.JSON)
                                    .withJsonSchema(readJsonSchema(schemaInfo.getInput()))
                                    .withElementName(""));
                }

                if (schemaInfo.getOutput() != null) {
                    definitions.getDefinitions().add(
                            new ObjectDefinition()
                                    .withInputType(ContentType.NONE)
                                    .withOutputType(ContentType.JSON)
                                    .withJsonSchema(readJsonSchema(schemaInfo.getOutput()))
                                    .withElementName(""));
                }
            }
        } catch (IOException e) {
            throw new ConnectorException(e);
        }

        return definitions;
    }

    private OperationSchemaInfo getSchemaInfo(String objectTypeId, OperationType operationType) {
        ResourceType resourceType = ResourceType.valueOf(objectTypeId);
        return RESOURCE_INFO_MAP.get(resourceType).getOperationSchemas().get(operationType);
    }

    private static String readJsonSchema(String fileName) throws IOException {
        try (InputStream is = ConnectBrowser.class.getResourceAsStream("/schemas/" + fileName)) {
            return toString(is, StandardCharsets.UTF_8.toString());
        }
    }

    public static String toString(InputStream in, String charsetName) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        for( int len; ( len = in.read(buf) ) != -1; ) {
            bout.write(buf, 0, len);
        }
        return bout.toString(charsetName);
    }
}
