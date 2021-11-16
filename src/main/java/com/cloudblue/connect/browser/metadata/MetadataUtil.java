/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.browser.metadata;

import java.util.*;
import java.util.stream.Collectors;

import static com.cloudblue.connect.client.constants.APIConstants.CollectionKeys.*;
import static com.cloudblue.connect.browser.metadata.Key.*;

public class MetadataUtil {
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
                        .collection(TIER_ACCOUNTS)
                        .id(Key.TA_ID)
                        .schema(TIER_ACCOUNT_SCHEMA)
                        .includeGetAction()
                        .includeListAction()
                        .addActionMetaData(Action.CREATE, new ActionMetadata()
                                .customAction(false)
                                .input(NEW_TIER_ACCOUNT_SCHEMA))
                        .addActionMetaData(Action.UPDATE, new ActionMetadata()
                                .customAction(false)
                                .input(NEW_TIER_ACCOUNT_SCHEMA)));

        METADATA_STORE.put(ResourceType.TIER_ACCOUNT_VERSION,
                new Metadata()
                        .collection(VERSIONS)
                        .id(TA_VERSION_ID)
                        .isSubCollection(true)
                        .parentCollection(TIER_ACCOUNTS)
                        .parentId(TA_ID)
                        .schema(TIER_ACCOUNT_SCHEMA)
                        .includeGetAction()
                        .includeListAction());

        METADATA_STORE.put(ResourceType.TIER_ACCOUNT_REQUEST,
                new Metadata()
                        .collection(ACCOUNT_REQUESTS)
                        .id(TAR_ID)
                        .schema(TIER_ACCOUNT_REQUEST_SCHEMA)
                        .includeGetAction()
                        .includeListAction()
                        .addActionMetaData(Action.CREATE, new ActionMetadata()
                                .customAction(false)
                                .input(NEW_TIER_ACCOUNT_REQUEST_SCHEMA))
                        .addActionMetaData(Action.ACCEPT, new ActionMetadata()
                                .includePayload(false))
                        .addActionMetaData(Action.IGNORE, new ActionMetadata()
                                .input(IGNORE_TAR_SCHEMA)));

        METADATA_STORE.put(ResourceType.TIER_CONFIG_REQUEST,
                new Metadata()
                        .collection(CONFIG_REQUESTS)
                        .id(TCR_ID)
                        .schema(TIER_CONFIG_REQUEST_SCHEMA)
                        .includeGetAction()
                        .includeListAction()
                        .addActionMetaData(Action.CREATE, new ActionMetadata()
                                .customAction(false)
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
                        .collection(TIER_CONFIG)
                        .id(TC_ID)
                        .schema(TIER_CONFIG_SCHEMA)
                        .includeGetAction()
                        .includeListAction());

        METADATA_STORE.put(ResourceType.REQUEST,
                new Metadata()
                        .collection(REQUESTS)
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
                        .collection(REQUESTS)
                        .id(REQUEST_ID)
                        .schema(FULFILLMENT_REQUEST_SCHEMA)
                        .addActionMetaData(Action.CREATE, new ActionMetadata()
                                .customAction(false)
                                .collectionAction(true)
                                .input(NEW_PURCHASE_REQUEST_SCHEMA)));

        METADATA_STORE.put(ResourceType.CHANGE_REQUEST,
                new Metadata()
                        .collection(REQUESTS)
                        .id(REQUEST_ID)
                        .schema(FULFILLMENT_REQUEST_SCHEMA)
                        .addActionMetaData(Action.CREATE, new ActionMetadata()
                                .customAction(false)
                                .collectionAction(true)
                                .input(NEW_CHANGE_REQUEST)));

        METADATA_STORE.put(ResourceType.ADMIN_HOLD_REQUEST,
                new Metadata()
                        .collection(REQUESTS)
                        .id(REQUEST_ID)
                        .schema(FULFILLMENT_REQUEST_SCHEMA)
                        .addActionMetaData(Action.CREATE, new ActionMetadata()
                                .customAction(false)
                                .collectionAction(true)
                                .input(NEW_ADMIN_HOLD_REQUEST_SCHEMA)));

        METADATA_STORE.put(ResourceType.BILLING_REQUEST,
                new Metadata()
                        .collection(REQUESTS)
                        .id(REQUEST_ID)
                        .schema(FULFILLMENT_REQUEST_SCHEMA)
                        .addActionMetaData(Action.CREATE,  new ActionMetadata()
                                .customAction(false)
                                .collectionAction(true)
                                .input(NEW_BILLING_REQUEST_SCHEMA)));

        METADATA_STORE.put(ResourceType.ASSET,
                new Metadata()
                        .collection(ASSETS)
                        .id(ASSET_ID)
                        .schema(ASSET_SCHEMA)
                        .includeGetAction()
                        .includeListAction());

        METADATA_STORE.put(ResourceType.SUBSCRIPTION_REQUEST,
                new Metadata()
                        .collection(SUBSCRIPTION_REQUESTS)
                        .id(REQUEST_ID)
                        .schema(FULFILLMENT_REQUEST_SCHEMA)
                        .includeGetAction()
                        .includeListAction());

        METADATA_STORE.put(ResourceType.SUBSCRIPTION_ASSET,
                new Metadata()
                        .collection(SUBSCRIPTION_ASSETS)
                        .id(ASSET_ID)
                        .schema(ASSET_SCHEMA)
                        .includeGetAction()
                        .includeListAction());

        METADATA_STORE.put(ResourceType.PRODUCT,
                new Metadata()
                        .collection(PRODUCTS)
                        .id(PRODUCT_ID)
                        .schema(PRODUCT_SCHEMA)
                        .includeGetAction()
                        .includeListAction());

        METADATA_STORE.put(ResourceType.PRODUCT_TEMPLATE,
                new Metadata()
                        .collection(TEMPLATES)
                        .id(TEMPLATE_ID)
                        .isSubCollection(true)
                        .parentCollection(PRODUCTS)
                        .parentId(PRODUCT_ID)
                        .schema(PRODUCT_TEMPLATE_SCHEMA)
                        .includeGetAction()
                        .includeListAction());

        METADATA_STORE.put(ResourceType.PRODUCT_ACTION,
                new Metadata()
                        .collection(ACTIONS)
                        .id(ACTION_ID)
                        .isSubCollection(true)
                        .parentCollection(PRODUCTS)
                        .parentId(PRODUCT_ID)
                        .schema(PRODUCT_ACTION_SCHEMA)
                        .includeGetAction()
                        .includeListAction());

        METADATA_STORE.put(ResourceType.PRODUCT_ITEM,
                new Metadata()
                        .collection(ITEMS)
                        .id(ITEM_ID)
                        .isSubCollection(true)
                        .parentCollection(PRODUCTS)
                        .parentId(PRODUCT_ID)
                        .schema(PRODUCT_ITEM_SCHEMA)
                        .includeGetAction()
                        .includeListAction());

        METADATA_STORE.put(ResourceType.PRODUCT_PARAMETER,
                new Metadata()
                        .collection(PARAMETERS)
                        .id(PARAMETER_ID)
                        .isSubCollection(true)
                        .parentCollection(PRODUCTS)
                        .parentId(PRODUCT_ID)
                        .schema(PRODUCT_PARAMETER_SCHEMA)
                        .includeGetAction()
                        .includeListAction());

        METADATA_STORE.put(ResourceType.PRODUCT_CONFIGURATION,
                new Metadata()
                        .collection(CONFIGURATIONS)
                        .isSubCollection(true)
                        .parentCollection(PRODUCTS)
                        .parentId(PRODUCT_ID)
                        .schema(PRODUCT_CONFIGURATION_SCHEMA)
                        .includeListAction());

        METADATA_STORE.put(ResourceType.USAGE_CHUNK,
                new Metadata()
                        .collection(CHUNKS)
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
                                .input(UPDATE_CHUNK_FILE_SCHEMA)));

        METADATA_STORE.put(ResourceType.USAGE_RECORD,
                new Metadata()
                        .collection(RECORDS)
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
                        .collection(FILES)
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
                        .collection(RECONCILIATIONS)
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
                        .collection(RECONCILIATIONS)
                        .id(USAGE_RECON_ID)
                        .schema(USAGE_RECON_SCHEMA)
                        .addActionMetaData(Action.DOWNLOAD, new ActionMetadata()
                                .action("processedfile")
                                .includePayload(false)));

        METADATA_STORE.put(ResourceType.RECONCILIATION_UPLOADED_FILE,
                new Metadata()
                        .collection(RECONCILIATIONS)
                        .id(USAGE_RECON_ID)
                        .schema(USAGE_RECON_SCHEMA)
                        .addActionMetaData(Action.DOWNLOAD, new ActionMetadata()
                                .action("uploadedfile")
                                .includePayload(false)));

        METADATA_STORE.put(ResourceType.USAGE_AGGREGATE,
                new Metadata()
                        .collection(AGGREGATES)
                        .schema(USAGE_AGGREGATE_SCHEMA)
                        .includeListAction());

        METADATA_STORE.put(ResourceType.CASE,
                new Metadata()
                        .collection(CASES)
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
                                .input(NEW_CASE_SCHEMA)));

        METADATA_STORE.put(ResourceType.ASSET_USAGE_AGGREGATE,
                new Metadata()
                        .collection(AGGREGATES)
                        .isSubCollection(true)
                        .parentCollection(ASSETS)
                        .parentId(ASSET_ID)
                        .schema(USAGE_AGGREGATE_SCHEMA)
                        .includeListAction());

        METADATA_STORE.put(ResourceType.CONVERSATION_MESSAGES,
                new Metadata()
                        .collection(MESSAGES)
                        .id(MESSAGE_ID)
                        .isSubCollection(true)
                        .parentCollection(CONVERSATION)
                        .parentId(CONVERSATION_ID)
                        .schema(CONVERSATION_MESSAGES_SCHEMA)
                        .includeListAction()
                        .addActionMetaData(Action.CREATE, new ActionMetadata()
                                .input(NEW_CONVERSATION_MESSAGES_SCHEMA)));

        METADATA_STORE.put(ResourceType.PRODUCT_ACTION_LINK,
                new Metadata()
                        .collection(ACTIONS)
                        .id(ACTION_ID)
                        .isSubCollection(true)
                        .parentCollection(PRODUCTS)
                        .parentId(PRODUCT_ID)
                        .schema(PRODUCT_ACTION_LINK_SCHEMA)
                        .includeGetAction(ASSET_ID));

        METADATA_STORE.put(ResourceType.BILLING_REQUEST_ATTRIBUTE,
                new Metadata()
                        .collection(ATTRIBUTES)
                        .isSubCollection(true)
                        .parentCollection(SUBSCRIPTION_REQUESTS)
                        .parentId(REQUEST_ID)
                        .schema(BILLING_REQUEST_ATTRIBUTES_SCHEMA)
                        .addActionMetaData(Action.UPDATE,
                                new ActionMetadata()
                                        .collectionAction(true)
                                        .input(BILLING_REQUEST_ATTRIBUTES_SCHEMA)));
    }

    private MetadataUtil() {}

    private static List<String> getSpecificResourceAction(String resourceType, Action[] allActions) {
        ResourceType type = ResourceType.valueOf(resourceType.toUpperCase());
        Metadata collectionInfo = METADATA_STORE.get(type);

        if (collectionInfo == null || collectionInfo.getActionMetadata().isEmpty()) {
            return new ArrayList<>();
        } else {
            return collectionInfo.getActionMetadata()
                    .keySet()
                    .stream()
                    .filter(action -> Arrays.stream(allActions)
                            .anyMatch(abstractAction -> action == abstractAction))
                    .map(Enum::name)
                    .collect(Collectors.toList());
        }
    }

    public static List<String> getDownloadActions(String resourceType) {
        return getSpecificResourceAction(resourceType, Action.getDownloadActions());
    }

    public static List<String> getUploadActions(String resourceType) {
        return getSpecificResourceAction(resourceType, Action.getUploadActions());
    }

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
