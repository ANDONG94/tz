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
import com.tdkj.util.checkUtil.MobileMailValidator;
import com.tdkj.util.listSplit;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.security.util.Length;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 就业信息导入
 */
@RestController
@RequestMapping("/importES")
public class ExcelEmploymentSintuationController {

    @Autowired
    private Excelfile excelfile;
    @Autowired
    private Aa10Service aa10Service;
    @Autowired
    HttpServletRequest request;

    private IdCardManageUtil idCardManageUtil = new IdCardManageUtil();

    @Autowired
    private PoorLaborForceService poorLaborForceService;


    /**
     * 正确信息
     *
     * @param batch
     * @return
     */
    @GetMapping("/save")
    public String saveCorrect(String batch, String aab301) {
        if (!"".equals(batch) && batch != null) {
            int mess = excelfile.SaveEmploymentSintuation(batch);
            FpUser fpUser = (FpUser) request.getSession().getAttribute("fpUser");
            request.getSession().setAttribute("ES" + aab301 + fpUser.getId() + "batch", null);
            return mess + "";
        } else {
            return null;
        }

    }


    /**
     * 正确信息
     *
     * @param
     * @return
     */
    @GetMapping("/tsave")
    public int tsaveCorrect() {
       String batch="2c9165a1731ed16801731ed54f830985";
        int mess = excelfile.SaveEmploymentSintuation(batch);
        return mess;
    }


    /**
     * 查询
     *
     * @return
     */
    @GetMapping("/clean")
    public String clean(String aab301) {
        FpUser fpUser = (FpUser) request.getSession().getAttribute("fpUser");
        String batch = (String) request.getSession().getAttribute("ES" + aab301 + fpUser.getId() + "batch");
        if (!"".equals(batch) && batch != null) {
            return batch;
        }
        return "false";

    }

    /**
     * 正确信息
     *
     * @param batch
     * @return
     */
    @GetMapping("/Correct")
    public String getCorrect(String batch) {
        if (!"".equals(batch) && batch != null) {
            List<EmploymentSituationTmp> list = excelfile.getEmploymentSintuationCorrect(batch);
            DataTableResultVO<EmploymentSituationTmp> result = new DataTableResultVO<>();
            result.setData(list);//data参数。
            result.setRecordsTotal(list.size());//数据的条数
            result.setRecordsFiltered(list.size());//过滤后数据的条数
            return JSONObject.fromObject(result).toString();
        } else {
            return null;
        }
    }

    /**
     * 错误信息
     *
     * @param batch
     * @return
     */
    @GetMapping("/Error")
    public String getError(String batch) {
        if (!"".equals(batch) && batch != null) {
            List<EmploymentSituationTmp> list = excelfile.getEmploymentSintuationError(batch);
            DataTableResultVO<EmploymentSituationTmp> result = new DataTableResultVO<>();
            result.setData(list);//data参数。
            result.setRecordsTotal(list.size());//数据的条数
            result.setRecordsFiltered(list.size());//过滤后数据的条数
            return JSONObject.fromObject(result).toString();
        } else {
            return null;
        }
    }


