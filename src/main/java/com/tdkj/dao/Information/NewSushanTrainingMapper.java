package com.tdkj.dao.Information;

import com.tdkj.model.NewSushanTraining;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewSushanTrainingMapper {

    int deleteByPrimaryKey(@Param("nst001")String nst001,@Param("realname")String realname);

    int insert(NewSushanTraining newSushanTraining);

    int insertSelective(NewSushanTraining newSushanTraining);

    NewSushanTraining selectByPrimaryKey(String nst001);

    int updateByPrimaryKeySelective(NewSushanTraining newSushanTraining);

    int updateByPrimaryKey(NewSushanTraining newSushanTraining);

    /**
     * 根据行政区划查询苏陕协作培训列表
     * @return
     */
    public List<NewSushanTraining> queryNewSushanTrainingByAab301(@Param("newSushanTraining")NewSushanTraining newSushanTraining, @Param("startRecord")String startRecord, @Param("pageSize")String pageSize);

    /**
     * 根据行政区划查询总共多条
     * @return
     */
    public String queryAllNewSushanTrainingCountByAab301(@Param("newSushanTraining")NewSushanTraining newSushanTraining);
}