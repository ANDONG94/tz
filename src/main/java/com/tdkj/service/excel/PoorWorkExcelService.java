package com.tdkj.service.excel;
import com.tdkj.model.PoorHouseholds;

import javax.servlet.http.HttpServletResponse;

public interface  PoorWorkExcelService {

    /**
     * 导出贫困户EXCEL
     *
     *
     * @return String
     * @throws Exception
     */
    String  export(PoorHouseholds poorHouseholds) throws Exception;

    //贫困户待确认导出
    String  exportToBeComfirePoorWork( PoorHouseholds poorHouseholds) throws Exception;

//待帮扶贫困户导出
    String  exportTobeHelpPoor(PoorHouseholds poorHouseholds) throws Exception;

}
