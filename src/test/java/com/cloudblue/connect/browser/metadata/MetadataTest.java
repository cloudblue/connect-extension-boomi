/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.browser.metadata;

import org.junit.Assert;
import org.junit.Test;

public class MetadataTest {

    @Test
    public void testGetPathBlankParentId() {
        Metadata metadata = MetadataUtil.getMetadata("PRODUCT_ITEM");
        String path = metadata.getPath("PRD-000-000-000-0001", "", "action", null);

        Assert.assertEquals("items/PRD-000-000-000-0001/action", path);
    }

    @Test
    public void testGetPathBlankId() {
        Metadata metadata = MetadataUtil.getMetadata("PRODUCT_ITEM");
        String path = metadata.getPath("", "PRD-000-000-000", "action", null);

        Assert.assertEquals("products/PRD-000-000-000/items/action", path);
    }
}
