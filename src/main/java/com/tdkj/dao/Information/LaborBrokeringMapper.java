package com.tdkj.dao.Information;

import com.tdkj.model.LaborBrokering;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LaborBrokeringMapper {
    int deleteByPrimaryKey(String lbk001);

    int insert(LaborBrokering laborBrokering);

    int insertSelective(LaborBrokering laborBrokering);

    LaborBrokering selectByPrimaryKey(String lbk001);

    int updateByPrimaryKeySelective(LaborBrokering laborBrokering);

    int updateByPrimaryKey(LaborBrokering laborBrokering);

    /**
     * 根据行政区划查询劳务经济人列表
     * @return
     */
    public List<LaborBrokering> querLaowuPersonByAab301(@Param("laborBrokering")LaborBrokering laborBrokering, @Param("startRecord")String startRecord, @Param("pageSize")String pageSize);

    /**
     * 根据行政区划查询总共多条
     * @return
     */
    public String queryAllByAab301(@Param("laborBrokering")LaborBrokering laborBrokering);
}