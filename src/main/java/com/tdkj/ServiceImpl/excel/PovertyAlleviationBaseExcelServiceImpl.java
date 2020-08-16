package com.tdkj.ServiceImpl.excel;

import com.github.pagehelper.PageHelper;
import com.tdkj.dao.InformationCollection.EmploymentSituationMapper;
import com.tdkj.excel.WriteExcelDataDelegated;
import com.tdkj.model.CommunityFactory;
import com.tdkj.model.PovertyAlleviationBase;
import com.tdkj.service.CommunityFactory.CommunityFactoryService;
import com.tdkj.service.PovertyAlleviationBase.PovertyAlleviationBaseService;
import com.tdkj.service.Ztree.ZtreeService;
import com.tdkj.service.excel.CommunityfactoryExcelService;
import com.tdkj.service.excel.PovertyAlleviationBaseExcelService;
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
public class PovertyAlleviationBaseExcelServiceImpl implements PovertyAlleviationBaseExcelService {
    private final static Logger logger = LoggerFactory.getLogger(PoorWorkExcelServiceImpl.class);

    @Autowired
    private HttpServletResponse response;
    @Autowired
    private ZtreeService ztreeService;
    @Autowired
    private PovertyAlleviationBaseService povertyAlleviationBaseService;
    /**
     * 劳动力就业信息导出
     */
    @Override
    public String export(PovertyAlleviationBase povertyAlleviationBase) throws Exception {

        String countlist = povertyAlleviationBaseService.queryAllByAab301(povertyAlleviationBase);
        List<PovertyAlleviationBase> list= povertyAlleviationBaseService.queryPovertyAlleviationBaseByAab301(povertyAlleviationBase,0+"",countlist+"");


        // 导出EXCEL文件名称
        String filaName = "扶贫基地台账";
        // 标题
        String[] titles = {"所属行政区","扶贫基地名称","级别","扶贫基地地址","安置点名称","从业人数","从业贫困劳动力数量","认定时间","注销时间"};
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
                            PovertyAlleviationBase povertyAlleviationBases = list.get(i - startRowCount);
                            // ---------   这一块变量照着抄就行  强迫症 后期也封装起来     -----------------------
                            eachDataRow.createCell(0).setCellValue(povertyAlleviationBases.getAab301() == null ? "" : povertyAlleviationBases.getAab301());
                            eachDataRow.createCell(1).setCellValue(povertyAlleviationBases.getPab003() == null ? "" : povertyAlleviationBases.getPab003());
                            eachDataRow.createCell(2).setCellValue(povertyAlleviationBases.getPab010() == null ? "" : povertyAlleviationBases.getPab010());
                            eachDataRow.createCell(3).setCellValue(povertyAlleviationBases.getPab004() == null ? "" : povertyAlleviationBases.getPab004());
                            eachDataRow.createCell(4).setCellValue(povertyAlleviationBases.getPab013() == null ? "" : povertyAlleviationBases.getPab013());
                            eachDataRow.createCell(5).setCellValue(povertyAlleviationBases.getPab007() == null ? "" : povertyAlleviationBases.getPab007()+"");
                            eachDataRow.createCell(6).setCellValue(povertyAlleviationBases.getPab008() == null ? "" : povertyAlleviationBases.getPab008()+"" );
                            eachDataRow.createCell(7).setCellValue(povertyAlleviationBases.getPab005() == null ? "" : povertyAlleviationBases.getPab005());
                            eachDataRow.createCell(8).setCellValue(povertyAlleviationBases.getPab006() == null ? "" : povertyAlleviationBases.getPab006());
                        }
                    }
                }

            }
        });
        return "导出用户EXCEL成功";
    }
}
