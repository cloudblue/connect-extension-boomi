/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.browser.metadata;

public enum Action {
    GET(true), LIST(false), CREATE(false),
    UPDATE(true), DELETE(true), ACCEPT(true),
    IGNORE(true), APPROVE(true), INQUIRE(true),
    PENDING(true), PEND(true), FAIL(true),
    ASSIGN(true), UNASSIGN(true), PURCHASE(true),
    CLOSE(true), REGENERATE(true), BULK_CLOSE(false),
    REJECT(true), REPROCESS(true), SUBMIT(true),
    CREATE_PURCHASE_REQUEST(false), CREATE_ADMIN_HOLD_REQUEST(false),
    CREATE_BILLING_REQUEST(false), DOWNLOAD(true),
    DOWNLOAD_UPLOADED_FILE(true), DOWNLOAD_PROCESSED_FILE(true),
    UPLOAD(true), UPLOAD_RECON_FILE(true), RESOLVE(true);

    boolean isDetailOperation;

    Action(boolean isDetailOperation) {
        this.isDetailOperation = isDetailOperation;
    }

    public boolean isDetailOperation() {
        return isDetailOperation;
    }
}
