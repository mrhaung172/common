package com.util;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StrUtil {

    /**
     * 判断字符串数组中是否存在指定字符串
     *
     * @param strArr str
     * @return
     */
    public static boolean isContain(String[] strArr, String str) {
        boolean isContain = false;
        if (strArr == null || strArr.length == 0) {
            return isContain;
        }

        List strList = Arrays.asList(strArr);
        if (strList.contains(str)) {
            isContain = true;
        }

        return isContain;
    }

    /**
     * 判断字符串前缀是否在字符串数组中
     *
     * @param
     * @return
     */
    public static boolean isPrefixExist(String str, String prefixs, String split) {
        boolean isPrefixExist = false;

        if (StringUtils.isEmpty(prefixs) || StringUtils.isEmpty(str)) {
            return isPrefixExist;
        }

        String[] strArr = prefixs.split(split);

        for (String e : strArr) {
            if (str.toLowerCase().startsWith(e.toLowerCase())) {
                isPrefixExist = true;
                break;
            }
        }

        return isPrefixExist;
    }


    public static String substr(String source, int length) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }

        if (source.length() > length) {
            return source.substring(0, length);
        } else {
            return source;
        }
    }

    /**
     * 获取指定key所对应值的长度限制
     *
     * @param key
     * @return
     */
    public static int getValueLength(String key) {
        int length = 0;

        if (StringUtils.isEmpty(key)) {
            return length;
        }

        if ("RSID".equals(key)) {
            length = 20;
        } else if ("TUID".equals(key)) {
            length = 50;
        } else if ("WMID".equals(key)) {
            length = 50;
        } else if ("FLP".equals(key)) {
            length = 256;
        } else if ("RLP".equals(key)) {
            length = 256;
        } else if ("FRP".equals(key)) {
            length = 256;
        } else if ("RRP".equals(key)) {
            length = 256;
        } else if ("FSID".equals(key)) {
            length = 20;
        } else if ("CID".equals(key)) {
            length = 50;
        } else if ("RGP".equals(key)) {
            length = 256;
        } else if ("SIGN".equals(key)) {
            length = 32;
        } else if ("CALLBACK_MSG".equals(key)) {
            length = 200;
        } else {
            length = 20;
        }

        return length;
    }

    public static String[] getData(String str, String regExp) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        String returnValue = "";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            returnValue = matcher.group(1);
        }

        return returnValue != null ? returnValue.split(",") : null;
    }

    public static String getRandomString(int length) {
        StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        int range = buffer.length();
        for (int i = 0; i < length; i++) {
            sb.append(buffer.charAt(random.nextInt(range)));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        substr(null, 1);
    }


}
