package com.tdkj.service.Information;

import com.tdkj.model.LaborAlleviationCompany;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hedd on 2019/8/25.
 * 劳务扶贫公司
 */
public interface LaborAlleviationCompanyService {

    int deleteByPrimaryKey(String lac001,String realname);

    int insert(LaborAlleviationCompany laborAlleviationCompany);

    int insertSelective(LaborAlleviationCompany laborAlleviationCompany);

    LaborAlleviationCompany selectByPrimaryKey(String lac001);

    int updateByPrimaryKeySelective(LaborAlleviationCompany laborAlleviationCompany);

    int updateByPrimaryKey(LaborAlleviationCompany laborAlleviationCompany);


    /**
     * 根据行政区划查询劳务扶贫公司列表
     * @return
     */
    public List<LaborAlleviationCompany> queryLaborAlleviationCompanyByAab301(LaborAlleviationCompany laborAlleviationCompany,String startRecord,String pageSize);

    /**
     * 根据行政区划查询总共多条
     * @return
     */
    public String queryAllByAab301(LaborAlleviationCompany laborAlleviationCompany);





}
