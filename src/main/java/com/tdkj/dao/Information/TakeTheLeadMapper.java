package com.tdkj.dao.Information;

import com.tdkj.model.TakeTheLead;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TakeTheLeadMapper {
    int deleteByPrimaryKey(String thl001);

    int insert(TakeTheLead takeTheLead);

    int insertSelective(TakeTheLead takeTheLead);

    TakeTheLead selectByPrimaryKey(String thl001);

    int updateByPrimaryKeySelective(TakeTheLead takeTheLead);

    int updateByPrimaryKey(TakeTheLead takeTheLead);

    /**
     * 根据行政区划查询创业带头人信息列表
     * @return
     */
    public List<TakeTheLead> queryChuangyeLeaderByAab301(@Param("takeTheLead")TakeTheLead takeTheLead, @Param("startRecord")String startRecord, @Param("pageSize")String pageSize);


    /**
     * 根据行政区划查询总共多条
     * @return
     */
    public String  queryAllByAab301(@Param("takeTheLead")TakeTheLead takeTheLead);
}