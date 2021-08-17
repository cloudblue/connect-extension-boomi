package com.cloudblue.connect.utils;

import com.cloudblue.connect.browser.ConnectBrowser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FileUtil {
    private FileUtil() {}

    public static String readJsonSchema(String fileName) throws IOException {
        try (InputStream is = ConnectBrowser.class.getResourceAsStream("/schemas/" + fileName)) {
            return toString(Objects.requireNonNull(is), StandardCharsets.UTF_8.toString());
        }
    }

    private static String toString(InputStream in, String charsetName) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        for( int len; ( len = in.read(buf) ) != -1; ) {
            bout.write(buf, 0, len);
        }
        return bout.toString(charsetName);
    }
}
