package com.tdkj.service.Subsidy;

import com.tdkj.model.EmploymentSituation;
import com.tdkj.model.EmploymentSubsidy;

import java.util.List;

/**
 * Created by hedd on 2019/8/25.
 * 就业补贴
 */
public interface EmploymentSubsidyService {

    int deleteByPrimaryKey(String ets001,String realname);

    int insert(EmploymentSubsidy record);

    int insertSelective(EmploymentSubsidy record);

    EmploymentSubsidy selectByPrimaryKey(String ets001);

    int updateByPrimaryKeySelective(EmploymentSubsidy record);

    int updateByPrimaryKey(EmploymentSubsidy record);

    /**
     * 根据劳动力id  查询该劳动力的就业补贴信息
     * @return
     */
    public List<EmploymentSubsidy> querEmploymentSubsidyByEts003(String ets003);


    /**
     *根据就业主键 删除就业补贴信息
     * @param ets002
     * @param realname
     * @return
     */
    public int deleteJiuYeSubsidyByEts002(String ets002,String realname);

    /**
     * 根据劳动力id  查询该劳动力的就业补贴信息
     * @return
     */
    public EmploymentSubsidy querEmploymentSubsidyCountByEts003(String ets003);

    /**
     * 根据就业id  查询该劳动力的就业补贴信息
     * @return
     */
    public EmploymentSubsidy querEmploymentSubsidyCountByEts002(String ets002);


}
