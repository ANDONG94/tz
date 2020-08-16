package com.tdkj.service.excel;

import com.tdkj.model.IncubatorBase;
import com.tdkj.model.PovertyAlleviationBase;

/**
 *
 * 劳动力就业信息导出
 */
public interface IncubatorBaseAccountExcelService {



    /**
     *园区
     * @return
     */
    String export(IncubatorBase incubatorBase) throws Exception;

}
