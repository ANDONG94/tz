package com.tdkj.ServiceImpl.excel;

import com.github.pagehelper.PageHelper;
import com.tdkj.dao.InformationCollection.EntrepreneurshipMapper;
import com.tdkj.excel.WriteExcelDataDelegated;
import com.tdkj.model.EppPlf;
import com.tdkj.service.excel.EntrepreneurShipExcelService;
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
public class EntrepreneurShipExcelServiceImpl implements EntrepreneurShipExcelService {
    private final static Logger logger = LoggerFactory.getLogger(PoorWorkExcelServiceImpl.class);

    @Autowired
    private HttpServletResponse response;
    @Autowired
    private EntrepreneurshipMapper entrepreneurshipMapper;

    /**
     * 劳动力创业信息导出
     */
    @Override
    public String export(EppPlf eppPlf) throws Exception {
        // 总记录数
       String countlist=entrepreneurshipMapper.queryAllEmploymentShipByAab301(eppPlf);
        // 总记录数
        List<EppPlf> list=entrepreneurshipMapper.queryEmploymentShipAccountByAab301(eppPlf,0+"",(Integer.parseInt(countlist)+1)+"");


        // 导出EXCEL文件名称
        String filaName = "贫困劳动力创业信息";
        // 标题
        String[] titles = {"所属行政区","姓名","易地扶贫搬迁","脱贫状态","性别","身份证号","联系电话","家庭住址","创业项目","创业单位名称","创业地址",
                "创业起始年月","创业终止年月","带动就业人数","带动贫困劳动力人数","享受政策","创业担保贷款（元）","创业补贴（元）","年龄","文化程度",
                "转移就业意愿","就业创业状态","培训教育意愿","苏陕协作","年度","在校状态","就业意向","劳动能力","是否残疾","培训教育状态"};

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

                            EppPlf epp = list.get(i - startRowCount);
                            // ---------   这一块变量照着抄就行  强迫症 后期也封装起来     -----------------------
                            eachDataRow.createCell(0).setCellValue(epp.getAab301() == null ? "" : epp.getAab301());
                            eachDataRow.createCell(1).setCellValue(epp.getPlf004() == null ? "" : epp.getPlf004());
                            eachDataRow.createCell(2).setCellValue(epp.getPhd013() == null ? "" : epp.getPhd013());
                            eachDataRow.createCell(3).setCellValue(epp.getPlf013() == null ? "" : epp.getPlf013());
                            eachDataRow.createCell(4).setCellValue(epp.getPlf007() == null ? "" : epp.getPlf007());
                            eachDataRow.createCell(5).setCellValue(epp.getPlf005() == null ? "" : epp.getPlf005());
                            eachDataRow.createCell(6).setCellValue(epp.getPlf006() == null ? "" : epp.getPlf006());
                            eachDataRow.createCell(7).setCellValue(epp.getPhd007() == null ? "" : epp.getPhd007());
                            eachDataRow.createCell(8).setCellValue(epp.getEpp003() == null ? "" : epp.getEpp003());
                            eachDataRow.createCell(9).setCellValue(epp.getEpp004() == null ? "" : epp.getEpp004());
                            eachDataRow.createCell(10).setCellValue(epp.getEpp005() == null ? "" : epp.getEpp005());
                            eachDataRow.createCell(11).setCellValue(epp.getEpp006() == null ? "" : epp.getEpp006());
                            eachDataRow.createCell(12).setCellValue(epp.getEpp007() == null ? "" : epp.getEpp007());
                            eachDataRow.createCell(13).setCellValue(epp.getEpp011() == null ? "" : epp.getEpp011());
                            eachDataRow.createCell(14).setCellValue(epp.getEpp012() == null ? "" : epp.getEpp012());
                            if(!"".equals(epp.getEpp013()) && epp.getEpp013()!=null ){
                                String epp013sub="";
                                String epp013 =epp.getEpp013();
                                String[] list =epp013.split(",");
                                for (int j = 0; j < list.length; j++) {
                                    String k=list[j];
                                    if(k.equals("1")){
                                        epp013sub+="创业培训、";
                                    }else if(k.equals("2")){
                                        epp013sub+="创业孵化、";
                                    }else if(k.equals("3")){
                                        epp013sub+="创业担保贷款、";
                                    }else if(k.equals("4")){
                                        epp013sub+="创业补贴、";
                                    }else if(k.equals("5")){
                                        epp013sub+="减免税、";
                                    }
                                }
                              //  eachDataRow.createCell(15).setCellValue(epp013sub.substring(0,epp013sub.length()-1));
                            }
                            eachDataRow.createCell(16).setCellValue(epp.getEpp015() == null ? "" : epp.getEpp015());
                            eachDataRow.createCell(17).setCellValue(epp.getEpp014() == null ? "" : epp.getEpp014());
                            eachDataRow.createCell(18).setCellValue(epp.getPlf008() == null ? "" : epp.getPlf008());
                            eachDataRow.createCell(19).setCellValue(epp.getPlf009() == null ? "" : epp.getPlf009());
                            eachDataRow.createCell(20).setCellValue(epp.getPlf010() == null ? "" : epp.getPlf010());
                            eachDataRow.createCell(21).setCellValue(epp.getPlf011() == null ? "" : epp.getPlf011());
                            eachDataRow.createCell(22).setCellValue(epp.getPlf012() == null ? "" : epp.getPlf012());
                            eachDataRow.createCell(23).setCellValue(epp.getPlf014() == null ? "" : epp.getPlf014());
                            eachDataRow.createCell(24).setCellValue(epp.getPlf016() == null ? "" : epp.getPlf016());
                            eachDataRow.createCell(25).setCellValue(epp.getPlf015() == null ? "" : epp.getPlf015());
                            eachDataRow.createCell(26).setCellValue(epp.getPlf017() == null ? "" : epp.getPlf017());
                            eachDataRow.createCell(27).setCellValue(epp.getPlf018() == null ? "" : epp.getPlf018());
                            eachDataRow.createCell(28).setCellValue(epp.getPlf023() == null ? "" : epp.getPlf023());
                            eachDataRow.createCell(29).setCellValue(epp.getPlf024() == null ? "" : epp.getPlf024());
                        }
                    }
                }

            }
        });
        return "导出用户EXCEL成功";
    }

}