    /**
     * 临时保存
     *
     * @param file
     * @param aab301
     * @param request
     * @return
     */
    @RequestMapping("/EmploymentSituation")
    public String upload(@RequestParam(value = "file") MultipartFile file, String aab301, HttpServletRequest request) {
        String batch = UUIDGenerator.generate().toString();
        FpUser fpUser = (FpUser) request.getSession().getAttribute("fpUser");
        if (!"".equals(aab301) && aab301 != null) {
            String newaab301 = aab301.substring(9, 12);
            if (newaab301.equals("000")) {
                //说明不是村级
                return "no";
            }
        } else {
            //说明没有值
            return "no";
        }
        try {


            List<EmploymentSituationTmp> list = new ArrayList<EmploymentSituationTmp>();
            System.out.println("开始");
            //使用POI解析Excel文件
            //如果是xls，使用HSSFWorkbook；2003年的excel  如果是xlsx，使用XSSFWorkbook  2007年excel
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            //根据名称获得指定Sheet对象
            XSSFSheet hssfSheet = workbook.getSheetAt(0);
            for (Row row : hssfSheet) {
                EmploymentSituationTmp employmentSituationTmp = new EmploymentSituationTmp();
                int rowNum = row.getRowNum();
                if (rowNum == 0) {//跳出第一行   一般第一行都是表头没有数据意义
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    String value = row.getCell(0).getStringCellValue();
                    if (!"劳动力姓名*".equals(value)) {
                        return "error";
                    }
                    continue;
                }
                employmentSituationTmp.setEys001(UUIDGenerator.generate().toString());
                employmentSituationTmp.setAae036(new Date());
                employmentSituationTmp.setDatasource("1");//1代表录入，2代表导入
                employmentSituationTmp.setAae100("1");
                employmentSituationTmp.setIdentification("1");
                employmentSituationTmp.setMessage("==");
                employmentSituationTmp.setAab301(aab301);
                employmentSituationTmp.setBatch(batch);
                employmentSituationTmp.setAae011(fpUser.getId());
                employmentSituationTmp.setCreateby(fpUser.getRealname());
                if (row.getCell(0) != null && !"".equals(row.getCell(0))) {//第0列数据 劳动力姓名必须填写
                    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    String aac003 = row.getCell(0).getStringCellValue();
                    if (!"".equals(aac003) && aac003 != null) {
                        employmentSituationTmp.setAac003(aac003);
                    } else {
                        String msss = employmentSituationTmp.getMessage();
                        employmentSituationTmp.setMessage(msss + "劳动力姓名必须填写==");
                        employmentSituationTmp.setIdentification("2");
                    }
                } else {
                    String msss = employmentSituationTmp.getMessage();
                    employmentSituationTmp.setMessage(msss + "劳动力姓名必须填写==");
                    employmentSituationTmp.setIdentification("2");
                }
                if (row.getCell(1) != null && !"".equals(row.getCell(1))) {//第1列数据  劳动力身份证号码
                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    String idcard = row.getCell(1).getStringCellValue();
                    String msg = idCardManageUtil.checkIdCard(idcard);
                    if (!msg.equals("该身份证有效！")) {
                        String msss = employmentSituationTmp.getMessage();
                        employmentSituationTmp.setMessage(msss + "(" + idcard + ")" + msg + "==");
                        employmentSituationTmp.setIdentification("2");
                    }
                    employmentSituationTmp.setEys002(idcard);
                } else {
                    String msss = employmentSituationTmp.getMessage();
                    employmentSituationTmp.setMessage(msss + "贫困劳动力身份证必须填写==");
                    employmentSituationTmp.setIdentification("2");
                }
                if (row.getCell(2) != null && !"".equals(row.getCell(2))) {//第10列数据  就业起始日期
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    String eys009 = row.getCell(2).getStringCellValue();
                    String eys004 = employmentSituationTmp.getEys004();
                    if (eys004 != "3") {
                        employmentSituationTmp.setEys009(eys009);
                        if (!"".equals(eys009) && eys009 != null) {
                            boolean True = MobileMailValidator.isValidDate(eys009);
                            if (!True) {
                                boolean num = MobileMailValidator.isNumeric(eys009);
                                if (num && eys009.length() == 5) {
                                    employmentSituationTmp.setEys009(dateFormat(Long.parseLong(eys009)));
                                } else {
                                    employmentSituationTmp.setEys009(eys009);
                                    String msss = employmentSituationTmp.getMessage();
                                    employmentSituationTmp.setMessage(msss + "就业起始日期格式错误==");
                                    employmentSituationTmp.setIdentification("2");
                                }
                            }

                        } else {
                            employmentSituationTmp.setEys009(eys009);
                            String msss = employmentSituationTmp.getMessage();
                            employmentSituationTmp.setMessage(msss + "就业起始日期必须填写==");
                            employmentSituationTmp.setIdentification("2");
                        }
                    }
                }
                if (row.getCell(3) != null && !"".equals(row.getCell(3))) {//第2列数据 就业渠道
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                    String eys003 = row.getCell(3).getStringCellValue();
                    if ("".equals(eys003)) {
                        eys003 = "自行就业";
                    }
                    message message = queryAa10("AAA013", eys003, "就业渠道", employmentSituationTmp);
                    employmentSituationTmp = checkType(message, employmentSituationTmp, "eys003");
                } else {
                    String eys003 = "自行就业";
                    message message = queryAa10("AAA013", eys003, "就业渠道", employmentSituationTmp);
                    employmentSituationTmp = checkType(message, employmentSituationTmp, "eys003");
                }

                if (row.getCell(4) != null && !"".equals(row.getCell(4))) {//第3列数据 就业地域
                    row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                    String eys012 = row.getCell(4).getStringCellValue();
                    message message = queryAa10("AAA008", eys012, "就业地域", employmentSituationTmp);
                    employmentSituationTmp = checkType(message, employmentSituationTmp, "eys012");
                } else {
                    String msss = employmentSituationTmp.getMessage();
                    employmentSituationTmp.setMessage(msss + "就业地域必须填写==");
                    employmentSituationTmp.setIdentification("2");
                }
                if (row.getCell(5) != null && !"".equals(row.getCell(5))) {//第11列数据   就业地址
                    row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                    String eys008 = row.getCell(5).getStringCellValue();
                    if (!"".equals(eys008) && eys008 != null) {
                        employmentSituationTmp.setEys008(row.getCell(5).getStringCellValue());
                    } else {
                        String msss = employmentSituationTmp.getMessage();
                        employmentSituationTmp.setMessage(msss + "就业地址必须填写==");
                        employmentSituationTmp.setIdentification("2");
                    }

                }
                if (row.getCell(6) != null && !"".equals(row.getCell(6))) {//第5列数据  就业单位
                    row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                    String eys006 = row.getCell(6).getStringCellValue();
                    employmentSituationTmp.setEys006(eys006);
                }
                if (row.getCell(7) != null && !"".equals(row.getCell(7))) {//第4列数据 就业类型
                    row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                    String eys004 = row.getCell(7).getStringCellValue();
                    message message = queryAa10("AAA006", eys004, "就业类型", employmentSituationTmp);
                    employmentSituationTmp = checkType(message, employmentSituationTmp, "eys004");
                } else {
                    String msss = employmentSituationTmp.getMessage();
                    employmentSituationTmp.setMessage(msss + "就业类型必须填写==");
                    employmentSituationTmp.setIdentification("2");
                }
                if (row.getCell(8) != null && !"".equals(row.getCell(8))) {//第5列数据  上年度农业人均纯收入(元)
                    row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                    String eys015 = row.getCell(8).getStringCellValue();
                    if (!"".equals(eys015) && eys015 != null) {
                        boolean True = MobileMailValidator.isNumeric(eys015);
                        employmentSituationTmp.setEys015(eys015);
                        if (!True) {
                            String msss = employmentSituationTmp.getMessage();
                            employmentSituationTmp.setMessage(msss + "上年度农业人均纯收入必须为数字==");
                            employmentSituationTmp.setIdentification("2");
                        }
                    }

                }


                if (row.getCell(9) != null && !"".equals(row.getCell(9))) {//第7列数据   就业单位性质
                    row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);
                    String eys007 = row.getCell(9).getStringCellValue();
                    if (!"".equals(eys007) && eys007 != null) {
                        message message = queryAa10("ECC207", eys007, "就业单位性质", employmentSituationTmp);
                        employmentSituationTmp = checkType(message, employmentSituationTmp, "eys007");
                    }

                }
                if (row.getCell(10) != null && !"".equals(row.getCell(10))) {//第8列数据   岗位工种
                    row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);
                    String eys005 = row.getCell(10).getStringCellValue();
                    if (!"".equals(eys005) && eys005 != null) {
                        message message = queryAa10("AAA018", eys005, "岗位工种", employmentSituationTmp);
                        employmentSituationTmp = checkType(message, employmentSituationTmp, "eys005");
                    }
                }
                if (row.getCell(11) != null && !"".equals(row.getCell(11))) {//第9列数据   特色就业
                    row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                    String eys011 = row.getCell(11).getStringCellValue();
                    if (!"".equals(eys011) && eys011 != null) {
                        message message = queryAa10("AAA005", eys011, "特色就业", employmentSituationTmp);
                        employmentSituationTmp = checkType(message, employmentSituationTmp, "eys011");
                    }
                }

                /*if(row.getCell(11)!=null && !"".equals(row.getCell(11))){//第11列数据  就业起始日期
                    row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);
                    String eys010=row.getCell(11).getStringCellValue();
                    if(!"".equals(eys010) && eys010!=null){
                        boolean True =MobileMailValidator.isValidDate(eys010);
                        if(True){
                            employmentSituationTmp.setEys010(eys010);
                        }else{
                            String msss=employmentSituationTmp.getMessage();
                            employmentSituationTmp.setMessage(msss+"结束日期格式错误==");
                            employmentSituationTmp.setIdentification("2");
                        }
                    }
                }*/

                if (row.getCell(12) != null && !"".equals(row.getCell(12))) {//第12列数据  签订合同或协议
                    row.getCell(12).setCellType(Cell.CELL_TYPE_STRING);
                    String eeys016 = row.getCell(12).getStringCellValue();
                    if (!"".equals(eeys016) && eeys016 != null) {
                        boolean True = MobileMailValidator.isNumeric(eeys016);
                        if (True) {
                            employmentSituationTmp.setEys016(eeys016);
                        } else {
                            String msss = employmentSituationTmp.getMessage();
                            employmentSituationTmp.setMessage(msss + "签订合同或协议（月）必须为数字==");
                            employmentSituationTmp.setIdentification("2");
                        }
                    }

                }

               /* if(row.getCell(13)!=null && !"".equals(row.getCell(13))){//第13列数据  就业见习补贴(元)
                    row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
                    String eys019=row.getCell(13).getStringCellValue();
                    if(!"".equals(eys019) && eys019!=null){
                        boolean True =MobileMailValidator.isNumeric(eys019);
                        if(True){
                            employmentSituationTmp.setEys019(eys019);
                        }else{
                            String msss=employmentSituationTmp.getMessage();
                            employmentSituationTmp.setMessage(msss+"就业见习补贴(元)必须为数字==");
                            employmentSituationTmp.setIdentification("2");
                        }
                    }
                }
                if(row.getCell(14)!=null && !"".equals(row.getCell(14))){//第14列数据  求职补贴(元)
                    row.getCell(14).setCellType(Cell.CELL_TYPE_STRING);
                    String eys020=row.getCell(14).getStringCellValue();
                    if(!"".equals(eys020) && eys020!=null){
                        boolean True =MobileMailValidator.isNumeric(eys020);
                        if(True){
                            employmentSituationTmp.setEys020(eys020);
                        }else{
                            String msss=employmentSituationTmp.getMessage();
                            employmentSituationTmp.setMessage(msss+"求职补贴(元)必须为数字==");
                            employmentSituationTmp.setIdentification("2");
                        }
                    }

                }
                if(row.getCell(15)!=null && !"".equals(row.getCell(15))){//第15列数据  转移就业交通补贴(元)
                    row.getCell(15).setCellType(Cell.CELL_TYPE_STRING);
                    String eys021=row.getCell(15).getStringCellValue();
                    if(!"".equals(eys021) && eys021!=null){
                        boolean True =MobileMailValidator.isNumeric(eys021);
                        if(True){
                            employmentSituationTmp.setEys021(eys021);
                        }else{
                            String msss=employmentSituationTmp.getMessage();
                            employmentSituationTmp.setMessage(msss+"转移就业交通补贴(元)必须为数字==");
                            employmentSituationTmp.setIdentification("2");
                        }
                    }
                }*/

                if (row.getCell(13) != null && !"".equals(row.getCell(13))) {//第11列数据   备注
                    row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);
                    employmentSituationTmp.setEys014(row.getCell(13).getStringCellValue());
                }
                String mess = employmentSituationTmp.getMessage();
                if (mess.length() > 2) {
                    mess = mess.substring(2, mess.length() - 2);
                } else {
                    mess = "";
                }
                employmentSituationTmp.setMessage(mess);
                    list.add(employmentSituationTmp);
            }
            //调用service执行保存typeLists的方法
            List<List<EmploymentSituationTmp>> result = listSplit.splitListES(list, 900);
            for (List list1 : result) {
                excelfile.saveExcelListES(list1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getSession().setAttribute("ES" + aab301 + fpUser.getId() + "batch", batch);
        return batch;
    }


    /**
     * 封装返回
     *
     * @param messages
     * @param type
     * @return
     */
    public static EmploymentSituationTmp checkType(message messages, EmploymentSituationTmp employmentSituationTmp, String type) {
        if (messages != null) {
            if (type.equals("eys003")) {
                employmentSituationTmp.setEys003(messages.getValue());
            } else if (type.equals("eys012")) {
                System.out.println("type====" + type);
                System.out.println("messages.getValue()====" + messages.getValue());
                employmentSituationTmp.setEys012(messages.getValue());
            } else if (type.equals("eys004")) {
                employmentSituationTmp.setEys004(messages.getValue());
            } else if (type.equals("eys007")) {
                employmentSituationTmp.setEys007(messages.getValue());
            } else if (type.equals("eys005")) {
                employmentSituationTmp.setEys005(messages.getValue());
            } else if (type.equals("eys011")) {
                employmentSituationTmp.setEys011(messages.getValue());
            }
            if (!messages.getAae100().equals("1")) {
                String mess = employmentSituationTmp.getMessage();
                employmentSituationTmp.setMessage(mess + messages.getMessage());
                employmentSituationTmp.setIdentification("2");
            }
        }
        return employmentSituationTmp;
    }

    /**
     * 下拉值
     *
     * @param aaa100
     * @param value
     * @param name
     * @param OBJ
     * @return
     */
    public message queryAa10(String aaa100, String value, String name, Object OBJ) {
        message message = new message();
        List<Aa10> aa10 = (List<Aa10>) request.getSession().getAttribute("AA10_" + aaa100);
        if (aa10 == null) {
            aa10 = aa10Service.queryAa10ByAaa100(aaa100);
            request.getSession().setAttribute("AA10_" + aaa100, aa10);
        }
        for (Aa10 aa10_type : aa10) {
            if (value.trim().equals(aa10_type.getAaa103().trim())) {
                message.setValue(aa10_type.getAaa102());
                message.setAae100("1");
                break;
            }
        }
        if (!"".equals(message.getValue()) && message.getValue() != null) {
        } else {
            message.setMessage(name + "类型错误==");
            message.setValue(value);
            message.setAae100("0");
        }
        return message;
    }


    public static String dateFormat(long l) {
        String format = "yyyy-MM-dd";
        Date setupTime = HSSFDateUtil.getJavaDate(Double.valueOf(l));
        System.out.println(setupTime);
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(setupTime);
        return dateString;
    }


}
