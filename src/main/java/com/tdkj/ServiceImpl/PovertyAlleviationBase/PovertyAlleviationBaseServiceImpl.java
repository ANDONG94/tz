package com.tdkj.ServiceImpl.PovertyAlleviationBase;

import com.tdkj.dao.PovertyAlleviationBase.PovertyAlleviationBaseMapper;
import com.tdkj.model.PovertyAlleviationBase;
import com.tdkj.service.PovertyAlleviationBase.PovertyAlleviationBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by len on 2019-04-29.
 */
@Service
public class PovertyAlleviationBaseServiceImpl implements PovertyAlleviationBaseService{

    @Autowired
    private PovertyAlleviationBaseMapper povertyAlleviationBaseMapper;

    @Override
    public int deleteByPrimaryKey(String pab001) {

        return povertyAlleviationBaseMapper.deleteByPrimaryKey(pab001);
    }

    @Override
    public int insert(PovertyAlleviationBase record) {
        return 0;
    }

    @Override
    public int insertSelective(PovertyAlleviationBase record) {

        return povertyAlleviationBaseMapper.insertSelective(record);
    }

    @Override
    public PovertyAlleviationBase selectByPrimaryKey(String pab001) {
        return povertyAlleviationBaseMapper.selectByPrimaryKey(pab001);
    }

    @Override
    public int updateByPrimaryKeySelective(PovertyAlleviationBase record) {
        return povertyAlleviationBaseMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(PovertyAlleviationBase record) {
        return 0;
    }

    /**
     * 根据aab301 查询扶贫基地信息
     * @param aab301
     * @return
     */
    @Override
    public List<PovertyAlleviationBase> queryPovertyAlleviationBaseByAab301(PovertyAlleviationBase povertyAlleviationBase,String startRecord,String pageSize) {
        return povertyAlleviationBaseMapper.queryPovertyAlleviationBaseByAab301(povertyAlleviationBase,startRecord, pageSize);
    }


    /**
     * 根据行政区划查询 总条数
     * @param aab301
     * @return
     */
    @Override
    public String queryAllByAab301(PovertyAlleviationBase povertyAlleviationBase) {
        return povertyAlleviationBaseMapper.queryAllByAab301(povertyAlleviationBase);
    }


    /**
     * 根据aab301   查询  待完善 扶贫基地信息
     * @return
     */
    @Override
    public List<PovertyAlleviationBase> querytobeCompletePovertyAlleviationBaseByAab301(PovertyAlleviationBase povertyAlleviationBase, String startRecord, String pageSize) {
        return povertyAlleviationBaseMapper.querytobeCompletePovertyAlleviationBaseByAab301(povertyAlleviationBase,startRecord, pageSize);
    }


    /**
     * 根据行政区划查询 待完善 扶贫基地 总条数
     * @return
     */
    @Override
    public String queryAlltobeCompleteByAab301(PovertyAlleviationBase povertyAlleviationBase) {
        return povertyAlleviationBaseMapper.queryAlltobeCompleteByAab301(povertyAlleviationBase);
    }


}
