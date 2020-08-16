package com.tdkj.service.Information;

import com.tdkj.model.NewSushanTraining;

import java.util.List;

/**
 * Created by hedd on 2019/11/22.
 */
public interface NewSushanTrainingService {

    int deleteByPrimaryKey(String nst001,String realname);

    int insert(NewSushanTraining newSushanTraining);

    int insertSelective(NewSushanTraining newSushanTraining);

    NewSushanTraining selectByPrimaryKey(String nst001);

    int updateByPrimaryKeySelective(NewSushanTraining newSushanTraining);

    int updateByPrimaryKey(NewSushanTraining newSushanTraining);

    /**
     * 根据行政区划查询苏陕协作培训列表
     * @return
     */
    public List<NewSushanTraining> queryNewSushanTrainingByAab301(NewSushanTraining newSushanTraining, String startRecord, String pageSize);

    /**
     * 根据行政区划查询总共多条
     * @return
     */
    public String queryAllNewSushanTrainingCountByAab301(NewSushanTraining newSushanTraining);
}
