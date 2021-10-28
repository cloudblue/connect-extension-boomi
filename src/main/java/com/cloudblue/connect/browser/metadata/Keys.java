/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.browser.metadata;

public enum Keys {
    TEMPLATE_ID("template_id", "Template ID"),
    REQUEST_ID("request_id", "Request ID"),
    ASSET_ID("asset_id", "Asset ID"),
    TA_ID("tier_account_id", "Tier Account ID"),
    TA_VERSION_ID("tier_account_version", "Tier Account Version"),
    TAR_ID("tier_account_request_id", "Tier Account Request ID"),
    TC_ID("tier_config_id", "Tier Config ID"),
    TCR_ID("tier_config_request_id", "Tier Config Request ID"),
    PRODUCT_ID("product_id", "Product ID"),
    USAGE_REPORT_ID("usage_report_id", "Usage Report ID"),
    USAGE_RECORD_ID("usage_record_id", "Usage Record ID"),
    USAGE_CHUNK_ID("usage_chunk_id", "Usage Chunk ID"),
    USAGE_RECON_ID("usage_recon_id", "Usage Recon ID"),
    CASE_ID("case_id", "Case ID"),
    MESSAGE_ID("message_id", "Message ID"),
    CONVERSATION_ID("conversation_id", "Conversation ID"),
    ACTION_ID("action_id", "Action ID"),
    ITEM_ID("item_id", "Item ID"),
    PARAMETER_ID("parameter_id", "Parameter ID"),
    UPLOAD_NOTE("upload_note", "Upload Note");

    private final String field;
    private final String label;

    Keys(String field, String label) {
        this.field = field;
        this.label = label;
    }

    public String getField() {
        return field;
    }

    public String getLabel() {
        return label;
    }
}
