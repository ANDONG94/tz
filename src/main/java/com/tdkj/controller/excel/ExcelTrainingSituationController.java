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
 * 培训信息导入
 */
@RestController
@RequestMapping("/importTs")
public class ExcelTrainingSituationController {

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
            int mess=excelfile.SaveTrainingSituation(batch);
            FpUser fpUser = (FpUser)request.getSession().getAttribute("fpUser");
            request.getSession().getAttribute("TR"+aab301+fpUser.getId()+"batch");
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
        String batch = (String) request.getSession().getAttribute("TR"+aab301+fpUser.getId()+"batch");
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
            List<TrainingSituationTmp>list= excelfile.getTrainingSituationCorrect(batch);
            DataTableResultVO<TrainingSituationTmp> result=new DataTableResultVO<>();
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
            List<TrainingSituationTmp>list= excelfile.getTrainingSituationError(batch);
            DataTableResultVO<TrainingSituationTmp> result=new DataTableResultVO<>();
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
    @RequestMapping("/TrainingSituation")
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


            List<TrainingSituationTmp> list = new ArrayList<TrainingSituationTmp>();
            System.out.println("开始");
            //使用POI解析Excel文件
            //如果是xls，使用HSSFWorkbook；2003年的excel  如果是xlsx，使用XSSFWorkbook  2007年excel
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            //根据名称获得指定Sheet对象
            XSSFSheet hssfSheet = workbook.getSheetAt(0);
            for (Row row : hssfSheet) {
                TrainingSituationTmp trainingSituationTmp = new TrainingSituationTmp();
                int rowNum = row.getRowNum();
                if(rowNum == 0){//跳出第一行   一般第一行都是表头没有数据意义
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    String value=row.getCell(0).getStringCellValue();
                    if(!"劳动力姓名*".equals(value)){
                        return "error";
                    }
                    continue;
                }
                trainingSituationTmp.setTsn001(UUIDGenerator.generate().toString());
                trainingSituationTmp.setAae036(new Date());
                trainingSituationTmp.setDatasource("2");//1代表录入，2代表导入
                trainingSituationTmp.setAae100("1");
                trainingSituationTmp.setIdentification("1");
                trainingSituationTmp.setMessage("==");
                trainingSituationTmp.setAab301(aab301);
                trainingSituationTmp.setBatch(batch);
                trainingSituationTmp.setAae011(fpUser.getId());
                trainingSituationTmp.setCreateby(fpUser.getRealname());
                if(row.getCell(0)!=null && !"".equals(row.getCell(0))){//第0列数据 劳动力姓名
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    String aac003=row.getCell(0).getStringCellValue();
                    if(!"".equals(aac003) && aac003!=null ){
                        trainingSituationTmp.setAac003(aac003);
                    }else{
                        String msss=trainingSituationTmp.getMessage();
                        trainingSituationTmp.setMessage(msss+"劳动力姓名必须填写==");
                        trainingSituationTmp.setIdentification("2");
                    }
                }else{
                    String msss=trainingSituationTmp.getMessage();
                    trainingSituationTmp.setMessage(msss+"劳动力姓名必须填写==");
                    trainingSituationTmp.setIdentification("2");
                }
                if(row.getCell(1)!=null && !"".equals(row.getCell(1))){//第1列数据  劳动力身份证号码
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    String idcard=row.getCell(1).getStringCellValue();
                    String msg=idCardManageUtil.checkIdCard(idcard);
                    if(!msg.equals("该身份证有效！")){
                        String msss=trainingSituationTmp.getMessage();
                        trainingSituationTmp.setMessage(msss+"("+idcard+")"+msg+"==");
                        trainingSituationTmp.setIdentification("2");
                    }
                        trainingSituationTmp.setTsn010(idcard);
                }else{
                    String msss=trainingSituationTmp.getMessage();
                    trainingSituationTmp.setMessage(msss+"贫困劳动力身份证必须填写==");
                    trainingSituationTmp.setIdentification("2");
                }

                if(row.getCell(2)!=null && !"".equals(row.getCell(2))){//第2列数据 培训类型x
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    String tsn003=row.getCell(2).getStringCellValue();
                    message message=queryAa10("AAA007",tsn003,"培训类型",trainingSituationTmp);
                    trainingSituationTmp=checkType(message,trainingSituationTmp,"tsn003");
                }else{
                    String msss=trainingSituationTmp.getMessage();
                    trainingSituationTmp.setMessage(msss+"培训类型必须填写==");
                    trainingSituationTmp.setIdentification("2");
                }
                if(row.getCell(3)!=null && !"".equals(row.getCell(3))){//第2列数据 培训工种x
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    String tsn004=row.getCell(3).getStringCellValue();
                    message message=queryAa10("AAA021",tsn004,"培训工种",trainingSituationTmp);
                    trainingSituationTmp=checkType(message,trainingSituationTmp,"tsn004");
                }else{
                    String msss=trainingSituationTmp.getMessage();
                    trainingSituationTmp.setMessage(msss+"培训工种必须填写==");
                    trainingSituationTmp.setIdentification("2");
                }

                if(row.getCell(4)!=null && !"".equals(row.getCell(4))){//第4列数据 培训单位名称*
                    row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                    String tsn005=row.getCell(4).getStringCellValue();
                    trainingSituationTmp.setTsn005(tsn005);
                }else{
                    String msss=trainingSituationTmp.getMessage();
                    trainingSituationTmp.setMessage(msss+"培训单位名称必须填写==");
                    trainingSituationTmp.setIdentification("2");
                }

                if(row.getCell(5)!=null && !"".equals(row.getCell(5))){//第5列数据  培训地址*
                    row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                    String tsn006=row.getCell(5).getStringCellValue();
                    trainingSituationTmp.setTsn006(tsn006);
                }

                if(row.getCell(6)!=null && !"".equals(row.getCell(6))){//第10列数据  培训起始日期
                    row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                    String tsn007=row.getCell(6).getStringCellValue();
                    boolean True =MobileMailValidator.isValidDate(tsn007);
                    if(True){
                        trainingSituationTmp.setTsn007(tsn007);
                    }else{
                        String msss=trainingSituationTmp.getMessage();
                        trainingSituationTmp.setMessage(msss+"培训起始日期格式错误==");
                        trainingSituationTmp.setIdentification("2");
                    }
                }else{
                    String msss=trainingSituationTmp.getMessage();
                    trainingSituationTmp.setMessage(msss+"培训起始日期必须填写==");
                    trainingSituationTmp.setIdentification("2");
                }
                if(row.getCell(7)!=null && !"".equals(row.getCell(7))){//第10列数据  培训终止日期
                    row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                    String tsn008=row.getCell(7).getStringCellValue();
                    if(tsn008!=null && !"".equals(tsn008)){
                        boolean True =MobileMailValidator.isValidDate(tsn008);
                        if(True){
                            trainingSituationTmp.setTsn008(tsn008);
                        }else{
                            String msss=trainingSituationTmp.getMessage();
                            trainingSituationTmp.setMessage(msss+"培训终止日期格式错误==");
                            trainingSituationTmp.setIdentification("2");
                        }
                    }

                }

                if(row.getCell(8)!=null && !"".equals(row.getCell(8))){//第7列数据   实际培训天数*
                    row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                    String tsn011=row.getCell(8).getStringCellValue();
                    if(tsn011!=null && !"".equals(tsn011)){
                        boolean True =MobileMailValidator.isNumeric(tsn011);
                        if(True){
                            trainingSituationTmp.setTsn011(tsn011);
                        }else{
                            String msss=trainingSituationTmp.getMessage();
                            trainingSituationTmp.setMessage(msss+"实际培训天数必须为数字==");
                            trainingSituationTmp.setIdentification("2");
                        }
                    }

                }
                if(row.getCell(9)!=null && !"".equals(row.getCell(9))){//第2列数据 苏陕协作x
                    row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
                    String tsn012=row.getCell(9).getStringCellValue();
                    message message=queryAa10("EDC441",tsn012,"苏陕协作",trainingSituationTmp);
                    trainingSituationTmp=checkType(message,trainingSituationTmp,"tsn012");
                }else{
                    String msss=trainingSituationTmp.getMessage();
                    trainingSituationTmp.setMessage(msss+"苏陕协作必须选择==");
                    trainingSituationTmp.setIdentification("2");
                }

                if(row.getCell(10)!=null && !"".equals(row.getCell(10))){//第7列数据   享受培训和生活补贴*
                    row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
                    String tsn016=row.getCell(10).getStringCellValue();
                    if(tsn016!=null && !"".equals(tsn016)){
                        boolean True =MobileMailValidator.isNumeric(tsn016);
                        if(True){
                            trainingSituationTmp.setTsn016(tsn016);
                        }else{
                            String msss=trainingSituationTmp.getMessage();
                            trainingSituationTmp.setMessage(msss+"==");
                            trainingSituationTmp.setIdentification("2");
                        }
                    }

                }
                if(row.getCell(11)!=null && !"".equals(row.getCell(11))){//第11列数据
                    String tsn002=row.getCell(11).getStringCellValue();
                    if(tsn002!=null && !"".equals(tsn002)){
                        boolean True =MobileMailValidator.isValidDate(tsn002);
                        if(True){
                            trainingSituationTmp.setTsn002(tsn002);
                        }else{
                            String msss=trainingSituationTmp.getMessage();
                            trainingSituationTmp.setMessage(msss+"培训后就业年月格式错误==");
                            trainingSituationTmp.setIdentification("2");
                        }
                    }
                }

                String mess=trainingSituationTmp.getMessage();
                if(mess.length()>2){
                    mess=  mess.substring(2,mess.length()-2);
                }else{
                    mess="";
                }
                trainingSituationTmp.setMessage(mess);


                 list.add(trainingSituationTmp);
            }
            //调用service执行保存typeLists的方法
            List<List<TrainingSituationTmp>> result = listSplit.splitListTS(list, 900);
            int i=0;
            for (List list1:result) {
                System.out.println(list1.size()+"======"+i);
                excelfile.saveExcelListTS(list1);
                i++;
            }


        }catch(Exception e){
            e.printStackTrace();
        }
        request.getSession().setAttribute("TR"+aab301+fpUser.getId()+"batch",batch);
        return batch;
    }


    /**
     * 封装返回
      * @param messages
     * @param type
     * @return
     */
public static TrainingSituationTmp checkType(message messages,TrainingSituationTmp trainingSituationTmp,String type){
 if(messages!=null){
        if(type.equals("tsn003")){
            trainingSituationTmp.setTsn003(messages.getValue());
        }else if(type.equals("tsn004")){
            trainingSituationTmp.setTsn004(messages.getValue());
        }else if(type.equals("tsn012")){
            trainingSituationTmp.setTsn012(messages.getValue());
        }
        if(!messages.getAae100().equals("1")){
            String mess=trainingSituationTmp.getMessage();
            trainingSituationTmp.setMessage(mess+messages.getMessage());
            trainingSituationTmp.setIdentification("2");
        }
    }
   return trainingSituationTmp;
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
