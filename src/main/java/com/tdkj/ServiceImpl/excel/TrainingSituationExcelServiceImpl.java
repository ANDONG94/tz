package com.tdkj.ServiceImpl.excel;

import com.github.pagehelper.PageHelper;
import com.tdkj.dao.InformationCollection.TrainingSituationMapper;
import com.tdkj.excel.WriteExcelDataDelegated;
import com.tdkj.model.TsnPlf;
import com.tdkj.service.excel.TrainingSituationExcelService;
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
public class TrainingSituationExcelServiceImpl implements TrainingSituationExcelService {
    private final static Logger logger = LoggerFactory.getLogger(PoorWorkExcelServiceImpl.class);

    @Autowired
    private HttpServletResponse response;
    @Autowired
    private TrainingSituationMapper trainingSituationMapper;

    /**
     * 劳动力培训息导出
     */
    @Override
    public String export(TsnPlf tsnPlf) throws Exception {
        // 总记录数
       String countlist=trainingSituationMapper.queryAllTrainSituationByAab301(tsnPlf);
        // 总记录数
        List<TsnPlf> list=trainingSituationMapper.queryTrainSituationAccountByAab301(tsnPlf,0+"",(Integer.parseInt(countlist)+1)+"");

        // 导出EXCEL文件名称
        String filaName = "贫困劳动力培训信息";
        // 标题
        String[] titles = {"所属行政区","姓名","易地扶贫搬迁","脱贫状态","性别","身份证号","联系电话","家庭住址","培训类型","培训工种","培训单位名称","培训单位地址",
                "培训开始时间","培训结束时间","培训/创业年月","苏陕协作","在苏就业年月","在陕就业年月","在陕创业年月","享受生活和交通补贴","年龄","文化程度",
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

                            TsnPlf tsn = list.get(i - startRowCount);
                            // ---------   这一块变量照着抄就行  强迫症 后期也封装起来     -----------------------
                            eachDataRow.createCell(0).setCellValue(tsn.getAab301() == null ? "" : tsn.getAab301());
                            eachDataRow.createCell(1).setCellValue(tsn.getPlf004() == null ? "" : tsn.getPlf004());
                            eachDataRow.createCell(2).setCellValue(tsn.getPhd013() == null ? "" : tsn.getPhd013());
                            eachDataRow.createCell(3).setCellValue(tsn.getPlf013() == null ? "" : tsn.getPlf013());
                            eachDataRow.createCell(4).setCellValue(tsn.getPlf007() == null ? "" : tsn.getPlf007());
                            eachDataRow.createCell(5).setCellValue(tsn.getPlf005() == null ? "" : tsn.getPlf005());
                            eachDataRow.createCell(6).setCellValue(tsn.getPlf006() == null ? "" : tsn.getPlf006());
                            eachDataRow.createCell(7).setCellValue(tsn.getPhd007() == null ? "" : tsn.getPhd007());
                            eachDataRow.createCell(8).setCellValue(tsn.getTsn003() == null ? "" : tsn.getTsn003());
                            eachDataRow.createCell(9).setCellValue(tsn.getTsn004() == null ? "" : tsn.getTsn004());
                            eachDataRow.createCell(10).setCellValue(tsn.getTsn005() == null ? "" : tsn.getTsn005());
                            eachDataRow.createCell(11).setCellValue(tsn.getTsn006() == null ? "" : tsn.getTsn006());
                            eachDataRow.createCell(12).setCellValue(tsn.getTsn007() == null ? "" : tsn.getTsn007());
                            eachDataRow.createCell(13).setCellValue(tsn.getTsn008() == null ? "" : tsn.getTsn008());
                            eachDataRow.createCell(14).setCellValue(tsn.getTsn002() == null ? "" : tsn.getTsn002());
                            eachDataRow.createCell(15).setCellValue(tsn.getTsn012() == null ? "" : tsn.getTsn012());
                            eachDataRow.createCell(16).setCellValue(tsn.getTsn013() == null ? "" : tsn.getTsn013());
                            eachDataRow.createCell(17).setCellValue(tsn.getTsn014() == null ? "" : tsn.getTsn014());
                            eachDataRow.createCell(18).setCellValue(tsn.getTsn015() == null ? "" : tsn.getTsn015());
                            eachDataRow.createCell(19).setCellValue(tsn.getTsn016() == null ? "" : tsn.getTsn016());
                            eachDataRow.createCell(20).setCellValue(tsn.getPlf008() == null ? "" : tsn.getPlf008());
                            eachDataRow.createCell(21).setCellValue(tsn.getPlf009() == null ? "" : tsn.getPlf009());
                            eachDataRow.createCell(22).setCellValue(tsn.getPlf010() == null ? "" : tsn.getPlf010());
                            eachDataRow.createCell(23).setCellValue(tsn.getPlf011() == null ? "" : tsn.getPlf011());
                            eachDataRow.createCell(24).setCellValue(tsn.getPlf012() == null ? "" : tsn.getPlf012());
                            eachDataRow.createCell(25).setCellValue(tsn.getPlf014() == null ? "" : tsn.getPlf014());
                            eachDataRow.createCell(26).setCellValue(tsn.getPlf016() == null ? "" : tsn.getPlf016());
                            eachDataRow.createCell(27).setCellValue(tsn.getPlf015() == null ? "" : tsn.getPlf015());
                            eachDataRow.createCell(28).setCellValue(tsn.getPlf017() == null ? "" : tsn.getPlf017());
                            eachDataRow.createCell(29).setCellValue(tsn.getPlf018() == null ? "" : tsn.getPlf018());
                            eachDataRow.createCell(30).setCellValue(tsn.getPlf023() == null ? "" : tsn.getPlf023());
                            eachDataRow.createCell(31).setCellValue(tsn.getPlf024() == null ? "" : tsn.getPlf024());
                        }
                    }
                }

            }
        });
        return "导出用户EXCEL成功";
    }

}
