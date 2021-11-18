/*
 * Copyright © 2021 Ingram Micro Inc. All rights reserved.
 * The software in this package is published under the terms of the Apache-2.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE file.
 */

package com.cloudblue.connect.operations;

import com.boomi.util.json.JSONUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class JsonUtil{
    public static final Logger LOGGER = Logger.getLogger("JsonUtil");

    private JsonUtil() {}

    public static String getFieldStringValue(InputStream inputStream, String fieldName) {
        String value = null;
        try (JsonParser parser = JSONUtil.newInitializedParser(inputStream)){

            while(!parser.isClosed()){
                JsonToken jsonToken = parser.nextToken();

                if(JsonToken.FIELD_NAME.equals(jsonToken)
                        && fieldName.equals(parser.getCurrentName())){
                    parser.nextToken();
                    value = parser.getValueAsString();
                    break;
                }
            }
        } catch (IOException e) {
            LOGGER.severe("Error while parsing JSON to get field value.");
        }

        return value;
    }
}
