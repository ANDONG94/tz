package com.tdkj.util;

import com.tdkj.excel.ExcelConstant;
import com.tdkj.excel.WriteExcelDataDelegated;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;


public class PoiUtil {

    private final static Logger logger = LoggerFactory.getLogger(PoiUtil.class);

    /**
     * 初始化EXCEL(sheet个数和标题)
     *
     * @param totalRowCount 总记录数
     * @param titles        标题集合
     * @return XSSFWorkbook对象
     */
    public static SXSSFWorkbook initExcel(Integer totalRowCount, String[] titles) {

        // 在内存当中保持 100 行 , 超过的数据放到硬盘中在内存当中保持 100 行 , 超过的数据放到硬盘中
        SXSSFWorkbook wb = new SXSSFWorkbook(100);

        //暂时不用
        /*Integer sheetCount = ((totalRowCount % ExcelConstant.PER_SHEET_ROW_COUNT == 0) ?
                (totalRowCount / ExcelConstant.PER_SHEET_ROW_COUNT) : (totalRowCount / ExcelConstant.PER_SHEET_ROW_COUNT + 1));*/
        Integer sheetCount=1;
        // 根据总记录数创建sheet并分配标题
        for (int i = 0; i < sheetCount; i++) {
            SXSSFSheet sheet = (SXSSFSheet) wb.createSheet("sheet" + (i + 1));
            SXSSFRow headRow = (SXSSFRow) sheet.createRow(0);

            for (int j = 0; j < titles.length; j++) {
                SXSSFCell headRowCell = (SXSSFCell) headRow.createCell(j);
                headRowCell.setCellValue(titles[j]);
            }
        }

        return wb;
    }


