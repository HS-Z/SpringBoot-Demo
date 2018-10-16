package com.zhs.demo.utils;

/**
 * Created by Zhang on 2018/10/12.
 */

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微软office，例如Excel的导入和导出
 */
@Service
public class PoiUtils {

    private Logger logger= LoggerFactory.getLogger(this.getClass());


    /**
     * excel导出
     * @param fileName  文件名称
     * @param sheetName sheet名
     * @param headName   头名称（可以为空）
     * @param titleMap  标题行名称
     * @param list  导出的数据
     */
    public Json exportExcel(String fileName, String sheetName, String headName, HashMap<String,String> titleMap, List<Map<String,Object>> list){

        if (StringUtils.isBlank(fileName)){
            return Json.fail("文件名称不能为空");
        }
        if (StringUtils.isBlank(sheetName)){
            return Json.fail("sheet名称不能为空");
        }
        if (titleMap == null || titleMap.isEmpty()){
            return Json.fail("标题名称不能为空");
        }
        if (list == null || list.size() == 0){
            return Json.fail("导出数据为空");
        }
        if (!(fileName.endsWith(".xlsx") || fileName.endsWith(".xls"))){   //判断文件后缀名是否符合标准
            fileName = fileName+".xlsx";
        }

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet(sheetName);

        sheet.setDefaultColumnWidth((short) 15);   //设置表格默认宽度

        if (StringUtils.isNotBlank(headName)){   //头名称不为空
            XSSFRow headRow=sheet.createRow(0);    //第一行
            headRow.setHeight((short)500);   //设置行高
            XSSFCell headCell=headRow.createCell(0);
            headCell.setCellValue(headName);

            CellStyle style=this.headStyle(workbook);
            headCell.setCellStyle(style);
            sheet.addMergedRegion(new CellRangeAddress(0,0,0,titleMap.size()-1));

            //标题行

            XSSFRow titleRow = sheet.createRow(1);   //第二行

            int colNum = 0;   //列序号
            for(String key : titleMap.keySet()) {

                XSSFCell titleCell=titleRow.createCell(colNum);  //创建标题的单元格
                titleCell.setCellValue(titleMap.get(key));  //给单元格写数据
                CellStyle titleStyle=this.titleStyle(workbook);
                titleCell.setCellStyle(titleStyle);  //设置单元格样式
                colNum++;  //切换到下个单元格
            }

            int rowNum = 2;
            colNum = 0;

            for(int i = 0; i < list.size(); i++) {
                Map<String, Object> data = list.get(i);
                XSSFRow contentRow = sheet.createRow(rowNum);
                for(String key : titleMap.keySet()) {
                    XSSFCell contentCell=contentRow.createCell(colNum);  //创建单元格
                    Object value=data.get(key);

                    if (value instanceof String){
                        contentCell.setCellValue(value.toString());
                    }else if (value instanceof Date){  //时间格式
                        Date date = (Date) value;
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        contentCell.setCellValue(format.format(date));
                    }

                    colNum++;
                }
                rowNum++;
                colNum=0;
            }

        }else {  //没有头描述

            //标题行

            XSSFRow titleRow = sheet.createRow(0);   //第一行

            int colNum = 0;   //列序号
            for(String key : titleMap.keySet()) {

                XSSFCell titleCell=titleRow.createCell(colNum);  //创建标题的单元格
                titleCell.setCellValue(titleMap.get(key));  //给单元格写数据
                CellStyle titleStyle=this.titleStyle(workbook);
                titleCell.setCellStyle(titleStyle);  //设置单元格样式
                colNum++;  //切换到下个单元格
            }

            int rowNum = 1;
            colNum = 0;

            for(int i = 0; i < list.size(); i++) {
                Map<String, Object> data = list.get(i);
                XSSFRow contentRow = sheet.createRow(rowNum);
                for(String key : titleMap.keySet()) {
                    XSSFCell contentCell=contentRow.createCell(colNum);  //创建单元格
                    Object value=data.get(key);

                    if (value instanceof String){
                        contentCell.setCellValue(value.toString());
                    }else if (value instanceof Date){  //时间格式
                        Date date = (Date) value;
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        contentCell.setCellValue(format.format(date));
                    }

                    colNum++;
                }
                rowNum++;
                colNum=0;
            }

        }

        try {

            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

            OutputStream output=response.getOutputStream();
            response.reset();
            response.setContentType("application/x-download");
            response.setCharacterEncoding("utf-8");  //处理乱码问题
            response.setHeader("Content-disposition", "attachment; filename="+new String(fileName.getBytes("gbk"), "iso8859-1"));
            workbook.write(output);
            output.close();

            return Json.ok("导出成功");

        }catch (Exception e){
            logger.error("导出excel表格失败");
            return Json.fail("导出excel表格失败");
        }

    }


    /**
     * 设置头标题样式
     * @return
     */
    public XSSFCellStyle headStyle(XSSFWorkbook workbook){

        XSSFCellStyle style=workbook.createCellStyle();

        //设置背景色
        style.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.index);   //设置单元格背景色
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);  //设置填充模式

        //设置对齐方式
        style.setAlignment(HorizontalAlignment.CENTER);  //水平对齐方式
        style.setVerticalAlignment(VerticalAlignment.CENTER);  //垂直对齐方式

        //设置字体
        XSSFFont font = workbook.createFont();
        font.setFontName("等线");   //设置字体名称
        font.setFontHeightInPoints((short) 16);  //设置字体大小
        font.setBold(true);  //设置加粗显示

        style.setFont(font);  //应用字体样式

        //设置自动换行
        style.setWrapText(true);

        return style;

    }


    /**
     * 设置标题样式
     * @return
     */
    public XSSFCellStyle titleStyle(XSSFWorkbook workbook){

        XSSFCellStyle style=workbook.createCellStyle();


        //设置边框
        /*style.setBorderBottom(BorderStyle.DASH_DOT);   //设置下边框
        style.setBorderLeft(BorderStyle.DASHED);   //设置左边框
        style.setBorderRight(BorderStyle.HAIR);  //设置右边框
        style.setBorderTop(BorderStyle.DOUBLE);  //设置上边框*/

        //设置对齐方式
        style.setAlignment(HorizontalAlignment.CENTER);  //水平对齐方式
        style.setVerticalAlignment(VerticalAlignment.CENTER);  //垂直对齐方式

        //设置字体
        XSSFFont font = workbook.createFont();
        font.setFontName("等线");   //设置字体名称
        font.setFontHeightInPoints((short) 12);  //设置字体大小
        font.setBold(true);  //设置加粗显示

        style.setFont(font);  //应用字体样式

        //设置自动换行
        style.setWrapText(true);

        return style;

    }

}
