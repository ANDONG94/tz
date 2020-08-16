package com.tdkj.service.Information;

import com.tdkj.model.LaborBrokering;

import java.util.List;

/**
 * Created by len on 2019-05-13.
 */
public interface LaborBrokeringService {
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
    public List<LaborBrokering> querLaowuPersonByAab301(LaborBrokering laborBrokering, String startRecord, String pageSize);

    /**
     * 根据行政区划查询总共多条
     * @return
     */
    public String queryAllByAab301(LaborBrokering laborBrokering);
}
