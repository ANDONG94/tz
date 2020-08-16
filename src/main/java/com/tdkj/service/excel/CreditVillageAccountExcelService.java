package com.tdkj.service.excel;

import com.tdkj.model.CreditVillage;

/**
 *
 * 劳动力就业信息导出
 */
public interface CreditVillageAccountExcelService {



    /**
     *信用乡村
     * @return
     */
    String export(CreditVillage creditVillage) throws Exception;
}
