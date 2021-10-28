/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.browser.metadata;

import com.boomi.connector.api.ObjectType;

public enum ResourceType {
    REQUEST("Request"), ASSET("Asset"),
    SUBSCRIPTION_REQUEST("Subscription Request"), SUBSCRIPTION_ASSET("Subscription Asset"),
    TIER_ACCOUNT("Tier Account"), TIER_ACCOUNT_VERSION("Tier Account Version"),
    TIER_ACCOUNT_REQUEST("Tier Account Request"),
    TIER_CONFIG_REQUEST("Tier Config Request"), TIER_CONFIG("Tier Config"),
    PRODUCT("Product"), PRODUCT_TEMPLATE("Product Template"),
    PRODUCT_ACTION("Product Action"), PRODUCT_ITEM("Product Item"),
    PRODUCT_PARAMETER("Product Parameter"), PRODUCT_CONFIGURATION("Product Configuration"),
    USAGE_REPORT("Usage Report"), USAGE_RECORD("Usage Report"),
    USAGE_CHUNK("Usage Chunk"), USAGE_RECONCILIATION("Usage Reconciliation"),
    USAGE_AGGREGATE("Usage Aggregate"), ASSET_USAGE_AGGREGATE("Asset Usage Aggregate"),
    CASE("Case"), CONVERSATION_MESSAGES("Conversation Message"),
    PRODUCT_ACTION_LINK("Product Action Link"), BILLING_REQUEST_ATTRIBUTE("Billing Request Attribute");

    String name;

    ResourceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ObjectType objectType() {
        ObjectType type = new ObjectType();
        type.setId(name());
        type.setLabel(getName());

        return type;
    }
}
