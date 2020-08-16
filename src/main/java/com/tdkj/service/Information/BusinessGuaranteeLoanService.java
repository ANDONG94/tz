package com.tdkj.service.Information;

import com.tdkj.model.BusinessGuaranteeLoan;
import com.tdkj.model.LaborBrokering;
import com.tdkj.model.NewSushanTraining;

import java.util.List;

/**
 * Created by hedd on 2019/11/22.
 */
public interface BusinessGuaranteeLoanService {

    int deleteByPrimaryKey(String bgl001,String realname);

    int insert(BusinessGuaranteeLoan businessGuaranteeLoan);

    int insertSelective(BusinessGuaranteeLoan businessGuaranteeLoan);

    BusinessGuaranteeLoan selectByPrimaryKey(String bgl001);

    int updateByPrimaryKeySelective(BusinessGuaranteeLoan businessGuaranteeLoan);

    int updateByPrimaryKey(BusinessGuaranteeLoan businessGuaranteeLoan);

    /**
     * 根据行政区划查询创业担保贷款列表
     * @return
     */
    public List<BusinessGuaranteeLoan> queryBusinessGuaranteeLoanByAab301(BusinessGuaranteeLoan businessGuaranteeLoan, String startRecord, String pageSize);

    /**
     * 根据行政区划查询总共多条
     * @return
     */
    public String queryAllBusinessGuaranteeLoanCountByAab301(BusinessGuaranteeLoan businessGuaranteeLoan);
}
