package com.tdkj.ServiceImpl.excel;

import com.github.pagehelper.PageHelper;
import com.tdkj.excel.WriteExcelDataDelegated;
import com.tdkj.model.PoorLaborForce;
import com.tdkj.model.PoorXzqh;
import com.tdkj.service.InformationCollection.PoorLaborForceService;
import com.tdkj.service.InformationCollection.PoorWorkService;
import com.tdkj.service.Ztree.ZtreeService;
import com.tdkj.service.excel.PoorLaborForceExcelService;
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
public class PoorLaborForceExcelServiceImpl implements PoorLaborForceExcelService {
    private final static Logger logger = LoggerFactory.getLogger(PoorLaborForceExcelServiceImpl.class);

    @Autowired
    private PoorWorkService poorWorkService;
    @Autowired
    private ZtreeService ztreeService;
    @Autowired
    private PoorLaborForceService poorLaborForceService;
    @Autowired
    private  HttpServletResponse response;

    @Override
    public String export(PoorLaborForce poorLaborForce ) throws Exception {

// 总记录数
        List<PoorLaborForce>   list = poorLaborForceService.queryExcel(poorLaborForce.getAab301());
          String aab301 =poorLaborForce.getAab301();
          if(!"".equals(aab301) && aab301 != null){
              aab301 =  aab301Substr(aab301)+ "%";
        }
        poorLaborForce.setAab301(aab301);
        String countlist = poorLaborForceService.queryAllPoorLaborForceFamily(poorLaborForce);
        // 导出EXCEL文件名称
        String filaName = "贫困劳动力信息";


        // 标题
        String[] titles = {"所属区域","劳动力姓名","身份证号","性别","劳动力电话","文化程度","就业创业状态","劳动能力","是否残疾人","备注"};

        // 开始导入
        logger.info("开始调用");
        // 开始导入
        PoiUtil.exportExcelToWebsite(response, Integer.parseInt(countlist), filaName, titles, new WriteExcelDataDelegated() {
            @Override
            public void writeExcelData(SXSSFSheet eachSheet, Integer startRowCount, Integer endRowCount, Integer currentPage, Integer pageSize) throws Exception {


                PageHelper.startPage(currentPage, pageSize);


                logger.info("查询结束");
                if (!CollectionUtils.isEmpty(list)) {
                    // --------------   这一块变量照着抄就行  强迫症 后期也封装起来     ----------------------
                    for (int i = startRowCount; i <= endRowCount; i++) {
                        SXSSFRow eachDataRow = (SXSSFRow) eachSheet.createRow(i);

                        if ((i - startRowCount) < list.size()) {

                            PoorLaborForce poorLaborForce= list.get(i - startRowCount);
                            // ---------   这一块变量照着抄就行  强迫症 后期也封装起来     -----------------------
                            eachDataRow.createCell(0).setCellValue(poorLaborForce.getAab301() == null ? "" : poorLaborForce.getAab301());
                            eachDataRow.createCell(1).setCellValue(poorLaborForce.getPlf004() == null ? "" : poorLaborForce.getPlf004());
                            eachDataRow.createCell(2).setCellValue(poorLaborForce.getPlf005() == null ? "" : poorLaborForce.getPlf005());
                            eachDataRow.createCell(3).setCellValue(poorLaborForce.getPlf007() == null ? "" : poorLaborForce.getPlf007());
                            eachDataRow.createCell(4).setCellValue(poorLaborForce.getPlf006() == null ? "" : poorLaborForce.getPlf006());
                            eachDataRow.createCell(5).setCellValue(poorLaborForce.getPlf009() == null ? "" : poorLaborForce.getPlf009());
                            eachDataRow.createCell(6).setCellValue(poorLaborForce.getPlf011() == null ? "" : poorLaborForce.getPlf011());
                            eachDataRow.createCell(7).setCellValue(poorLaborForce.getPlf018() == null ? "" : poorLaborForce.getPlf018()+"");
                            eachDataRow.createCell(8).setCellValue(poorLaborForce.getPlf023()== null ? "" : poorLaborForce.getPlf023());
                            eachDataRow.createCell(9).setCellValue(poorLaborForce.getPlf019()== null ? "" : poorLaborForce.getPlf019());



                        }
                    }
                }

            }
        });
        return "导出用户EXCEL成功";
    }


