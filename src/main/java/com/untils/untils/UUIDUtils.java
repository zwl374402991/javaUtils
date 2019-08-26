package com.untils.untils;

import java.util.UUID;

public class UUIDUtils {

    /**
     * 
     * @return uuid
     */
    public static String genUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
