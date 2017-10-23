package com.util;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

public class Converter {
    private static final Logger logger = Logger.getLogger(Converter.class);

    public static void main(String[] args) {
//		String strjson = new XMLSerializer().readFromFile(new File("/Users/md005/Desktop/1.xml")).toString();
//		System.out.println(strjson);
//
//		XMLSerializer xmlSerializer = new XMLSerializer();
//        xmlSerializer.setTypeHintsEnabled(false);
//        xmlSerializer.setRootName("pReq");
//        xmlSerializer.setElementName("pRow");
//        String strxml = xmlSerializer.write(JSONSerializer.toJSON(strjson));
//        System.out.println(strxml);

//		<pOriMerBillNo>string</pOriMerBillNo>
//        <pTrdAmt>string</pTrdAmt>
//        <pFAcctType>string</pFAcctType>
//        <pFIpsAcctNo>string</pFIpsAcctNo>
//        <pFTrdFee>string</pFTrdFee>
//        <pTAcctType>string</pTAcctType>
//        <pTIpsAcctNo>string</pTIpsAcctNo>
//<pTTrdFee>string</pTTrdFee>
        JSONObject json = (JSONObject) xmlToObj("<?xml version=\"1.0\" encoding=\"utf-8\"?><pReq><pStatus>9999</pStatus><pMerBillNo>1#1#1410252884476</pMerBillNo><pIdentNo>410621198406155011</pIdentNo><pRealName>环迅</pRealName><pIpsAcctNo></pIpsAcctNo><pIpsAcctDate></pIpsAcctDate><pMemo1>http://172.16.6.171:9000/IPSAction/IPSCallBack;http://172.16.6.171:9000/IPSAction/IPSCallBack;1;2;1;hx</pMemo1><pMemo2>pMemo2</pMemo2><pMemo3>pMemo3</pMemo3></pReq>");
        System.out.println(json);

        String xml = jsonToXml(json.toString(), "pReq", null, null, null);

        System.out.println(xml);

        System.out.println(xmlToObj(xml));

    }

    /**
     * xml字符串转json字符串
     *
     * @param xml
     * @return
     */
    public static String xmlToJson(String xml) {
        return new XMLSerializer().read(xml).toString();
    }

    /**
     * json字符串转xml字符串
     *
     * @param json
     * @return
     */
    public static String jsonToXml(String json) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        xmlSerializer.setTypeHintsEnabled(false);

