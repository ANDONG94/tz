package com.tdkj.controller.excel;


import com.tdkj.dto.ErrorMsgDTO;
import com.tdkj.dto.message;
import com.tdkj.model.*;
import com.tdkj.service.InformationCollection.PoorLaborForceService;
import com.tdkj.service.Param.Aa10Service;
import com.tdkj.service.excel.Excelfile;
import com.tdkj.util.IdCardManageUtil;
import com.tdkj.util.UUIDGenerator;
import com.tdkj.util.checkUtil.MobileMailValidator;
import com.tdkj.util.listSplit;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 技工院校信息导入
 */
@RestController
@RequestMapping("/importThs")
public class ExcelTechnicalSchoolsController {

    @Autowired
    private Excelfile excelfile;
    @Autowired
    private Aa10Service aa10Service;
    @Autowired
    HttpServletRequest request;

    private IdCardManageUtil idCardManageUtil=new IdCardManageUtil();

    @Autowired
    private PoorLaborForceService poorLaborForceService;




    /**
     * 正确信息
     * @param batch
     * @return
     */
    @GetMapping("/save")
    public String saveCorrect(String batch,String aab301){
        if(!"".equals(batch) && batch != null){
            int mess=excelfile.SaveTechnicalSchools(batch);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            request.getSession().getAttribute("TS"+aab301+fpUser.getId()+"batch");
            return mess+"";
        }else{
            return null;
        }
    }

    /**
     * 查询
     * @return
     */
    @GetMapping("/clean")
    public String clean(String aab301){

        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        String batch = (String) request.getSession().getAttribute("TS"+aab301+fpUser.getId()+"batch");
        if(!"".equals(batch) && batch!=null){
            return batch;
        }
        return "false";

    }

    /**
     * 正确信息
     * @param batch
     * @return
     */
    @GetMapping("/Correct")
    public String getCorrect(String batch){
        if(!"".equals(batch) && batch != null){
            List<TechnicalSchoolsTmp>list= excelfile.getTechnicalSchoolsCorrect(batch);
            DataTableResultVO<TechnicalSchoolsTmp> result=new DataTableResultVO<>();
            result.setData(list);//data参数。
            result.setRecordsTotal(list.size());//数据的条数
            result.setRecordsFiltered(list.size());//过滤后数据的条数
            return JSONObject.fromObject(result).toString();
        }else{
            return null;
        }
    }

    /**
     * 错误信息
     * @param batch
     * @return
     */
    @GetMapping("/Error")
    public String getError(String batch){
        if(!"".equals(batch) && batch != null){
            List<TechnicalSchoolsTmp>list= excelfile.getTechnicalSchoolsError(batch);
            DataTableResultVO<TechnicalSchoolsTmp> result=new DataTableResultVO<>();
            result.setData(list);//data参数。
            result.setRecordsTotal(list.size());//数据的条数
            result.setRecordsFiltered(list.size());//过滤后数据的条数
            return JSONObject.fromObject(result).toString();
        }else{
            return null;
        }
    }


