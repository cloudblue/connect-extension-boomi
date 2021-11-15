/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.browser.metadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActionMetadata {

    private String output;
    private String input;
    private String action;
    private boolean includePayload = true;
    private boolean collectionAction = false;
    private final List<Key> filters = new ArrayList<>();
    private final List<Key> formAttributes = new ArrayList<>();
    private String fileName;
    private boolean customAction = true;

    public ActionMetadata output(String output) {
        this.output = output;
        return this;
    }

    public ActionMetadata input(String input) {
        this.input = input;
        return this;
    }

    public ActionMetadata action(String action) {
        this.action = action;
        return this;
    }

    public ActionMetadata includePayload(boolean includePayload) {
        this.includePayload = includePayload;
        return this;
    }

    public ActionMetadata collectionAction(boolean collectionAction) {
        this.collectionAction = collectionAction;
        return this;
    }

    public ActionMetadata filter(Key... filter) {
        this.filters.addAll(Arrays.asList(filter));

        return this;
    }

    public ActionMetadata formAttributes(Key... attributes) {
        this.formAttributes.addAll(Arrays.asList(attributes));

        return this;
    }

    public ActionMetadata fileName(String fileName) {
        this.fileName = fileName;

        return this;
    }

    public ActionMetadata customAction(boolean customAction) {
        this.customAction = customAction;

        return this;
    }

    public String getOutput() {
        return output;
    }

    public String  getInput() {
        return input;
    }

    public String getAction() {
        return action;
    }

    public boolean isIncludePayload() {
        return includePayload;
    }

    public boolean isCollectionAction() {
        return collectionAction;
    }

    public List<Key> getFilters() {
        return filters;
    }

    public List<Key> getFormAttributes() {
        return formAttributes;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isCustomAction() {
        return customAction;
    }
}
