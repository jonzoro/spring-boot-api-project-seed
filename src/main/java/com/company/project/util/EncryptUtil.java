package com.company.project.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Base64;

public class EncryptUtil {

    private EncryptUtil() {}

    /**
     * MD5方法
     */
    public static String md5(String text, String key) {
        //加密后的字符串
        return DigestUtils.md5Hex(text + key);
    }

    /**
     * MD5验证方法
     */
    public static boolean verifyMD5(String raw, String key, String md5) {
        //根据传入的密钥进行验证
        String md5Text = md5(raw, key);
        return md5Text.equalsIgnoreCase(md5);
    }

    /***
     * base64编码
     */
    public static String base64Encode(String content) {
        return Base64.getEncoder().encodeToString(content.getBytes());
    }

    /***
     * base64编码
     */
    public static byte[] base64Decode(String content) {
        return Base64.getDecoder().decode(content);
    }

}
