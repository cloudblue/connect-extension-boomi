package com.cloudblue.connect.browser;

public enum ResourceType {
    REQUEST("Request", "requests"),
    ASSET("Asset", "assets"),
    TIER_ACCOUNT("Tier Account", "tier/accounts"),
    TIER_ACCOUNT_REQUEST("Tier Account Request", "tier/account-request"),
    TIER_CONFIG("Tier Config", "tier/config"),
    TIER_CONFIG_REQUEST("Tier Config Request", "tier/config-request");

    String name;
    String path;

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    ResourceType(String name, String path) {
        this.name = name;
        this.path = path;
    }
}
