package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class VipExcel {

    private final static Logger logger = Logger.getLogger(VipExcel.class);

    public static File getVipExcelData(String[][] mothSummary, String[][] investSummery, String[][] fundFlow,
            String[][] repaymentBidTerm, Map<Integer,String[][]> vipLevelFundFlowStat, Map<Integer, String[][]> vipUseFundDetals, Date time) {
        XSSFWorkbook workbook;
        try {
            workbook = new XSSFWorkbook(
                    new FileInputStream(VipExcel.class.getResource("/templates/vipReport.xlsx").getFile()));
            XSSFSheet sheet = workbook.getSheetAt(0);



            XSSFCellStyle cellStyle = workbook.createCellStyle();

            cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
            cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);// 左边框
            cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);// 上边框
            cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);// 右边框

            for (int i = 6; i > 1; i--) {
                insertDate(3, 0, vipLevelFundFlowStat.get(i), workbook.getSheetAt(7 - i), cellStyle);
                insertDate(14, 0, vipUseFundDetals.get(i), workbook.getSheetAt(7 - i), cellStyle);
                romoveExtraRow(workbook.getSheetAt(7 - i));
            }
            insertDate(3, 1, mothSummary, sheet, cellStyle);
            insertDate(10, 0, investSummery, sheet, cellStyle);
            insertDate(63, 0, fundFlow, sheet, cellStyle);
            insertDate(74, 0, repaymentBidTerm, sheet, cellStyle);

            romoveExtraRow(sheet);
            String filePath = VipExcel.class.getClassLoader().getResource("/temporary/").getPath();
            String fileName = "vip_" + org.apache.http.client.utils.DateUtils.formatDate(time);
            File vipFile = new File(filePath + fileName + ".xlsx");
            FileOutputStream out = new FileOutputStream(vipFile);
            workbook.write(out);
            out.close();
            return vipFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void main(String[] args) {

    }

    public static void insertDate(int startRow, int startCell, String[][] arr, XSSFSheet sheet,
            XSSFCellStyle cellStyle) {

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                XSSFRow xssRow = sheet.getRow(startRow + i);
                if (xssRow == null) {
                    xssRow = sheet.createRow(startRow + i);
                }
                XSSFCell xssCell = xssRow.createCell(j + startCell);

                xssCell.setCellValue(arr[i][j]);
                xssCell.setCellStyle(cellStyle);
            }
        }

    }

    public static void romoveExtraRow(XSSFSheet sheet) {
        int lastRow = sheet.getLastRowNum();
        int row = 0;
        do {
            if (isNullRow(sheet.getRow(row)) && isNullRow(sheet.getRow(row + 1))) {
                sheet.shiftRows(row + 1, lastRow, -1);
            } else {
                row++;
            }
            lastRow = sheet.getLastRowNum();
        } while (row < lastRow - 1);
    }

    public static boolean isNullRow(XSSFRow row) {
        if (row == null) {
            return true;
        }

        for (int i = 0; i < row.getLastCellNum(); i++) {
            if (StringUtils.isNotBlank(row.getCell(i).getStringCellValue())) {
                return false;
            }
        }
        return true;

    }
}
