package com.tdkj.service.Subsidy;

import com.tdkj.model.EmploymentSubsidy;
import com.tdkj.model.VentureSubsidy;

import java.util.List;

/**
 * Created by hedd on 2019/8/25.
 */
public interface VentureSubsidyService {
    int deleteByPrimaryKey(String ves001,String realname);

    int insert(VentureSubsidy ventureSubsidy);

    int insertSelective(VentureSubsidy ventureSubsidy);

    VentureSubsidy selectByPrimaryKey(String ves001);

    int updateByPrimaryKeySelective(VentureSubsidy ventureSubsidy);

    int updateByPrimaryKey(VentureSubsidy ventureSubsidy);

    /**
     * 根据劳动力id  查询该劳动力的创业补贴信息
     * @return
     */
    public List<VentureSubsidy> queryChuangYeSubsidyByVes003(String ets003);


    /**
     *根据就业主键 删除创业补贴信息
     * @param ves002
     * @param realname
     * @return
     */
    public int deleteChuangYeSubsidyByVes002(String ves002,String realname);

    /**
     * 根据劳动力id  查询该劳动力的创业补贴信息
     * @return
     */
    public VentureSubsidy queryChuangYeSubsidyCountByVes003(String ves003);


    /**
     * 根据创业id  查询该劳动力的创业补贴信息
     * @return
     */
    public VentureSubsidy queryChuangYeSubsidyCountByVes002(String ves002);


}