        return xmlSerializer.write(JSONSerializer.toJSON(json));
    }

    /**
     * json字符串转xml字符串
     *
     * @param json
     * @param rootName
     * @param elementName
     * @param objectName
     * @param arrayName
     * @return
     */
    public static String jsonToXml(String json, String rootName, String elementName, String objectName, String arrayName) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        xmlSerializer.setTypeHintsEnabled(false);

        if (StringUtils.isNotBlank(rootName)) {
            xmlSerializer.setRootName(rootName);
        }

        if (StringUtils.isNotBlank(elementName)) {
            xmlSerializer.setElementName(elementName);
        }

        if (StringUtils.isNotBlank(objectName)) {
            xmlSerializer.setObjectName(objectName);
        }

        if (StringUtils.isNotBlank(arrayName)) {
            xmlSerializer.setArrayName(arrayName);
        }

        return xmlSerializer.write(JSONSerializer.toJSON(json));
    }

    /**
     * xml字符串转json对象/数组
     *
     * @param xml
     * @return
     */
    public static JSON xmlToObj(String xml) {
        return new XMLSerializer().read(xml);
    }

    /**
     * 将bean 转换为map
     *
     * @param thisObj
     * @return
     * @author luzj
     * @date 2015年6月1日 下午5:29:31
     */
    public static Map getMapValue(Object thisObj) {
        Map map = new HashMap();
        Class c;
        StringBuffer bf = new StringBuffer("[" + thisObj.getClass().getName() + "转换为map:");
        try {
            c = Class.forName(thisObj.getClass().getName());
            Method[] m = c.getMethods();
            for (int i = 0; i < m.length; i++) {
                String method = m[i].getName();
                if (method.startsWith("get")) {
                    try {
                        Object value = m[i].invoke(thisObj);
                        if (value != null) {
                            String key = method.substring(3).toLowerCase();
//							key = key.substring(0, 1).toUpperCase()
//									+ key.substring(1);
                            map.put(key, value);
                            bf.append(key + " = " + value + " , ");
                        }
                    } catch (Exception e) {
                        logger.error("error:将bean 转换为map----->" + method, e);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("error:将bean 转换为map----->" + e.getMessage(), e);
            e.printStackTrace();
        }
        logger.info(bf.toString());
        return map;
    }

    /**
     * MAP类型数组转换成NameValuePair类型
     *
     * @param properties MAP类型数组
     * @return NameValuePair类型数组
     */
    public static NameValuePair[] map2Pair(Map properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int index = 0;
        Iterator iter = properties.entrySet().iterator();
        while (iter.hasNext()) {
            Entry entry = (Entry) iter.next();

            if ((entry.getValue() instanceof Double)) {
                nameValuePair[index] = new BasicNameValuePair((String) entry.getKey(), Double.toString((Double) entry.getValue()));
            } else if ((entry.getValue() instanceof Integer)) {
                nameValuePair[index] = new BasicNameValuePair((String) entry.getKey(), Integer.toString((Integer) entry.getValue()));
            } else if ((entry.getValue() instanceof Long)) {
                nameValuePair[index] = new BasicNameValuePair((String) entry.getKey(), Long.toString((Long) entry.getValue()));
            } else if ((entry.getValue() instanceof Date)) {
                nameValuePair[index] = new BasicNameValuePair((String) entry.getKey(), DateFormatUtils.format((Date) entry.getValue(), "yyyy-MM-dd"));
            } else if ((entry.getValue() instanceof Boolean)) {
                nameValuePair[index] = new BasicNameValuePair((String) entry.getKey(), Boolean.toString((Boolean) entry.getValue()));
            } else {
                nameValuePair[index] = new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue());
            }
            index++;
        }

        return nameValuePair;
    }

    /**
     * 将map转成数组，形如arr[index]="key=value"
     *
     * @param map
     * @return
     */
    public static String[] map2ArrayWithKeyValue(Map map) {
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

        return arr;
    }

    public static Map<String, Object> parseJSON2Map(String jsonStr) {
        Map<String, Object> map = new HashMap<String, Object>();
        //最外层解析
        JSONObject json = JSONObject.fromObject(jsonStr);
        for (Object k : json.keySet()) {
            Object v = json.get(k);
            //如果内层还是数组的话，继续解析
            if (v instanceof JSONArray) {
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                Iterator<JSONObject> it = ((JSONArray) v).iterator();
                while (it.hasNext()) {
                    JSONObject json2 = it.next();
                    list.add(parseJSON2Map(json2.toString()));
                }
                map.put(k.toString(), list);
            } else {
                map.put(k.toString(), v);
            }
        }
        return map;
    }

    /**
     * String2Map
     *
     * @para key1=value1&key2=value2....
     * <p>
     * add by hof_20160112_1956
     */
    public static Map string2Map(String str) {
        Map map = new HashMap();

        if (StringUtils.isEmpty(str)) {
            return map;
        }

        String[] keyValueArr = str.split("&");
        if (keyValueArr != null && keyValueArr.length != 0) {
            for (String keyValue : keyValueArr) {
                if (keyValue != null && keyValue.trim().length() != 0) {
                    String[] keyOrValue = keyValue.split("=");
                    if (keyOrValue != null && keyOrValue.length == 2) {
                        map.put(keyOrValue[0], keyOrValue[1]);
                    }
                }
            }
        }

        return map;
    }

    /**
     * String2List
     * <p>
     * str;str;str
     * <p>
     * add by hof_20160118_1848
     */
    public static List string2List(String str, String separator) {
        List list = new ArrayList();

        if (StringUtils.isNotEmpty(str)) {
            list = Arrays.asList(str.split(separator));
        }

        return list;
    }


    /**
     * @param map
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String mapToStringWithKeyValue(Map map) {
        if (map == null) {
            return null;
        }
        String str = "";
        String[] stringArr = new String[map.size()];
        int index = 0;
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Entry entry = (Entry) iter.next();
            stringArr[index] = entry.getKey() + "=" + entry.getValue();
            index++;
        }

        for (String string : stringArr) {
            str += string + "&";
        }

        if (str.endsWith("&")) {
            str = str.substring(0, str.length() - 1);
        }
        logger.info("mapToString： " + str);
        return str;
    }

    public static String mapArrToSignFormat(Map<String, String> map) {
        String original = "";
        for (String string : map2ArrayWithKeyValue(map)) {
            original += string + "&";
        }
        if (original.endsWith("&")) {
            original = original.substring(0, original.length() - 1);
        }
        return original;
    }

    public static Long[] stringToLongs(String[] arrs) {

        Long[] ints = new Long[arrs.length];
        for (int i = 0; i < arrs.length; i++) {
            ints[i] = Long.parseLong(arrs[i]);
        }

        return ints;
    }
}
