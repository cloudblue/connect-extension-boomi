/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.browser.metadata;

import java.util.EnumMap;
import java.util.Map;

import static com.cloudblue.connect.browser.metadata.Key.*;

public class MetadataUtil {

    public static class Collections {

        private Collections() {}

        public static final String SEPARATOR = "/";
        public static final String REQUESTS = "requests";
        public static final String ASSETS = "assets";
        public static final String HELPDESK = "helpdesk";
        public static final String CASES = HELPDESK + SEPARATOR + "cases";
        public static final String USAGE = "usage";
        public static final String FILES = USAGE + SEPARATOR + "files";
        public static final String RECORDS = USAGE + SEPARATOR + "records";
        public static final String CHUNKS = USAGE + SEPARATOR + "chunks";
        public static final String RECONCILIATIONS = USAGE + SEPARATOR + "reconciliations";
        public static final String AGGREGATES = USAGE + SEPARATOR + "aggregates";
        public static final String PRODUCTS = "products";
        public static final String ACTIONS = "actions";
        public static final String ITEMS = "items";
        public static final String PARAMETERS = "parameters";
        public static final String CONFIGURATIONS = "configurations";
        public static final String ACCOUNTS = "accounts";
        public static final String TIER = "tier";
        public static final String TIER_ACCOUNTS = TIER + SEPARATOR + ACCOUNTS;
        public static final String VERSIONS = "versions";
        public static final String TIER_CONFIG = TIER + SEPARATOR + "configs";
        public static final String ACCOUNT_REQUESTS = TIER + SEPARATOR + "account-requests";
        public static final String CONFIG_REQUESTS = TIER + SEPARATOR + "config-requests";
        public static final String TEMPLATES = "templates";
        public static final String SUBSCRIPTIONS = "subscriptions";
        public static final String SUBSCRIPTION_REQUESTS = SUBSCRIPTIONS + SEPARATOR + REQUESTS;
        public static final String SUBSCRIPTION_ASSETS = SUBSCRIPTIONS + SEPARATOR + ASSETS;
        public static final String ATTRIBUTES = "attributes";
        public static final String CONVERSATION = "conversations";
        public static final String MESSAGES = "messages";
    }

    private static final Map<ResourceType, Metadata> METADATA_STORE = new EnumMap<>(ResourceType.class);

