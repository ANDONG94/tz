package com.tdkj.ServiceImpl.excel;

import com.github.pagehelper.PageHelper;
import com.tdkj.dao.InformationCollection.EmploymentSituationMapper;
import com.tdkj.excel.WriteExcelDataDelegated;
import com.tdkj.model.EysPlf;
import com.tdkj.model.PoorXzqh;
import com.tdkj.service.Ztree.ZtreeService;
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
public class EmploymentSintuationExcelServiceImpl implements EmploymentSintuationExcelService {
    private final static Logger logger = LoggerFactory.getLogger(PoorWorkExcelServiceImpl.class);

    @Autowired
    private HttpServletResponse response;
    @Autowired
    private EmploymentSituationMapper employmentSituationMapper;
    @Autowired
    private ZtreeService ztreeService;

    /**
     * 劳动力就业信息导出
     */
    @Override
    public String export(EysPlf eysPlf) throws Exception {
        // 总记录数
       String countlist=employmentSituationMapper.queryAllEmploymentSituationByAab301(eysPlf);
        // 总记录数

        List<EysPlf> list=employmentSituationMapper.queryEmploymentSituationAccountByAab301(eysPlf,0+"",(Integer.parseInt(countlist)+1)+"");


        // 导出EXCEL文件名称
        String filaName = "贫困劳动力就业信息";
        // 标题
        String[] titles = {"所属行政区","姓名","性别","年龄","身份证号","就业类型","就业地域","特色就业","就业岗位(工种)","就业地址","就业单位名称",
                "就业起始年月","就业终止年月","联系电话","脱贫状态","文化程度","转移就业意愿","就业创业状态","培训教育意愿","培训教育状态","苏陕协作","年度","在校状态",
                "就业意向","劳动能力","是否残疾","就业渠道","就业单位性质",
                "签订合同或协议月","岗位补贴","工资发放周期","就业见习补贴","求职补贴","转移就业交通补贴","享受政策"};

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

                            EysPlf employmentSituation = list.get(i - startRowCount);
                            // ---------   这一块变量照着抄就行  强迫症 后期也封装起来     -----------------------
                            eachDataRow.createCell(0).setCellValue(employmentSituation.getAab301() == null ? "" : employmentSituation.getAab301());
                            eachDataRow.createCell(1).setCellValue(employmentSituation.getPlf004() == null ? "" : employmentSituation.getPlf004());
                            eachDataRow.createCell(2).setCellValue(employmentSituation.getPlf007() == null ? "" : employmentSituation.getPlf007());
                            eachDataRow.createCell(3).setCellValue(employmentSituation.getPlf008() == null ? "" : employmentSituation.getPlf008());
                            eachDataRow.createCell(4).setCellValue(employmentSituation.getPlf005() == null ? "" : employmentSituation.getPlf005());
                            eachDataRow.createCell(5).setCellValue(employmentSituation.getEys004() == null ? "" : employmentSituation.getEys004());
                            eachDataRow.createCell(6).setCellValue(employmentSituation.getEys012() == null ? "" : employmentSituation.getEys012());
                            eachDataRow.createCell(7).setCellValue(employmentSituation.getEys011() == null ? "" : employmentSituation.getEys011());
                            eachDataRow.createCell(8).setCellValue(employmentSituation.getEys005() == null ? "" : employmentSituation.getEys005());
                            eachDataRow.createCell(9).setCellValue(employmentSituation.getEys008() == null ? "" : employmentSituation.getEys008());
                            eachDataRow.createCell(10).setCellValue(employmentSituation.getEys006() == null ? "" : employmentSituation.getEys006());
                            eachDataRow.createCell(11).setCellValue(employmentSituation.getEys009() == null ? "" : employmentSituation.getEys009());
                            eachDataRow.createCell(12).setCellValue(employmentSituation.getEys010() == null ? "" : employmentSituation.getEys010());
                            eachDataRow.createCell(13).setCellValue(employmentSituation.getPlf006() == null ? "" : employmentSituation.getPlf006());
                            eachDataRow.createCell(14).setCellValue(employmentSituation.getPlf013() == null ? "" : employmentSituation.getPlf013());
                            eachDataRow.createCell(15).setCellValue(employmentSituation.getPlf009() == null ? "" : employmentSituation.getPlf009());
                            eachDataRow.createCell(16).setCellValue(employmentSituation.getPlf010() == null ? "" : employmentSituation.getPlf010());
                            eachDataRow.createCell(17).setCellValue(employmentSituation.getPlf011() == null ? "" : employmentSituation.getPlf011());
                            eachDataRow.createCell(18).setCellValue(employmentSituation.getPlf012() == null ? "" : employmentSituation.getPlf012());
                            eachDataRow.createCell(19).setCellValue(employmentSituation.getPlf024() == null ? "" : employmentSituation.getPlf024());
                            eachDataRow.createCell(20).setCellValue(employmentSituation.getPlf014() == null ? "" : employmentSituation.getPlf014());
                            eachDataRow.createCell(21).setCellValue(employmentSituation.getPlf016() == null ? "" : employmentSituation.getPlf016());
                            eachDataRow.createCell(22).setCellValue(employmentSituation.getPlf015() == null ? "" : employmentSituation.getPlf015());
                            eachDataRow.createCell(23).setCellValue(employmentSituation.getPlf017() == null ? "" : employmentSituation.getPlf017());
                            eachDataRow.createCell(24).setCellValue(employmentSituation.getPlf018() == null ? "" : employmentSituation.getPlf018());
                            eachDataRow.createCell(25).setCellValue(employmentSituation.getPlf023() == null ? "" : employmentSituation.getPlf023());
                            eachDataRow.createCell(26).setCellValue(employmentSituation.getEys003() == null ? "" : employmentSituation.getEys003());
                            eachDataRow.createCell(27).setCellValue(employmentSituation.getEys007() == null ? "" : employmentSituation.getEys007());
                            eachDataRow.createCell(28).setCellValue(employmentSituation.getEys016() == null ? "" : employmentSituation.getEys016());
                            eachDataRow.createCell(29).setCellValue(employmentSituation.getEys017() == null ? "" : employmentSituation.getEys017());
                            eachDataRow.createCell(30).setCellValue(employmentSituation.getEys018() == null ? "" : employmentSituation.getEys018());
                            eachDataRow.createCell(31).setCellValue(employmentSituation.getEys019() == null ? "" : employmentSituation.getEys019());
                            eachDataRow.createCell(32).setCellValue(employmentSituation.getEys020() == null ? "" : employmentSituation.getEys020());
                            eachDataRow.createCell(33).setCellValue(employmentSituation.getEys021() == null ? "" : employmentSituation.getEys021());
                            eachDataRow.createCell(34).setCellValue(employmentSituation.getEys022() == null ? "" : employmentSituation.getEys022());
                        }
                    }
                }

            }
        });
        return "导出用户EXCEL成功";
    }


    /**
     * 弱劳动力就业信息导出
     */
    @Override
    public String exportRuoEmploymentSintuation(EysPlf eysPlf) throws Exception {
        // 总记录数
        String countlist=employmentSituationMapper.queryAllRuoEmploymentSituationByAab301(eysPlf);
        // 总记录数

        List<EysPlf> list=employmentSituationMapper.queryRuoEmploymentSituationAccountByAab301(eysPlf,0+"",(Integer.parseInt(countlist)+1)+"");


        // 导出EXCEL文件名称
        String filaName = "弱劳动力就业信息";
        // 标题
        String[] titles = {"所属行政区","姓名","性别","年龄","身份证号","就业类型","就业地域","特色就业","就业岗位(工种)","就业地址","就业单位名称",
                "就业起始年月","就业终止年月","联系电话","脱贫状态","文化程度","转移就业意愿","就业创业状态","培训教育意愿","培训教育状态","苏陕协作","年度","在校状态",
                "就业意向","劳动能力","是否残疾","就业渠道","就业单位性质",
                "签订合同或协议月","岗位补贴","工资发放周期","就业见习补贴","求职补贴","转移就业交通补贴","享受政策"};

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

                            EysPlf employmentSituation = list.get(i - startRowCount);
                            // ---------   这一块变量照着抄就行  强迫症 后期也封装起来     -----------------------
                            eachDataRow.createCell(0).setCellValue(employmentSituation.getAab301() == null ? "" : employmentSituation.getAab301());
                            eachDataRow.createCell(1).setCellValue(employmentSituation.getPlf004() == null ? "" : employmentSituation.getPlf004());
                            eachDataRow.createCell(2).setCellValue(employmentSituation.getPlf007() == null ? "" : employmentSituation.getPlf007());
                            eachDataRow.createCell(3).setCellValue(employmentSituation.getPlf008() == null ? "" : employmentSituation.getPlf008());
                            eachDataRow.createCell(4).setCellValue(employmentSituation.getPlf005() == null ? "" : employmentSituation.getPlf005());
                            eachDataRow.createCell(5).setCellValue(employmentSituation.getEys004() == null ? "" : employmentSituation.getEys004());
                            eachDataRow.createCell(6).setCellValue(employmentSituation.getEys012() == null ? "" : employmentSituation.getEys012());
                            eachDataRow.createCell(7).setCellValue(employmentSituation.getEys011() == null ? "" : employmentSituation.getEys011());
                            eachDataRow.createCell(8).setCellValue(employmentSituation.getEys005() == null ? "" : employmentSituation.getEys005());
                            eachDataRow.createCell(9).setCellValue(employmentSituation.getEys008() == null ? "" : employmentSituation.getEys008());
                            eachDataRow.createCell(10).setCellValue(employmentSituation.getEys006() == null ? "" : employmentSituation.getEys006());
                            eachDataRow.createCell(11).setCellValue(employmentSituation.getEys009() == null ? "" : employmentSituation.getEys009());
                            eachDataRow.createCell(12).setCellValue(employmentSituation.getEys010() == null ? "" : employmentSituation.getEys010());
                            eachDataRow.createCell(13).setCellValue(employmentSituation.getPlf006() == null ? "" : employmentSituation.getPlf006());
                            eachDataRow.createCell(14).setCellValue(employmentSituation.getPlf013() == null ? "" : employmentSituation.getPlf013());
                            eachDataRow.createCell(15).setCellValue(employmentSituation.getPlf009() == null ? "" : employmentSituation.getPlf009());
                            eachDataRow.createCell(16).setCellValue(employmentSituation.getPlf010() == null ? "" : employmentSituation.getPlf010());
                            eachDataRow.createCell(17).setCellValue(employmentSituation.getPlf011() == null ? "" : employmentSituation.getPlf011());
                            eachDataRow.createCell(18).setCellValue(employmentSituation.getPlf012() == null ? "" : employmentSituation.getPlf012());
                            eachDataRow.createCell(19).setCellValue(employmentSituation.getPlf024() == null ? "" : employmentSituation.getPlf024());
                            eachDataRow.createCell(20).setCellValue(employmentSituation.getPlf014() == null ? "" : employmentSituation.getPlf014());
                            eachDataRow.createCell(21).setCellValue(employmentSituation.getPlf016() == null ? "" : employmentSituation.getPlf016());
                            eachDataRow.createCell(22).setCellValue(employmentSituation.getPlf015() == null ? "" : employmentSituation.getPlf015());
                            eachDataRow.createCell(23).setCellValue(employmentSituation.getPlf017() == null ? "" : employmentSituation.getPlf017());
                            eachDataRow.createCell(24).setCellValue(employmentSituation.getPlf018() == null ? "" : employmentSituation.getPlf018());
                            eachDataRow.createCell(25).setCellValue(employmentSituation.getPlf023() == null ? "" : employmentSituation.getPlf023());
                            eachDataRow.createCell(26).setCellValue(employmentSituation.getEys003() == null ? "" : employmentSituation.getEys003());
                            eachDataRow.createCell(27).setCellValue(employmentSituation.getEys007() == null ? "" : employmentSituation.getEys007());
                            eachDataRow.createCell(28).setCellValue(employmentSituation.getEys016() == null ? "" : employmentSituation.getEys016());
                            eachDataRow.createCell(29).setCellValue(employmentSituation.getEys017() == null ? "" : employmentSituation.getEys017());
                            eachDataRow.createCell(30).setCellValue(employmentSituation.getEys018() == null ? "" : employmentSituation.getEys018());
                            eachDataRow.createCell(31).setCellValue(employmentSituation.getEys019() == null ? "" : employmentSituation.getEys019());
                            eachDataRow.createCell(32).setCellValue(employmentSituation.getEys020() == null ? "" : employmentSituation.getEys020());
                            eachDataRow.createCell(33).setCellValue(employmentSituation.getEys021() == null ? "" : employmentSituation.getEys021());
                            eachDataRow.createCell(34).setCellValue(employmentSituation.getEys022() == null ? "" : employmentSituation.getEys022());
                        }
                    }
                }

            }
        });
        return "导出用户EXCEL成功";
    }



    /**
     * 公益性岗位人员导出
     * @param eysPlf
     * @return
     * @throws Exception
     */
    @Override
    public String exportPublicPost(EysPlf eysPlf) throws Exception {
        // 总记录数
        String countlist=employmentSituationMapper.queryPublicPostCountByAab301(eysPlf);
        // 总记录数

        List<EysPlf> list=employmentSituationMapper.queryPublicPostAccountByAab301(eysPlf,0+"",(Integer.parseInt(countlist)+1)+"");


        // 导出EXCEL文件名称
        String filaName = "贫困劳动力公益性岗位就业信息";
        // 标题
        String[] titles = {"所属行政区","姓名","性别","年龄","身份证号","就业类型","就业地域","特色就业","就业岗位(工种)","就业地址","就业单位名称",
                "就业起始年月","就业终止年月","联系电话","脱贫状态","文化程度","转移就业意愿","就业创业状态","培训教育意愿","培训教育状态","苏陕协作","年度","在校状态",
                "就业意向","劳动能力","是否残疾","就业渠道","就业单位性质",
                "签订合同或协议月","岗位补贴","工资发放周期","就业见习补贴","求职补贴","转移就业交通补贴","享受政策"};

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

                            EysPlf employmentSituation = list.get(i - startRowCount);
                            // ---------   这一块变量照着抄就行  强迫症 后期也封装起来     -----------------------
                            eachDataRow.createCell(0).setCellValue(employmentSituation.getAab301() == null ? "" : employmentSituation.getAab301());
                            eachDataRow.createCell(1).setCellValue(employmentSituation.getPlf004() == null ? "" : employmentSituation.getPlf004());
                            eachDataRow.createCell(2).setCellValue(employmentSituation.getPlf007() == null ? "" : employmentSituation.getPlf007());
                            eachDataRow.createCell(3).setCellValue(employmentSituation.getPlf008() == null ? "" : employmentSituation.getPlf008());
                            eachDataRow.createCell(4).setCellValue(employmentSituation.getPlf005() == null ? "" : employmentSituation.getPlf005());
                            eachDataRow.createCell(5).setCellValue(employmentSituation.getEys004() == null ? "" : employmentSituation.getEys004());
                            eachDataRow.createCell(6).setCellValue(employmentSituation.getEys012() == null ? "" : employmentSituation.getEys012());
                            eachDataRow.createCell(7).setCellValue(employmentSituation.getEys011() == null ? "" : employmentSituation.getEys011());
                            eachDataRow.createCell(8).setCellValue(employmentSituation.getEys005() == null ? "" : employmentSituation.getEys005());
                            eachDataRow.createCell(9).setCellValue(employmentSituation.getEys008() == null ? "" : employmentSituation.getEys008());
                            eachDataRow.createCell(10).setCellValue(employmentSituation.getEys006() == null ? "" : employmentSituation.getEys006());
                            eachDataRow.createCell(11).setCellValue(employmentSituation.getEys009() == null ? "" : employmentSituation.getEys009());
                            eachDataRow.createCell(12).setCellValue(employmentSituation.getEys010() == null ? "" : employmentSituation.getEys010());
                            eachDataRow.createCell(13).setCellValue(employmentSituation.getPlf006() == null ? "" : employmentSituation.getPlf006());
                            eachDataRow.createCell(14).setCellValue(employmentSituation.getPlf013() == null ? "" : employmentSituation.getPlf013());
                            eachDataRow.createCell(15).setCellValue(employmentSituation.getPlf009() == null ? "" : employmentSituation.getPlf009());
                            eachDataRow.createCell(16).setCellValue(employmentSituation.getPlf010() == null ? "" : employmentSituation.getPlf010());
                            eachDataRow.createCell(17).setCellValue(employmentSituation.getPlf011() == null ? "" : employmentSituation.getPlf011());
                            eachDataRow.createCell(18).setCellValue(employmentSituation.getPlf012() == null ? "" : employmentSituation.getPlf012());
                            eachDataRow.createCell(19).setCellValue(employmentSituation.getPlf024() == null ? "" : employmentSituation.getPlf024());
                            eachDataRow.createCell(20).setCellValue(employmentSituation.getPlf014() == null ? "" : employmentSituation.getPlf014());
                            eachDataRow.createCell(21).setCellValue(employmentSituation.getPlf016() == null ? "" : employmentSituation.getPlf016());
                            eachDataRow.createCell(22).setCellValue(employmentSituation.getPlf015() == null ? "" : employmentSituation.getPlf015());
                            eachDataRow.createCell(23).setCellValue(employmentSituation.getPlf017() == null ? "" : employmentSituation.getPlf017());
                            eachDataRow.createCell(24).setCellValue(employmentSituation.getPlf018() == null ? "" : employmentSituation.getPlf018());
                            eachDataRow.createCell(25).setCellValue(employmentSituation.getPlf023() == null ? "" : employmentSituation.getPlf023());
                            eachDataRow.createCell(26).setCellValue(employmentSituation.getEys003() == null ? "" : employmentSituation.getEys003());
                            eachDataRow.createCell(27).setCellValue(employmentSituation.getEys007() == null ? "" : employmentSituation.getEys007());
                            eachDataRow.createCell(28).setCellValue(employmentSituation.getEys016() == null ? "" : employmentSituation.getEys016());
                            eachDataRow.createCell(29).setCellValue(employmentSituation.getEys017() == null ? "" : employmentSituation.getEys017());
                            eachDataRow.createCell(30).setCellValue(employmentSituation.getEys018() == null ? "" : employmentSituation.getEys018());
                            eachDataRow.createCell(31).setCellValue(employmentSituation.getEys019() == null ? "" : employmentSituation.getEys019());
                            eachDataRow.createCell(32).setCellValue(employmentSituation.getEys020() == null ? "" : employmentSituation.getEys020());
                            eachDataRow.createCell(33).setCellValue(employmentSituation.getEys021() == null ? "" : employmentSituation.getEys021());
                            eachDataRow.createCell(34).setCellValue(employmentSituation.getEys022() == null ? "" : employmentSituation.getEys022());
                        }
                    }
                }

            }
        });
        return "导出用户EXCEL成功";
    }
}
