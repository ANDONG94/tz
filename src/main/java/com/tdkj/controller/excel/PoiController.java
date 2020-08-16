package com.tdkj.controller.excel;



import com.tdkj.model.FpUser;
import com.tdkj.model.PoorHouseholds;
import com.tdkj.model.PoorLaborForce;
import com.tdkj.model.PoorXzqh;
import com.tdkj.service.InformationCollection.PoorLaborForceService;
import com.tdkj.service.InformationCollection.PoorWorkService;
import com.tdkj.service.Ztree.ZtreeService;
import com.tdkj.service.excel.PoorLaborForceExcelService;
import com.tdkj.service.excel.PoorWorkExcelService;
import com.tdkj.util.DownloadUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.List;


@RestController
@RequestMapping("/poi")
public class PoiController {

    @Autowired
    private PoorWorkService poorWorkService;
    @Autowired
    private ZtreeService ztreeService;
    @Autowired
    private PoorLaborForceService poorLaborForceService;

    @Autowired
    private PoorLaborForceExcelService poorLaborForceExcelService;
    @Autowired
    private PoorWorkExcelService poorWorkExcelService;

    @GetMapping("/exporpoorwork")
    public String exportXls(HttpServletRequest request, String treeid, String phd002_scan, String phd003_scan, String phd012_scan, HttpServletResponse response) throws Exception{


       /* if(!"".equals(treeid) && treeid != null){
            treeid =  aab301Substr(treeid)+ "%";
        }else{
            //从session中获取当前登录人的aab301 行政区划
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            if(fpUser.getAab301()==null && "".equals(fpUser.getAab301())){

            }
            treeid = fpUser.getAab301();
            treeid =  aab301Substr(treeid)+ "%";
        }*/
        // 导出
        List<PoorHouseholds> list= poorWorkService.queryPoi(treeid,phd002_scan,phd003_scan,phd012_scan);

        //1 创建工作簿 HSSFWorkbook   2003    XSSFWorkbook   2007
        SXSSFWorkbook wb = new SXSSFWorkbook(100);
        //2 创建工作表
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




        /***
         * 定义公共变量
         */
        int rowNo = 0,cellNo = 0;
        Row nRow = null;
        Cell nCell = null;

        /**************大标题*************/
        //3 创建行
        nRow = sheet.createRow(rowNo);
        // 设置行高
        nRow.setHeightInPoints(36);

        //4 创建单元格
        nCell = nRow.createCell(cellNo);
        //5 设置内容
        nCell.setCellValue("贫困户信息");
        //6 设置内容格式
        // 合并单元格
        //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, (short) 0, (short) 9));

        // 横向居中  +   水平居中   +  红色宋体22号
        nCell.setCellStyle(bigTitleCellStyle(wb));


        /*************小标题输出**************/
        // 行号rowNo需要变化吗  列需要变化吗？
        rowNo++;
        String[] titles = {"所属区域","姓名","身份证","性别","易地扶贫搬迁","脱贫状态","认定年度","劳动力数量","联系方式","家庭住址"};

        //3 创建行
        nRow = sheet.createRow(rowNo);
        for (String title:titles){

            //4 创建单元格
            nCell = nRow.createCell(cellNo++);// 先创建cell单元格，然后在自增
            //5 设置内容
            nCell.setCellValue(title);
            //6 设置内容格式
            nCell.setCellStyle(titleCellStyle(wb));
        }


        /**************内容*************/
        // 行号和列号需要变化？
        rowNo++;


        for(PoorHouseholds poorHouseholds:list){
            cellNo=0;
            //3 创建行
            nRow = sheet.createRow(rowNo++);

            //4 创建单元格
            nCell = nRow.createCell(cellNo++);
            //5 设置内容
            nCell.setCellValue( poorHouseholds.getAab301());
            //6 设置内容格式
            nCell.setCellStyle(contentCellStyle(wb));

            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(poorHouseholds.getPhd002());
            nCell.setCellStyle(contentCellStyle(wb));

            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(poorHouseholds.getPhd003());
            nCell.setCellStyle(contentCellStyle(wb));

            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(poorHouseholds.getPhd004());
            nCell.setCellStyle(contentCellStyle(wb));

            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(poorHouseholds.getPhd013());
            nCell.setCellStyle(contentCellStyle(wb));

            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(poorHouseholds.getPhd014());
            nCell.setCellStyle(contentCellStyle(wb));

            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(poorHouseholds.getPhd012());
            nCell.setCellStyle(contentCellStyle(wb));

            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(poorHouseholds.getPhd008()+"");
            nCell.setCellStyle(contentCellStyle(wb));

            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(poorHouseholds.getPhd006());
            nCell.setCellStyle(contentCellStyle(wb));

            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(poorHouseholds.getPhd007());
            nCell.setCellStyle(contentCellStyle(wb));

        }




        /*************7 下载**********************/
        DownloadUtil downloadUtil = new DownloadUtil();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 将wb写入流
        wb.write(byteArrayOutputStream);

        /**
         byteArrayOutputStream 将文件内容写入ByteArrayOutputStream
         response HttpServletResponse	写入response
         returnName 返回的文件名
         */
        System.out.println("下载");
        downloadUtil.download(byteArrayOutputStream,response,"贫困户信息.xlsx");
        System.out.println("cgl");
       return "1";
    }




