package com.util;

import java.util.List;

/**
 * 表格文件
 *
 * @author ocean~
 */
public class EWorkbook {

    private String fileName; // 文件名
    private List<ESheet> sheets; // 工作表

    public EWorkbook() {
    }

    public EWorkbook(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<ESheet> getSheets() {
        return sheets;
    }

    public void setSheets(List<ESheet> sheets) {
        this.sheets = sheets;
    }

}
