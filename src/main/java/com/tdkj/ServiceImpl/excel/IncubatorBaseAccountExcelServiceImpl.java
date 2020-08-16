package com.tdkj.ServiceImpl.excel;

import com.github.pagehelper.PageHelper;
import com.tdkj.excel.WriteExcelDataDelegated;
import com.tdkj.model.IncubatorBase;
import com.tdkj.model.PovertyAlleviationBase;
import com.tdkj.service.IncubatorBase.IncubatorBaseService;
import com.tdkj.service.PovertyAlleviationBase.PovertyAlleviationBaseService;
import com.tdkj.service.Ztree.ZtreeService;
import com.tdkj.service.excel.IncubatorBaseAccountExcelService;
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
public class IncubatorBaseAccountExcelServiceImpl implements IncubatorBaseAccountExcelService {
    private final static Logger logger = LoggerFactory.getLogger(PoorWorkExcelServiceImpl.class);

    @Autowired
    private HttpServletResponse response;
    @Autowired
    private ZtreeService ztreeService;
    @Autowired
    private IncubatorBaseService incubatorBaseService;
    /**
     * 劳动力就业信息导出
     */
    @Override
    public String export(IncubatorBase incubatorBase) throws Exception {


        String countlist =incubatorBaseService.queryAllByAab301(incubatorBase);
        List<IncubatorBase> list= incubatorBaseService.queryIncubatorBaseByAab301(incubatorBase,0+"",countlist+"");

        // 导出EXCEL文件名称
        String filaName = "创业基地园区中心台账";
        // 标题
        String[] titles = {"所属行政区","类别","名称","地址","级别","累计扶持创业人数","当年扶持创业人数","累计扶持就业人数","当年扶持就业人数"};
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
                            IncubatorBase incubatorBases = list.get(i - startRowCount);
                            // ---------   这一块变量照着抄就行  强迫症 后期也封装起来     -----------------------
                            eachDataRow.createCell(0).setCellValue(incubatorBases.getAab301() == null ? "" : incubatorBases.getAab301());
                            eachDataRow.createCell(1).setCellValue(incubatorBases.getIbb005() == null ? "" : incubatorBases.getIbb005());
                            eachDataRow.createCell(2).setCellValue(incubatorBases.getIbb003() == null ? "" : incubatorBases.getIbb003());
                            eachDataRow.createCell(3).setCellValue(incubatorBases.getIbb004() == null ? "" : incubatorBases.getIbb004());
                            eachDataRow.createCell(4).setCellValue(incubatorBases.getIbb006() == null ? "" : incubatorBases.getIbb006());
                            eachDataRow.createCell(5).setCellValue(incubatorBases.getIbb010() == null ? "" : incubatorBases.getIbb010()+"");
                            eachDataRow.createCell(6).setCellValue(incubatorBases.getIbb011() == null ? "" : incubatorBases.getIbb011()+"" );
                            eachDataRow.createCell(7).setCellValue(incubatorBases.getIbb012() == null ? "" : incubatorBases.getIbb012()+"");
                            eachDataRow.createCell(8).setCellValue(incubatorBases.getIbb013() == null ? "" : incubatorBases.getIbb013()+"");
                        }
                    }
                }

            }
        });
        return "导出用户EXCEL成功";
    }
}