    public static final String EMPTY_OBJECT_SCHEMA = "EmptyInput-schema.json";
    public static final String TIER_ACCOUNT_SCHEMA = "TierAccount-schema.json";
    public static final String TIER_ACCOUNT_REQUEST_SCHEMA = "TierAccountRequest-schema.json";
    public static final String TIER_CONFIG_REQUEST_SCHEMA = "TierConfigRequest-schema.json";
    public static final String TIER_CONFIG_SCHEMA = "TierConfig-schema.json";
    public static final String FULFILLMENT_REQUEST_SCHEMA = "Request-schema.json";
    public static final String ASSET_SCHEMA = "Asset-schema.json";
    public static final String PRODUCT_SCHEMA = "Product-schema.json";
    public static final String PRODUCT_TEMPLATE_SCHEMA = "Template-schema.json";
    public static final String PRODUCT_ACTION_SCHEMA = "ProductAction-schema.json";
    public static final String PRODUCT_ITEM_SCHEMA = "ProductItem-schema.json";
    public static final String PRODUCT_PARAMETER_SCHEMA = "ProductParameter-schema.json";
    public static final String PRODUCT_CONFIGURATION_SCHEMA = "ProductConfigurationParameter-schema.json";
    public static final String CHUNK_FILE_SCHEMA = "UsageChunkFile-schema.json";
    public static final String RECORD_BULK_CLOSE_RESPONSE_SCHEMA = "UsageRecordBulkCloseResponse-schema.json";
    public static final String USAGE_RECORD_SCHEME = "UsageRecord-schema.json";
    public static final String USAGE_REPORT_SCHEMA = "UsageReport-schema.json";
    public static final String USAGE_RECON_SCHEMA = "UsageReconciliation-schema.json";
    public static final String USAGE_AGGREGATE_SCHEMA = "UsageAggregate-schema.json";
    public static final String CASE_SCHEMA = "Case-schema.json";
    public static final String CONVERSATION_MESSAGES_SCHEMA = "ConversationMessage-schema.json";
    public static final String NEW_CONVERSATION_MESSAGES_SCHEMA = "NewConversationMessage-schema.json";
    public static final String NEW_CASE_SCHEMA = "NewCase-schema.json";
    public static final String NEW_USAGE_REPORT_SCHEMA = "NewUsageReport-schema.json";
    public static final String NEW_PURCHASE_REQUEST_SCHEMA = "NewPurchaseRequest-schema.json";
    public static final String NEW_BILLING_REQUEST_SCHEMA = "NewBillingRequest-schema.json";
    public static final String NEW_ADMIN_HOLD_REQUEST_SCHEMA = "NewAdminHoldRequest-schema.json";
    public static final String NEW_CHANGE_REQUEST = "NewChangeRequest-schema.json";
    public static final String NEW_TIER_ACCOUNT_SCHEMA = "NewTierAccount-schema.json";
    public static final String NEW_TIER_ACCOUNT_REQUEST_SCHEMA = "NewTierAccountRequest-schema.json";
    public static final String NEW_TIER_ACCOUNT_CONFIG_REQUEST_SCHEMA = "NewTierConfigRequest-schema.json";
    public static final String PRODUCT_ACTION_LINK_SCHEMA = "ProductActionLink-schema.json";
    public static final String BILLING_REQUEST_ATTRIBUTES_SCHEMA = "BillingRequestAttributes-schema.json";
    public static final String UPDATE_TCR_SCHEMA = "UpdateTierConfigRequest-schema.json";
    public static final String UPDATE_USAGE_REPORT_SCHEMA = "UpdateUsageReport-schema.json";
    public static final String UPDATE_REQUEST_SCHEMA = "UpdateRequest-schema.json";
    public static final String UPDATE_CHUNK_FILE_SCHEMA = "UpdateUsageChunkFile-schema.json";
    public static final String NO_OUTPUT_SCHEMA = "Null";
    public static final String IGNORE_TAR_SCHEMA = "IgnoreTar-schema.json";
    public static final String APPROVE_TCR_SCHEMA = "ApproveTcr-schema.json";
    public static final String FAIL_TCR_SCHEMA = "FailTcr-schema.json";
    public static final String APPROVE_REQUEST_SCHEMA = "ApproveRequest-schema.json";
    public static final String ASSIGN_REQUEST_SCHEMA = "AssignRequest-schema.json";
    public static final String FAIL_REQUEST_SCHEMA = "FailRequest-schema.json";
    public static final String INQUIRE_REQUEST_SCHEMA = "InquireRequest-schema.json";
    public static final String CLOSE_CHUNK_SCHEMA = "CloseChunk-schema.json";
    public static final String CLOSE_RECORD_SCHEMA = "CloseRecord-schema.json";
    public static final String BULK_CLOSE_RECORD_SCHEMA = "CloseBulkRecord-schema.json";
    public static final String REJECT_REPORT_SCHEMA = "RejectReport-schema.json";
    public static final String ACCEPT_REPORT_SCHEMA = "AcceptReport-schema.json";
    public static final String CLOSE_CASE_SCHEMA = "CloseCase-schema.json";