    /**
     * 临时保存
     * @param file
     * @param aab301
     * @param request
     * @return
     */
    @RequestMapping("/TechnicalSchools")
    public  String upload(@RequestParam(value = "file")MultipartFile file, String aab301, HttpServletRequest request) {
        String batch =UUIDGenerator.generate().toString();
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
      if(!"".equals(aab301) && aab301 !=null){
            String newaab301 = aab301.substring(9,12);
            if(newaab301.equals("000")){
                //说明不是村级
                return "no";
            }
        }else{
            //说明没有值
            return "no";
        }
        try {
            List<TechnicalSchoolsTmp> list = new ArrayList<TechnicalSchoolsTmp>();
            System.out.println("开始");
            //使用POI解析Excel文件
            //如果是xls，使用HSSFWorkbook；2003年的excel  如果是xlsx，使用XSSFWorkbook  2007年excel
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            //根据名称获得指定Sheet对象
            XSSFSheet hssfSheet = workbook.getSheetAt(0);
            for (Row row : hssfSheet) {
                TechnicalSchoolsTmp technicalSchoolsTmp = new TechnicalSchoolsTmp();
                int rowNum = row.getRowNum();
                if(rowNum == 0){//跳出第一行   一般第一行都是表头没有数据意义
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    String value=row.getCell(0).getStringCellValue();
                    if(!"劳动力姓名*".equals(value)){
                        return "error";
                    }
                    continue;
                }
                technicalSchoolsTmp.setThs001(UUIDGenerator.generate().toString());
                technicalSchoolsTmp.setAae036(new Date());
                technicalSchoolsTmp.setDatasource("2");//1代表录入，2代表导入
                technicalSchoolsTmp.setAae100("1");
                technicalSchoolsTmp.setIdentification("1");
                technicalSchoolsTmp.setMessage("==");
                technicalSchoolsTmp.setAab301(aab301);
                technicalSchoolsTmp.setBatch(batch);
                technicalSchoolsTmp.setAae011(fpUser.getId());
                technicalSchoolsTmp.setCreateby(fpUser.getRealname());
                if(row.getCell(0)!=null && !"".equals(row.getCell(0))){//第0列数据 劳动力姓名
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    String aac003=row.getCell(0).getStringCellValue();
                    if(!"".equals(aac003) && aac003!=null ){
                        technicalSchoolsTmp.setAac003(aac003);
                    }else{
                        String msss=technicalSchoolsTmp.getMessage();
                        technicalSchoolsTmp.setMessage(msss+"劳动力姓名必须填写==");
                        technicalSchoolsTmp.setIdentification("2");
                    }
                }else{
                    String msss=technicalSchoolsTmp.getMessage();
                    technicalSchoolsTmp.setMessage(msss+"劳动力姓名必须填写==");
                    technicalSchoolsTmp.setIdentification("2");
                }
                if(row.getCell(1)!=null && !"".equals(row.getCell(1))){//第1列数据  劳动力身份证号码
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    String idcard=row.getCell(1).getStringCellValue();
                    String msg=idCardManageUtil.checkIdCard(idcard);
                    if(!msg.equals("该身份证有效！")){
                        String msss=technicalSchoolsTmp.getMessage();
                        technicalSchoolsTmp.setMessage(msss+"("+idcard+")"+msg+"==");
                        technicalSchoolsTmp.setIdentification("2");
                    }
                        technicalSchoolsTmp.setThs002(idcard);
                }else{
                    String msss=technicalSchoolsTmp.getMessage();
                    technicalSchoolsTmp.setMessage(msss+"贫困劳动力身份证必须填写==");
                    technicalSchoolsTmp.setIdentification("2");
                }

                if(row.getCell(2)!=null && !"".equals(row.getCell(2))){//第2列数据 技工院校名称
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    String ths003=row.getCell(2).getStringCellValue();
                    if(ths003!=null && !"".equals(ths003)){
                        technicalSchoolsTmp.setThs003(ths003);
                    }else{
                        String msss=technicalSchoolsTmp.getMessage();
                        technicalSchoolsTmp.setMessage(msss+"技工院校名称必须填写==");
                        technicalSchoolsTmp.setIdentification("2");
                    }

                }else{
                    String msss=technicalSchoolsTmp.getMessage();
                    technicalSchoolsTmp.setMessage(msss+"技工院校名称必须填写==");
                    technicalSchoolsTmp.setIdentification("2");
                }
                if(row.getCell(3)!=null && !"".equals(row.getCell(3))){//第2列数据 技工院校地址
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    String ths004=row.getCell(3).getStringCellValue();
                    if(ths004!=null && !"".equals(ths004)){
                        technicalSchoolsTmp.setThs004(ths004);
                    }else{
                        String msss=technicalSchoolsTmp.getMessage();
                        technicalSchoolsTmp.setMessage(msss+"技工院校地址必须填写==");
                        technicalSchoolsTmp.setIdentification("2");
                    }

                }else{
                    String msss=technicalSchoolsTmp.getMessage();
                    technicalSchoolsTmp.setMessage(msss+"技工院校地址必须填写==");
                    technicalSchoolsTmp.setIdentification("2");
                }
                if(row.getCell(4)!=null && !"".equals(row.getCell(4))){//第2列数据 所学专业
                    row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                    String ths007=row.getCell(4).getStringCellValue();
                    if(ths007!=null && !"".equals(ths007)){
                        technicalSchoolsTmp.setThs007(ths007);
                    }
                }

                if(row.getCell(5)!=null && !"".equals(row.getCell(5))){//第10列数据  入学日期
                    row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                    String ths005=row.getCell(5).getStringCellValue();

                    if(ths005!=null && !"".equals(ths005)){
                      String sub=  ths005.substring(4,5);
                        if (!sub.equals("-")){
                            ths005=ths005.substring(0,4)+"-09-01";
                        }
                        boolean True =MobileMailValidator.isValidDate(ths005);
                        if(True){
                            technicalSchoolsTmp.setThs005(ths005);
                        }else{
                            String msss=technicalSchoolsTmp.getMessage();
                            technicalSchoolsTmp.setMessage(msss+"入学日期格式错误==");
                            technicalSchoolsTmp.setIdentification("2");
                        }
                    }else{
                        String msss=technicalSchoolsTmp.getMessage();
                        technicalSchoolsTmp.setMessage(msss+"入学日期必须填写==");
                        technicalSchoolsTmp.setIdentification("2");
                    }

                }else{
                    String msss=technicalSchoolsTmp.getMessage();
                    technicalSchoolsTmp.setMessage(msss+"培训起始日期必须填写==");
                    technicalSchoolsTmp.setIdentification("2");
                }

                if(row.getCell(6)!=null && !"".equals(row.getCell(6))){//第10列数据  毕业日期
                    row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                    String ths006=row.getCell(6).getStringCellValue();
                    if(ths006!=null && !"".equals(ths006)){
                        boolean True =MobileMailValidator.isValidDate(ths006);
                        if(True){
                            technicalSchoolsTmp.setThs006(ths006);
                        }else{
                            String msss=technicalSchoolsTmp.getMessage();
                            technicalSchoolsTmp.setMessage(msss+"毕业日期格式错误==");
                            technicalSchoolsTmp.setIdentification("2");
                        }
                    }

                }
                if(row.getCell(7)!=null && !"".equals(row.getCell(7))){//第10列数据  毕业后就业年月
                    row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                    String ths008=row.getCell(7).getStringCellValue();
                        technicalSchoolsTmp.setThs008(ths008);
                }

                if(row.getCell(8)!=null && !"".equals(row.getCell(8))){//第7列数据   学年制*
                    row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                    String ths013=row.getCell(8).getStringCellValue();
                    if(ths013!=null && !"".equals(ths013)){
                        boolean True =MobileMailValidator.isNumeric(ths013);
                        if(True){
                            technicalSchoolsTmp.setThs013(ths013);
                        }else{
                            String msss=technicalSchoolsTmp.getMessage();
                            technicalSchoolsTmp.setMessage(msss+"学年制必须为数字==");
                            technicalSchoolsTmp.setIdentification("2");
                        }
                    }

                }
                if(row.getCell(9)!=null && !"".equals(row.getCell(9))){//第2列数据 是否苏陕协作x
                    row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
                    String ths010=row.getCell(9).getStringCellValue();
                    if(ths010!=null && !"".equals(ths010)){
                        message message=queryAa10("EDC441",ths010,"是否苏陕协作",technicalSchoolsTmp);
                        technicalSchoolsTmp=checkType(message,technicalSchoolsTmp,"ths010");
                    }else{
                        String msss=technicalSchoolsTmp.getMessage();
                        technicalSchoolsTmp.setMessage(msss+"是否苏陕协作必须选择==");
                        technicalSchoolsTmp.setIdentification("2");
                    }

                }
                if(row.getCell(10)!=null && !"".equals(row.getCell(10))){//第7列数据   享受助学金*
                    row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
                    String ths014=row.getCell(10).getStringCellValue();
                    if(ths014!=null && !"".equals(ths014)){
                        boolean True =MobileMailValidator.isNumeric(ths014);
                        if(True){
                            technicalSchoolsTmp.setThs014(ths014);
                        }else{
                            String msss=technicalSchoolsTmp.getMessage();
                            technicalSchoolsTmp.setMessage(msss+"享受助学金必须为数字==");
                            technicalSchoolsTmp.setIdentification("2");
                        }
                    }

                }

                if(row.getCell(11)!=null && !"".equals(row.getCell(11))){//第7列数据   享受免学费补贴*
                    row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                    String ths016=row.getCell(11).getStringCellValue();
                    if(ths016!=null && !"".equals(ths016)){
                        boolean True =MobileMailValidator.isNumeric(ths016);
                        if(True){
                            technicalSchoolsTmp.setThs016(ths016);
                        }else{
                            String msss=technicalSchoolsTmp.getMessage();
                            technicalSchoolsTmp.setMessage(msss+"享受免学费补贴必须为数字==");
                            technicalSchoolsTmp.setIdentification("2");
                        }
                    }

                }
                if(row.getCell(12)!=null && !"".equals(row.getCell(12))){//第7列数据   享受雨露计划补贴*
                    row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
                    String ths015=row.getCell(12).getStringCellValue();
                    if(ths015!=null && !"".equals(ths015)){
                        boolean True =MobileMailValidator.isNumeric(ths015);
                        if(True){
                            technicalSchoolsTmp.setThs015(ths015);
                        }else{
                            String msss=technicalSchoolsTmp.getMessage();
                            technicalSchoolsTmp.setMessage(msss+"享受雨露计划贴必须为数字==");
                            technicalSchoolsTmp.setIdentification("2");
                        }
                    }
                }

                if(row.getCell(13)!=null && !"".equals(row.getCell(13))){//第11列数据
                    row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
                    String ths011= row.getCell(13).getStringCellValue();
                    technicalSchoolsTmp.setThs011(ths011);
                }

                String mess=technicalSchoolsTmp.getMessage();
                if(mess.length()>2){
                    mess=  mess.substring(2,mess.length()-2);
                }else{
                    mess="";
                }
                technicalSchoolsTmp.setMessage(mess);

                 list.add(technicalSchoolsTmp);
            }
            //调用service执行保存typeLists的方法
            List<List<TechnicalSchoolsTmp>> result = listSplit.splitListTHS(list, 900);
            int i=0;
            for (List list1:result) {
                System.out.println(list1.size()+"======"+i);
                excelfile.saveExcelListTSS(list1);
                i++;
            }


        }catch(Exception e){
            e.printStackTrace();
        }
        request.getSession().setAttribute("TS"+aab301+fpUser.getId()+"batch",batch);
        return batch;
    }


