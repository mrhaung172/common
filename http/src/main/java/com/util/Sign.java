package com.util;

import com.pp100.model.domain.SignDataItemEnum;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.util.StringUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;

public class Sign {
    private static final Logger logger = Logger.getLogger(Sign.class);

    public static void main(String[] args) {
        Map map = new LinkedMap();
        map.put("test", "ybjr");
        map.put("partner", "xtz");
        map.put("project_id", "3308");

        logger.info(signMd5("123"));
        logger.info(sign("123", "MD5"));
        logger.info(sign("123", "SHA-1"));
        logger.info(sign("123", "SHA-256"));
    }

    ;

    /*
        public static Map<String, String> sign(String jsapi_ticket, String url) {
            Map<String, String> ret = new HashMap<String, String>();
            String nonce_str = create_nonce_str();
            String timestamp = create_timestamp();
            String string1;
            String signature = "";

            // 注意这里参数名必须全部小写，且必须有序
            string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
            logger.info("加密前字符串" + string1);
            signature = sha1(string1);
            logger.info("加密后字符串" + signature);

            ret.put("url", url);
            ret.put("jsapi_ticket", jsapi_ticket);
            ret.put("nonceStr", nonce_str);
            ret.put("timestamp", timestamp);
            ret.put("signature", signature);
            System.out.println(string1);

            try {
                MessageDigest crypt = MessageDigest.getInstance("SHA-1");
                crypt.reset();
                crypt.update(string1.getBytes("UTF-8"));
                signature = byteToHex(crypt.digest());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            ret.put("url", url);
            ret.put("jsapi_ticket", jsapi_ticket);
            ret.put("nonceStr", nonce_str);
            ret.put("timestamp", timestamp);
            ret.put("signature", signature);

            return ret;
        }
    */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     * 加签 MD5 3DES可选 可控制参数排序. 若排序，规则为：将key按字母表升序排列，以&分隔，尾+key值
     *
     * @param signParamMap
     * @param md5Key
     * @param keyIndex
     * @param threeDesKey
     * @param encryptType
     * @return add by hof_20151113_1212
     */
    public static String sign(Map signParamMap, String md5Key, String threeDesKey, String encryptType, SignDataItemEnum signDataItem,
                              boolean isSort, boolean isIncludeKey) {
        if (signParamMap == null || signParamMap.isEmpty()) {
            logger.info("无需要加签的数据" + signParamMap);
            return null;
        }

        String original = "";
        String[] s = new String[signParamMap.size()];

        if (SignDataItemEnum.SIGN_WITH_KEY_VALUE.equals(signDataItem)) {
            s = Converter.map2ArrayWithKeyValue(signParamMap);
        } else {
            s = (String[]) signParamMap.values().toArray(s);
        }

        if (isSort) {
            Arrays.sort(s);
        }

        for (String string : s) {
            if (SignDataItemEnum.SIGN_WITH_ONLY_VALUE.equals(signDataItem)) {
                original += string;
            } else {
                original += string + "&";
            }
        }

        if (isIncludeKey) {
            if (SignDataItemEnum.SIGN_WITH_ONLY_VALUE.equals(signDataItem)) {
                original = original + md5Key;
            } else {
                original = original + "key=" + md5Key;
            }
        } else if (original.endsWith("&")) {
            original = original.substring(0, original.length() - 1);
        }

        String sign = sign(original, md5Key, threeDesKey, encryptType);

        return sign;
    }

    /**
     * 加签 MD5 3DES可选
     *
     * @param original
     * @param md5Key
     * @param threeDesKey
     * @param encryptType
     * @return add by hof_20151201_1145
     */
    public static String sign(String original, String md5Key, String threeDesKey, String encryptType) {
        String sign = "";

        logger.info("参与加签字符串：" + original);
        /*
         * if(threeDesKey!=null && Constants.THREE_DES.equals(encryptType) ){ sign = Encrypt(original, threeDesKey); } else
         */
        if (Constants.SHA1.equals(encryptType)) {
            sign = sha1(original);
        } else {
            sign = DigestUtils.md5Hex(original);
        }

        logger.info("加签后字符串：" + sign);
        return sign;
    }

    /**
     * 加签sha1加密算法
     *
     * @param original
     */
    public static String sha1(String original) {

        String signature = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(original.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            logger.error("sha1算法加密异常", e);
            return null;
        } catch (UnsupportedEncodingException e) {
            logger.error("不支持编码类型", e);
            return null;
        }
        return signature;

    }

    @SuppressWarnings("rawtypes")
    public static String map2ArrayWithKeyValue(Map map) {
        String sign = "";
        if (map == null) {
            return null;
        }
        String[] arr = new String[map.size()];
        int index = 0;
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Entry entry = (Entry) iter.next();
            arr[index] = entry.getKey() + "=" + entry.getValue();
            index++;
        }

        sign = StringUtils.join(arr, "&");
        return sign;
    }

    @SuppressWarnings("rawtypes")
    public static String map2ArrayWithKeyValueUrlEncode(Map map) throws UnsupportedEncodingException {
        String sign = "";
        if (map == null) {
            return null;
        }
        String[] arr = new String[map.size()];
        int index = 0;
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Entry entry = (Entry) iter.next();
            arr[index] = entry.getKey() + "=" + URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8");
            index++;
        }

        sign = StringUtils.join(arr, "&");
        return sign;
    }

    /**
     * new
     *
     * @param map
     * @return
     * @throws UnsupportedEncodingException
     * @author Administrator
     * @created 2017年4月14日 下午3:17:44
     */
    @SuppressWarnings("rawtypes")
    public static String[] map2ArrayWithKeyValue(Map map, boolean encode) throws UnsupportedEncodingException {
        String sign = "";
        if (map == null) {
            return null;
        }
        String[] arr = new String[map.size()];
        int index = 0;
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Entry entry = (Entry) iter.next();
            arr[index] = entry.getKey() + "=" + (encode ? URLEncoder.encode(String.valueOf(entry.getValue()), "UTF-8") : entry.getValue());
            index++;
        }

        return arr;
    }

    public static String signMd5(String str) {
        logger.info("签名前-------： " + str);

        MessageDigest mdInst;
        try {
            mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(str.getBytes());
            String sign = StringUtil.byte2Hex(mdInst.digest());

            logger.info("签名后-------： " + sign);

            return sign;
        } catch (Exception e) {
            logger.error("md5加密失败--> " + e);
        }
        return null;

    }

    /**
     * 加签算法
     *
     * @param original
     */
    public static String sign(String original, String signType) {

        String signature = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance(signType);
            crypt.reset();
            crypt.update(original.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            logger.error("sha1算法加密异常", e);
            return null;
        } catch (UnsupportedEncodingException e) {
            logger.error("不支持编码类型", e);
            return null;
        }
        return signature;

    }
}
