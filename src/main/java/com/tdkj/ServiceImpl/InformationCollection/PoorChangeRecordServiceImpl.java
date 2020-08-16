package com.tdkj.ServiceImpl.InformationCollection;

import com.tdkj.dao.InformationCollection.PoorChangeRecordMapper;
import com.tdkj.model.PoorChangeRecord;
import com.tdkj.service.InformationCollection.PoorChangeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hedd on 2019/7/6.
 * 户主信息变更
 */
@Service
public class PoorChangeRecordServiceImpl implements PoorChangeRecordService {

    @Autowired
   private PoorChangeRecordMapper poorChangeRecordMapper;

    @Override
    public int deleteByPrimaryKey(String pcr001) {
        return poorChangeRecordMapper.deleteByPrimaryKey(pcr001);
    }

    @Override
    public int insert(PoorChangeRecord record) {
        return poorChangeRecordMapper.insert(record);
    }

    @Override
    public int insertSelective(PoorChangeRecord record) {
        return poorChangeRecordMapper.insertSelective(record);
    }

    @Override
    public PoorChangeRecord selectByPrimaryKey(String pcr001) {
        return poorChangeRecordMapper.selectByPrimaryKey(pcr001);
    }

    @Override
    public int updateByPrimaryKeySelective(PoorChangeRecord record) {
        return poorChangeRecordMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PoorChangeRecord record) {
        return poorChangeRecordMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<PoorChangeRecord> queryPoorChangeByPcr002(String pcr002) {
        return poorChangeRecordMapper.queryPoorChangeByPcr002(pcr002);
    }


}
