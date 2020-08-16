package com.tdkj.ServiceImpl.Ztree;

import com.tdkj.dao.Ztree.PoorXzqhMapper;
import com.tdkj.model.PoorXzqh;
import com.tdkj.service.Ztree.ZtreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by len on 2019-04-26.
 */
@Service
public class ZtreeServiceImpl implements ZtreeService{

    @Autowired
    private PoorXzqhMapper poorXzqhMapper;

    public List<PoorXzqh> getAllGroupList(String parentid,String first,String menutype) {
        return poorXzqhMapper.getAllGroupList(parentid,first,menutype);
    }

    @Override
    public List<PoorXzqh> queryPoorXzqh() {
        return poorXzqhMapper.queryPoorXzqh();
    }

    @Override
    public PoorXzqh queryPoorXzqhSub(String parentid) {
        return poorXzqhMapper.queryPoorXzqhSub(parentid);
    }

    /**
     * 查询所有市区街道
     * @return
     */
    @Override
    public List<PoorXzqh> queryArea(String xzqhbm) {
        return poorXzqhMapper.queryArea(xzqhbm);
    }


    /**
     * 根据id  查询所下级  包括村子
     * @param xzqhbm
     * @return
     */
    @Override
    public List<PoorXzqh> queryAreaByPid(String xzqhbm) {
        return poorXzqhMapper.queryAreaByPid(xzqhbm);
    }

    /**
     * 根据aab301查询编码info
     * @param aab301
     * @return
     */
    @Override
    public PoorXzqh queryAab301Info(String aab301) {
        return poorXzqhMapper.queryAab301Info(aab301);
    }

    @Override
    public PoorXzqh queryAab301Count(String aab301) {
        return poorXzqhMapper.queryAab301Count(aab301);
    }

    /**
     * 根据aab301  查询information 中的信息
     * @param poorXzqh
     * @return
     */
    @Override
    public List<PoorXzqh> queryInformationByAab301(PoorXzqh poorXzqh,String startRecord,String pageSize) {
        return poorXzqhMapper.queryInformationByAab301(poorXzqh,startRecord,pageSize);
    }

    @Override
    public String queryAllInformationByAab301(PoorXzqh poorXzqh) {
        return poorXzqhMapper.queryAllInformationByAab301(poorXzqh);
    }


}
