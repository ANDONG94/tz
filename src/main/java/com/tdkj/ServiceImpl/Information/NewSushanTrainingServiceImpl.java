package com.tdkj.ServiceImpl.Information;

import com.tdkj.dao.Information.NewSushanTrainingMapper;
import com.tdkj.model.NewSushanTraining;
import com.tdkj.service.Information.NewSushanTrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hedd on 2019/11/22.
 * 新苏陕协作培训
 */
@Service
public class NewSushanTrainingServiceImpl implements NewSushanTrainingService {

    @Autowired
    private NewSushanTrainingMapper newSushanTrainingMapper;

    @Override
    public int deleteByPrimaryKey(String nst001,String realname) {
        return newSushanTrainingMapper.deleteByPrimaryKey(nst001,realname);
    }

    @Override
    public int insert(NewSushanTraining newSushanTraining) {
        return newSushanTrainingMapper.insert(newSushanTraining);
    }

    @Override
    public int insertSelective(NewSushanTraining newSushanTraining) {
        return newSushanTrainingMapper.insertSelective(newSushanTraining);
    }

    @Override
    public NewSushanTraining selectByPrimaryKey(String nst001) {
        return newSushanTrainingMapper.selectByPrimaryKey(nst001);
    }

    @Override
    public int updateByPrimaryKeySelective(NewSushanTraining newSushanTraining) {
        return newSushanTrainingMapper.updateByPrimaryKeySelective(newSushanTraining);
    }

    @Override
    public int updateByPrimaryKey(NewSushanTraining newSushanTraining) {
        return newSushanTrainingMapper.updateByPrimaryKey(newSushanTraining);
    }

    @Override
    public List<NewSushanTraining> queryNewSushanTrainingByAab301(NewSushanTraining newSushanTraining, String startRecord, String pageSize) {
        return newSushanTrainingMapper.queryNewSushanTrainingByAab301(newSushanTraining,startRecord,pageSize);
    }

    @Override
    public String queryAllNewSushanTrainingCountByAab301(NewSushanTraining newSushanTraining) {
        return newSushanTrainingMapper.queryAllNewSushanTrainingCountByAab301(newSushanTraining);
    }
}
