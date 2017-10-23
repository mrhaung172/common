package com.util;

import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;


public class ExcelUtils {

    public static <T> File export(String sheetName, List<T> list, String[] titles, String[] fieldNames) {
        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = null;

        // 对每个表生成一个新的sheet,并以表名命名
        if (sheetName == null) {
            sheetName = "sheet1";
        }

        sheet = wb.createSheet(sheetName);

        // 设置表头的说明
        HSSFRow topRow = sheet.createRow(0);

        for (int i = 0; i < titles.length; i++) {
            setCellGBKValue(topRow.createCell((short) i), titles[i]);
        }

        for (int i = 0; i < list.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1);
            JSONObject obj = JSONObject.fromObject(list.get(i));

            for (int j = 0; j < fieldNames.length; j++) {
                setCellGBKValue(row.createCell((short) j), obj.get(fieldNames[j]) + "");
            }
        }

        String path = ExcelUtils.class.getResource("/tmp/").getPath();
        String filename = UUID.randomUUID().toString() + ".xls";
        File file = new File(path + "/" + filename);

        try {
            OutputStream os = new FileOutputStream(file);
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

        return file;
    }

    // 生成工作簿
    public static <T> HSSFWorkbook createHSSFWorkbook(EWorkbook ewb) {
        List<ESheet> sheets = ewb.getSheets();
        if (null == sheets || sheets.size() == 0) {
            return null;
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        //设置字体
        HSSFFont font = wb.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
//		font.setFontHeightInPoints((short) 20); // 字体高度
//		font.setColor(HSSFFont.COLOR_RED); // 字体颜色
//		font.setFontName("黑体"); // 字体
//		font.setItalic(true); // 是否使用斜体
//		font.setStrikeout(true); // 是否使用划线
        //设置单元格类型
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        cellStyle.setWrapText(true);

        int sheet_i = 0;
        for (ESheet esheet : sheets) {
            String eSheetName = esheet.getName();
            HSSFSheet sheet = wb.createSheet(null != eSheetName ? eSheetName : "sheet" + ++sheet_i);
            // 设置列字段名称
            HSSFRow topRow = sheet.createRow(0);
            String[] titles = esheet.getTitles();
            for (int i = 0, len = titles.length; i < len; i++) {
                HSSFCell cell = topRow.createCell((short) i);
                cell.setCellStyle(cellStyle);
                setCellGBKValue(cell, titles[i]);
                sheet.setColumnWidth((short) i, (short) 5000);
            }
            // 加载表数据
            String[] fieldNames = esheet.getFieldNames();
            List<T> content = esheet.getContent();
            for (int i = 0, size = content.size(); i < size; i++) {
                HSSFRow row = sheet.createRow(i + 1);
                JSONObject obj = JSONObject.fromObject(content.get(i));
                for (int j = 0, len = fieldNames.length; j < len; j++) {
                    Object _obj = obj.get(fieldNames[j]);
                    setCellGBKValue(row.createCell((short) j), null == _obj ? "" : _obj + "");
                }
            }
            // 自动调整列宽，对中文或全半角支持不好
//			for (int i = 0, len = titles.length; i < len; i++) {
//				sheet.autoSizeColumn((short) i);
//			}
        }
        return wb;
    }

    // 导出含多个工作表的excel
    public static <T> File export(EWorkbook ewb) {
        HSSFWorkbook wb = createHSSFWorkbook(ewb);
        // 生成文件
        String path = Class.class.getResource("/tmp/").getPath();
//		String fileName = UUID.randomUUID().toString() + ".xls";
        File file = new File(path + "/" + ewb.getFileName() + ".xls");
        try {
            OutputStream os = new FileOutputStream(file);
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            return null;
        }
        return file;
    }

    @SuppressWarnings("deprecation")
    public static void setCellGBKValue(HSSFCell cell, String value) {
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        // 设置CELL的编码信息

        cell.setCellValue(value);
    }

}
