package com.tdkj.service.Information;

import com.tdkj.model.ExSituPoverty;

import java.util.List;

/**
 * Created by hedd on 2019/8/25.
 */
public interface ExSituPovertyService {

    int deleteByPrimaryKey(String esp001,String realname);

    int insert(ExSituPoverty exSituPoverty);

    int insertSelective(ExSituPoverty exSituPoverty);

    ExSituPoverty selectByPrimaryKey(String esp001);

    int updateByPrimaryKeySelective(ExSituPoverty exSituPoverty);

    int updateByPrimaryKey(ExSituPoverty exSituPoverty);

    /**
     * 根据行政区划查询创业服务站列表
     * @return
     */
    public List<ExSituPoverty> queryExSituPovertyByAab301(ExSituPoverty exSituPoverty, String startRecord, String pageSize);

    /**
     * 根据行政区划查询总共多条
     * @return
     */
    public String queryAllByAab301(ExSituPoverty exSituPoverty);
}
