package com.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class XMLUtils {


    public static <T> Element addElementsByList(Element parentElement, Class<T> claz, List<T> entityPropertys, String elementName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Field[] properties = claz.getDeclaredFields();//获得实体类的所有属性

        for (T t : entityPropertys) {                                //递归实体
            Element secondElement = parentElement.addElement("product");            //二级节点

            for (Field field : properties) {
                field.setAccessible(true);

                Object value = field.get(t);
                secondElement.addElement(field.getName()).setText(value == null ? "" : value.toString());
            }
        }
        return parentElement;

    }


    public static void writerXml(File file, Document dom) {
        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new FileOutputStream(file), OutputFormat.createPrettyPrint());
            writer.write(dom);
            writer.close();
        } catch (Exception e) {

        }

    }


    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InstantiationException, FileNotFoundException, IOException {
        File file = new File("D:\\PP100.rar");

        System.out.println(DigestUtils.md5Hex(new FileInputStream(file)));
    }
}
