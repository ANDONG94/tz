package com.tdkj.ServiceImpl.Param;

import com.tdkj.dao.Param.Aa10Mapper;
import com.tdkj.model.Aa10;
import com.tdkj.service.Param.Aa10Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by len on 2019-04-28.
 */
@Service
public class Aa10ServiceImpl implements Aa10Service {

    @Autowired
    private Aa10Mapper aa10Mapper;

    @Override
    public int insert(Aa10 record) {
        return 0;
    }

    @Override
    public int insertSelective(Aa10 record) {
        return 0;
    }

    /**
     * 根据aaa100查询参数信息
     * @param aaa100
     * @return
     */
    @Override
    public List<Aa10> queryAa10ByAaa100(String aaa100) {
        return aa10Mapper.queryAa10ByAaa100(aaa100);
    }

    @Override
    public List<Aa10> queryAllAa10list() {
        return aa10Mapper.queryAllAa10list();
    }

    /**
     * 根据aaa100查询参数信息
     * @param aaa100
     * @return
     */

    @Override
    public Aa10 queryAa10ByAaa100Type(String aaa100,String value) {
        List<Aa10> aa10s=aa10Mapper.queryAa10ByAaa100Type(aaa100,value);
        if(aa10s.size()<1){
            return new Aa10();
        }
        return aa10s.get(0);
    }
}
