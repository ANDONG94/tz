package com.tdkj.service.excel;

import com.tdkj.model.JobFair;

/**
 *
 * 劳动力就业信息导出
 */
public interface JobFairAccountExcelService {



    /**
     *招聘会
     * @return
     */
    String export(JobFair jobFair) throws Exception;
}
