package com.company.project.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;


public class ExcelUtil {

    private ExcelUtil() {}

    /***
     * 前端下载Excel文件
     */
    public static <T> void responseExcel(List<T> dataList, Map<String, Object> sumData, String title, String[] headers, HttpServletResponse response)
            throws IllegalAccessException, IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 设置导出的文件名
        String fileName = buildFileName(title);
        // 写入数据
        if (null == sumData || sumData.isEmpty()) {
            buildSimpleSheet(workbook, dataList, headers);
        } else {
            buildMultiPartSheet(workbook, dataList, sumData, headers);
        }
        // 响应回前端，弹出下载提示
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=$fileName");
        response.setHeader("filename", fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    /***
     * 保存Excel文件
     */
    public static <T> String saveExcel(List<T> dataList, Map<String, Object> sumData, String title, String[] headers) throws IllegalAccessException, IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 设置导出的文件名
        String fileName = buildFileName(title);
        // 写入数据
        if (null == sumData || sumData.isEmpty()) {
            buildSimpleSheet(workbook, dataList, headers);
        } else {
            buildMultiPartSheet(workbook, dataList, sumData, headers);
        }
        // 保存文件到临时目录
        File directory = new File("");
        String filePath = directory.getAbsolutePath() + File.separator + "excel" + File.separator + fileName;
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                throw new IOException("创建excel目录失败！");
            }
        }
        FileOutputStream out = new FileOutputStream(filePath);
        workbook.write(out);
        out.close();
        return filePath;
    }

    /***
     * 单页数据表+表头带样式
     */
    private static <T> void buildSimpleSheet(HSSFWorkbook workbook, List<T> dataList, String[] headers) throws IllegalAccessException {
        HSSFSheet sheet = workbook.createSheet();
        // 添加表头
        HSSFRow headRow = sheet.createRow(0);
        buildHeadRow(headRow, sheet, workbook, headers);
        // 新增数据行，并且设置单元格数据，在表中存放查询到的数据放入对应的列
        buildDataContent(1, dataList, sheet);
    }

    /***
     * 单页数据表+第一行为汇总数据+表头带样式
     */
    private static <T> void buildMultiPartSheet(HSSFWorkbook workbook, List<T> dataList, Map<String, Object> sumData, String[] headers) throws IllegalAccessException {
        HSSFSheet sheet = workbook.createSheet();
        // 添加汇总信息
        if (!sumData.isEmpty()) {
            HSSFRow sumRow = sheet.createRow(0);
            // 写入汇总信息
            StringBuilder sumContent = new StringBuilder();
            for (Object obj : sumData.entrySet()) {
                Map.Entry entry = (Map.Entry) obj;
                sumContent.append(entry.getKey()).append(": ").append(entry.getValue()).append(" ");
            }
            HSSFCell cell = sumRow.createCell(0);
            HSSFRichTextString text = new HSSFRichTextString(sumContent.toString());
            cell.setCellValue(text);
            // 添加样式
            HSSFCellStyle style = workbook.createCellStyle();
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            cell.setCellStyle(style);
            // 合并单元格
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, headers.length - 1));
        }
        // 添加表头
        HSSFRow headRow = sheet.createRow(2);
        buildHeadRow(headRow, sheet, workbook, headers);
        // 新增数据行，并且设置单元格数据，在表中存放查询到的数据放入对应的列
        buildDataContent(3, dataList, sheet);
    }

    private static String buildFileName(String title) {
        String titleDefault = title;
        if (title.isEmpty()) {
            titleDefault = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        }
        return String.format("%s.xls", titleDefault);
    }

    private static void buildHeadRow(HSSFRow headRow, HSSFSheet sheet, HSSFWorkbook workbook, String[] headers) {
        // 表头样式
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.index);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.index);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.index);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.index);

        for (int i = 0; i < headers.length; i++) {
            // 写入值
            HSSFCell cell = headRow.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            // 添加样式
            sheet.setColumnWidth(i, 8000);
            cell.setCellStyle(style);
        }
    }

    private static <T> void buildDataContent(int startRowNum, List<T> dataList, HSSFSheet sheet) throws IllegalAccessException {
        int rowNum = startRowNum;
        for (T data : dataList) {
            List<Field> fields = Arrays.asList(data.getClass().getDeclaredFields());
            HSSFRow dataRow = sheet.createRow(rowNum);
            if (data instanceof Map) {
                Collection dataVals = ((Map) data).values();
                for (int i = 0; i < dataVals.size(); i++) {
                    dataRow.createCell(i).setCellValue(dataVals.toArray()[i].toString());
                }
            } else {
                for (int i = 0; i < fields.size(); i++) {
                    Field field = fields.get(i);
                    field.setAccessible(true);
                    if (field.getType().isInstance(new Date())) {
                        Date rawData = (Date) field.get(data);
                        String fmtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rawData);
                        dataRow.createCell(i).setCellValue(fmtDate);
                    } else if (null != field.get(data)) {
                        dataRow.createCell(i).setCellValue(field.get(data).toString());
                    }
                }
            }
            rowNum++;
        }
    }
}
