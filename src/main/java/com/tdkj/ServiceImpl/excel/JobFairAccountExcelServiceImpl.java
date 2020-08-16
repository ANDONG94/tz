package com.tdkj.ServiceImpl.excel;

import com.github.pagehelper.PageHelper;
import com.tdkj.excel.WriteExcelDataDelegated;
import com.tdkj.model.CommunityFactory;
import com.tdkj.model.JobFair;
import com.tdkj.service.CommunityFactory.CommunityFactoryService;
import com.tdkj.service.JobFair.JobFairService;
import com.tdkj.service.Ztree.ZtreeService;
import com.tdkj.service.excel.CommunityfactoryExcelService;
import com.tdkj.service.excel.JobFairAccountExcelService;
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
public class JobFairAccountExcelServiceImpl implements JobFairAccountExcelService {
    private final static Logger logger = LoggerFactory.getLogger(PoorWorkExcelServiceImpl.class);

    @Autowired
    private HttpServletResponse response;
    @Autowired
    private ZtreeService ztreeService;
    @Autowired
    private JobFairService jobFairService;

    /**
     * 劳动力就业信息导出
     */
    @Override
    public String export(JobFair jobFair) throws Exception {

        String countlist = jobFairService.queryAllByAab301(jobFair);
        List<JobFair> list= jobFairService.queryJobFairServiceByAab301(jobFair,0+"",countlist+"");




        // 导出EXCEL文件名称
        String filaName = "社区工厂台账";
        // 标题
        String[] titles = {"所属行政区","招聘会名称","合办单位","苏陕协作","举办地点","开始日期","提供就业岗位","贫困劳动力岗位","参会人数","贫困劳动力参会人数","签订意向协议人数",
                "贫困劳动力签订意向人数","个月内就业人数","贫困劳动力就业人数"};

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
                            JobFair jobFair = list.get(i - startRowCount);
                            // ---------   这一块变量照着抄就行  强迫症 后期也封装起来     -----------------------
                            eachDataRow.createCell(0).setCellValue(jobFair.getAab301() == null ? "" : jobFair.getAab301());
                            eachDataRow.createCell(1).setCellValue(jobFair.getJfr002() == null ? "" : jobFair.getJfr002());
                            eachDataRow.createCell(2).setCellValue(jobFair.getJfr003() == null ? "" : jobFair.getJfr003());
                            eachDataRow.createCell(3).setCellValue(jobFair.getJfr004() == null ? "" : jobFair.getJfr004());
                            eachDataRow.createCell(4).setCellValue(jobFair.getJfr005() == null ? "" : jobFair.getJfr005());
                            eachDataRow.createCell(5).setCellValue(jobFair.getJfr006()== null ? "" : jobFair.getJfr006()+"" );
                            eachDataRow.createCell(6).setCellValue(jobFair.getJfr007() == null ? "" : jobFair.getJfr007()+"");
                            eachDataRow.createCell(7).setCellValue(jobFair.getJfr008()== null ? "" : jobFair.getJfr008()+"");
                            eachDataRow.createCell(8).setCellValue(jobFair.getJfr009() == null ? "" : jobFair.getJfr009()+"");
                            eachDataRow.createCell(9).setCellValue(jobFair.getJfr010() == null ? "" : jobFair.getJfr010()+"");
                            eachDataRow.createCell(10).setCellValue(jobFair.getJfr011() == null ? "" : jobFair.getJfr011()+"");
                            eachDataRow.createCell(11).setCellValue(jobFair.getJfr012() == null ? "" : jobFair.getJfr012()+"");
                            eachDataRow.createCell(12).setCellValue(jobFair.getJfr013() == null ? "" : jobFair.getJfr013()+"");
                            eachDataRow.createCell(13).setCellValue(jobFair.getJfr014() == null ? "" : jobFair.getJfr014()+"");

                        }
                    }
                }

            }
        });
        return "导出用户EXCEL成功";
    }
}
