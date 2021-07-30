package com.cloudblue.connect.browser;

import com.boomi.connector.api.*;
import com.boomi.connector.util.BaseBrowser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConnectBrowser extends BaseBrowser {
    private static final List<ResourceInfo> RESOURCE_INFO_LIST = new ArrayList<>();

    static {
        RESOURCE_INFO_LIST.add(
                new ResourceInfo()
                        .id("request")
                        .name("Request")
                        .operationSchema(
                                "get",
                                new OperationSchemaInfo().input("Request-schema.json")
                        )
        );
        RESOURCE_INFO_LIST.add(
                new ResourceInfo()
                        .id("asset")
                        .name("Asset")
                        .operationSchema(
                                "get",
                                new OperationSchemaInfo().input("Asset-schema.json")
                        )
        );
        RESOURCE_INFO_LIST.add(
                new ResourceInfo()
                        .id("tier-account")
                        .name("Tier Account")
                        .operationSchema(
                                "get",
                                new OperationSchemaInfo().input("TierAccount-schema.json")
                        )
        );
        RESOURCE_INFO_LIST.add(
                new ResourceInfo()
                        .id("tier-account-request")
                        .name("Tier Account Request")
                        .operationSchema(
                                "get",
                                new OperationSchemaInfo().input("TierAccountRequest-schema.json")
                        )
        );
        RESOURCE_INFO_LIST.add(
                new ResourceInfo()
                        .id("tier-config")
                        .name("Tier Config")
                        .operationSchema(
                                "get",
                                new OperationSchemaInfo().input("TierConfig-schema.json")
                        )
        );
        RESOURCE_INFO_LIST.add(
                new ResourceInfo()
                        .id("tier-config-request")
                        .name("Tier Config Request")
                        .operationSchema(
                                "get",
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
        for (ResourceInfo resourceInfo : RESOURCE_INFO_LIST) {
            types.getTypes().add(resourceInfo.objectType());
        }
        return types;
    }

    @Override
    public ObjectDefinitions getObjectDefinitions(String s, Collection<ObjectDefinitionRole> collection) {
        return null;
    }
}