    static {
        METADATA_STORE.put(ResourceType.TIER_ACCOUNT,
                new Metadata()
                        .collection(Collections.TIER_ACCOUNTS)
                        .id(Key.TA_ID)
                        .schema(TIER_ACCOUNT_SCHEMA)
                        .includeGetAction()
                        .includeListAction()
                        .addActionMetaData(Action.CREATE, new ActionMetadata()
                                .customAction(false)
                                .collectionAction(true)
                                .input(NEW_TIER_ACCOUNT_SCHEMA))
                        .addActionMetaData(Action.UPDATE, new ActionMetadata()
                                .customAction(false)
                                .input(NEW_TIER_ACCOUNT_SCHEMA)));

        METADATA_STORE.put(ResourceType.TIER_ACCOUNT_VERSION,
                new Metadata()
                        .collection(Collections.VERSIONS)
                        .id(TA_VERSION_ID)
                        .isSubCollection(true)
                        .parentCollection(Collections.TIER_ACCOUNTS)
                        .parentId(TA_ID)
                        .schema(TIER_ACCOUNT_SCHEMA)
                        .includeGetAction()
                        .includeListAction());

        METADATA_STORE.put(ResourceType.TIER_ACCOUNT_REQUEST,
                new Metadata()
                        .collection(Collections.ACCOUNT_REQUESTS)
                        .id(TAR_ID)
                        .schema(TIER_ACCOUNT_REQUEST_SCHEMA)
                        .includeGetAction()
                        .includeListAction()
                        .addActionMetaData(Action.CREATE, new ActionMetadata()
                                .customAction(false)
                                .collectionAction(true)
                                .input(NEW_TIER_ACCOUNT_REQUEST_SCHEMA))
                        .addActionMetaData(Action.ACCEPT, new ActionMetadata()
                                .includePayload(false))
                        .addActionMetaData(Action.IGNORE, new ActionMetadata()
                                .input(IGNORE_TAR_SCHEMA)));

        METADATA_STORE.put(ResourceType.TIER_CONFIG_REQUEST,
                new Metadata()
                        .collection(Collections.CONFIG_REQUESTS)
                        .id(TCR_ID)
                        .schema(TIER_CONFIG_REQUEST_SCHEMA)
                        .includeGetAction()
                        .includeListAction()
                        .addActionMetaData(Action.CREATE, new ActionMetadata()
                                .customAction(false)
                                .collectionAction(true)
                                .input(NEW_TIER_ACCOUNT_CONFIG_REQUEST_SCHEMA))
                        .addActionMetaData(Action.UPDATE, new ActionMetadata()
                                .customAction(false)
                                .input(UPDATE_TCR_SCHEMA))
                        .addActionMetaData(Action.APPROVE, new ActionMetadata()
                                .input(APPROVE_TCR_SCHEMA))
                        .addActionMetaData(Action.INQUIRE, new ActionMetadata()
                                .input(EMPTY_OBJECT_SCHEMA)
                                .output(NO_OUTPUT_SCHEMA)
                                .includePayload(false))
                        .addActionMetaData(Action.PENDING, new ActionMetadata()
                                .output(NO_OUTPUT_SCHEMA)
                                .input(EMPTY_OBJECT_SCHEMA)
                                .includePayload(false)
                                .action("pend"))
                        .addActionMetaData(Action.REJECT, new ActionMetadata()
                                .output(NO_OUTPUT_SCHEMA)
                                .input(EMPTY_OBJECT_SCHEMA)
                                .input(FAIL_TCR_SCHEMA)
                                .action("fail")
                                .includePayload(false)));

        METADATA_STORE.put(ResourceType.TIER_CONFIG,
                new Metadata()
                        .collection(Collections.TIER_CONFIG)
                        .id(TC_ID)
                        .schema(TIER_CONFIG_SCHEMA)
                        .includeGetAction()
                        .includeListAction());

        METADATA_STORE.put(ResourceType.REQUEST,
                new Metadata()
                        .collection(Collections.REQUESTS)
                        .id(REQUEST_ID)
                        .schema(FULFILLMENT_REQUEST_SCHEMA)
                        .includeGetAction()
                        .includeListAction()
                        .addActionMetaData(Action.APPROVE, new ActionMetadata()
                                .input(APPROVE_REQUEST_SCHEMA))
                        .addActionMetaData(Action.ASSIGN, new ActionMetadata()
                                .input(ASSIGN_REQUEST_SCHEMA))
                        .addActionMetaData(Action.REJECT, new ActionMetadata()
                                .input(FAIL_REQUEST_SCHEMA)
                                .action("fail"))
                        .addActionMetaData(Action.INQUIRE, new ActionMetadata()
                                .input(INQUIRE_REQUEST_SCHEMA))
                        .addActionMetaData(Action.PURCHASE, new ActionMetadata())
                        .addActionMetaData(Action.UNASSIGN, new ActionMetadata()
                                .includePayload(false))
                        .addActionMetaData(Action.PENDING, new ActionMetadata()
                                .action("pend")
                                .includePayload(false))
                        .addActionMetaData(Action.UPDATE, new ActionMetadata()
                                .customAction(false)
                                .input(UPDATE_REQUEST_SCHEMA)));

        METADATA_STORE.put(ResourceType.PURCHASE_REQUEST,
                new Metadata()
                        .collection(Collections.REQUESTS)
                        .id(REQUEST_ID)
                        .schema(FULFILLMENT_REQUEST_SCHEMA)
                        .addActionMetaData(Action.CREATE, new ActionMetadata()
                                .customAction(false)
                                .collectionAction(true)
                                .input(NEW_PURCHASE_REQUEST_SCHEMA)));

        METADATA_STORE.put(ResourceType.CHANGE_REQUEST,
                new Metadata()
                        .collection(Collections.REQUESTS)
                        .id(REQUEST_ID)
                        .schema(FULFILLMENT_REQUEST_SCHEMA)
                        .addActionMetaData(Action.CREATE, new ActionMetadata()
                                .customAction(false)
                                .collectionAction(true)
                                .input(NEW_CHANGE_REQUEST)));

        METADATA_STORE.put(ResourceType.ADMIN_HOLD_REQUEST,
                new Metadata()
                        .collection(Collections.REQUESTS)
                        .id(REQUEST_ID)
                        .schema(FULFILLMENT_REQUEST_SCHEMA)
                        .addActionMetaData(Action.CREATE, new ActionMetadata()
                                .customAction(false)
                                .collectionAction(true)
                                .input(NEW_ADMIN_HOLD_REQUEST_SCHEMA)));

        METADATA_STORE.put(ResourceType.BILLING_REQUEST,
                new Metadata()
                        .collection(Collections.REQUESTS)
                        .id(REQUEST_ID)
                        .schema(FULFILLMENT_REQUEST_SCHEMA)
                        .addActionMetaData(Action.CREATE,  new ActionMetadata()
                                .customAction(false)
                                .collectionAction(true)
                                .input(NEW_BILLING_REQUEST_SCHEMA)));

        METADATA_STORE.put(ResourceType.ASSET,
                new Metadata()
                        .collection(Collections.ASSETS)
                        .id(ASSET_ID)
                        .schema(ASSET_SCHEMA)
                        .includeGetAction()
                        .includeListAction());

        METADATA_STORE.put(ResourceType.SUBSCRIPTION_REQUEST,
                new Metadata()
                        .collection(Collections.SUBSCRIPTION_REQUESTS)
                        .id(REQUEST_ID)
                        .schema(FULFILLMENT_REQUEST_SCHEMA)
                        .includeGetAction()
                        .includeListAction());

        METADATA_STORE.put(ResourceType.SUBSCRIPTION_ASSET,
                new Metadata()
                        .collection(Collections.SUBSCRIPTION_ASSETS)
                        .id(ASSET_ID)
                        .schema(ASSET_SCHEMA)
                        .includeGetAction()
                        .includeListAction());

        METADATA_STORE.put(ResourceType.PRODUCT,
                new Metadata()
                        .collection(Collections.PRODUCTS)
                        .id(PRODUCT_ID)
                        .schema(PRODUCT_SCHEMA)
                        .includeGetAction()
                        .includeListAction());

        METADATA_STORE.put(ResourceType.PRODUCT_TEMPLATE,
                new Metadata()
                        .collection(Collections.TEMPLATES)
                        .id(TEMPLATE_ID)
                        .isSubCollection(true)
                        .parentCollection(Collections.PRODUCTS)
                        .parentId(PRODUCT_ID)
                        .schema(PRODUCT_TEMPLATE_SCHEMA)
                        .includeGetAction()
                        .includeListAction());

        METADATA_STORE.put(ResourceType.PRODUCT_ACTION,
                new Metadata()
                        .collection(Collections.ACTIONS)
                        .id(ACTION_ID)
                        .isSubCollection(true)
                        .parentCollection(Collections.PRODUCTS)
                        .parentId(PRODUCT_ID)
                        .schema(PRODUCT_ACTION_SCHEMA)
                        .includeGetAction()
                        .includeListAction());

        METADATA_STORE.put(ResourceType.PRODUCT_ITEM,
                new Metadata()
                        .collection(Collections.ITEMS)
                        .id(ITEM_ID)
                        .isSubCollection(true)
                        .parentCollection(Collections.PRODUCTS)
                        .parentId(PRODUCT_ID)
                        .schema(PRODUCT_ITEM_SCHEMA)
                        .includeGetAction()
                        .includeListAction());

        METADATA_STORE.put(ResourceType.PRODUCT_PARAMETER,
                new Metadata()
                        .collection(Collections.PARAMETERS)
                        .id(PARAMETER_ID)
                        .isSubCollection(true)
                        .parentCollection(Collections.PRODUCTS)
                        .parentId(PRODUCT_ID)
                        .schema(PRODUCT_PARAMETER_SCHEMA)
                        .includeGetAction()
                        .includeListAction());

        METADATA_STORE.put(ResourceType.PRODUCT_CONFIGURATION,
                new Metadata()
                        .collection(Collections.CONFIGURATIONS)
                        .isSubCollection(true)
                        .parentCollection(Collections.PRODUCTS)
                        .parentId(PRODUCT_ID)
                        .schema(PRODUCT_CONFIGURATION_SCHEMA)
                        .includeListAction());

        METADATA_STORE.put(ResourceType.USAGE_CHUNK,
                new Metadata()
                        .collection(Collections.CHUNKS)
                        .id(USAGE_CHUNK_ID)
                        .schema(CHUNK_FILE_SCHEMA)
                        .includeGetAction()
                        .includeListAction()
                        .addActionMetaData(Action.REGENERATE, new ActionMetadata()
                                .includePayload(false))
                        .addActionMetaData(Action.CLOSE, new ActionMetadata()
                                .input(CLOSE_CHUNK_SCHEMA))
                        .addActionMetaData(Action.DOWNLOAD, new ActionMetadata()
                                .includePayload(false))
                        .addActionMetaData(Action.UPDATE, new ActionMetadata()
                                .customAction(false)
                                .input(UPDATE_CHUNK_FILE_SCHEMA)));

        METADATA_STORE.put(ResourceType.USAGE_RECORD,
                new Metadata()
                        .collection(Collections.RECORDS)
                        .id(USAGE_RECORD_ID)
                        .schema(USAGE_RECORD_SCHEME)
                        .includeGetAction()
                        .includeListAction()
                        .addActionMetaData(Action.CLOSE, new ActionMetadata()
                                .input(CLOSE_RECORD_SCHEMA))
                        .addActionMetaData(Action.BULK_CLOSE, new ActionMetadata()
                                .output(RECORD_BULK_CLOSE_RESPONSE_SCHEMA)
                                .input(BULK_CLOSE_RECORD_SCHEMA)
                                .collectionAction(true)
                                .action("close-records")));

        METADATA_STORE.put(ResourceType.USAGE_REPORT,
                new Metadata()
                        .collection(Collections.FILES)
                        .id(USAGE_REPORT_ID)
                        .schema(USAGE_REPORT_SCHEMA)
                        .includeGetAction()
                        .includeListAction()
                        .addActionMetaData(Action.UPLOAD, new ActionMetadata()
                                .fileName("usage_file"))
                        .addActionMetaData(Action.UPLOAD_RECON_FILE, new ActionMetadata()
                                .fileName("recon_file")
                                .action("reconciliation"))
                        .addActionMetaData(Action.UPDATE, new ActionMetadata()
                                .customAction(false)
                                .input(UPDATE_USAGE_REPORT_SCHEMA))
                        .addActionMetaData(Action.CREATE, new ActionMetadata()
                                .customAction(false)
                                .collectionAction(true)
                                .input(NEW_USAGE_REPORT_SCHEMA))
                        .addActionMetaData(Action.CLOSE, new ActionMetadata()
                                .includePayload(false))
                        .addActionMetaData(Action.SUBMIT, new ActionMetadata()
                                .includePayload(false))
                        .addActionMetaData(Action.REPROCESS, new ActionMetadata()
                                .includePayload(false))
                        .addActionMetaData(Action.DELETE, new ActionMetadata()
                                .includePayload(false)
                                .action(Action.DELETE.name().toLowerCase()))
                        .addActionMetaData(Action.REJECT, new ActionMetadata()
                                .input(REJECT_REPORT_SCHEMA))
                        .addActionMetaData(Action.ACCEPT, new ActionMetadata()
                                .input(ACCEPT_REPORT_SCHEMA)));

        METADATA_STORE.put(ResourceType.USAGE_RECONCILIATION,
                new Metadata()
                        .collection(Collections.RECONCILIATIONS)
                        .id(USAGE_RECON_ID)
                        .schema(USAGE_RECON_SCHEMA)
                        .includeGetAction()
                        .includeListAction()
                        .addActionMetaData(Action.UPLOAD, new ActionMetadata()
                                .fileName("recon_file")
                                .formAttributes(UPLOAD_NOTE)
                                .customAction(false)
                                .collectionAction(true)));

        METADATA_STORE.put(ResourceType.RECONCILIATION_PROCESSED_FILE,
                new Metadata()
                        .collection(Collections.RECONCILIATIONS)
                        .id(USAGE_RECON_ID)
                        .schema(USAGE_RECON_SCHEMA)
                        .addActionMetaData(Action.DOWNLOAD, new ActionMetadata()
                                .action("processedfile")
                                .includePayload(false)));

        METADATA_STORE.put(ResourceType.RECONCILIATION_UPLOADED_FILE,
                new Metadata()
                        .collection(Collections.RECONCILIATIONS)
                        .id(USAGE_RECON_ID)
                        .schema(USAGE_RECON_SCHEMA)
                        .addActionMetaData(Action.DOWNLOAD, new ActionMetadata()
                                .action("uploadedfile")
                                .includePayload(false)));

        METADATA_STORE.put(ResourceType.USAGE_AGGREGATE,
                new Metadata()
                        .collection(Collections.AGGREGATES)
                        .schema(USAGE_AGGREGATE_SCHEMA)
                        .includeListAction());

        METADATA_STORE.put(ResourceType.CASE,
                new Metadata()
                        .collection(Collections.CASES)
                        .id(CASE_ID)
                        .schema(CASE_SCHEMA)
                        .includeGetAction()
                        .includeListAction()
                        .addActionMetaData(Action.PENDING, new ActionMetadata()
                                .action("pend")
                                .includePayload(false))
                        .addActionMetaData(Action.INQUIRE, new ActionMetadata()
                                .includePayload(false))
                        .addActionMetaData(Action.RESOLVE, new ActionMetadata()
                                .includePayload(false))
                        .addActionMetaData(Action.CLOSE, new ActionMetadata()
                                .input(CLOSE_CASE_SCHEMA))
                        .addActionMetaData(Action.CREATE, new ActionMetadata()
                                .collectionAction(true)
                                .customAction(false)
                                .input(NEW_CASE_SCHEMA)));

        METADATA_STORE.put(ResourceType.ASSET_USAGE_AGGREGATE,
                new Metadata()
                        .collection(Collections.AGGREGATES)
                        .isSubCollection(true)
                        .parentCollection(Collections.ASSETS)
                        .parentId(ASSET_ID)
                        .schema(USAGE_AGGREGATE_SCHEMA)
                        .includeListAction());

        METADATA_STORE.put(ResourceType.CONVERSATION_MESSAGES,
                new Metadata()
                        .collection(Collections.MESSAGES)
                        .id(MESSAGE_ID)
                        .isSubCollection(true)
                        .parentCollection(Collections.CONVERSATION)
                        .parentId(CONVERSATION_ID)
                        .schema(CONVERSATION_MESSAGES_SCHEMA)
                        .includeListAction()
                        .addActionMetaData(Action.CREATE, new ActionMetadata()
                                .customAction(false)
                                .collectionAction(true)
                                .input(NEW_CONVERSATION_MESSAGES_SCHEMA)));

        METADATA_STORE.put(ResourceType.PRODUCT_ACTION_LINK,
                new Metadata()
                        .collection(Collections.ACTIONS)
                        .id(ACTION_ID)
                        .isSubCollection(true)
                        .parentCollection(Collections.PRODUCTS)
                        .parentId(PRODUCT_ID)
                        .schema(PRODUCT_ACTION_LINK_SCHEMA)
                        .includeGetAction(ASSET_ID));

        METADATA_STORE.put(ResourceType.BILLING_REQUEST_ATTRIBUTE,
                new Metadata()
                        .collection(Collections.ATTRIBUTES)
                        .isSubCollection(true)
                        .parentCollection(Collections.SUBSCRIPTION_REQUESTS)
                        .parentId(REQUEST_ID)
                        .schema(BILLING_REQUEST_ATTRIBUTES_SCHEMA)
                        .addActionMetaData(Action.UPDATE,
                                new ActionMetadata()
                                        .collectionAction(true)
                                        .input(BILLING_REQUEST_ATTRIBUTES_SCHEMA)));
    }

    private MetadataUtil() {}

    public static Metadata getMetadata(String resourceType) {
        ResourceType type = ResourceType.valueOf(resourceType.toUpperCase());
        return METADATA_STORE.get(type);
    }

    public static ActionMetadata getActionMetadata(String resourceType, String action) {
        Metadata metadata = getMetadata(resourceType);
        return metadata.getActionMetadata()
                .get(Action.valueOf(action.toUpperCase()));
    }

    public static Map<ResourceType, Metadata> getMetadataStore() {
        return METADATA_STORE;
    }
}
