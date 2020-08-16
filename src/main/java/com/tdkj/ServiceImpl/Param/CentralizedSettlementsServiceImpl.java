package com.tdkj.ServiceImpl.Param;

import com.tdkj.dao.Param.CentralizedSettlementsMapper;
import com.tdkj.model.CentralizedSettlements;
import com.tdkj.service.Param.CentralizedSettlementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by len on 2019-05-16.
 */
@Service
public class CentralizedSettlementsServiceImpl implements CentralizedSettlementsService {

    @Autowired
    private CentralizedSettlementsMapper centralizedSettlementsMapper;

    @Override
    public int deleteByPrimaryKey(String cts001) {
        return centralizedSettlementsMapper.deleteByPrimaryKey(cts001);
    }

    @Override
    public int insert(CentralizedSettlements record) {
        return 0;
    }

    @Override
    public int insertSelective(CentralizedSettlements record) {
        return centralizedSettlementsMapper.insertSelective(record);
    }

    @Override
    public CentralizedSettlements selectByPrimaryKey(String cts001) {
        return centralizedSettlementsMapper.selectByPrimaryKey(cts001);
    }

    @Override
    public int updateByPrimaryKeySelective(CentralizedSettlements record) {
        return centralizedSettlementsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CentralizedSettlements record) {
        return 0;
    }

    @Override
    public List<CentralizedSettlements> queryCentralizedSettlementsByAab301(String aab301) {
        return centralizedSettlementsMapper.queryCentralizedSettlementsByAab301(aab301);
    }

    /**
     * 集中安置点查询  用于分页
     * @param aab301
     * @return
     */
    @Override
    public List<CentralizedSettlements> queryAllCentralizedSettlementByAab301(String aab301, String cts002_scan, String startRecord, String pageSize) {
        return centralizedSettlementsMapper.queryAllCentralizedSettlementByAab301(aab301,cts002_scan,startRecord,pageSize);
    }

    /**
     * 查询有多少个安置点
     * @param aab301
     * @param cts002_scan
     * @return
     */
    @Override
    public String queryAllByAab301(String aab301, String cts002_scan) {
        return centralizedSettlementsMapper.queryAllByAab301(aab301,cts002_scan);
    }

    /**
     * 查询该安置点是有已经被使用
     * @param cts001
     * @return
     */
    @Override
    public String queryCentralizedSettlementIsInPhd(String cts001) {
        return centralizedSettlementsMapper.queryCentralizedSettlementIsInPhd(cts001);
    }


}
