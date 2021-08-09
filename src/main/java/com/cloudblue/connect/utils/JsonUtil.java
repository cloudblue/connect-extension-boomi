package com.cloudblue.connect.utils;

import com.boomi.util.json.JSONUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class JsonUtil {
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
