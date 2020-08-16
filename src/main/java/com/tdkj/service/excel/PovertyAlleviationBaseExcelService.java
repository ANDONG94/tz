package com.tdkj.service.excel;

import com.tdkj.model.CommunityFactory;
import com.tdkj.model.PovertyAlleviationBase;

/**
 *
 * 劳动力就业信息导出
 */
public interface PovertyAlleviationBaseExcelService {



    /**
     *扶贫基地
     * @return
     */
    String export(PovertyAlleviationBase povertyAlleviationBase) throws Exception;

}
