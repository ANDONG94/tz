package com.tdkj.ServiceImpl.excel;

import com.github.pagehelper.PageHelper;
import com.tdkj.dao.InformationCollection.EmploymentSituationMapper;
import com.tdkj.excel.WriteExcelDataDelegated;
import com.tdkj.model.CommunityFactory;
import com.tdkj.model.EysPlf;
import com.tdkj.service.CommunityFactory.CommunityFactoryService;
import com.tdkj.service.Ztree.ZtreeService;
import com.tdkj.service.excel.CommunityfactoryExcelService;
import com.tdkj.service.excel.EmploymentSintuationExcelService;
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
public class CommunityfactoryExcelServiceImpl implements CommunityfactoryExcelService {
    private final static Logger logger = LoggerFactory.getLogger(PoorWorkExcelServiceImpl.class);

    @Autowired
    private HttpServletResponse response;
    @Autowired
    private ZtreeService ztreeService;
    @Autowired
    private CommunityFactoryService communityFactoryService;

    /**
     * 劳动力就业信息导出
     */
    @Override
    public String export(CommunityFactory communityFactory) throws Exception {
        String countlist = communityFactoryService.queryAllByAab301(communityFactory);
        List<CommunityFactory> list= communityFactoryService.queryCommunityFactoryByAab301(communityFactory,0+"",countlist+"");



        // 导出EXCEL文件名称
        String filaName = "社区工厂台账";
        // 标题
        String[] titles = {"所属行政区","社区工厂名称","认定时间","注销时间","社区工厂地址","从业人数","贫困劳动力数量","安置点名称"};

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
                            CommunityFactory communityFactorys = list.get(i - startRowCount);
                            // ---------   这一块变量照着抄就行  强迫症 后期也封装起来     -----------------------
                            eachDataRow.createCell(0).setCellValue(communityFactorys.getAab301() == null ? "" : communityFactorys.getAab301());
                            eachDataRow.createCell(1).setCellValue(communityFactorys.getCtf002() == null ? "" : communityFactorys.getCtf002());
                            eachDataRow.createCell(2).setCellValue(communityFactorys.getCtf005() == null ? "" : communityFactorys.getCtf005());
                            eachDataRow.createCell(3).setCellValue(communityFactorys.getCtf006() == null ? "" : communityFactorys.getCtf006());
                            eachDataRow.createCell(4).setCellValue(communityFactorys.getCtf003() == null ? "" : communityFactorys.getCtf003());
                            eachDataRow.createCell(5).setCellValue(communityFactorys.getCtf007()== null ? "" : communityFactorys.getCtf007()+"" );
                            eachDataRow.createCell(6).setCellValue(communityFactorys.getCtf008() == null ? "" : communityFactorys.getCtf008()+"");
                            eachDataRow.createCell(7).setCellValue(communityFactorys.getCtf004() == null ? "" : communityFactorys.getCtf004());
                        }
                    }
                }

            }
        });
        return "导出用户EXCEL成功";
    }
}
