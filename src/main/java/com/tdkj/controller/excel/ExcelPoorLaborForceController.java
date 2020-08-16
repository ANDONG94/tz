package com.tdkj.controller.excel;


import com.tdkj.dto.ErrorMsgDTO;
import com.tdkj.dto.message;
import com.tdkj.model.*;
import com.tdkj.service.InformationCollection.PoorLaborForceService;
import com.tdkj.service.InformationCollection.PoorWorkService;
import com.tdkj.service.Param.Aa10Service;
import com.tdkj.service.Param.CentralizedSettlementsService;
import com.tdkj.service.Ztree.ZtreeService;
import com.tdkj.service.excel.Excelfile;
import com.tdkj.util.IdCardManageUtil;
import com.tdkj.util.UUIDGenerator;
import com.tdkj.util.ValidateUtils;
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
 * 贫困劳动力导入
 */
@RestController
@RequestMapping("/importPLF")
public class ExcelPoorLaborForceController {

    @Autowired
    private Excelfile excelfile;
    @Autowired
    private Aa10Service aa10Service;
    @Autowired
    HttpServletRequest request;
    @Autowired
    private CentralizedSettlementsService centralizedSettlementsService;
    @Autowired
    private ZtreeService ztreeService;
    @Autowired
    private PoorWorkService poorWorkService;
    private IdCardManageUtil idCardManageUtil=new IdCardManageUtil();

    @Autowired
    private PoorLaborForceService poorLaborForceService;