    @GetMapping("/exporPLFss")
    public String exportXlsPoorLaborForce11(HttpServletRequest request, String treeid, String phd002_scan, String phd003_scan, String phd012_scan, HttpServletResponse response) throws Exception{


       // pkldlExcelService.export(treeid,phd002_scan,phd003_scan,phd012_scan,response);

        if(!"".equals(treeid) && treeid != null){
            treeid =  aab301Substr(treeid)+ "%";
        }else{
            //从session中获取当前登录人的aab301 行政区划
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            if(fpUser.getAab301()==null && "".equals(fpUser.getAab301())){

            }
            treeid = fpUser.getAab301();
            treeid =  aab301Substr(treeid)+ "%";
        }
        // 导出
        List<PoorLaborForce>   list = poorLaborForceService.queryExcel(treeid);


        //1 创建工作簿 HSSFWorkbook   2003    XSSFWorkbook   2007
        SXSSFWorkbook wb = new SXSSFWorkbook(100);
        //2 创建工作表
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


        /***
         * 定义公共变量
         */
        int rowNo = 0,cellNo = 0;
        Row nRow = null;
        Cell nCell = null;

        /**************大标题*************/
        //3 创建行
        nRow = sheet.createRow(rowNo);
        // 设置行高
        nRow.setHeightInPoints(36);

        //4 创建单元格
        nCell = nRow.createCell(cellNo);
        //5 设置内容
        nCell.setCellValue("贫困劳动力信息");
        //6 设置内容格式
        // 合并单元格
        //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, (short) 0, (short) 9));

        // 横向居中  +   水平居中   +  红色宋体22号
        nCell.setCellStyle(bigTitleCellStyle(wb));


        /*************小标题输出**************/
        // 行号rowNo需要变化吗  列需要变化吗？
        rowNo++;

        String[] titles = {"所属区域","劳动力姓名","身份证号","性别","劳动力电话","文化程度","就业创业状态","劳动能力","是否残疾人","备注"};

        //3 创建行
        nRow = sheet.createRow(rowNo);
        for (String title:titles){

            //4 创建单元格
            nCell = nRow.createCell(cellNo++);// 先创建cell单元格，然后在自增
            //5 设置内容
            nCell.setCellValue(title);
            //6 设置内容格式
            nCell.setCellStyle(titleCellStyle(wb));
        }


        /**************内容*************/
        // 行号和列号需要变化？
        rowNo++;


        for(PoorLaborForce poorLaborForce:list){
            cellNo=0;
            //3 创建行
            nRow = sheet.createRow(rowNo++);

            //4 创建单元格
            nCell = nRow.createCell(cellNo++);
            //5 设置内容
            nCell.setCellValue( poorLaborForce.getAab301());
            //6 设置内容格式
            nCell.setCellStyle(contentCellStyle(wb));

            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(poorLaborForce.getPlf004());
            nCell.setCellStyle(contentCellStyle(wb));

            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(poorLaborForce.getPlf005());
            nCell.setCellStyle(contentCellStyle(wb));

            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(poorLaborForce.getPlf007());
            nCell.setCellStyle(contentCellStyle(wb));

            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(poorLaborForce.getPlf006());
            nCell.setCellStyle(contentCellStyle(wb));

            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(poorLaborForce.getPlf009());
            nCell.setCellStyle(contentCellStyle(wb));

            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(poorLaborForce.getPlf011());
            nCell.setCellStyle(contentCellStyle(wb));

            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(poorLaborForce.getPlf018()+"");
            nCell.setCellStyle(contentCellStyle(wb));

            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(poorLaborForce.getPlf023());
            nCell.setCellStyle(contentCellStyle(wb));

            nCell = nRow.createCell(cellNo++);
            nCell.setCellValue(poorLaborForce.getPlf019());
            nCell.setCellStyle(contentCellStyle(wb));

        }




        /*************7 下载**********************/
        DownloadUtil downloadUtil = new DownloadUtil();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 将wb写入流
        wb.write(byteArrayOutputStream);

        /**
         byteArrayOutputStream 将文件内容写入ByteArrayOutputStream
         response HttpServletResponse	写入response
         returnName 返回的文件名
         */
        System.out.println("下载");
        downloadUtil.download(byteArrayOutputStream,response,"贫困劳动力信息.xlsx");
        System.out.println("cgl");
        return "1";
    }


    public CellStyle bigTitleCellStyle(Workbook wb){
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

    public CellStyle titleCellStyle(Workbook wb){
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
    public CellStyle contentCellStyle(Workbook wb){
        // 边框线   水平垂直居中
        CellStyle cellStyle = wb.createCellStyle();
        // 边框线
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);// 细线
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);//
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);//
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);//

        return cellStyle;

    }

    //获取aab301的截取
    public  String aab301Substr(String aab301){
        PoorXzqh poorXzqh =ztreeService.queryPoorXzqhSub(aab301);
        if(aab301.equals("610180000000")){
            aab301=aab301.substring(0, 5);
        }else  if(poorXzqh!=null && !"".equals(poorXzqh)){
            String type=poorXzqh.getType();
            if(type.equals("1")){
                aab301=aab301.substring(0, 2);
            }else if(type.equals("2")){
                aab301=aab301.substring(0, 4);
            }else if(type.equals("3")){
                aab301=aab301.substring(0, 6);
            }else if(type.equals("4")){
                aab301=aab301.substring(0, 9);
            }else if(type.equals("5")){
                aab301=aab301;
            }
        }
        return aab301;
    }
}
