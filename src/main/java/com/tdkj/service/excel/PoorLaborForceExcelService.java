package com.tdkj.service.excel;

import com.tdkj.model.PoorLaborForce;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface  PoorLaborForceExcelService {

    /**
     * 贫困劳动力
     * @return String
     * @throws Exception
     */
    String  export( PoorLaborForce poorLaborForce) throws Exception;

    /**
     * 贫困劳动力台账
     *
     *
     * @return String
     * @throws Exception
     */
    String  exportFamily(PoorLaborForce poorLaborForce) throws Exception;

    /**
     * 贫困劳动力台账
     *
     *
     * @return String
     * @throws Exception
     */
    String  exportLedger(PoorLaborForce poorLaborForce) throws Exception;

    /**
     * 贫困劳动力台账
     *
     *
     * @return String
     * @throws Exception
     */
    String  exportLedgerAccount(PoorLaborForce poorLaborForce) throws Exception;

    //劳动力待确认导出
    String  exportToBeComfirePoorLaborForce(PoorLaborForce poorLaborForce) throws Exception;

    //未就业创业劳动力导出
    String  exportTobeWorker(PoorLaborForce poorLaborForce) throws Exception;


    /**
     * 弱贫困劳动力台账
     *
     *
     * @return String
     * @throws Exception
     */
    String  exortRuoPoorLaborForceLedger(PoorLaborForce poorLaborForce) throws Exception;

}