    /**
     * 正确信息
     * @param batch
     * @return
     */
    @GetMapping("/save")
    public String saveCorrect(String batch,String aab301) throws Exception {
        if(!"".equals(batch) && batch != null){
            int mess=excelfile.SavePoorLaborForce(batch);
            excelfile.PutPoorHouseholds(batch);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            request.getSession().setAttribute("PLF"+aab301+fpUser.getId()+"batch",null);
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
        String batch = (String) request.getSession().getAttribute("PLF"+aab301+fpUser.getId()+"batch");
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
            List<PoorLaborForceTmp>list= excelfile.getPoorLaborForceTmpCorrect(batch);
            DataTableResultVO<PoorLaborForceTmp> result=new DataTableResultVO<>();
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
            List<PoorLaborForceTmp>list= excelfile.getPoorLaborForceTmpError(batch);
            DataTableResultVO<PoorLaborForceTmp> result=new DataTableResultVO<>();
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
    @RequestMapping("/PoorLaborForce")
    public  String upload(@RequestParam(value = "file")MultipartFile file, String aab301, HttpServletRequest request) {
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        String batch =UUIDGenerator.generate().toString();
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
            List<PoorLaborForceTmp> list = new ArrayList<PoorLaborForceTmp>();
            System.out.println("开始");
            //使用POI解析Excel文件
            //如果是xls，使用HSSFWorkbook；2003年的excel  如果是xlsx，使用XSSFWorkbook  2007年excel
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            //根据名称获得指定Sheet对象
            XSSFSheet hssfSheet = workbook.getSheetAt(0);
            for (Row row : hssfSheet) {
                PoorLaborForceTmp laborForceTmp = new PoorLaborForceTmp();
                int rowNum = row.getRowNum();
                if(rowNum == 0){//跳出第一行   一般第一行都是表头没有数据意义
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    String value=row.getCell(0).getStringCellValue();
                    String zt=row.getCell(6).getStringCellValue();
                    if(!"户主姓名*".equals(value)){
                        return "error";
                    }
                    if(!"就业创业状态".equals(zt)){
                        return "error";
                    }
                    continue;
                }
                laborForceTmp.setPlf001(UUIDGenerator.generate().toString());
                laborForceTmp.setAae036(new Date());
                laborForceTmp.setDatasource("2");//1代表录入，2代表导入
                laborForceTmp.setAae100("1");
                laborForceTmp.setIdentification("1");
                laborForceTmp.setMessage("==");
                laborForceTmp.setAab301(aab301);
                laborForceTmp.setBatch(batch);
                laborForceTmp.setAae011(fpUser.getId());
                laborForceTmp.setCreateby(fpUser.getRealname());
                laborForceTmp.setPlf025("0");

                if(row.getCell(0)!=null && !"".equals(row.getCell(0))){//第0列数据 劳动力姓名
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    String aac003=row.getCell(0).getStringCellValue();
                    if(!"".equals(aac003) && aac003!=null ){
                        laborForceTmp.setAac003(aac003);
                    }else{
                        String msss=laborForceTmp.getMessage();
                        laborForceTmp.setMessage(msss+"户主姓名必须填写==");
                        laborForceTmp.setIdentification("2");
                    }
                }else{
                    String msss=laborForceTmp.getMessage();
                    laborForceTmp.setMessage(msss+"户主姓名必须填写==");
                    laborForceTmp.setIdentification("2");
                }
                if(!"".equals(row.getCell(1)) && row.getCell(1)!=null ){//第1列数据  户主身份证
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    String idcard=row.getCell(1).getStringCellValue();
                    String msg=idCardManageUtil.checkIdCard(idcard);
                    if(msg.equals("该身份证有效！")){
                        laborForceTmp.setPlf002(idcard);
                    }else{
                        String msss=laborForceTmp.getMessage();
                        laborForceTmp.setMessage(msss+"户主"+msg+"==");
                        laborForceTmp.setIdentification("2");
                    }

                }else{
                    String msss=laborForceTmp.getMessage();
                    laborForceTmp.setMessage(msss+"户主身份证必须填写==");
                    laborForceTmp.setIdentification("2");
                }

                if(!"".equals(row.getCell(2)) &&row.getCell(2)!=null ){//第2列数据 劳动力姓名
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    laborForceTmp.setPlf004(row.getCell(2).getStringCellValue());
                }else{
                    String msss=laborForceTmp.getMessage();
                    laborForceTmp.setMessage(msss+"劳动力姓名必须填写==");
                    laborForceTmp.setIdentification("2");
                }
                if(!"".equals(row.getCell(3)) &&row.getCell(3)!=null){//第3列数据  劳动力身份证
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    String Plf005=row.getCell(3).getStringCellValue();
                    String msg=idCardManageUtil.checkIdCard(Plf005);
                    if(msg.equals("该身份证有效！")){
                        String sex=idCardManageUtil.getSexFromIdCard(Plf005);
                        String age=idCardManageUtil.IdNOToAge(Plf005);
                        laborForceTmp.setPlf005(Plf005);
                        laborForceTmp.setPlf007(sex);
                        laborForceTmp.setPlf008(age);
                    }else{
                        laborForceTmp.setPlf005(Plf005);
                        String msss=laborForceTmp.getMessage();
                        laborForceTmp.setMessage(msss+"劳动力"+msg+"==");
                        laborForceTmp.setIdentification("2");
                    }
                }else{
                    String msss=laborForceTmp.getMessage();
                    laborForceTmp.setMessage(msss+"劳动力身份证==");
                    laborForceTmp.setIdentification("2");
                }
                if(!"".equals(row.getCell(4)) &&row.getCell(4)!=null){//第4列数据  劳动力电话
                    row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                    String plf006=row.getCell(4).getStringCellValue();
                    boolean bool=ValidateUtils.isMobile(plf006);
                    if(bool){
                        laborForceTmp.setPlf006(plf006);
                    }else{
                        laborForceTmp.setPlf006(plf006);
                        String msg=laborForceTmp.getMessage();
                        laborForceTmp.setMessage(msg+"电话号码错误==");
                        laborForceTmp.setIdentification("2");
                    }
                }else{
                    String msss=laborForceTmp.getMessage();
                    laborForceTmp.setMessage(msss+"劳动力电话必须填写==");
                    laborForceTmp.setIdentification("2");
                }
                if(!"".equals(row.getCell(5)) &&row.getCell(5)!=null){//第5列数据  劳动力文化程度
                    row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                    String plf009=row.getCell(5).getStringCellValue();
                    message message=queryAa10("AAC011",plf009,"劳动力文化程度",laborForceTmp);
                    laborForceTmp=checkType(message,laborForceTmp,"009");
                }else{
                    String msss=laborForceTmp.getMessage();
                    laborForceTmp.setMessage(msss+"劳动力文化程度必须选择==");
                    laborForceTmp.setIdentification("2");
                }
                if(!"".equals(row.getCell(6)) &&row.getCell(6)!=null){//第6列数据   就创业状态
                    row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                    String plf011=row.getCell(6).getStringCellValue();
                    if(!"".equals(plf011) && plf011!=null){
                        message message=queryAa10("AAA002",plf011,"就业创业状态",laborForceTmp);
                        laborForceTmp=checkType(message,laborForceTmp,"011");
                    }
                }
                if(!"".equals(row.getCell(7)) &&row.getCell(7)!=null){//第6列数据   就创业意愿

                        if(!"".equals(row.getCell(6)) && row.getCell(6)!=null){
                            row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                            String plf011=row.getCell(6).getStringCellValue();
                            if("转移就业".equals(plf011)){
                                //laborForceTmp.setPlf010("1");
                            }else if ("农业就业".equals(plf011)){

                            }else if ("自主创业".equals(plf011)){
                                //laborForceTmp.setPlf010("2");
                            }else if ("未就业创业".equals(plf011)){
                                row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                                String plf010=row.getCell(7).getStringCellValue();
                                if(!"".equals(plf010) && plf010 !=null){
                                    message message=queryAa10("AAA004",plf010,"就创业意愿",laborForceTmp);
                                    laborForceTmp=checkType(message,laborForceTmp,"010");
                                }else{
                                    String msss=laborForceTmp.getMessage();
                                    laborForceTmp.setMessage(msss+"未就业创业必须选择就创业意愿==");
                                    laborForceTmp.setIdentification("2");
                                }

                            }
                        }else{
                            row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                            String plf010=row.getCell(7).getStringCellValue();
                            message message=queryAa10("AAA004",plf010,"就创业意愿",laborForceTmp);
                            laborForceTmp=checkType(message,laborForceTmp,"010");
                        }

                }

                if(!"".equals(row.getCell(8)) &&row.getCell(8)!=null){//第7列数据   劳动能力
                    row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                    String plf018=row.getCell(8).getStringCellValue();
                    if(!"".equals(plf018) && plf018!=null){
                        message message=queryAa10("AAA016",plf018,"劳动能力",laborForceTmp);
                        laborForceTmp=checkType(message,laborForceTmp,"018");
                    }
                }
                if(!"".equals(row.getCell(9)) &&row.getCell(9)!=null){//第8列数据   培训教育状态
                    row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
                    String plf024=row.getCell(9).getStringCellValue();
                    if(plf024!=null && !"".equals(plf024)){
                        message message=queryAa10("PLF024",plf024,"培训教育状态",laborForceTmp);
                        laborForceTmp=checkType(message,laborForceTmp,"024");
                    }
                }

                if(!"".equals(row.getCell(10)) &&row.getCell(10)!=null){//第8列数据   培训教育意愿
                    row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
                    String plf012=row.getCell(10).getStringCellValue();
                    String plf024 = "";
                    if (row.getCell(9)!=null){
                        plf024=row.getCell(9).getStringCellValue();
                    }

                    if(!"".equals(plf024) && plf024 !=null){

                            if("转移就业技能培训".equals(plf024)){
                                //laborForceTmp.setPlf012("1");
                            }else if("创业培训".equals(plf024)){
                               // laborForceTmp.setPlf012("2");
                            }else if("岗位技能提升培训".equals(plf024)){
                               // laborForceTmp.setPlf012("3");
                            }else if("农业实用技术培训".equals(plf024)){
                               // laborForceTmp.setPlf012("4");
                            }else if("上技校".equals(plf024)){
                              //  laborForceTmp.setPlf012("5");
                            }else if("未参加培训".equals(plf024)){
                                if(!"".equals(plf012)&&  plf012!=null ){
                                    message message=queryAa10("AAA003",plf012,"培训教育意愿",laborForceTmp);
                                    laborForceTmp=checkType(message,laborForceTmp,"012");
                                }
                            }
                    }

                }
                if(!"".equals(row.getCell(11)) &&row.getCell(11)!=null){//第9列数据   是否残疾人
                    row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                    String plf023=row.getCell(11).getStringCellValue();
                    if(plf023!=null && !"".equals(plf023)){
                        message message=queryAa10("EDC441",plf023,"是否残疾人",laborForceTmp);
                        laborForceTmp=checkType(message,laborForceTmp,"023");
                    }
                }
                if(!"".equals(row.getCell(12)) &&row.getCell(12)!=null){//第10列数据  上学状态
                    row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
                    String plf015=row.getCell(12).getStringCellValue();
                    if(plf015!=null && !"".equals(plf015)){
                        message message=queryAa10("AAA017",plf015,"上学状态",laborForceTmp);
                        laborForceTmp=checkType(message,laborForceTmp,"015");
                    }
                }
                if(!"".equals(row.getCell(13)) &&row.getCell(13)!=null){//第11列数据   备注
                    row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
                    laborForceTmp.setPlf019(row.getCell(13).getStringCellValue());
                }
                String mess=laborForceTmp.getMessage();
                if(mess.length()>2){
                    mess=  mess.substring(2,mess.length()-2);
                }else{
                    mess="";
                }
                laborForceTmp.setMessage(mess);
                list.add(laborForceTmp);
            }
            //调用service执行保存typeLists的方法
            if(list.size()>999){

                return "MAX";
            }
            excelfile.saveExcelListPLF(list);
        }catch(Exception e){
            e.printStackTrace();
        }
        request.getSession().setAttribute("PLF"+aab301+fpUser.getId()+"batch",batch);
        return batch;
    }


    /**
     * 封装返回
      * @param messages
     * @param poorLaborForceTmp
     * @param type
     * @return
     */
public static PoorLaborForceTmp checkType(message messages,PoorLaborForceTmp poorLaborForceTmp,String type){

    if(messages!=null){
        if(type.equals("010")){
            poorLaborForceTmp.setPlf010(messages.getValue());
        }else if(type.equals("018")){
            poorLaborForceTmp.setPlf018(messages.getValue());

        }else if(type.equals("012")){
            poorLaborForceTmp.setPlf012(messages.getValue());
        }else if(type.equals("023")){
            poorLaborForceTmp.setPlf023(messages.getValue());
        }else if(type.equals("015")){
            poorLaborForceTmp.setPlf015(messages.getValue());
        }else if(type.equals("009")){
            poorLaborForceTmp.setPlf009(messages.getValue());
        }else if(type.equals("011")){
            poorLaborForceTmp.setPlf011(messages.getValue());
        }else if(type.equals("024")){
            poorLaborForceTmp.setPlf024(messages.getValue());
        }
        if(!messages.getAae100().equals("1")){
            String mess=poorLaborForceTmp.getMessage();
            poorLaborForceTmp.setMessage(mess+messages.getMessage());
            poorLaborForceTmp.setIdentification("2");
        }
    }
   return poorLaborForceTmp;
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
                System.out.println(name+"=========="+value);
                System.out.println(aa10);
            }
        return message;
    }

