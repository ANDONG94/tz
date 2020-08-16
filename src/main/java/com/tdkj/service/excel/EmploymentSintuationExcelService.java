package com.tdkj.service.excel;

import com.tdkj.model.EysPlf;

/**
 *
 * 劳动力就业信息导出
 */
public interface EmploymentSintuationExcelService {

    /**
     * 劳动力就业信息导出
     * @param aab301
     * @return
     */
    String export(EysPlf eysPlf) throws Exception;


    /**
     * 劳动力公益性岗位就业信息导出
     * @return
     */
    String exportPublicPost(EysPlf eysPlf) throws Exception;

    /**
     * 弱劳动力就业信息导出
     * @param aab301
     * @return
     */
    String exportRuoEmploymentSintuation(EysPlf eysPlf) throws Exception;

}
