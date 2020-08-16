package com.tdkj.service.excel;

import com.tdkj.model.TsnPlf;

/**
 *
 * 劳动力就业信息导出
 */
public interface TrainingSituationExcelService {

    /**
     * 劳动力就业信息导出
     * @param tsnPlf
     * @return
     */
    String export(TsnPlf tsnPlf) throws Exception;
}
