package com.tdkj.ServiceImpl.Information;

import com.tdkj.dao.Information.PublicServiceWorkstationMapper;
import com.tdkj.model.PublicServiceWorkstation;
import com.tdkj.service.Information.PublicServiceWorkstationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by len on 2019-05-13.
 */
@Service
public class PublicServiceWorkstationServiceImpl implements PublicServiceWorkstationService {

   @Autowired
   private PublicServiceWorkstationMapper publicServiceWorkstationMapper;

    @Override
    public int deleteByPrimaryKey(String psw001) {
        return publicServiceWorkstationMapper.deleteByPrimaryKey(psw001);
    }

    @Override
    public int insert(PublicServiceWorkstation record) {
        return 0;
    }

    @Override
    public int insertSelective(PublicServiceWorkstation record) {
        return publicServiceWorkstationMapper.insertSelective(record);
    }

    @Override
    public PublicServiceWorkstation selectByPrimaryKey(String psw001) {
        return publicServiceWorkstationMapper.selectByPrimaryKey(psw001);
    }

    @Override
    public int updateByPrimaryKeySelective(PublicServiceWorkstation record) {
        return publicServiceWorkstationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PublicServiceWorkstation record) {
        return 0;
    }


    /**
     * 根据行政区划益性劳务工作站信息列表
     * @param aab301
     * @return
     */
    @Override
    public List<PublicServiceWorkstation> querPublicWorkByAab301(String aab301, String psw002_scan, String psw003_scan, String startRecord, String pageSize) {
        return publicServiceWorkstationMapper.querPublicWorkByAab301(aab301,psw002_scan,psw003_scan,startRecord,pageSize);
    }

    @Override
    public String queryAllByAab301(String aab301) {
        return publicServiceWorkstationMapper.queryAllByAab301(aab301);
    }
}
