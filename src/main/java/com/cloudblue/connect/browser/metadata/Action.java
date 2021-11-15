/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.browser.metadata;

public enum Action {
    GET(true), LIST(false),
    CREATE(false), UPDATE(true),
    DELETE(true), ACCEPT(true),
    IGNORE(true), APPROVE(true),
    REJECT(true), UPLOAD(true),
    INQUIRE(true), PENDING(true),
    ASSIGN(true), UNASSIGN(true),
    PURCHASE(true), CLOSE(true),
    REGENERATE(true), BULK_CLOSE(false),
    REPROCESS(true), SUBMIT(true),

    RESOLVE(true),
    DOWNLOAD(true),
    UPLOAD_RECON_FILE(true);

    boolean isDetailOperation;

    Action(boolean isDetailOperation) {
        this.isDetailOperation = isDetailOperation;
    }

    public boolean isDetailOperation() {
        return isDetailOperation;
    }

    public static Action[] getDownloadActions() {
        return new Action[] {
                DOWNLOAD,
        };
    }

    public static Action[] getUploadActions() {
        return new Action[] {
                UPLOAD,
                UPLOAD_RECON_FILE,
        };
    }
}
