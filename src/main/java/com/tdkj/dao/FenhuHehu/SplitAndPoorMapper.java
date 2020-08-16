package com.tdkj.dao.FenhuHehu;

import com.tdkj.model.PoorLaborForce;
import com.tdkj.model.SplitAndPoor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SplitAndPoorMapper {
    int deleteByPrimaryKey(String sap001);

    int insert(SplitAndPoor splitAndPoor);

    int insertSelective(SplitAndPoor splitAndPoor);

    SplitAndPoor selectByPrimaryKey(String sap001);

    int updateByPrimaryKeySelective(SplitAndPoor splitAndPoor);

    int updateByPrimaryKey(SplitAndPoor splitAndPoor);

    /**
     * 根据行政区划查询  分户合户记录  信息
     * @return
     */
    List<SplitAndPoor> querySplitAndPoorByAab301(@Param("splitAndPoor")SplitAndPoor splitAndPoor, @Param("startRecord")String startRecord, @Param("pageSize")String pageSize);



    /**
     *根据行政区划查询分户合户记录 总共多条
     * @return
     */
    String queryAllSplitAndPoorByAab301(@Param("splitAndPoor")SplitAndPoor splitAndPoor);
}