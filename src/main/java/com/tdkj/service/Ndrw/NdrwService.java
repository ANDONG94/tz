package com.tdkj.service.Ndrw;

import com.tdkj.model.CreditVillage;
import com.tdkj.model.Ndrw;

import java.util.List;

/**
 * Created by hedd on 2019/8/16.
 */
public interface NdrwService {
    int deleteByPrimaryKey(String ndrwid);

    int insert(Ndrw record);

    int insertSelective(Ndrw record);

    Ndrw selectByPrimaryKey(String ndrwid);

    int updateByPrimaryKeySelective(Ndrw record);

    int updateByPrimaryKey(Ndrw record);

    /**
     * 根据aab301 查询年度任务信息
     * @return
     */
    public List<Ndrw> queryNdrwByAab301(Ndrw ndrw, String startRecord, String pageSize) ;


    /**
     * 根据行政区划查询总条数
     * @param aab301
     * @return
     */
    public String queryAllCountByAab301(Ndrw ndrw);

    //根据年度检查 是否年度任务已经存在
    public List<Ndrw> checkNdrwByAab301(Ndrw ndrw) ;

}
