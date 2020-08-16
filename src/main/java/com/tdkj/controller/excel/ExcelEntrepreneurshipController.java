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
 * 创业信息导入
 */
@RestController
@RequestMapping("/importEP")
public class ExcelEntrepreneurshipController {

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
            int mess=excelfile.SaveEntrepreneurship(batch);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            request.getSession().getAttribute("EP"+aab301+fpUser.getId()+"batch");
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
        String batch = (String) request.getSession().getAttribute("EP"+aab301+fpUser.getId()+"batch");
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
            List<EntrepreneurshipTmp>list= excelfile.getEntrepreneurshipCorrect(batch);
            DataTableResultVO<EntrepreneurshipTmp> result=new DataTableResultVO<>();
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
            List<EntrepreneurshipTmp>list= excelfile.getEntrepreneurshipError(batch);
            DataTableResultVO<EntrepreneurshipTmp> result=new DataTableResultVO<>();
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
    @RequestMapping("/EntrepreneurshipTmp")
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


            List<EntrepreneurshipTmp> list = new ArrayList<EntrepreneurshipTmp>();
            System.out.println("开始");
            //使用POI解析Excel文件
            //如果是xls，使用HSSFWorkbook；2003年的excel  如果是xlsx，使用XSSFWorkbook  2007年excel
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            //根据名称获得指定Sheet对象
            XSSFSheet hssfSheet = workbook.getSheetAt(0);
            for (Row row : hssfSheet) {
                EntrepreneurshipTmp entrepreneurshipTmp = new EntrepreneurshipTmp();
                int rowNum = row.getRowNum();
                if(rowNum == 0){//跳出第一行   一般第一行都是表头没有数据意义
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    String value=row.getCell(0).getStringCellValue();
                    if(!"劳动力姓名*".equals(value)){
                        return "error";
                    }
                    continue;
                }
                entrepreneurshipTmp.setEpp001(UUIDGenerator.generate().toString());
                entrepreneurshipTmp.setAae036(new Date());
                entrepreneurshipTmp.setDatasource("2");//1代表录入，2代表导入
                entrepreneurshipTmp.setAae100("1");
                entrepreneurshipTmp.setIdentification("1");
                entrepreneurshipTmp.setMessage("==");
                entrepreneurshipTmp.setAab301(aab301);
                entrepreneurshipTmp.setBatch(batch);
                entrepreneurshipTmp.setAae011(fpUser.getId());
                entrepreneurshipTmp.setCreateby(fpUser.getRealname());
                if(row.getCell(0)!=null && !"".equals(row.getCell(0))){//第0列数据 劳动力姓名
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    String aac003=row.getCell(0).getStringCellValue();
                    if(!"".equals(aac003) && aac003!=null ){
                        entrepreneurshipTmp.setAac003(aac003);
                    }else{
                        String msss=entrepreneurshipTmp.getMessage();
                        entrepreneurshipTmp.setMessage(msss+"劳动力姓名必须填写==");
                        entrepreneurshipTmp.setIdentification("2");
                    }
                }else{
                    String msss=entrepreneurshipTmp.getMessage();
                    entrepreneurshipTmp.setMessage(msss+"劳动力姓名必须填写==");
                    entrepreneurshipTmp.setIdentification("2");
                }
                if(row.getCell(1)!=null && !"".equals(row.getCell(1))){//第1列数据  劳动力身份证号码
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    String idcard=row.getCell(1).getStringCellValue();
                    String msg=idCardManageUtil.checkIdCard(idcard);
                    if(!msg.equals("该身份证有效！")){
                        String msss=entrepreneurshipTmp.getMessage();
                        entrepreneurshipTmp.setMessage(msss+"("+idcard+")"+msg+"==");
                        entrepreneurshipTmp.setIdentification("2");
                    }
                        entrepreneurshipTmp.setEpp002(idcard);
                }else{
                    String msss=entrepreneurshipTmp.getMessage();
                    entrepreneurshipTmp.setMessage(msss+"贫困劳动力身份证必须填写==");
                    entrepreneurshipTmp.setIdentification("2");
                }

                if(row.getCell(2)!=null && !"".equals(row.getCell(2))){//第2列数据 创业项目
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    String epp003=row.getCell(2).getStringCellValue();
                    message message=queryAa10("AAA019",epp003,"创业项目",entrepreneurshipTmp);
                    entrepreneurshipTmp=checkType(message,entrepreneurshipTmp,"epp003");
                }else{
                    String msss=entrepreneurshipTmp.getMessage();
                    entrepreneurshipTmp.setMessage(msss+"创业项目必须填写==");
                    entrepreneurshipTmp.setIdentification("2");
                }
                if(row.getCell(3)!=null && !"".equals(row.getCell(3))){//第3列数据 创业单位
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    String epp004=row.getCell(3).getStringCellValue();
                    entrepreneurshipTmp.setEpp004(epp004);
                }

                if(row.getCell(4)!=null && !"".equals(row.getCell(4))){//第4列数据 创业地址
                    row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                    String epp005=row.getCell(4).getStringCellValue();
                    if(!"".equals(epp005) && epp005!=null){

                    }else{
                        String msss=entrepreneurshipTmp.getMessage();
                        entrepreneurshipTmp.setMessage(msss+"创业地址必须填写==");
                        entrepreneurshipTmp.setIdentification("2");
                    }
                    entrepreneurshipTmp.setEpp005(epp005);
                }else{
                    String msss=entrepreneurshipTmp.getMessage();
                    entrepreneurshipTmp.setMessage(msss+"创业地址必须填写==");
                    entrepreneurshipTmp.setIdentification("2");
                }
                if(row.getCell(5)!=null && !"".equals(row.getCell(5))){//第5列数据  登记注册*
                    row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                    String epp010=row.getCell(5).getStringCellValue();
                    message message=queryAa10("EDC441",epp010,"登记注册",entrepreneurshipTmp);
                    entrepreneurshipTmp=checkType(message,entrepreneurshipTmp,"epp010");
                }else{
                    String msss=entrepreneurshipTmp.getMessage();
                    entrepreneurshipTmp.setMessage(msss+"登记注册必须填写==");
                    entrepreneurshipTmp.setIdentification("2");
                }
                if(row.getCell(6)!=null && !"".equals(row.getCell(6))){//第5列数据  起始日期
                    row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                    String epp006=row.getCell(6).getStringCellValue();
                    if(!"".equals(epp006) && epp006 !=null){
                        boolean True =MobileMailValidator.isValidDate(epp006);
                        if(!True){
                            String msss=entrepreneurshipTmp.getMessage();
                            entrepreneurshipTmp.setMessage(msss+"创业起始日期格式错误==");
                            entrepreneurshipTmp.setIdentification("2");
                        }
                    }else{
                        String msss=entrepreneurshipTmp.getMessage();
                        entrepreneurshipTmp.setMessage(msss+"就业起始日期必须填写==");
                        entrepreneurshipTmp.setIdentification("2");
                    }
                    entrepreneurshipTmp.setEpp006(epp006);
                }
                if(row.getCell(7)!=null && !"".equals(row.getCell(7))){//第7列数据   带动就业人数*
                    row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                    String epp011=row.getCell(7).getStringCellValue();
                    if(!"".equals(epp011) && epp011!=null){
                        boolean True =MobileMailValidator.isNumeric(epp011);
                        if(True){
                            entrepreneurshipTmp.setEpp011(epp011);
                        }else{
                            String msss=entrepreneurshipTmp.getMessage();
                            entrepreneurshipTmp.setMessage(msss+"带动就业人数必须为数字==");
                            entrepreneurshipTmp.setIdentification("2");
                        }
                    }

                }

                if(row.getCell(8)!=null && !"".equals(row.getCell(8))){//第7列数据   带动贫困劳动力人数*
                    row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                    String epp012=row.getCell(8).getStringCellValue();
                    if(!"".equals(epp012) && epp012!=null){
                        boolean True =MobileMailValidator.isNumeric(epp012);
                        if(True){
                            entrepreneurshipTmp.setEpp012(epp012);
                        }else{
                            String msss=entrepreneurshipTmp.getMessage();
                            entrepreneurshipTmp.setMessage(msss+"带动贫困劳动力人数必须为数字==");
                            entrepreneurshipTmp.setIdentification("2");
                        }
                    }
                }

                if(row.getCell(9)!=null && !"".equals(row.getCell(9))){//第7列数据   创业担保贷款*
                    row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
                    String epp014=row.getCell(9).getStringCellValue();
                    if(!"".equals(epp014) && epp014!=null){
                        boolean True =MobileMailValidator.isNumeric(epp014);
                        if(True){
                            entrepreneurshipTmp.setEpp014(epp014);
                        }else{
                            String msss=entrepreneurshipTmp.getMessage();
                            entrepreneurshipTmp.setMessage(msss+"创业担保贷款必须为数字==");
                            entrepreneurshipTmp.setIdentification("2");
                        }
                    }

                }

                if(row.getCell(10)!=null && !"".equals(row.getCell(10))){//第7列数据   创业补贴*
                    row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
                    String epp015=row.getCell(10).getStringCellValue();
                    if(!"".equals(epp015) && epp015!=null){
                        boolean True =MobileMailValidator.isNumeric(epp015);
                        if(True){
                            entrepreneurshipTmp.setEpp015(epp015);
                        }else{
                            String msss=entrepreneurshipTmp.getMessage();
                            entrepreneurshipTmp.setMessage(msss+"创业补贴必须为数字==");
                            entrepreneurshipTmp.setIdentification("2");
                        }
                    }
            }

                if(row.getCell(11)!=null && !"".equals(row.getCell(11))){//第11列数据   创业培训
                    row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                    String epp013= row.getCell(11).getStringCellValue();
                    if(!"".equals(epp013) && epp013!=null){
                        if ("是".equals(epp013)){
                            entrepreneurshipTmp.setEpp013("1");
                        }
                    }

                }
                if(row.getCell(12)!=null && !"".equals(row.getCell(12))){//第11列数据   创业孵化
                    row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
                    String epp013= row.getCell(12).getStringCellValue();
                    if(!"".equals(epp013) && epp013!=null){
                        if ("是".equals(epp013)){
                          String epp=  entrepreneurshipTmp.getEpp013();
                          if(!"".equals(epp) && epp !=null){
                              entrepreneurshipTmp.setEpp013(",2");
                          }
                        }
                    }
                }

                if(row.getCell(13)!=null && !"".equals(row.getCell(13))){//第11列数据   减免税
                    row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
                    String epp013= row.getCell(13).getStringCellValue();
                    if(!"".equals(epp013) && epp013!=null){
                        if ("是".equals(epp013)){
                            String epp=  entrepreneurshipTmp.getEpp013();
                            if(!"".equals(epp) && epp !=null){
                                entrepreneurshipTmp.setEpp013(",3");
                            }
                        }
                    }
                }

                String mess=entrepreneurshipTmp.getMessage();
                if(mess.length()>2){
                    mess=  mess.substring(2,mess.length()-2);
                }else{
                    mess="";
                }
                entrepreneurshipTmp.setMessage(mess);
                 list.add(entrepreneurshipTmp);
            }
            //调用service执行保存typeLists的方法
            //调用service执行保存typeLists的方法
            List<List<EntrepreneurshipTmp>> result = listSplit.splitListEP(list, 900);
            for (List list1:result) {
                excelfile.saveExcelListEP(list1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        request.getSession().setAttribute("EP"+aab301+fpUser.getId()+"batch",batch);
        return batch;
    }


    /**
     * 封装返回
      * @param messages
     * @param type
     * @return
     */
public static EntrepreneurshipTmp checkType(message messages,EntrepreneurshipTmp entrepreneurshipTmp,String type){
 if(messages!=null){
        if(type.equals("epp003")){
            entrepreneurshipTmp.setEpp003(messages.getValue());
        }else if(type.equals("epp010")){
            entrepreneurshipTmp.setEpp010(messages.getValue());
        }
        if(!messages.getAae100().equals("1")){
            String mess=entrepreneurshipTmp.getMessage();
            entrepreneurshipTmp.setMessage(mess+messages.getMessage());
            entrepreneurshipTmp.setIdentification("2");
        }
    }
   return entrepreneurshipTmp;
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
