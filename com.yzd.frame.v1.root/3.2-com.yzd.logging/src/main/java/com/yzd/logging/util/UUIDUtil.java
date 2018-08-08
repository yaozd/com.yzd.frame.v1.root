package com.yzd.logging.util;

import java.util.UUID;

public class UUIDUtil {
    public static String getUUID()
    {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "").toUpperCase();
        return uuid;
    }
}
