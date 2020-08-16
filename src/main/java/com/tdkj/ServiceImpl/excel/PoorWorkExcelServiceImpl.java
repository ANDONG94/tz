package com.tdkj.ServiceImpl.excel;

import com.github.pagehelper.PageHelper;
import com.tdkj.excel.WriteExcelDataDelegated;
import com.tdkj.model.PoorHouseholds;
import com.tdkj.model.PoorXzqh;
import com.tdkj.service.InformationCollection.PoorWorkService;
import com.tdkj.service.Ztree.ZtreeService;
import com.tdkj.service.excel.PoorWorkExcelService;
import com.tdkj.util.PoiUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class PoorWorkExcelServiceImpl implements PoorWorkExcelService {
    private final static Logger logger = LoggerFactory.getLogger(PoorWorkExcelServiceImpl.class);

    @Autowired
    private PoorWorkService poorWorkService;
    @Autowired
    private ZtreeService ztreeService;
    @Autowired
    private  HttpServletResponse response;


    /**
     * 贫困户导出
     * @return
     * @throws Exception
     */
    @Override
    public String export(PoorHouseholds poorHouseholds) throws Exception {
        // 总记录数
        String totalRowCount = poorWorkService.queryAllAccoutByAab301(poorHouseholds);
        List<PoorHouseholds> errLst = poorWorkService.queryPoorAccountByAab301(poorHouseholds, 0 + "", (Integer.parseInt(totalRowCount) + 1) + "");

        // 导出EXCEL文件名称
        String filaName = "贫困户信息";
        // 标题
        String[] titles = {"所属区域","姓名", "身份证", "性别", "易地扶贫搬迁", "脱贫状态", "认定年度", "劳动力数量","有就业创业意愿人数","已就业创业人数","已就业创业人姓名","年龄", "联系方式","安置点名称","是否残疾人","详细住址"};

        // 开始导入
        logger.info("开始调用");
        // 开始导入
        PoiUtil.exportExcelToWebsite(response, Integer.parseInt(totalRowCount), filaName, titles, new WriteExcelDataDelegated() {
            @Override
            public void writeExcelData(SXSSFSheet eachSheet, Integer startRowCount, Integer endRowCount, Integer currentPage, Integer pageSize) throws Exception {


                PageHelper.startPage(currentPage, pageSize);


                logger.info("查询结束");
                if (!CollectionUtils.isEmpty(errLst)) {
                    // --------------   这一块变量照着抄就行  强迫症 后期也封装起来     ----------------------
                    for (int i = startRowCount; i <= endRowCount; i++) {
                        SXSSFRow eachDataRow = (SXSSFRow) eachSheet.createRow(i);

                        if ((i - startRowCount) < errLst.size()) {

                            PoorHouseholds poorHouseholds = errLst.get(i - startRowCount);
                            // ---------   这一块变量照着抄就行  强迫症 后期也封装起来     -----------------------
                            eachDataRow.createCell(0).setCellValue(poorHouseholds.getAab301() == null ? "" : poorHouseholds.getAab301());
                            eachDataRow.createCell(1).setCellValue(poorHouseholds.getPhd002() == null ? "" : poorHouseholds.getPhd002());
                            eachDataRow.createCell(2).setCellValue(poorHouseholds.getPhd003() == null ? "" : poorHouseholds.getPhd003());
                            eachDataRow.createCell(3).setCellValue(poorHouseholds.getPhd004() == null ? "" : poorHouseholds.getPhd004());
                            eachDataRow.createCell(4).setCellValue(poorHouseholds.getPhd013() == null ? "" : poorHouseholds.getPhd013());
                            eachDataRow.createCell(5).setCellValue(poorHouseholds.getPhd014() == null ? "" : poorHouseholds.getPhd014());
                            eachDataRow.createCell(6).setCellValue(poorHouseholds.getPhd012() == null ? "" : poorHouseholds.getPhd012());
                            eachDataRow.createCell(7).setCellValue(poorHouseholds.getPhd008()== null ? "" : poorHouseholds.getPhd008().toString());
                            eachDataRow.createCell(8).setCellValue(poorHouseholds.getHavejiuyechuangyecount()== null ? "" : poorHouseholds.getHavejiuyechuangyecount());
                            eachDataRow.createCell(9).setCellValue(poorHouseholds.getYijiuyechuangyecount()== null ? "" : poorHouseholds.getYijiuyechuangyecount());
                            eachDataRow.createCell(10).setCellValue(poorHouseholds.getYijiuyechuangyename()== null ? "" : poorHouseholds.getYijiuyechuangyename());
                            eachDataRow.createCell(11).setCellValue(poorHouseholds.getPhd005()== null ? "" : poorHouseholds.getPhd005());
                            eachDataRow.createCell(12).setCellValue(poorHouseholds.getPhd006() == null ? "" : poorHouseholds.getPhd006());
                            eachDataRow.createCell(13).setCellValue(poorHouseholds.getPhd010() == null ? "" : poorHouseholds.getPhd010());
                            eachDataRow.createCell(14).setCellValue(poorHouseholds.getPhd016() == null ? "" : poorHouseholds.getPhd016());
                            eachDataRow.createCell(15).setCellValue(poorHouseholds.getPhd007() == null ? "" : poorHouseholds.getPhd007());
                            /*if (null != poorHouseholds.getCreateUid()) {
                                eachDataRow.createCell(7).setCellValue(poorHouseholds.getCreateUid());
                            }
                            if (null != poorHouseholds.getCreateTime()) {
                                eachDataRow.createCell(8).setCellValue(DateUtil.formatDate(poorHouseholds.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                            }
                            if (null != poorHouseholds.getUpdateUid()) {
                                eachDataRow.createCell(9).setCellValue(eachUserVO.getUpdateUid());
                            }
                            if (null != poorHouseholds.getUpdateTime()) {
                                eachDataRow.createCell(10).setCellValue(DateUtil.formatDate(poorHouseholds.getUpdateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                            }*/
                        }
                    }
                }

            }
        });
        return "导出用户EXCEL成功";
    }


    /**
     * 贫困户待确认导出
     * @param poorHouseholds
     * @return
     */
    @Override
    public String exportToBeComfirePoorWork(PoorHouseholds poorHouseholds) throws Exception {

        // 总记录数
        String totalRowCount = poorWorkService.queryAllToBeComfireByAab301(poorHouseholds);
        List<PoorHouseholds> errLst= poorWorkService.queryToBeComfirePoorByAab301(poorHouseholds,0+"",Integer.parseInt(totalRowCount)+1+"");

        // 导出EXCEL文件名称
        String filaName = "待确认贫困户信息";
        // 标题
        String[] titles = {"所属区域","姓名", "身份证", "性别", "易地扶贫搬迁", "脱贫状态", "认定年度", "劳动力数量", "联系方式","安置点名称","是否残疾人","详细住址"};

        // 开始导入
        logger.info("开始调用");
        // 开始导入
        PoiUtil.exportExcelToWebsite(response, Integer.parseInt(totalRowCount), filaName, titles, new WriteExcelDataDelegated() {
            @Override
            public void writeExcelData(SXSSFSheet eachSheet, Integer startRowCount, Integer endRowCount, Integer currentPage, Integer pageSize) throws Exception {


                PageHelper.startPage(currentPage, pageSize);


                logger.info("查询结束");
                if (!CollectionUtils.isEmpty(errLst)) {
                    // --------------   这一块变量照着抄就行  强迫症 后期也封装起来     ----------------------
                    for (int i = startRowCount; i <= endRowCount; i++) {
                        SXSSFRow eachDataRow = (SXSSFRow) eachSheet.createRow(i);

                        if ((i - startRowCount) < errLst.size()) {

                            PoorHouseholds poorHouseholds = errLst.get(i - startRowCount);
                            // ---------   这一块变量照着抄就行  强迫症 后期也封装起来     -----------------------
                            eachDataRow.createCell(0).setCellValue(poorHouseholds.getAab301() == null ? "" : poorHouseholds.getAab301());
                            eachDataRow.createCell(1).setCellValue(poorHouseholds.getPhd002() == null ? "" : poorHouseholds.getPhd002());
                            eachDataRow.createCell(2).setCellValue(poorHouseholds.getPhd003() == null ? "" : poorHouseholds.getPhd003());
                            eachDataRow.createCell(3).setCellValue(poorHouseholds.getPhd004() == null ? "" : poorHouseholds.getPhd004());
                            eachDataRow.createCell(4).setCellValue(poorHouseholds.getPhd013() == null ? "" : poorHouseholds.getPhd013());
                            eachDataRow.createCell(5).setCellValue(poorHouseholds.getPhd014() == null ? "" : poorHouseholds.getPhd014());
                            eachDataRow.createCell(6).setCellValue(poorHouseholds.getPhd012() == null ? "" : poorHouseholds.getPhd012());
                            eachDataRow.createCell(7).setCellValue(poorHouseholds.getPhd008()== null ? "" : poorHouseholds.getPhd008().toString());
                            eachDataRow.createCell(8).setCellValue(poorHouseholds.getPhd006() == null ? "" : poorHouseholds.getPhd006());
                            eachDataRow.createCell(9).setCellValue(poorHouseholds.getPhd010() == null ? "" : poorHouseholds.getPhd010());
                            eachDataRow.createCell(10).setCellValue(poorHouseholds.getPhd016() == null ? "" : poorHouseholds.getPhd016());
                            eachDataRow.createCell(11).setCellValue(poorHouseholds.getPhd007() == null ? "" : poorHouseholds.getPhd007());
                            /*if (null != poorHouseholds.getCreateUid()) {
                                eachDataRow.createCell(7).setCellValue(poorHouseholds.getCreateUid());
                            }
                            if (null != poorHouseholds.getCreateTime()) {
                                eachDataRow.createCell(8).setCellValue(DateUtil.formatDate(poorHouseholds.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                            }
                            if (null != poorHouseholds.getUpdateUid()) {
                                eachDataRow.createCell(9).setCellValue(eachUserVO.getUpdateUid());
                            }
                            if (null != poorHouseholds.getUpdateTime()) {
                                eachDataRow.createCell(10).setCellValue(DateUtil.formatDate(poorHouseholds.getUpdateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                            }*/
                        }
                    }
                }

            }
        });
        return "导出用户EXCEL成功";
    }

    /**
     * 带帮扶贫困户导出
     * @param poorHouseholds
     * @return
     * @throws Exception
     */
    @Override
    public String exportTobeHelpPoor(PoorHouseholds poorHouseholds) throws Exception {
        // 总记录数
        String totalRowCount = poorWorkService.queryAllTobeHelpPoorByAab301(poorHouseholds);
        List<PoorHouseholds> errLst = poorWorkService.queryTobeHelpPoorByAab301(poorHouseholds, 0 + "", (Integer.parseInt(totalRowCount) + 1) + "");

        // 导出EXCEL文件名称
        String filaName = "待帮扶贫困户信息";
        // 标题
        String[] titles = {"所属区域","姓名", "身份证", "性别", "易地扶贫搬迁", "脱贫状态", "认定年度", "劳动力数量", "联系方式","安置点名称","是否残疾人","详细住址"};

        // 开始导入
        logger.info("开始调用");
        // 开始导入
        PoiUtil.exportExcelToWebsite(response, Integer.parseInt(totalRowCount), filaName, titles, new WriteExcelDataDelegated() {
            @Override
            public void writeExcelData(SXSSFSheet eachSheet, Integer startRowCount, Integer endRowCount, Integer currentPage, Integer pageSize) throws Exception {


                PageHelper.startPage(currentPage, pageSize);


                logger.info("查询结束");
                if (!CollectionUtils.isEmpty(errLst)) {
                    // --------------   这一块变量照着抄就行  强迫症 后期也封装起来     ----------------------
                    for (int i = startRowCount; i <= endRowCount; i++) {
                        SXSSFRow eachDataRow = (SXSSFRow) eachSheet.createRow(i);

                        if ((i - startRowCount) < errLst.size()) {

                            PoorHouseholds poorHouseholds = errLst.get(i - startRowCount);
                            // ---------   这一块变量照着抄就行  强迫症 后期也封装起来     -----------------------
                            eachDataRow.createCell(0).setCellValue(poorHouseholds.getAab301() == null ? "" : poorHouseholds.getAab301());
                            eachDataRow.createCell(1).setCellValue(poorHouseholds.getPhd002() == null ? "" : poorHouseholds.getPhd002());
                            eachDataRow.createCell(2).setCellValue(poorHouseholds.getPhd003() == null ? "" : poorHouseholds.getPhd003());
                            eachDataRow.createCell(3).setCellValue(poorHouseholds.getPhd004() == null ? "" : poorHouseholds.getPhd004());
                            eachDataRow.createCell(4).setCellValue(poorHouseholds.getPhd013() == null ? "" : poorHouseholds.getPhd013());
                            eachDataRow.createCell(5).setCellValue(poorHouseholds.getPhd014() == null ? "" : poorHouseholds.getPhd014());
                            eachDataRow.createCell(6).setCellValue(poorHouseholds.getPhd012() == null ? "" : poorHouseholds.getPhd012());
                            eachDataRow.createCell(7).setCellValue(poorHouseholds.getPhd008()== null ? "" : poorHouseholds.getPhd008().toString());
                            eachDataRow.createCell(8).setCellValue(poorHouseholds.getPhd006() == null ? "" : poorHouseholds.getPhd006());
                            eachDataRow.createCell(9).setCellValue(poorHouseholds.getPhd010() == null ? "" : poorHouseholds.getPhd010());
                            eachDataRow.createCell(10).setCellValue(poorHouseholds.getPhd016() == null ? "" : poorHouseholds.getPhd016());
                            eachDataRow.createCell(11).setCellValue(poorHouseholds.getPhd007() == null ? "" : poorHouseholds.getPhd007());
                            /*if (null != poorHouseholds.getCreateUid()) {
                                eachDataRow.createCell(7).setCellValue(poorHouseholds.getCreateUid());
                            }
                            if (null != poorHouseholds.getCreateTime()) {
                                eachDataRow.createCell(8).setCellValue(DateUtil.formatDate(poorHouseholds.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                            }
                            if (null != poorHouseholds.getUpdateUid()) {
                                eachDataRow.createCell(9).setCellValue(eachUserVO.getUpdateUid());
                            }
                            if (null != poorHouseholds.getUpdateTime()) {
                                eachDataRow.createCell(10).setCellValue(DateUtil.formatDate(poorHouseholds.getUpdateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                            }*/
                        }
                    }
                }

            }
        });
        return "导出用户EXCEL成功";
    }


    //获取aab301的截取
    public  String aab301Substr(String aab301){
        PoorXzqh poorXzqh =ztreeService.queryPoorXzqhSub(aab301);
        if(aab301.equals("610180000000")){
            aab301=aab301.substring(0, 5);
        }else if(poorXzqh!=null && !"".equals(poorXzqh)){
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
