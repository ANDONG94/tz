package com.tdkj.ServiceImpl.InformationCollection;

import com.tdkj.dao.InformationCollection.TrainingSituationMapper;
import com.tdkj.model.TrainingSituation;
import com.tdkj.model.TsnPlf;
import com.tdkj.service.InformationCollection.TrainingSituationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by len on 2019-04-29.
 */
@Service
public class TrainingSituationServiceImpl implements TrainingSituationService {

    @Autowired
    private TrainingSituationMapper trainingSituationMapper;

    @Override
    public int deleteByPrimaryKey(String tsn001) {
        return trainingSituationMapper.deleteByPrimaryKey(tsn001);
    }

    @Override
    public int insert(TrainingSituation trainingSituation) {
        return 0;
    }

    @Override
    public int insertSelective(TrainingSituation trainingSituation) {
        return trainingSituationMapper.insertSelective(trainingSituation);
    }

    @Override
    public TrainingSituation selectByPrimaryKey(String tsn001) {
        return trainingSituationMapper.selectByPrimaryKey(tsn001);
    }

    @Override
    public int updateByPrimaryKeySelective(TrainingSituation trainingSituation) {
        return trainingSituationMapper.updateByPrimaryKeySelective(trainingSituation);
    }

    @Override
    public int updateByPrimaryKey(TrainingSituation record) {
        return 0;
    }

    /**
     * 根据劳动力主键查询培训信息
     * @param tsn010
     * @return
     */
    @Override
    public List<TrainingSituation> queryTrainingSituationByTsn010(String tsn010) {
        return trainingSituationMapper.queryTrainingSituationByTsn010(tsn010);
    }

    /**
     * 据劳动力主键删除培训信息
     * @param tsn010
     * @return
     */
    @Override
    public int deleteTrainingSituationByTsn010(String tsn010,String realname) {
        return trainingSituationMapper.deleteTrainingSituationByTsn010(tsn010,realname);
    }

    @Override
    public List<TsnPlf> queryTrainSituationAccountByAab301(TsnPlf tsnPlf, String startRecord, String pageSize) {
        return trainingSituationMapper.queryTrainSituationAccountByAab301(tsnPlf,startRecord,pageSize);
    }

    @Override
    public String queryAllTrainSituationByAab301(TsnPlf tsnPlf) {
        return trainingSituationMapper.queryAllTrainSituationByAab301(tsnPlf);
    }

    /**
     * 根据劳动力Id 修改isnew=0
     * @param tsn010
     * @return
     */
    @Override
    public int updateIsNewByTsn010(String tsn010) {
        return trainingSituationMapper.updateIsNewByTsn010(tsn010);
    }

    //根据劳动力id 查询最新的培训信息
    @Override
    public TrainingSituation queryNewTrainSituation(String tsn002) {
        return trainingSituationMapper.queryNewTrainSituation(tsn002);
    }
}
