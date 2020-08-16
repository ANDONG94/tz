package com.tdkj.service.excel;

import com.tdkj.model.CommunityFactory;
import com.tdkj.model.EysPlf;

/**
 *
 * 劳动力就业信息导出
 */
public interface CommunityfactoryExcelService {



    /**
     *社区工厂导出
     * @return
     */
    String export(CommunityFactory communityFactory) throws Exception;
}
