package com.tdkj.ServiceImpl.InformationCollection;

import com.tdkj.dao.InformationCollection.EntrepreneurshipMapper;
import com.tdkj.model.Entrepreneurship;
import com.tdkj.model.EppPlf;
import com.tdkj.service.InformationCollection.EntrepreneurshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by len on 2019-04-29.
 */
@Service
public class EntrepreneurshipServiceImpl implements EntrepreneurshipService {

    @Autowired
    private EntrepreneurshipMapper entrepreneurshipMapper;

    @Override
    public int deleteByPrimaryKey(String epp001) {
        return entrepreneurshipMapper.deleteByPrimaryKey(epp001);
    }

    @Override
    public int insert(Entrepreneurship record) {
        return 0;
    }

    @Override
    public int insertSelective(Entrepreneurship entrepreneurship) {
        return entrepreneurshipMapper.insertSelective(entrepreneurship);
    }

    @Override
    public Entrepreneurship selectByPrimaryKey(String epp001) {
        return entrepreneurshipMapper.selectByPrimaryKey(epp001);
    }

    @Override
    public int updateByPrimaryKeySelective(Entrepreneurship entrepreneurship) {
        return entrepreneurshipMapper.updateByPrimaryKeySelective(entrepreneurship);
    }

    @Override
    public int updateByPrimaryKey(Entrepreneurship entrepreneurship) {
        return 0;
    }

    /**
     * 根据劳动力主键查询创业信息
     * @param epp002
     * @return
     */
    @Override
    public List<Entrepreneurship> queryEntrepreneurshipByEpp002(String epp002) {
        return entrepreneurshipMapper.queryEntrepreneurshipByEpp002(epp002);
    }

    /**
     * 根据劳动力主键删除创业信息
     * @param epp002
     * @return
     */
    @Override
    public int deleteEntrepreneurshipByEpp002(String epp002,String realname) {
        return entrepreneurshipMapper.deleteEntrepreneurshipByEpp002(epp002,realname);
    }


    /**
     * 根据行政区划查询劳动力创业台账信息
     * @param aab301
     * @return
     */
    @Override
    public List<EppPlf> queryEmploymentShipAccountByAab301(EppPlf eppPlf, String startRecord, String pageSize) {
        return entrepreneurshipMapper.queryEmploymentShipAccountByAab301(eppPlf,startRecord,pageSize);
    }


    /**
     *根据行政区划查询劳动力创业台账总共多条
     * @return
     */
    @Override
    public String queryAllEmploymentShipByAab301(EppPlf eppPlf) {
        return entrepreneurshipMapper.queryAllEmploymentShipByAab301(eppPlf);
    }

    @Override
    public int updateIsNewByEpp002(String epp002) {
        return entrepreneurshipMapper.updateIsNewByEpp002(epp002);
    }

    //根据劳动力id 查询最新的创业信息
    @Override
    public Entrepreneurship queryNewEntrepreneurship(String epp002) {
        return entrepreneurshipMapper.queryNewEntrepreneurship(epp002);
    }

    @Override
    public int f_jy_cy(String plf001) {
        return entrepreneurshipMapper.f_jy_cy(plf001);
    }
}
