package com.tdkj.service.Subsidy;

import com.tdkj.model.TrainingSubsidy;
import com.tdkj.model.VentureSubsidy;

import java.util.List;

/**
 * Created by hedd on 2019/8/25.
 */
public interface TrainingSubsidyService {

    int deleteByPrimaryKey(String tgs001,String realname);

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
    public int deletePeiXunSubsidyByTgs002(String tgs002,String realname);

    /**
     * 根据劳动力id  查询该劳动力的培训补贴信息
     * @return
     */
    public TrainingSubsidy queryPeiXunSubsidyCountByTgs003(String tgs003);

    /**
     * 根据培训id  查询该劳动力的培训补贴信息
     * @return
     */
    public TrainingSubsidy queryPeiXunSubsidyCountByTgs002(String tgs002);




}