    @Override
    public String exportLedger(PoorLaborForce poorLaborForce) throws Exception {
        // 总记录数
        String countlist = poorLaborForceService.queryAllPoorLaborForceAccoutByAab301(poorLaborForce);
        List<PoorLaborForce> list= poorLaborForceService.queryPoorLaborForceImportByAab301(poorLaborForce,0+"",Integer.parseInt(countlist)+1+"");

        // 导出EXCEL文件名称
        String filaName = "贫困劳动力信息";
        // 标题
        String[] titles = {"所属区域","劳动力姓名","身份证号","性别","劳动力电话","文化程度","就业创业状态","就创业意愿","劳动能力","培训教育状态","培训教育意愿","是否残疾人","上学状态","易地扶贫搬迁","安置点名称","备注"};
        // 开始导入
        logger.info("开始调用");
        // 开始导入
        PoiUtil.exportExcelToWebsite(response, Integer.parseInt(countlist), filaName, titles, new WriteExcelDataDelegated() {
            @Override
            public void writeExcelData(SXSSFSheet eachSheet, Integer startRowCount, Integer endRowCount, Integer currentPage, Integer pageSize) throws Exception {


                PageHelper.startPage(currentPage, pageSize);


                logger.info("查询结束");
                if (!CollectionUtils.isEmpty(list)) {
                    // --------------   这一块变量照着抄就行  强迫症 后期也封装起来     ----------------------
                    for (int i = startRowCount; i <= endRowCount; i++) {
                        SXSSFRow eachDataRow = (SXSSFRow) eachSheet.createRow(i);

                        if ((i - startRowCount) < list.size()) {

                            PoorLaborForce poorLaborForce= list.get(i - startRowCount);
                            // ---------   这一块变量照着抄就行  强迫症 后期也封装起来     -----------------------
                            eachDataRow.createCell(0).setCellValue(poorLaborForce.getAab301() == null ? "" : poorLaborForce.getAab301());
                            eachDataRow.createCell(1).setCellValue(poorLaborForce.getPlf004() == null ? "" : poorLaborForce.getPlf004());
                            eachDataRow.createCell(2).setCellValue(poorLaborForce.getPlf005() == null ? "" : poorLaborForce.getPlf005());
                            eachDataRow.createCell(3).setCellValue(poorLaborForce.getPlf007() == null ? "" : poorLaborForce.getPlf007());
                            eachDataRow.createCell(4).setCellValue(poorLaborForce.getPlf006() == null ? "" : poorLaborForce.getPlf006());
                            eachDataRow.createCell(5).setCellValue(poorLaborForce.getPlf009() == null ? "" : poorLaborForce.getPlf009());
                            eachDataRow.createCell(6).setCellValue(poorLaborForce.getPlf011() == null ? "" : poorLaborForce.getPlf011());
                            eachDataRow.createCell(7).setCellValue(poorLaborForce.getPlf010() == null ? "" : poorLaborForce.getPlf010());
                            eachDataRow.createCell(8).setCellValue(poorLaborForce.getPlf018()== null ? "" : poorLaborForce.getPlf018());
                            eachDataRow.createCell(9).setCellValue(poorLaborForce.getPlf024()== null ? "" : poorLaborForce.getPlf024());
                            eachDataRow.createCell(10).setCellValue(poorLaborForce.getPlf012()== null ? "" : poorLaborForce.getPlf012());
                            eachDataRow.createCell(11).setCellValue(poorLaborForce.getPlf023()== null ? "" : poorLaborForce.getPlf023());
                            eachDataRow.createCell(12).setCellValue(poorLaborForce.getPlf015()== null ? "" : poorLaborForce.getPlf015());
                            eachDataRow.createCell(13).setCellValue(poorLaborForce.getPhd013()== null ? "" : poorLaborForce.getPhd013());
                            eachDataRow.createCell(14).setCellValue(poorLaborForce.getPhd010()== null ? "" : poorLaborForce.getPhd010());
                            eachDataRow.createCell(15).setCellValue(poorLaborForce.getPlf019()== null ? "" : poorLaborForce.getPlf019());
                        }
                    }
                }

            }
        });
        return "导出用户EXCEL成功";
    }

