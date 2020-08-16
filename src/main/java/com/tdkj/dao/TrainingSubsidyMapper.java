package com.tdkj.dao;

import com.tdkj.model.TrainingSubsidy;
import com.tdkj.model.VentureSubsidy;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TrainingSubsidyMapper {

    int deleteByPrimaryKey(@Param("tgs001")String tgs001, @Param("realname")String realname);

    int insert(TrainingSubsidy trainingSubsidy);

    int insertSelective(TrainingSubsidy trainingSubsidy);

    TrainingSubsidy selectByPrimaryKey(String tgs001);

    int updateByPrimaryKeySelective(TrainingSubsidy trainingSubsidy);

    int updateByPrimaryKey(TrainingSubsidy trainingSubsidy);

    /**
     * 根据劳动力id  查询该劳动力的培训补贴信息
     * @return
     */
    public List<TrainingSubsidy> queryPeiXunSubsidyByTgs003(String tgs003);


    /**
     *根据就业主键 删除培训补贴信息
     * @param tgs002
     * @param realname
     * @return
     */
    public int deletePeiXunSubsidyByTgs002(@Param("tgs002")String tgs002 ,@Param("realname")String realname);


    /**
     * 根据劳动力id  查询该劳动力的培训补贴信息
     * @return
     */
    public TrainingSubsidy queryPeiXunSubsidyCountByTgs003(String tgs003);

    /**
     * 根据劳动力id  查询该培训id的培训补贴信息
     * @return
     */
    public TrainingSubsidy queryPeiXunSubsidyCountByTgs002(String tgs002);


}