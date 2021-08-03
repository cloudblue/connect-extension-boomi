package com.cloudblue.connect.browser;

public enum ResourceType {
    REQUEST("Request"),
    ASSET("Asset"),
    TIER_ACCOUNT("Tier Account"),
    TIER_ACCOUNT_REQUEST("Tier Account Request"),
    TIER_CONFIG("Tier Config"),
    TIER_CONFIG_REQUEST("Tier Config Request");

    String name;

    public String getName() {
        return name;
    }

    ResourceType(String name) {
        this.name = name;
    }
}
