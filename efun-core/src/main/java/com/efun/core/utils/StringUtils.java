package com.efun.core.utils;

import java.security.SecureRandom;
import java.util.regex.Pattern;

/**
 * StringUtils
 *
 * @author Galen
 * @since 2016/05/30
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final Pattern PATTERN_EMAIL_ADDR = Pattern.compile("^[a-zA-Z0-9_\\-]+(\\.[_a-zA-Z0-9\\-]+)*@([_a-z0-9\\-]+\\.)+([a-z]{2}|aero|arpa|biz|com|coop|edu|gov|info|int|jobs|mil|museum|name|nato|net|org|pro|travel)$");

    public static final String CHARACTER_TYPE_DIGIT = "DIGIT";

    public static final String CHARACTER_TYPE_LETTER = "LETTER";

    public static boolean isEmail(String email) {
        return isNotBlank(email) && PATTERN_EMAIL_ADDR.matcher(email).matches();
    }

    public static final String getRandomString(int size) {
        return getRandomString(size, "");
    }

    /**
     * 获取随机数
     * @param size
     * @param characterType
     * @return
     */
    public static final String getRandomString(int size, String characterType) {
        if (size <= 0) {
            size = 6;
        }
        String str;
        switch (characterType) {
            case CHARACTER_TYPE_DIGIT:
                str = "0123456789";
                break;
            case CHARACTER_TYPE_LETTER:
                str = "qwertyuipasdfghjkzxcvbnm";
                break;
            default:
                str = "0123456789qwertyuipasdfghjkzxcvbnm";
                break;
        }

        StringBuilder strBuilder = new StringBuilder();
        int strLenth = str.length();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < size; i++) {
            strBuilder.append(str.charAt(random.nextInt(strLenth)));
        }
        return strBuilder.toString();
    }

}
