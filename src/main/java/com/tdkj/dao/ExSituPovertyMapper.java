package com.tdkj.dao;

import com.tdkj.model.ExSituPoverty;
import com.tdkj.model.JobInformationBase;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExSituPovertyMapper {

    int deleteByPrimaryKey(@Param("esp001")String esp001,@Param("realname")String realname);

    int insert(ExSituPoverty exSituPoverty);

    int insertSelective(ExSituPoverty exSituPoverty);

    ExSituPoverty selectByPrimaryKey(String esp001);

    int updateByPrimaryKeySelective(ExSituPoverty exSituPoverty);

    int updateByPrimaryKey(ExSituPoverty exSituPoverty);

    /**
     * 根据行政区划查询创业服务站列表
     * @return
     */
    public List<ExSituPoverty> queryExSituPovertyByAab301(@Param("exSituPoverty")ExSituPoverty exSituPoverty, @Param("startRecord")String startRecord, @Param("pageSize")String pageSize);

    /**
     * 根据行政区划查询总共多条
     * @return
     */
    public String queryAllByAab301(@Param("exSituPoverty")ExSituPoverty exSituPoverty);


}