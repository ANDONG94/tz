package com.tdkj.ServiceImpl.excel;

import com.github.pagehelper.PageHelper;
import com.tdkj.excel.WriteExcelDataDelegated;
import com.tdkj.model.CreditVillage;
import com.tdkj.service.CreditVillage.CreditVillageService;
import com.tdkj.service.Ztree.ZtreeService;
import com.tdkj.service.excel.CreditVillageAccountExcelService;
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
public class CreditVillageAccountExcelServiceImpl implements CreditVillageAccountExcelService {
    private final static Logger logger = LoggerFactory.getLogger(PoorWorkExcelServiceImpl.class);

    @Autowired
    private HttpServletResponse response;
    @Autowired
    private ZtreeService ztreeService;
    @Autowired
    private CreditVillageService creditVillageService;

    /**
     * 劳动力就业信息导出
     */
    @Override
    public String export(CreditVillage creditVillage) throws Exception {

        String countlist= creditVillageService.queryAllByAab301(creditVillage);
        List<CreditVillage> list= creditVillageService.queryCreditVillageByAab301(creditVillage,0+"",countlist+"");


        // 导出EXCEL文件名称
        String filaName = "信用乡村台账";
        // 标题
        String[] titles = {"所属行政区","信用乡村名称","年度","是否贫困村","是否涉贫存村","成功创业户数","贫困劳动力成功创业户数","当年新增贷款户数",
                "当年新增贷款贫困户数","当年新增贷款总额","贫困户当年新增贷款总额","建成时间","注销时间"};

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
                            CreditVillage creditVillage = list.get(i - startRowCount);
                            // ---------   这一块变量照着抄就行  强迫症 后期也封装起来     -----------------------
                            eachDataRow.createCell(0).setCellValue(creditVillage.getAab301() == null ? "" : creditVillage.getAab301());
                            eachDataRow.createCell(1).setCellValue(creditVillage.getCvg002() == null ? "" : creditVillage.getCvg002());
                            eachDataRow.createCell(2).setCellValue(creditVillage.getCvg010() == null ? "" : creditVillage.getCvg010());
                            eachDataRow.createCell(3).setCellValue(creditVillage.getCvg015() == null ? "" : creditVillage.getCvg015());
                            eachDataRow.createCell(4).setCellValue(creditVillage.getCvg016() == null ? "" : creditVillage.getCvg016());
                            eachDataRow.createCell(5).setCellValue(creditVillage.getCvg013() == null ? "" : creditVillage.getCvg013() );
                            eachDataRow.createCell(6).setCellValue(creditVillage.getCvg014() == null ? "" : creditVillage.getCvg014());
                            eachDataRow.createCell(7).setCellValue(creditVillage.getCvg005() == null ? "" : creditVillage.getCvg005()+"");
                            eachDataRow.createCell(8).setCellValue(creditVillage.getCvg006() == null ? "" : creditVillage.getCvg006()+"");
                            eachDataRow.createCell(9).setCellValue(creditVillage.getCvg007()  == null ? "" : creditVillage.getCvg007()+"");
                            eachDataRow.createCell(10).setCellValue(creditVillage.getCvg008() == null ? "" : creditVillage.getCvg008()+"");
                            eachDataRow.createCell(11).setCellValue(creditVillage.getCvg004() == null ? "" : creditVillage.getCvg004());
                            eachDataRow.createCell(12).setCellValue(creditVillage.getCvg012() == null ? "" : creditVillage.getCvg012());
                        }
                    }
                }

            }
        });
        return "导出用户EXCEL成功";
    }
}
