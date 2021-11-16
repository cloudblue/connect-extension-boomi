/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.utils;

import com.boomi.connector.api.ConnectorException;
import com.boomi.connector.api.DynamicPropertyMap;

public class Common {

    private Common() {}

    public static String getDynamicPropertyValue(
            DynamicPropertyMap propertyMap,
            String property) {
        String value;

        value = propertyMap.getProperty(property, null);

        if (value == null || value.isEmpty()) {
            throw new ConnectorException(
                    String.format("Value for dynamic property %s is required.", property));
        }

        return value;
    }
}
