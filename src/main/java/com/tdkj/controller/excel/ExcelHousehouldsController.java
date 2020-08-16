package com.tdkj.controller.excel;


import com.tdkj.dto.ErrorMsgDTO;
import com.tdkj.dto.message;
import com.tdkj.model.*;
import com.tdkj.service.InformationCollection.PoorWorkService;
import com.tdkj.service.Param.Aa10Service;
import com.tdkj.service.Param.CentralizedSettlementsService;
import com.tdkj.service.Ztree.ZtreeService;
import com.tdkj.service.excel.Excelfile;
import com.tdkj.util.AA10Util;
import com.tdkj.util.IdCardManageUtil;
import com.tdkj.util.UUIDGenerator;
import com.tdkj.util.ValidateUtils;
import com.tdkj.util.checkUtil.MobileMailValidator;
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
 * 贫困户导入
 */
@RestController
@RequestMapping("/import")
public class ExcelHousehouldsController {

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

    private AA10Util aa10Util  =new AA10Util();
    private IdCardManageUtil idCardManageUtil=new IdCardManageUtil();

    /**
     * 正确信息
     * @param batch
     * @return
     */
    @GetMapping("/save")
    public String saveCorrect(String batch,String aab301) throws Exception {
        if(!"".equals(batch) && batch != null){
            int mess=excelfile.SavePoorHouseholds(batch);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            request.getSession().setAttribute("HD"+aab301+fpUser.getId()+"batch",null);
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
        String batch = (String) request.getSession().getAttribute("HD"+aab301+fpUser.getId()+"batch");
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
            List<PoorHouseholdsTmp>list= excelfile.getPoorHouseholdsTmpCorrect(batch);
            DataTableResultVO<PoorHouseholdsTmp> result=new DataTableResultVO<>();
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
            List<PoorHouseholdsTmp>list= excelfile.getPoorHouseholdsTmpError(batch);
            DataTableResultVO<PoorHouseholdsTmp> result=new DataTableResultVO<>();
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
    @RequestMapping("/households")
    public  String upload(@RequestParam(value = "file")MultipartFile file,String aab301, HttpServletRequest request) {
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
            List<PoorHouseholdsTmp> list = new ArrayList<PoorHouseholdsTmp>();
            System.out.println("开始");
            //使用POI解析Excel文件
            //如果是xls，使用HSSFWorkbook；2003年的excel  如果是xlsx，使用XSSFWorkbook  2007年excel
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            //根据名称获得指定Sheet对象
            XSSFSheet hssfSheet = workbook.getSheetAt(0);
            for (Row row : hssfSheet) {
                PoorHouseholdsTmp poorHouseholdsTmp = new PoorHouseholdsTmp();
                int rowNum = row.getRowNum();
                if(rowNum == 0){//跳出第一行   一般第一行都是表头没有数据意义
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    String value=row.getCell(0).getStringCellValue();
                    if(!"认定年度".equals(value)){
                        return "error";
                    }
                    continue;
                }
                poorHouseholdsTmp.setPhd001(UUIDGenerator.generate().toString());
                poorHouseholdsTmp.setAae036(new Date());
                poorHouseholdsTmp.setDatasource("2");//1代表录入，2代表导入
                poorHouseholdsTmp.setAae100("1");
                poorHouseholdsTmp.setIdentification("1");
                poorHouseholdsTmp.setMessage("==");
                poorHouseholdsTmp.setAab301(aab301);
                poorHouseholdsTmp.setBatch(batch);
                poorHouseholdsTmp.setAae011(fpUser.getId());
                poorHouseholdsTmp.setCreateby(fpUser.getRealname());
                if(row.getCell(0)!=null && !"".equals(row.getCell(0))){//第1列数据  认定年度
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    String Phd012=row.getCell(0).getStringCellValue();

                    if(!"".equals(Phd012) && Phd012!=null){
                        boolean True = MobileMailValidator.isNumeric(Phd012);
                        if(!True){
                            String msss=poorHouseholdsTmp.getMessage();
                            poorHouseholdsTmp.setMessage(msss+"认定年度必须为数字==");
                            poorHouseholdsTmp.setIdentification("2");
                        }
                        poorHouseholdsTmp.setPhd012(row.getCell(0).getStringCellValue());
                    }
                }
                if(row.getCell(1)!=null && !"".equals(row.getCell(1))){//第2列数据 户主姓名
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    String Phd002= row.getCell(1).getStringCellValue();
                    if(!"".equals(Phd002) && Phd002!=null){
                        poorHouseholdsTmp.setPhd002(row.getCell(1).getStringCellValue());
                    }else{
                        String msss=poorHouseholdsTmp.getMessage();
                        poorHouseholdsTmp.setMessage(msss+"户主姓名必须填写==");
                        poorHouseholdsTmp.setIdentification("2");
                    }

                }else{
                    String msss=poorHouseholdsTmp.getMessage();
                    poorHouseholdsTmp.setMessage(msss+"户主姓名必须填写==");
                    poorHouseholdsTmp.setIdentification("2");
                }
                if(row.getCell(2)!=null && !"".equals(row.getCell(2))){//第4列数据  户主身份证
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    String phd003=row.getCell(2).getStringCellValue();
                    String msg=idCardManageUtil.checkIdCard(phd003);
                    if(msg.equals("该身份证有效！")){
                        String sex=idCardManageUtil.getSexFromIdCard(phd003);
                        String age=idCardManageUtil.IdNOToAge(phd003);
                        poorHouseholdsTmp.setPhd004(sex);
                        poorHouseholdsTmp.setPhd005(age);
                    }else{
                        String msss=poorHouseholdsTmp.getMessage();
                        poorHouseholdsTmp.setMessage(msss+"("+phd003+")"+msg+"==");
                        poorHouseholdsTmp.setIdentification("2");
                    }
                    poorHouseholdsTmp.setPhd003(phd003.toString());
                }else{
                    String msss=poorHouseholdsTmp.getMessage();
                    poorHouseholdsTmp.setMessage(msss+"户主身份证号码必须填写==");
                    poorHouseholdsTmp.setIdentification("2");
                }
                if(row.getCell(3)!=null && !"".equals(row.getCell(3))){//第5列数据  户主电话
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    String phd006=row.getCell(3).getStringCellValue();
                    boolean bool=ValidateUtils.isMobile(phd006);
                    if(bool){
                        poorHouseholdsTmp.setPhd006(phd006);
                    }else{
                        poorHouseholdsTmp.setPhd006(phd006);
                        String msg=poorHouseholdsTmp.getMessage();
                        poorHouseholdsTmp.setMessage(msg+"电话号码错误==");
                        poorHouseholdsTmp.setIdentification("2");
                    }
                }else{
                    String msss=poorHouseholdsTmp.getMessage();
                    poorHouseholdsTmp.setMessage(msss+"户主电话号码必须填写==");
                    poorHouseholdsTmp.setIdentification("2");
                }
                if(row.getCell(4)!=null && !"".equals(row.getCell(4))){//第6列数据  详细地址
                    row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                    String Phd007= row.getCell(4).getStringCellValue();
                    if(!"".equals(Phd007) && Phd007!=null){
                        poorHouseholdsTmp.setPhd007(row.getCell(4).getStringCellValue());
                    }else{
                        String msss=poorHouseholdsTmp.getMessage();
                        poorHouseholdsTmp.setMessage(msss+"详细地址必须填写==");
                        poorHouseholdsTmp.setIdentification("2");
                    }
                }else{
                    String msss=poorHouseholdsTmp.getMessage();
                    poorHouseholdsTmp.setMessage(msss+"详细地址必须填写==");
                    poorHouseholdsTmp.setIdentification("2");
                }
                if(row.getCell(5)!=null && !"".equals(row.getCell(5))){//第7列数据   是否属于易地搬迁
                    row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                    String Phd013=row.getCell(5).getStringCellValue();
                    message message=queryAa10("EDC441",Phd013,"是否属于易地扶贫搬迁",poorHouseholdsTmp);
                    poorHouseholdsTmp=checkType(message,poorHouseholdsTmp,"013");

                }else{
                    String msss=poorHouseholdsTmp.getMessage();
                    poorHouseholdsTmp.setMessage(msss+"是否属于易地扶贫搬迁必须选择==");
                    poorHouseholdsTmp.setIdentification("2");
                }
                if(row.getCell(6)!=null && !"".equals(row.getCell(6))){//第8列数据  集中安置点名称
                    row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                    String phd010=row.getCell(6).getStringCellValue();
                    phd010=phd010.substring(phd010.indexOf("-")+1);
                    if(phd010!=null && !"".equals(phd010)){
                        message message=getName(phd010);
                        poorHouseholdsTmp=checkType(message,poorHouseholdsTmp,"010");
                    }
                }else{

                   String phd013= poorHouseholdsTmp.getPhd013();
                   if(phd013!=null && !"".equals(phd013)){
                       if("1".equals(phd013)){
                           String msss=poorHouseholdsTmp.getMessage();
                           poorHouseholdsTmp.setMessage(msss+"易地扶贫搬迁户必须填写安置点名称==");
                           poorHouseholdsTmp.setIdentification("2");
                       }

                   }
                }
                if(row.getCell(7)!=null && !"".equals(row.getCell(7))){//第9列数据   是否残疾人
                    row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                    String Phd016=row.getCell(7).getStringCellValue();
                    if(Phd016!=null && !"".equals(Phd016)){
                        message message=queryAa10("EDC441",Phd016,"是否残疾人",poorHouseholdsTmp);
                        poorHouseholdsTmp=checkType(message,poorHouseholdsTmp,"016");
                    }

                }
                if(row.getCell(8)!=null && !"".equals(row.getCell(8))){//第10列数据  脱贫状态
                    row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                    String Phd014=row.getCell(8).getStringCellValue();
                    if(Phd014!=null && !"".equals(Phd014)){
                        message message=queryAa10("AAA012",Phd014,"脱贫状态",poorHouseholdsTmp);
                        poorHouseholdsTmp=checkType(message,poorHouseholdsTmp,"014");
                    }

                }
                if(row.getCell(9)!=null && !"".equals(row.getCell(9))){//第11列数据   备注
                    row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
                    poorHouseholdsTmp.setPhd011(row.getCell(9).getStringCellValue());
                }
                String mess=poorHouseholdsTmp.getMessage();
                if(mess.length()>2){
                    mess=  mess.substring(2,mess.length()-2);
                }else{
                    mess="";
                }
                poorHouseholdsTmp.setMessage(mess);
                list.add(poorHouseholdsTmp);
            }
            //调用service执行保存typeLists的方法
            if(list.size()>999){

                return "MAX";
            }
            excelfile.saveExcelList(list);
        }catch(Exception e){
            e.printStackTrace();
        }
        request.getSession().setAttribute("HD"+aab301+fpUser.getId()+"batch",batch);
        return batch;
    }


    /**
     * 封装返回
      * @param messages
     * @param poorHouseholdsTmp
     * @param type
     * @return
     */
public static PoorHouseholdsTmp checkType(message messages,PoorHouseholdsTmp poorHouseholdsTmp,String type){

    if(messages!=null){
        if(type.equals("013")){
            poorHouseholdsTmp.setPhd013(messages.getValue());
        }else if(type.equals("014")){
            poorHouseholdsTmp.setPhd014(messages.getValue());
        }else if(type.equals("016")){
            poorHouseholdsTmp.setPhd016(messages.getValue());
        }else if(type.equals("010")){
            poorHouseholdsTmp.setPhd010(messages.getValue());
        }
        if(!messages.getAae100().equals("1")){
            String mess=poorHouseholdsTmp.getMessage();
            poorHouseholdsTmp.setMessage(mess+messages.getMessage());
            poorHouseholdsTmp.setIdentification("2");
        }
    }
   return poorHouseholdsTmp;
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
        }else  if(poorXzqh!=null && !"".equals(poorXzqh)) {
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
        List<CentralizedSettlements> list=(List<CentralizedSettlements>)request.getSession().getAttribute("centralized"+aab301);
        if (list==null){
            list= centralizedSettlementsService.queryCentralizedSettlementsByAab301(aab301+"%");
            request.getSession().setAttribute("centralized"+aab301,list);
        }
        if(list.size()>0){

            for (CentralizedSettlements cs:list) {
                String cts002=cs.getCts002();
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
