package com.zhs.demo.utils;

/**
 * Created by Zhang on 2018/10/12.
 */

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
     * 创建一个空白的excel文件
     * 支持格式为2007，后缀名为.xlsx
     * 支持格式为2003，后缀名为.xls
     * @return
     */
    public Json creatBlankXlsx(){

        XSSFWorkbook workbook = new XSSFWorkbook();   //创建一个空的

        try {
            //新创建的xls需要新创建新的工作簿，office默认创建的时候会默认生成三个sheet
            Sheet sheet=workbook.createSheet("first sheet");  //不加，会导致生成的文件打不开
            FileOutputStream out = new FileOutputStream(new File("blankXlsx.xlsx"));
//            FileOutputStream out1 = new FileOutputStream(new File("23dd.xls"));
            workbook.write(out);
//            workbook.write(out1);
            out.close();
//            out1.close();
            logger.info("创建成功");
        }catch (Exception e){
            logger.error("创建失败，失败原因："+e.getMessage());
            return Json.fail("失败");
        }

        return Json.ok("");

    }


    /**
     * 打开已有的工作簿
     * @return
     */
    public Json open(){

        try {
            File file = new File("blankXlsx.xlsx");
            FileInputStream inputStream = new FileInputStream(file);

            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

            if (file.isFile() && file.exists()){
                return Json.ok("成功打开");
            }else {
                return Json.fail("打开失败");
            }
        }catch (Exception e){
            logger.error("打开失败，失败原因："+e.getMessage());
            return Json.fail("失败");
        }
    }


    public void exportExcel(String sheetName, String[] headName, List<T> list){

        XSSFWorkbook workbook = new XSSFWorkbook();  //创建工作簿

        Sheet sheet = workbook.createSheet(sheetName);  //创建表格

        Row row = sheet.createRow(0);   //创建标题行
        Cell cell = row.createCell(0);  //设置第一行只有一列

        sheet.setDefaultColumnWidth(30);  //设置表格的默认宽度

        XSSFCellStyle style = workbook.createCellStyle();   //创建样式

        //设置背景色
        style.setFillForegroundColor(IndexedColors.GOLD.index);   //设置单元格背景色
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);  //设置填充模式

        //设置边框
        style.setBorderBottom(BorderStyle.DASH_DOT);   //设置下边框
        style.setBorderLeft(BorderStyle.DASHED);   //设置左边框
        style.setBorderRight(BorderStyle.HAIR);  //设置右边框
        style.setBorderTop(BorderStyle.DOUBLE);  //设置上边框

        //设置对齐方式
        style.setAlignment(HorizontalAlignment.CENTER);  //水平对齐方式
        style.setVerticalAlignment(VerticalAlignment.CENTER);  //垂直对齐方式


        //设置字体
        XSSFFont font = workbook.createFont();
        font.setFontName("宋体");   //设置字体名称
        font.setFontHeightInPoints((short) 16);  //设置字体大小
        font.setBold(true);  //设置加粗显示

        style.setFont(font);  //应用字体样式

        //设置列宽
        sheet.setColumnWidth(0,200);   //第一个参数代表列(从0开始)，第2个参数代表宽度值

        //设置自动换行
        style.setWrapText(true);

    }


    /**
     * excel导出
     * @param fileName  文件名称
     * @param sheetName
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
                titleCell.setCellValue(titleMap.get(key).toString());  //给单元格写数据
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
