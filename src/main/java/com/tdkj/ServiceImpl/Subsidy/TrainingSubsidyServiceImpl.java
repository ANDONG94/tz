package com.tdkj.ServiceImpl.Subsidy;

import com.tdkj.dao.TrainingSubsidyMapper;
import com.tdkj.model.TrainingSubsidy;
import com.tdkj.service.Subsidy.TrainingSubsidyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hedd on 2019/8/25.
 * 培训补贴
 */
@Service
public class TrainingSubsidyServiceImpl implements TrainingSubsidyService {

    @Autowired
    private TrainingSubsidyMapper trainingSubsidyMapper;

    @Override
    public int deleteByPrimaryKey(String tgs001,String realname) {
        return trainingSubsidyMapper.deleteByPrimaryKey(tgs001,realname);
    }

    @Override
    public int insert(TrainingSubsidy trainingSubsidy) {
        return trainingSubsidyMapper.insert(trainingSubsidy);
    }

    @Override
    public int insertSelective(TrainingSubsidy trainingSubsidy) {
        return trainingSubsidyMapper.insertSelective(trainingSubsidy);
    }

    @Override
    public TrainingSubsidy selectByPrimaryKey(String tgs001) {
        return trainingSubsidyMapper.selectByPrimaryKey(tgs001);
    }

    @Override
    public int updateByPrimaryKeySelective(TrainingSubsidy trainingSubsidy) {
        return trainingSubsidyMapper.updateByPrimaryKeySelective(trainingSubsidy);
    }

    @Override
    public int updateByPrimaryKey(TrainingSubsidy trainingSubsidy) {
        return trainingSubsidyMapper.updateByPrimaryKey(trainingSubsidy);
    }

    /**
     * 根据劳动力id  查询该劳动力的培训补贴信息
     * @return
     */
    @Override
    public List<TrainingSubsidy> queryPeiXunSubsidyByTgs003(String tgs003) {
        return trainingSubsidyMapper.queryPeiXunSubsidyByTgs003(tgs003);
    }

    /**
     *根据就业主键 删除培训补贴信息
     * @param tgs002
     * @param realname
     * @return
     */
    @Override
    public int deletePeiXunSubsidyByTgs002(String tgs002, String realname) {
        return trainingSubsidyMapper.deletePeiXunSubsidyByTgs002(tgs002,realname);
    }

    @Override
    public TrainingSubsidy queryPeiXunSubsidyCountByTgs003(String tgs003) {
        return trainingSubsidyMapper.queryPeiXunSubsidyCountByTgs003(tgs003);
    }

    @Override
    public TrainingSubsidy queryPeiXunSubsidyCountByTgs002(String tgs002) {
        return trainingSubsidyMapper.queryPeiXunSubsidyCountByTgs002(tgs002);
    }
}