    @Override
    public String exportLedgerAccount(PoorLaborForce poorLaborForce) throws Exception {
        // 总记录数
        String countlist = poorLaborForceService.queryAllPoorPersonAccountByAab301(poorLaborForce);
        List<PoorLaborForce> list= poorLaborForceService.queryPoorPersonAccountByAab301(poorLaborForce,0+"",Integer.parseInt(countlist)+1+"");

        // 导出EXCEL文件名称
        String filaName = "贫困人口信息";
        // 标题
        String[] titles = {"所属区域","劳动力姓名","身份证号","性别","劳动力电话","文化程度","就业创业状态","就创业意愿","劳动能力","培训教育状态","培训教育意愿","是否残疾人","上学状态","易地扶贫搬迁","安置点名称","备注"};
        // 开始导入
        logger.info("开始调用");
        // 开始导入
        PoiUtil.exportExcelToWebsite(response, Integer.parseInt(countlist), filaName, titles, new WriteExcelDataDelegated() {
            @Override
            public void writeExcelData(SXSSFSheet eachSheet, Integer startRowCount, Integer endRowCount, Integer currentPage, Integer pageSize) throws Exception {


                PageHelper.startPage(currentPage, pageSize);


                logger.info("查询结束");
                if (!CollectionUtils.isEmpty(list)) {
                    // --------------   这一块变量照着抄就行  强迫症 后期也封装起来     ----------------------
                    for (int i = startRowCount; i <= endRowCount; i++) {
                        SXSSFRow eachDataRow = (SXSSFRow) eachSheet.createRow(i);

                        if ((i - startRowCount) < list.size()) {

                            PoorLaborForce poorLaborForce= list.get(i - startRowCount);
                            // ---------   这一块变量照着抄就行  强迫症 后期也封装起来     -----------------------
                            eachDataRow.createCell(0).setCellValue(poorLaborForce.getAab301() == null ? "" : poorLaborForce.getAab301());
                            eachDataRow.createCell(1).setCellValue(poorLaborForce.getPlf004() == null ? "" : poorLaborForce.getPlf004());
                            eachDataRow.createCell(2).setCellValue(poorLaborForce.getPlf005() == null ? "" : poorLaborForce.getPlf005());
                            eachDataRow.createCell(3).setCellValue(poorLaborForce.getPlf007() == null ? "" : poorLaborForce.getPlf007());
                            eachDataRow.createCell(4).setCellValue(poorLaborForce.getPlf006() == null ? "" : poorLaborForce.getPlf006());
                            eachDataRow.createCell(5).setCellValue(poorLaborForce.getPlf009() == null ? "" : poorLaborForce.getPlf009());
                            eachDataRow.createCell(6).setCellValue(poorLaborForce.getPlf011() == null ? "" : poorLaborForce.getPlf011());
                            eachDataRow.createCell(7).setCellValue(poorLaborForce.getPlf010() == null ? "" : poorLaborForce.getPlf010());
                            eachDataRow.createCell(8).setCellValue(poorLaborForce.getPlf018()== null ? "" : poorLaborForce.getPlf018());
                            eachDataRow.createCell(9).setCellValue(poorLaborForce.getPlf024()== null ? "" : poorLaborForce.getPlf024());
                            eachDataRow.createCell(10).setCellValue(poorLaborForce.getPlf012()== null ? "" : poorLaborForce.getPlf012());
                            eachDataRow.createCell(11).setCellValue(poorLaborForce.getPlf023()== null ? "" : poorLaborForce.getPlf023());
                            eachDataRow.createCell(12).setCellValue(poorLaborForce.getPlf015()== null ? "" : poorLaborForce.getPlf015());
                            eachDataRow.createCell(13).setCellValue(poorLaborForce.getPhd013()== null ? "" : poorLaborForce.getPhd013());
                            eachDataRow.createCell(14).setCellValue(poorLaborForce.getPhd010()== null ? "" : poorLaborForce.getPhd010());
                            eachDataRow.createCell(15).setCellValue(poorLaborForce.getPlf019()== null ? "" : poorLaborForce.getPlf019());
                        }
                    }
                }

            }
        });
        return "导出用户EXCEL成功";
    }