    /**
     * 下载EXCEL到本地指定的文件夹
     *
     * @param wb         EXCEL对象SXSSFWorkbook
     * @param exportPath 导出路径
     */
    public static void downLoadExcelToLocalPath(SXSSFWorkbook wb, String exportPath) {
        FileOutputStream fops = null;
        try {
            fops = new FileOutputStream(exportPath);
            wb.write(fops);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != wb) {
                try {
                    wb.dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != fops) {
                try {
                    fops.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 下载EXCEL到浏览器
     *
     * @param wb       EXCEL对象XSSFWorkbook
     * @param response
     * @param fileName 文件名称
     * @throws IOException
     */
    public static void downLoadExcelToWebsite(SXSSFWorkbook wb, HttpServletResponse response, String fileName) throws IOException {

        response.setHeader("Content-disposition", "attachment; filename="
                + new String((fileName + ".xlsx").getBytes("utf-8"), "ISO8859-1"));//设置下载的文件名

        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            wb.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != wb) {
                try {
                    wb.dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 导出Excel到本地指定路径
     *
     * @param totalRowCount           总记录数
     * @param titles                  标题
     * @param exportPath              导出路径
     * @param writeExcelDataDelegated 向EXCEL写数据/处理格式的委托类 自行实现
     * @throws Exception
     */
    public static final void exportExcelToLocalPath(Integer totalRowCount, String[] titles, String exportPath, WriteExcelDataDelegated writeExcelDataDelegated) throws Exception {

        logger.info("开始导出：" + DateUtil.formatDate(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));

        // 初始化EXCEL
        SXSSFWorkbook wb = PoiUtil.initExcel(totalRowCount, titles);

        // 调用委托类分批写数据
        int sheetCount = wb.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++) {
            SXSSFSheet eachSheet = (SXSSFSheet) wb.getSheetAt(i);

            for (int j = 1; j <= ExcelConstant.PER_SHEET_WRITE_COUNT; j++) {

                int currentPage = i * ExcelConstant.PER_SHEET_WRITE_COUNT + j;
                int pageSize = ExcelConstant.PER_WRITE_ROW_COUNT;
                int startRowCount = (j - 1) * ExcelConstant.PER_WRITE_ROW_COUNT + 1;
                int endRowCount = startRowCount + pageSize - 1;


                writeExcelDataDelegated.writeExcelData(eachSheet, startRowCount, endRowCount, currentPage, pageSize);

            }
        }


        // 下载EXCEL
        PoiUtil.downLoadExcelToLocalPath(wb, exportPath);

        logger.info("导出完成：" + DateUtil.formatDate(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
    }


    /**
     * 导出Excel到浏览器
     *
     * @param response
     * @param totalRowCount           总记录数
     * @param fileName                文件名称
     * @param titles                  标题
     * @param writeExcelDataDelegated 向EXCEL写数据/处理格式的委托类 自行实现
     * @throws Exception
     */
    public static final void exportExcelToWebsite(HttpServletResponse response, Integer totalRowCount, String fileName, String[] titles, WriteExcelDataDelegated writeExcelDataDelegated) throws Exception {

        logger.info("开始导出：" + DateUtil.formatDate(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));

        // 初始化EXCEL
        SXSSFWorkbook wb = PoiUtil.initExcel(totalRowCount, titles);


        // 调用委托类分批写数据
        int sheetCount = wb.getNumberOfSheets();
        for (int i = 0; i < sheetCount; i++) {
            SXSSFSheet eachSheet = (SXSSFSheet) wb.getSheetAt(i);
            Sheet sheet = wb.createSheet();
            // 设置列宽
            sheet.setColumnWidth(0,35*256);
            sheet.setColumnWidth(1,20*256);
            sheet.setColumnWidth(2,30*256);
            sheet.setColumnWidth(3,10*256);
            sheet.setColumnWidth(4,10*256);
            sheet.setColumnWidth(5,10*256);
            sheet.setColumnWidth(6,10*256);
            sheet.setColumnWidth(7,10*256);
            sheet.setColumnWidth(8,15*256);
            sheet.setColumnWidth(9,30*256);
            //为了提高效率暂时不用
           /* for (int j = 1; j <= ExcelConstant.PER_SHEET_WRITE_COUNT; j++) {

                int currentPage = i * ExcelConstant.PER_SHEET_WRITE_COUNT + j;
                int pageSize = ExcelConstant.PER_WRITE_ROW_COUNT;
                int startRowCount = (j - 1) * ExcelConstant.PER_WRITE_ROW_COUNT + 1;
                int endRowCount = startRowCount + pageSize - 1;

                writeExcelDataDelegated.writeExcelData(eachSheet, startRowCount, endRowCount, currentPage, pageSize);

            }*/
            for (int j = 1; j <=1; j++) {

                int currentPage = i * 1+ j;
                int pageSize =totalRowCount;
                int startRowCount = (j - 1) * totalRowCount + 1;
                int endRowCount = startRowCount + pageSize - 1;

                writeExcelDataDelegated.writeExcelData(eachSheet, startRowCount, endRowCount, currentPage, pageSize);

            }
        }


        // 下载EXCEL
        PoiUtil.downLoadExcelToWebsite(wb, response, fileName);

        logger.info("导出完成：" + DateUtil.formatDate(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
    }


    public static CellStyle bigTitleCellStyle(Workbook wb){
//        横向居中  +   水平居中   +  红色宋体22号
        CellStyle cellStyle = wb.createCellStyle();
        // 横向居中
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        // 垂直居中
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        Font font = wb.createFont();
        font.setFontHeight((short) 440);
        font.setColor(Font.COLOR_RED);
        font.setFontName("宋体");

        cellStyle.setFont(font);

        return cellStyle;
    }

    public static CellStyle titleCellStyle(Workbook wb){
        // 宋体16号  倾斜   边框线   水平垂直居中
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setItalic(true);
        /* font.setBold(true);*/

        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(font);

        // 边框线
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);// 细线
        cellStyle.setBorderRight(CellStyle.BORDER_DASHED);//圆点....
        cellStyle.setBorderBottom(CellStyle.BORDER_DOTTED);// 矩形的虚线_ _ _ _ _
        cellStyle.setBorderLeft(CellStyle.BORDER_DOUBLE);// 双线
        // 横向居中
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        // 垂直居中
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        return cellStyle;

    }
    public static  CellStyle contentCellStyle(Workbook wb){
        // 边框线   水平垂直居中
        CellStyle cellStyle = wb.createCellStyle();
        // 边框线
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);// 细线
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);//
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);//
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);//

        return cellStyle;

    }
    public static void kill(){


    }
}
