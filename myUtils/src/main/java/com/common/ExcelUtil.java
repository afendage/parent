package com.common;

import org.apache.commons.collections4.map.LinkedMap;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by gaoz on 17/6/28.
 */
public class ExcelUtil {
    /**
     * 填充数据到普通的excel文件中
     *
     * @param rs
     * @param wb
     * @param headers
     * @throws Exception
     */
    public static void fillExcelData(ResultSet rs, Workbook wb, String[] headers) throws Exception {
        Sheet sheet = wb.createSheet();
        Row row = sheet.createRow(0);

        // 先填充行头 : "编号","姓名","电话","Email","QQ","出生日期"
        for (int i = 0; i < headers.length; i++) {
            row.createCell(i).setCellValue(headers[i]);
        }
        // 再填充数据
        int rowIndex = 1;
        while (rs.next()) {
            row = sheet.createRow(rowIndex++);
            for (int i = 0; i < headers.length; i++) {
                Object objVal = rs.getObject(i + 1);
                if (objVal instanceof Date) {
                    row.createCell(i).setCellValue(CommonUtils.formatDate((Date) objVal, "yyyy-MM-dd HH:mm:ss"));
                } else {
                    row.createCell(i).setCellValue(objVal.toString());
                }
            }
        }
    }

    /**
     * 填充数据到普通的excel文件中
     *
     * @param list
     * @param wb
     * @throws Exception
     */
    public static void fillExcelData(List<LinkedMap> list, HSSFWorkbook wb) throws Exception {
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("sheet1");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
        // 生成一个字体
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);// HSSFColor.VIOLET.index
        //字体颜色
        font.setFontHeightInPoints((short) 14);
        if (list == null || list.size() == 0) {
            return;
        }
        LinkedMap linkedMap = list.get(0);
        Iterator<Map.Entry> iterator = linkedMap.entrySet().iterator();

        int index = 0;
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            row.createCell(index).setCellValue(entry.getKey().toString());
            index++;
        }

        // 再填充数据
        int rowIndex = 1;
        for (LinkedMap map : list) {
            index = 0;
            row = sheet.createRow(rowIndex);
            for (Object key : map.keySet()) {
                if (null == map.get(key)) {
                    row.createCell(index).setCellValue("");
                } else {
                    row.createCell(index).setCellValue(map.get(key).toString());
                }
                index++;
            }
            rowIndex++;
        }
    }

    /**
     * 处理单元格格式的第三种方法:比较全面
     *
     * @param cell
     * @return
     */
    public static String formatCell(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_NUMERIC:

                // 日期格式的处理
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    return sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                }

                return String.valueOf(cell.getNumericCellValue());

            // 字符串
            case HSSFCell.CELL_TYPE_STRING:
                return cell.getStringCellValue();

            // 公式
            case HSSFCell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();

            // 空白
            case HSSFCell.CELL_TYPE_BLANK:
                return "";

            // 布尔取值
            case HSSFCell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() + "";

            // 错误类型
            case HSSFCell.CELL_TYPE_ERROR:
                return cell.getErrorCellValue() + "";
        }

        return "";
    }
}