    /**
     * 弱劳动力导出
     * @param poorLaborForce
     * @return
     * @throws Exception
     */
    @Override
    public String exortRuoPoorLaborForceLedger(PoorLaborForce poorLaborForce) throws Exception {
        // 总记录数
        String countlist = poorLaborForceService.queryAllRuoPoorLaborForceAccoutByAab301(poorLaborForce);
        List<PoorLaborForce> list= poorLaborForceService.queryRuoPoorLaborForceAccountByAab301(poorLaborForce,0+"",Integer.parseInt(countlist)+1+"");

        // 导出EXCEL文件名称
        String filaName = "弱劳动力信息";
        // 标题
        String[] titles = {"所属区域","劳动力姓名","身份证号","性别","劳动力电话","文化程度","就业创业状态","就创业意愿","劳动能力","培训教育状态","培训教育意愿","是否残疾人","上学状态","易地扶贫搬迁","安置点名称","备注"};
        // 开始导入
        logger.info("开始调用");
        // 开始导入
        PoiUtil.exportExcelToWebsite(response, Integer.parseInt(countlist), filaName, titles, new WriteExcelDataDelegated() {
            @Override
            public void writeExcelData(SXSSFSheet eachSheet, Integer startRowCount, Integer endRowCount, Integer currentPage, Integer pageSize) throws Exception {


                PageHelper.startPage(currentPage, pageSize);


                logger.info("查询结束");
                if (!CollectionUtils.isEmpty(list)) {
                    // --------------   这一块变量照着抄就行  强迫症 后期也封装起来     ----------------------
                    for (int i = startRowCount; i <= endRowCount; i++) {
                        SXSSFRow eachDataRow = (SXSSFRow) eachSheet.createRow(i);

                        if ((i - startRowCount) < list.size()) {

                            PoorLaborForce poorLaborForce= list.get(i - startRowCount);
                            // ---------   这一块变量照着抄就行  强迫症 后期也封装起来     -----------------------
                            eachDataRow.createCell(0).setCellValue(poorLaborForce.getAab301() == null ? "" : poorLaborForce.getAab301());
                            eachDataRow.createCell(1).setCellValue(poorLaborForce.getPlf004() == null ? "" : poorLaborForce.getPlf004());
                            eachDataRow.createCell(2).setCellValue(poorLaborForce.getPlf005() == null ? "" : poorLaborForce.getPlf005());
                            eachDataRow.createCell(3).setCellValue(poorLaborForce.getPlf007() == null ? "" : poorLaborForce.getPlf007());
                            eachDataRow.createCell(4).setCellValue(poorLaborForce.getPlf006() == null ? "" : poorLaborForce.getPlf006());
                            eachDataRow.createCell(5).setCellValue(poorLaborForce.getPlf009() == null ? "" : poorLaborForce.getPlf009());
                            eachDataRow.createCell(6).setCellValue(poorLaborForce.getPlf011() == null ? "" : poorLaborForce.getPlf011());
                            eachDataRow.createCell(7).setCellValue(poorLaborForce.getPlf010() == null ? "" : poorLaborForce.getPlf010());
                            eachDataRow.createCell(8).setCellValue(poorLaborForce.getPlf018()== null ? "" : poorLaborForce.getPlf018());
                            eachDataRow.createCell(9).setCellValue(poorLaborForce.getPlf024()== null ? "" : poorLaborForce.getPlf024());
                            eachDataRow.createCell(10).setCellValue(poorLaborForce.getPlf012()== null ? "" : poorLaborForce.getPlf012());
                            eachDataRow.createCell(11).setCellValue(poorLaborForce.getPlf023()== null ? "" : poorLaborForce.getPlf023());
                            eachDataRow.createCell(12).setCellValue(poorLaborForce.getPlf015()== null ? "" : poorLaborForce.getPlf015());
                            eachDataRow.createCell(13).setCellValue(poorLaborForce.getPhd013()== null ? "" : poorLaborForce.getPhd013());
                            eachDataRow.createCell(14).setCellValue(poorLaborForce.getPhd010()== null ? "" : poorLaborForce.getPhd010());
                            eachDataRow.createCell(15).setCellValue(poorLaborForce.getPlf019()== null ? "" : poorLaborForce.getPlf019());
                        }
                    }
                }

            }
        });
        return "导出用户EXCEL成功";
    }


