package com.tdkj.ServiceImpl.Subsidy;

import com.tdkj.dao.VentureSubsidyMapper;
import com.tdkj.model.VentureSubsidy;
import com.tdkj.service.Subsidy.VentureSubsidyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hedd on 2019/8/25.
 * 创业补贴
 */
@Service
public class VentureSubsidyServiceImpl implements VentureSubsidyService {

    @Autowired
    private VentureSubsidyMapper ventureSubsidyMapper;

    @Override
    public int deleteByPrimaryKey(String ves001, String realname) {
        return ventureSubsidyMapper.deleteByPrimaryKey(ves001,realname);
    }

    @Override
    public int insert(VentureSubsidy ventureSubsidy) {
        return ventureSubsidyMapper.insert(ventureSubsidy);
    }

    @Override
    public int insertSelective(VentureSubsidy ventureSubsidy) {
        return ventureSubsidyMapper.insertSelective(ventureSubsidy);
    }

    @Override
    public VentureSubsidy selectByPrimaryKey(String ves001) {
        return ventureSubsidyMapper.selectByPrimaryKey(ves001);
    }

    @Override
    public int updateByPrimaryKeySelective(VentureSubsidy ventureSubsidy) {
        return ventureSubsidyMapper.updateByPrimaryKeySelective(ventureSubsidy);
    }

    @Override
    public int updateByPrimaryKey(VentureSubsidy ventureSubsidy) {
        return ventureSubsidyMapper.updateByPrimaryKey(ventureSubsidy);
    }

    /**
     * 根据劳动力id  查询该劳动力的创业补贴信息
     * @return
     */
    @Override
    public List<VentureSubsidy> queryChuangYeSubsidyByVes003(String ves003) {
        return ventureSubsidyMapper.queryChuangYeSubsidyByVes003(ves003);
    }

    /**
     *根据就业主键 删除创业补贴信息
     * @param ves002
     * @param realname
     * @return
     */
    @Override
    public int deleteChuangYeSubsidyByVes002(String ves002, String realname) {
        return ventureSubsidyMapper.deleteChuangYeSubsidyByVes002(ves002,realname);
    }

    @Override
    public VentureSubsidy queryChuangYeSubsidyCountByVes003(String ves003) {
        return ventureSubsidyMapper.queryChuangYeSubsidyCountByVes003(ves003);
    }

    @Override
    public VentureSubsidy queryChuangYeSubsidyCountByVes002(String ves002) {
        return ventureSubsidyMapper.queryChuangYeSubsidyCountByVes002(ves002);
    }
}