    /**
     * 安置点名称
     * @param name
     * @return
     */
    public message getName(String name){
        message message = new message();
        String aab301;
        FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
        aab301 = fpUser.getAab301();
        PoorXzqh poorXzqh =ztreeService.queryPoorXzqhSub(aab301);
        if(aab301.equals("610180000000")){
            aab301=aab301.substring(0, 5);
        }else   if(poorXzqh!=null && !"".equals(poorXzqh)) {
            String type = poorXzqh.getType();
            if (type.equals("1")) {
                aab301 = aab301.substring(0, 2);
            } else if (type.equals("2")) {
                aab301 = aab301.substring(0, 4);
            } else if (type.equals("3")) {
                aab301 = aab301.substring(0, 6);
            } else if (type.equals("4")) {
                aab301 = aab301.substring(0, 6);
            } else if (type.equals("5")) {
                aab301 = aab301.substring(0, 6);
            }
        }
        List<CentralizedSettlements> list= centralizedSettlementsService.queryCentralizedSettlementsByAab301(aab301+"%");
        if(list.size()>0){

            for (CentralizedSettlements cs:list) {
                String cts002=cs.getCts002();
                System.out.println(list);
                if(cts002.equals(name.trim())){
                    message.setAae100("1");
                    message.setValue(cs.getCts001());
                    return message;
                }else{
                    message.setValue(name);
                    message.setAae100("0");
                    message.setMessage("安置点名称选择有误==");
                }
            }
        }else{
            message.setValue(name);
            message.setAae100("0");
            message.setMessage("安置点名称选择有误==");
        }


        return message;
    }
}