    /**
     * 劳动力待确认导出
     * @param poorLaborForce
     * @return
     * @throws Exception
     */
    @Override
    public String exportToBeComfirePoorLaborForce(PoorLaborForce poorLaborForce) throws Exception {
        // 总记录数
        String countlist = poorLaborForceService.queryAllPoorLaborForceToBeComfireByAab301(poorLaborForce);
        List<PoorLaborForce> list= poorLaborForceService.queryPoorLaborForceToBeComfireByAab301(poorLaborForce,0+"",Integer.parseInt(countlist)+1+"");

        // 导出EXCEL文件名称
        String filaName = "待确认劳动力信息";
        // 标题
        String[] titles = {"所属区域","劳动力姓名","身份证号","性别","劳动力电话","文化程度","就业创业状态","就创业意愿","劳动能力","培训教育状态","培训教育意愿","是否残疾人","上学状态","备注"};
        // 开始导入
        logger.info("开始调用");
        // 开始导入
        PoiUtil.exportExcelToWebsite(response, Integer.parseInt(countlist), filaName, titles, new WriteExcelDataDelegated() {
            @Override
            public void writeExcelData(SXSSFSheet eachSheet, Integer startRowCount, Integer endRowCount, Integer currentPage, Integer pageSize) throws Exception {


                PageHelper.startPage(currentPage, pageSize);


                logger.info("查询结束");
                if (!CollectionUtils.isEmpty(list)) {
                    // --------------   这一块变量照着抄就行  强迫症 后期也封装起来     ----------------------
                    for (int i = startRowCount; i <= endRowCount; i++) {
                        SXSSFRow eachDataRow = (SXSSFRow) eachSheet.createRow(i);

                        if ((i - startRowCount) < list.size()) {

                            PoorLaborForce poorLaborForce= list.get(i - startRowCount);
                            // ---------   这一块变量照着抄就行  强迫症 后期也封装起来     -----------------------
                            eachDataRow.createCell(0).setCellValue(poorLaborForce.getAab301() == null ? "" : poorLaborForce.getAab301());
                            eachDataRow.createCell(1).setCellValue(poorLaborForce.getPlf004() == null ? "" : poorLaborForce.getPlf004());
                            eachDataRow.createCell(2).setCellValue(poorLaborForce.getPlf005() == null ? "" : poorLaborForce.getPlf005());
                            eachDataRow.createCell(3).setCellValue(poorLaborForce.getPlf007() == null ? "" : poorLaborForce.getPlf007());
                            eachDataRow.createCell(4).setCellValue(poorLaborForce.getPlf006() == null ? "" : poorLaborForce.getPlf006());
                            eachDataRow.createCell(5).setCellValue(poorLaborForce.getPlf009() == null ? "" : poorLaborForce.getPlf009());
                            eachDataRow.createCell(6).setCellValue(poorLaborForce.getPlf011() == null ? "" : poorLaborForce.getPlf011());
                            eachDataRow.createCell(7).setCellValue(poorLaborForce.getPlf010() == null ? "" : poorLaborForce.getPlf010());
                            eachDataRow.createCell(8).setCellValue(poorLaborForce.getPlf018()== null ? "" : poorLaborForce.getPlf018());
                            eachDataRow.createCell(9).setCellValue(poorLaborForce.getPlf024()== null ? "" : poorLaborForce.getPlf024());
                            eachDataRow.createCell(10).setCellValue(poorLaborForce.getPlf012()== null ? "" : poorLaborForce.getPlf012());
                            eachDataRow.createCell(11).setCellValue(poorLaborForce.getPlf023()== null ? "" : poorLaborForce.getPlf023());
                            eachDataRow.createCell(12).setCellValue(poorLaborForce.getPlf019()== null ? "" : poorLaborForce.getPlf019());
                        }
                    }
                }

            }
        });
        return "导出用户EXCEL成功";
    }


