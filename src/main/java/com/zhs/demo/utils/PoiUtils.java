package com.zhs.demo.utils;

/**
 * Created by Zhang on 2018/10/12.
 */

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

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



}
