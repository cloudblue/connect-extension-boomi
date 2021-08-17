package com.cloudblue.connect.utils;

import com.cloudblue.connect.browser.OperationSchemaInfo;
import com.cloudblue.connect.browser.ResourceInfo;
import com.cloudblue.connect.browser.ResourceOperationType;
import com.cloudblue.connect.browser.ResourceType;

import java.util.EnumMap;
import java.util.Map;

public class SchemaUtil {
    private static final Map<ResourceType, ResourceInfo> RESOURCE_INFO_MAP = new EnumMap<>(ResourceType.class);

    public static final String GET_RESOURCE_INPUT_SCHEMA = "GetResourceInput-schema.json";
    public static final String REQUEST_SCHEMA = "Request-schema.json";
    public static final String NEW_REQUEST_SCHEMA = "NewRequest-schema.json";
    public static final String ASSET_SCHEMA = "Asset-schema.json";
    public static final String TIER_ACCOUNT_SCHEMA = "TierAccount-schema.json";
    public static final String TIER_ACCOUNT_REQUEST_SCHEMA = "TierAccountRequest-schema.json";
    public static final String TIER_CONFIG_SCHEMA = "TierConfig-schema.json";
    public static final String TIER_CONFIG_REQUEST_SCHEMA = "TierConfigRequest-schema.json";

    static {
        RESOURCE_INFO_MAP.put(
                ResourceType.REQUEST,
                new ResourceInfo()
                        .id(ResourceType.REQUEST.name())
                        .name(ResourceType.REQUEST.getName())
                        .operationSchema(
                                ResourceOperationType.GET,
                                new OperationSchemaInfo()
                                        .input(GET_RESOURCE_INPUT_SCHEMA)
                                        .output(REQUEST_SCHEMA)
                        ).operationSchema(
                                ResourceOperationType.CREATE,
                                new OperationSchemaInfo()
                                        .input(NEW_REQUEST_SCHEMA)
                                        .output(REQUEST_SCHEMA)
                        ).operationSchema(
                                ResourceOperationType.LIST,
                                new OperationSchemaInfo()
                                    .output(REQUEST_SCHEMA)
                        )
        );
        RESOURCE_INFO_MAP.put(
                ResourceType.ASSET,
                new ResourceInfo()
                        .id(ResourceType.ASSET.name())
                        .name(ResourceType.ASSET.getName())
                        .operationSchema(
                                ResourceOperationType.GET,
                                new OperationSchemaInfo()
                                        .input(GET_RESOURCE_INPUT_SCHEMA)
                                        .output(ASSET_SCHEMA)
                        ).operationSchema(
                                ResourceOperationType.LIST,
                                new OperationSchemaInfo()
                                        .output(ASSET_SCHEMA)
                        )
        );
        RESOURCE_INFO_MAP.put(
                ResourceType.TIER_ACCOUNT,
                new ResourceInfo()
                        .id(ResourceType.TIER_ACCOUNT.name())
                        .name(ResourceType.TIER_ACCOUNT.getName())
                        .operationSchema(
                                ResourceOperationType.GET,
                                new OperationSchemaInfo()
                                        .input(GET_RESOURCE_INPUT_SCHEMA)
                                        .output(TIER_ACCOUNT_SCHEMA)
                        ).operationSchema(
                                ResourceOperationType.LIST,
                                new OperationSchemaInfo()
                                        .output(TIER_ACCOUNT_SCHEMA)
                        )
        );
        RESOURCE_INFO_MAP.put(
                ResourceType.TIER_ACCOUNT_REQUEST,
                new ResourceInfo()
                        .id(ResourceType.TIER_ACCOUNT_REQUEST.name())
                        .name(ResourceType.TIER_ACCOUNT_REQUEST.getName())
                        .operationSchema(
                                ResourceOperationType.GET,
                                new OperationSchemaInfo()
                                        .input(GET_RESOURCE_INPUT_SCHEMA)
                                        .output(TIER_ACCOUNT_REQUEST_SCHEMA)
                        ).operationSchema(
                                ResourceOperationType.LIST,
                                new OperationSchemaInfo()
                                        .output(TIER_ACCOUNT_REQUEST_SCHEMA)
                        )
        );
        RESOURCE_INFO_MAP.put(
                ResourceType.TIER_CONFIG,
                new ResourceInfo()
                        .id(ResourceType.TIER_CONFIG.name())
                        .name(ResourceType.TIER_CONFIG.getName())
                        .operationSchema(
                                ResourceOperationType.GET,
                                new OperationSchemaInfo()
                                        .input(GET_RESOURCE_INPUT_SCHEMA)
                                        .output(TIER_CONFIG_SCHEMA)
                        ).operationSchema(
                                ResourceOperationType.LIST,
                                new OperationSchemaInfo()
                                        .output(TIER_CONFIG_SCHEMA)
                        )
        );
        RESOURCE_INFO_MAP.put(
                ResourceType.TIER_CONFIG_REQUEST,
                new ResourceInfo()
                        .id(ResourceType.TIER_CONFIG_REQUEST.name())
                        .name(ResourceType.TIER_CONFIG_REQUEST.getName())
                        .operationSchema(
                                ResourceOperationType.GET,
                                new OperationSchemaInfo()
                                        .input(GET_RESOURCE_INPUT_SCHEMA)
                                        .output(TIER_CONFIG_REQUEST_SCHEMA)
                        ).operationSchema(
                                ResourceOperationType.LIST,
                                new OperationSchemaInfo()
                                        .output(TIER_CONFIG_REQUEST_SCHEMA)
                        )
        );
    }

    private SchemaUtil() {}

    public static OperationSchemaInfo getSchemaInfo(String objectTypeId, String customOperationType) {
        ResourceType resourceType = ResourceType.valueOf(objectTypeId);
        ResourceOperationType operationType = ResourceOperationType
                .valueOf(customOperationType.toUpperCase());
        return RESOURCE_INFO_MAP.get(resourceType)
                .getOperationSchemas()
                .get(operationType);
    }

    public static Map<ResourceType, ResourceInfo> getResourceMap() {
        return RESOURCE_INFO_MAP;
    }

}