    /**
     * 未就业创业劳动力导出
     * @param poorLaborForce
     * @return
     * @throws Exception
     */
    @Override
    public String exportTobeWorker(PoorLaborForce poorLaborForce) throws Exception {
        // 总记录数
        String countlist = poorLaborForceService.queryAllTobeHelpWorkerByAab301(poorLaborForce);
        List<PoorLaborForce> list= poorLaborForceService.queryTobeHelpWorkerByAab301(poorLaborForce,0+"",Integer.parseInt(countlist)+1+"");

        // 导出EXCEL文件名称
        String filaName = "未就业创业劳动力信息";
        // 标题
        String[] titles = {"所属区域","劳动力姓名","身份证号","性别","劳动力电话","文化程度","就业创业状态","就创业意愿","劳动能力","培训教育状态","培训教育意愿","是否残疾人","上学状态","易地扶贫搬迁","备注"};
        // 开始导入
        logger.info("开始调用");
        // 开始导入
        PoiUtil.exportExcelToWebsite(response, Integer.parseInt(countlist), filaName, titles, new WriteExcelDataDelegated() {
            @Override
            public void writeExcelData(SXSSFSheet eachSheet, Integer startRowCount, Integer endRowCount, Integer currentPage, Integer pageSize) throws Exception {


                PageHelper.startPage(currentPage, pageSize);


                logger.info("查询结束");
                if (!CollectionUtils.isEmpty(list)) {
                    // --------------   这一块变量照着抄就行  强迫症 后期也封装起来     ----------------------
                    for (int i = startRowCount; i <= endRowCount; i++) {
                        SXSSFRow eachDataRow = (SXSSFRow) eachSheet.createRow(i);

                        if ((i - startRowCount) < list.size()) {

                            PoorLaborForce poorLaborForce= list.get(i - startRowCount);
                            // ---------   这一块变量照着抄就行  强迫症 后期也封装起来     -----------------------
                            eachDataRow.createCell(0).setCellValue(poorLaborForce.getAab301() == null ? "" : poorLaborForce.getAab301());
                            eachDataRow.createCell(1).setCellValue(poorLaborForce.getPlf004() == null ? "" : poorLaborForce.getPlf004());
                            eachDataRow.createCell(2).setCellValue(poorLaborForce.getPlf005() == null ? "" : poorLaborForce.getPlf005());
                            eachDataRow.createCell(3).setCellValue(poorLaborForce.getPlf007() == null ? "" : poorLaborForce.getPlf007());
                            eachDataRow.createCell(4).setCellValue(poorLaborForce.getPlf006() == null ? "" : poorLaborForce.getPlf006());
                            eachDataRow.createCell(5).setCellValue(poorLaborForce.getPlf009() == null ? "" : poorLaborForce.getPlf009());
                            eachDataRow.createCell(6).setCellValue(poorLaborForce.getPlf011() == null ? "" : poorLaborForce.getPlf011());
                            eachDataRow.createCell(7).setCellValue(poorLaborForce.getPlf010() == null ? "" : poorLaborForce.getPlf010());
                            eachDataRow.createCell(8).setCellValue(poorLaborForce.getPlf018()== null ? "" : poorLaborForce.getPlf018());
                            eachDataRow.createCell(9).setCellValue(poorLaborForce.getPlf024()== null ? "" : poorLaborForce.getPlf024());
                            eachDataRow.createCell(10).setCellValue(poorLaborForce.getPlf012()== null ? "" : poorLaborForce.getPlf012());
                            eachDataRow.createCell(11).setCellValue(poorLaborForce.getPlf023()== null ? "" : poorLaborForce.getPlf023());
                            eachDataRow.createCell(12).setCellValue(poorLaborForce.getPlf015()== null ? "" : poorLaborForce.getPlf015());
                            eachDataRow.createCell(13).setCellValue(poorLaborForce.getPhd013()== null ? "" : poorLaborForce.getPhd013());
                            eachDataRow.createCell(14).setCellValue(poorLaborForce.getPlf019()== null ? "" : poorLaborForce.getPlf019());
                        }
                    }
                }

            }
        });
        return "导出用户EXCEL成功";
    }

