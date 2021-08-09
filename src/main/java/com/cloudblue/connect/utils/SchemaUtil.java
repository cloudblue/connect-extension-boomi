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
                                        .output("Request-schema.json")
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
                                        .output("Asset-schema.json")
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
                                        .output("TierAccount-schema.json")
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
                                        .output("TierAccountRequest-schema.json")
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
                                        .output("TierConfig-schema.json")
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
                                        .output("TierConfigRequest-schema.json")
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