    /**
     * 封装返回
      * @param messages
     * @param type
     * @return
     */
public static TechnicalSchoolsTmp checkType(message messages,TechnicalSchoolsTmp technicalSchoolsTmp,String type){
 if(messages!=null){
        if(type.equals("ths010")){
            technicalSchoolsTmp.setThs010(messages.getValue());
        }
        if(!messages.getAae100().equals("1")){
            String mess=technicalSchoolsTmp.getMessage();
            technicalSchoolsTmp.setMessage(mess+messages.getMessage());
            technicalSchoolsTmp.setIdentification("2");
        }
    }
   return technicalSchoolsTmp;
}

    /**
     * 下拉值
     * @param aaa100
     * @param value
     * @param name
     * @param OBJ
     * @return
     */
    public  message queryAa10(String aaa100, String value, String name,Object OBJ){
        message message = new message();
        List<Aa10> aa10=(List<Aa10>)request.getSession().getAttribute("AA10_"+aaa100);
        if (aa10==null){
            aa10= aa10Service.queryAa10ByAaa100(aaa100);
            request.getSession().setAttribute("AA10_"+aaa100,aa10);
        }
        for (Aa10 aa10_type: aa10) {
            if (value.trim().equals(aa10_type.getAaa103().trim())){
                message.setValue(aa10_type.getAaa102());
                message.setAae100("1");
                break ;
            }
        }
        if(!"".equals(message.getValue()) && message.getValue() != null){
        }else{
            message.setMessage(name+"类型错误==");
            message.setValue(value);
            message.setAae100("0");
        }
        return message;
    }
}
