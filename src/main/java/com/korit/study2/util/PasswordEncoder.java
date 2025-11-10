package com.korit.study2.util;

import java.util.Objects;

public class PasswordEncoder {
    public static final String secret = "암호키";
    public static String encode (String str) {
        if (str == null) {
            return null;
        }
        long hash = Integer.toUnsignedLong(Objects.hash(str));
        long encodedHash = hash + Objects.hash(secret);
        return Long.toHexString(encodedHash);
    }

    public static boolean match(String inputPW, String userinfoPW) {
        if (inputPW == null || userinfoPW == null) {
            return false;
        }
        return encode(inputPW).equals(userinfoPW);
    }

}
