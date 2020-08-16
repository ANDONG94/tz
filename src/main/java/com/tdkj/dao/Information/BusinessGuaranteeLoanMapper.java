package com.tdkj.dao.Information;

import com.tdkj.model.BusinessGuaranteeLoan;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessGuaranteeLoanMapper {
    int deleteByPrimaryKey(@Param("bgl001")String bgl001,@Param("realname")String realname);

    int insert(BusinessGuaranteeLoan businessGuaranteeLoan);

    int insertSelective(BusinessGuaranteeLoan businessGuaranteeLoan);

    BusinessGuaranteeLoan selectByPrimaryKey(String bgl001);

    int updateByPrimaryKeySelective(BusinessGuaranteeLoan businessGuaranteeLoan);

    int updateByPrimaryKey(BusinessGuaranteeLoan businessGuaranteeLoan);

    /**
     * 根据行政区划查询创业担保贷款列表
     * @return
     */
    public List<BusinessGuaranteeLoan> queryBusinessGuaranteeLoanByAab301(@Param("businessGuaranteeLoan")BusinessGuaranteeLoan businessGuaranteeLoan, @Param("startRecord")String startRecord, @Param("pageSize")String pageSize);

    /**
     * 根据行政区划查询总共多条
     * @return
     */
    public String queryAllBusinessGuaranteeLoanCountByAab301(@Param("businessGuaranteeLoan")BusinessGuaranteeLoan businessGuaranteeLoan);
}