package com.tdkj.service.Ztree;

import com.tdkj.model.PoorXzqh;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by len on 2019-04-26.
 */
public interface ZtreeService {

    /**
     * 查询树菜单
     * @param parentid
     * @return
     */
    public List<PoorXzqh> getAllGroupList(String parentid,String first,String menutype);


    public List<PoorXzqh> queryPoorXzqh();

    /**
     * 查询级别
     * @param parentid
     * @return
     */
    public PoorXzqh queryPoorXzqhSub(String parentid);

    /**
     * 查询市区街道
     * @return
     */
    public List<PoorXzqh> queryArea(String xzqhbm);


    /**
     * 根据id  查询所下级  包括村子
     * @param xzqhbm
     * @return
     */
    public List<PoorXzqh> queryAreaByPid(String xzqhbm);


    /**
     * 根据aab301查询编码info
     * @param aab301
     * @return
     */
    public PoorXzqh queryAab301Info(String aab301);


    PoorXzqh queryAab301Count(String aab301);


    /**
     * 根据aab301  查询information 中的信息
     * @param poorXzqh
     * @return
     */
    public List<PoorXzqh> queryInformationByAab301(PoorXzqh poorXzqh,String startRecord,String pageSize);

    /**
     * 查询information 的个数
     * @param poorXzqh
     * @return
     */
    public String queryAllInformationByAab301(PoorXzqh poorXzqh);


}
