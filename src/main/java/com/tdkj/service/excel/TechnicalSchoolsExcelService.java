package com.tdkj.service.excel;

import com.tdkj.model.ThsPlf;

/**
 *
 * 劳动力就业信息导出
 */
public interface TechnicalSchoolsExcelService {

    /**
     * 劳动力技工院校信息导出
     * @param  thsPlf
     * @return
     */
    String export(ThsPlf thsPlf) throws Exception;
}
