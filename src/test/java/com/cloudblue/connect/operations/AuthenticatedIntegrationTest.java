/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.operations;

import org.junit.BeforeClass;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public abstract class AuthenticatedIntegrationTest {
    private static final String API_URL_KEY = "ApiUrl";
    private static final String VENDOR_TOKEN = "VendorToken";
    private static final String DIST_TOKEN = "DistributorToken";
    private static final String URL_PROPERTY = "url";
    private static final String TOKEN_PROPERTY = "customAuthCredentials";

    private static String apiUrl;
    private static String vendorToken;
    private static String distributorToken;

    public static String getPropertyValue(String property, Properties properties) {
        String value = properties.getProperty(property, null);

        if (value == null || value.isEmpty()) {
            value = System.getenv(property);
        }

        return value;
    }

    @BeforeClass
    public static void init() throws IOException {
        Properties properties = new Properties();

        ClassLoader classLoader = AuthenticatedIntegrationTest.class.getClassLoader();
        properties.load(new FileReader(Objects.requireNonNull(
                classLoader.getResource("credentials.properties")).getFile()));

        apiUrl = getPropertyValue(API_URL_KEY, properties);
        vendorToken = getPropertyValue(VENDOR_TOKEN, properties);
        distributorToken = getPropertyValue(DIST_TOKEN, properties);
    }

    protected void addVendorConnProperties(ConnectConnectorTestContext testContext) {
        testContext.addConnectionProperty(URL_PROPERTY, apiUrl);
        testContext.addConnectionProperty(TOKEN_PROPERTY, vendorToken);
    }

    protected void addDistConnProperties(ConnectConnectorTestContext testContext) {
        testContext.addConnectionProperty(URL_PROPERTY, apiUrl);
        testContext.addConnectionProperty(TOKEN_PROPERTY, distributorToken);
    }
}
