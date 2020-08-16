package com.tdkj.service.Subsidy;

import com.tdkj.model.TechnicalSchoolSubsidy;
import com.tdkj.model.TrainingSubsidy;

import java.util.List;

/**
 * Created by hedd on 2019/8/25.
 */
public interface TechnicalSchoolSubsidyService {

    int deleteByPrimaryKey(String tss001,String realname);

    int insert(TechnicalSchoolSubsidy technicalSchoolSubsidy);

    int insertSelective(TechnicalSchoolSubsidy technicalSchoolSubsidy);

    TechnicalSchoolSubsidy selectByPrimaryKey(String tss001);

    int updateByPrimaryKeySelective(TechnicalSchoolSubsidy technicalSchoolSubsidy);

    int updateByPrimaryKey(TechnicalSchoolSubsidy technicalSchoolSubsidy);


    /**
     * 根据劳动力id  查询该劳动力的技校补贴信息
     * @return
     */
    public List<TechnicalSchoolSubsidy> querySchoolSubsidyByTss003(String tss003);


    /**
     *根据就业主键 删除技校补贴信息
     * @param tss002
     * @param realname
     * @return
     */
    public int deleteSchoolSubsidyByTss002(String tss002,String realname);

    /**
     * 根据劳动力id  查询该劳动力的技校补贴信息
     * @return
     */
    public TechnicalSchoolSubsidy querySchoolSubsidyCountByTss003(String tss003);

    /**
     * 根据技校id  查询该劳动力的技校补贴信息
     * @return
     */
    public TechnicalSchoolSubsidy querySchoolSubsidyCountByTss002(String tss002);


}
