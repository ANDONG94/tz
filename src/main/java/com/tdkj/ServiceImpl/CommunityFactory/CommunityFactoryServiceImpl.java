package com.tdkj.ServiceImpl.CommunityFactory;

import com.tdkj.dao.CommunityFactory.CommunityFactoryMapper;
import com.tdkj.model.CommunityFactory;
import com.tdkj.service.CommunityFactory.CommunityFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by len on 2019-04-29.
 */
@Service
public class CommunityFactoryServiceImpl implements CommunityFactoryService {

    @Autowired
    private CommunityFactoryMapper communityFactoryMapper;

    @Override
    public int deleteByPrimaryKey(String ctf001) {
        return communityFactoryMapper.deleteByPrimaryKey(ctf001);
    }

    @Override
    public int insert(CommunityFactory record) {
        return 0;
    }

    @Override
    public int insertSelective(CommunityFactory communityFactory) {
        return communityFactoryMapper.insertSelective(communityFactory);
    }

    @Override
    public CommunityFactory selectByPrimaryKey(String ctf001) {
        return communityFactoryMapper.selectByPrimaryKey(ctf001);
    }

    @Override
    public int updateByPrimaryKeySelective(CommunityFactory communityFactory) {
        return communityFactoryMapper.updateByPrimaryKeySelective(communityFactory);
    }

    @Override
    public int updateByPrimaryKey(CommunityFactory communityFactory) {
        return 0;
    }


    /**
     * 根据行政区划查询社区工厂信息
     * @return
     */
    @Override
    public List<CommunityFactory> queryCommunityFactoryByAab301(CommunityFactory communityFactory,String startRecord,String pageSize){
        return communityFactoryMapper.queryCommunityFactoryByAab301(communityFactory,startRecord,pageSize);
    }

    /**
     * 根据行政区划查询有多少条数据
     * @return
     */
    @Override
    public String queryAllByAab301(CommunityFactory communityFactory) {
        return communityFactoryMapper.queryAllByAab301(communityFactory);
    }


    //**********************************社区工厂待完善  信息查询******************************************************************************************


    /**
     * 根据行政区划查询   待完善的社区工厂信息
     * @return
     */
    @Override
    public List<CommunityFactory> querytobeCompleteFactoryByAab301(CommunityFactory communityFactory, String startRecord, String pageSize) {
        return communityFactoryMapper.querytobeCompleteFactoryByAab301(communityFactory,startRecord,pageSize);
    }


    /**
     * 根据行政区划查询  待完善的社区工厂  有多少条数据
     * @return
     */
    @Override
    public String queryAlltobeCompleteFactoryByAab301(CommunityFactory communityFactory) {
        return communityFactoryMapper.queryAlltobeCompleteFactoryByAab301(communityFactory);
    }
}
