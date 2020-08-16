package com.tdkj.service.excel;

import com.tdkj.model.EppPlf;

/**
 *
 * 劳动力就业信息导出
 */
public interface EntrepreneurShipExcelService {

    /**
     * 劳动力创业信息导出
     * @param EppPlf eppPlf
     * @return
     */
    String export(EppPlf eppPlf) throws Exception;
}