    /**
     * 家庭成员导出
     * @param poorLaborForce
     * @return
     * @throws Exception
     */
    @Override
    public String exportFamily(PoorLaborForce poorLaborForce) throws Exception {
        // 总记录数
        String countlist = poorLaborForceService.queryAllPoorLaborForceFamily(poorLaborForce);
        List<PoorLaborForce> list= poorLaborForceService.queryPoorLaborForceFamily(poorLaborForce);

        // 导出EXCEL文件名称
        String filaName = "家庭成员信息";
        // 标题
        String[] titles = {"所属区域","户主姓名","户主身份证号","劳动力姓名","身份证号","性别","劳动力电话","文化程度","就业创业状态","就创业意愿","劳动能力","培训教育状态","培训教育意愿","是否残疾人","上学状态","备注"};
        // 开始导入
        logger.info("开始调用");
        // 开始导入
        PoiUtil.exportExcelToWebsite(response, Integer.parseInt(countlist), filaName, titles, new WriteExcelDataDelegated() {
            @Override
            public void writeExcelData(SXSSFSheet eachSheet, Integer startRowCount, Integer endRowCount, Integer currentPage, Integer pageSize) throws Exception {


                PageHelper.startPage(currentPage, pageSize);


                logger.info("查询结束");
                if (!CollectionUtils.isEmpty(list)) {
                    // --------------   这一块变量照着抄就行  强迫症 后期也封装起来     ----------------------
                    for (int i = startRowCount; i <= endRowCount; i++) {
                        SXSSFRow eachDataRow = (SXSSFRow) eachSheet.createRow(i);

                        if ((i - startRowCount) < list.size()) {

                            PoorLaborForce poorLaborForce= list.get(i - startRowCount);
                            // ---------   这一块变量照着抄就行  强迫症 后期也封装起来     -----------------------
                            eachDataRow.createCell(0).setCellValue(poorLaborForce.getAab301() == null ? "" : poorLaborForce.getAab301());
                            eachDataRow.createCell(1).setCellValue(poorLaborForce.getTsn003() == null ? "" : poorLaborForce.getTsn003());
                            eachDataRow.createCell(2).setCellValue(poorLaborForce.getPlf002() == null ? "" : poorLaborForce.getPlf002());
                            eachDataRow.createCell(3).setCellValue(poorLaborForce.getPlf004() == null ? "" : poorLaborForce.getPlf004());
                            eachDataRow.createCell(4).setCellValue(poorLaborForce.getPlf005() == null ? "" : poorLaborForce.getPlf005());
                            eachDataRow.createCell(5).setCellValue(poorLaborForce.getPlf007() == null ? "" : poorLaborForce.getPlf007());
                            eachDataRow.createCell(6).setCellValue(poorLaborForce.getPlf006() == null ? "" : poorLaborForce.getPlf006());
                            eachDataRow.createCell(7).setCellValue(poorLaborForce.getPlf009() == null ? "" : poorLaborForce.getPlf009());
                            eachDataRow.createCell(8).setCellValue(poorLaborForce.getPlf011() == null ? "" : poorLaborForce.getPlf011());
                            eachDataRow.createCell(9).setCellValue(poorLaborForce.getPlf010() == null ? "" : poorLaborForce.getPlf010());
                            eachDataRow.createCell(10).setCellValue(poorLaborForce.getPlf018()== null ? "" : poorLaborForce.getPlf018());
                            eachDataRow.createCell(11).setCellValue(poorLaborForce.getPlf024()== null ? "" : poorLaborForce.getPlf024());
                            eachDataRow.createCell(12).setCellValue(poorLaborForce.getPlf012()== null ? "" : poorLaborForce.getPlf012());
                            eachDataRow.createCell(13).setCellValue(poorLaborForce.getPlf023()== null ? "" : poorLaborForce.getPlf023());
                            eachDataRow.createCell(14).setCellValue(poorLaborForce.getPlf019()== null ? "" : poorLaborForce.getPlf019());
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
        return aab301+"%";
    }

}
