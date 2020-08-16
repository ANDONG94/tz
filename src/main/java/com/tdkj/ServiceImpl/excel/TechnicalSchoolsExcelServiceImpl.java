package com.tdkj.ServiceImpl.excel;

import com.github.pagehelper.PageHelper;
import com.tdkj.dao.InformationCollection.TechnicalSchoolsMapper;
import com.tdkj.excel.WriteExcelDataDelegated;
import com.tdkj.model.ThsPlf;
import com.tdkj.service.excel.TechnicalSchoolsExcelService;
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
public class TechnicalSchoolsExcelServiceImpl implements TechnicalSchoolsExcelService {
    private final static Logger logger = LoggerFactory.getLogger(PoorWorkExcelServiceImpl.class);

    @Autowired
    private HttpServletResponse response;
    @Autowired
    private TechnicalSchoolsMapper technicalSchoolsMapper;

    /**
     * 劳动力技校信息导出
     */
    @Override
    public String export(ThsPlf thsPlf) throws Exception {
        // 总记录数
       String countlist=technicalSchoolsMapper.queryAllTechnicalSchoolByAab301(thsPlf);
        // 总记录数
        List<ThsPlf> list=technicalSchoolsMapper.queryTechnicalSchoolAccountByAab301(thsPlf,0+"",(Integer.parseInt(countlist)+1)+"");


        // 导出EXCEL文件名称
        String filaName = "贫困劳动力技工院校信息";
        // 标题
        String[] titles = {  "所属行政区","姓名","易地扶贫搬迁","脱贫状态","性别","身份证号","联系电话","家庭住址","技校名称","技校地址","入学日期","毕业日期","所学专业","毕业后就业日期",
                "技校区域","苏陕协作","学年制","享受助学金","享受雨露计划补贴","享受免学费补贴","年龄","文化程度","转移就业意愿","就业创业状态","培训教育意愿","苏陕协作","年度","在校状态",
                "就业意向","劳动能力","是否残疾","培训教育状态"};

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

                            ThsPlf ths = list.get(i - startRowCount);
                            // ---------   这一块变量照着抄就行  强迫症 后期也封装起来     -----------------------
                            eachDataRow.createCell(0).setCellValue(ths.getAab301() == null ? "" : ths.getAab301());
                            eachDataRow.createCell(1).setCellValue(ths.getPlf004() == null ? "" : ths.getPlf004());
                            eachDataRow.createCell(2).setCellValue(ths.getPhd013() == null ? "" : ths.getPhd013());
                            eachDataRow.createCell(3).setCellValue(ths.getPlf013() == null ? "" : ths.getPlf013());
                            eachDataRow.createCell(4).setCellValue(ths.getPlf007() == null ? "" : ths.getPlf007());
                            eachDataRow.createCell(5).setCellValue(ths.getPlf005() == null ? "" : ths.getPlf005());
                            eachDataRow.createCell(6).setCellValue(ths.getPlf006() == null ? "" : ths.getPlf006());
                            eachDataRow.createCell(7).setCellValue(ths.getPhd007() == null ? "" : ths.getPhd007());
                            eachDataRow.createCell(8).setCellValue(ths.getThs003() == null ? "" : ths.getThs003());
                            eachDataRow.createCell(9).setCellValue(ths.getThs004() == null ? "" : ths.getThs004());
                            eachDataRow.createCell(10).setCellValue(ths.getThs005() == null ? "" : ths.getThs005());
                            eachDataRow.createCell(11).setCellValue(ths.getThs006() == null ? "" : ths.getThs006());
                            eachDataRow.createCell(12).setCellValue(ths.getThs007() == null ? "" : ths.getThs007());
                            eachDataRow.createCell(13).setCellValue(ths.getThs008() == null ? "" : ths.getThs008());
                            eachDataRow.createCell(14).setCellValue(ths.getThs009() == null ? "" : ths.getThs009());
                            eachDataRow.createCell(15).setCellValue(ths.getThs010() == null ? "" : ths.getThs010());
                            eachDataRow.createCell(16).setCellValue(ths.getThs013() == null ? "" : ths.getThs013());
                            eachDataRow.createCell(17).setCellValue(ths.getThs014() == null ? "" : ths.getThs014());
                            eachDataRow.createCell(18).setCellValue(ths.getThs016() == null ? "" : ths.getThs016());
                            eachDataRow.createCell(19).setCellValue(ths.getThs015() == null ? "" : ths.getThs015());
                            eachDataRow.createCell(20).setCellValue(ths.getPlf008() == null ? "" : ths.getPlf008());
                            eachDataRow.createCell(21).setCellValue(ths.getPlf009() == null ? "" : ths.getPlf009());
                            eachDataRow.createCell(22).setCellValue(ths.getPlf010() == null ? "" : ths.getPlf010());
                            eachDataRow.createCell(23).setCellValue(ths.getPlf011() == null ? "" : ths.getPlf011());
                            eachDataRow.createCell(24).setCellValue(ths.getPlf012() == null ? "" : ths.getPlf012());
                            eachDataRow.createCell(25).setCellValue(ths.getPlf014() == null ? "" : ths.getPlf014());
                            eachDataRow.createCell(26).setCellValue(ths.getPlf016() == null ? "" : ths.getPlf016());
                            eachDataRow.createCell(27).setCellValue(ths.getPlf015() == null ? "" : ths.getPlf015());
                            eachDataRow.createCell(28).setCellValue(ths.getPlf017() == null ? "" : ths.getPlf017());
                            eachDataRow.createCell(29).setCellValue(ths.getPlf018() == null ? "" : ths.getPlf018());
                            eachDataRow.createCell(30).setCellValue(ths.getPlf023() == null ? "" : ths.getPlf023());
                            eachDataRow.createCell(31).setCellValue(ths.getPlf024() == null ? "" : ths.getPlf024());
                        }
                    }
                }

            }
        });
        return "导出用户EXCEL成功";
    }

}
