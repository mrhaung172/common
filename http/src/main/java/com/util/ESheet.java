package com.util;

import java.util.List;

/**
 * 工作表
 *
 * @author ocean~
 */
public class ESheet<T> {

    private String name; // 工作表名称
    private String[] titles; // 表头
    private String[] fieldNames; // 列字段名称
    private List<T> content; // 工作表数据

    public ESheet() {
    }

    public ESheet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public void setFieldNames(String[] fieldNames) {
        this.fieldNames = fieldNames;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

}
