package com.tdkj.dao.ndrw;

import com.tdkj.model.Ndrw;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NdrwMapper {
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
    public List<Ndrw> queryNdrwByAab301(@Param("ndrw")Ndrw ndrw, @Param("startRecord")String startRecord, @Param("pageSize")String pageSize) ;


    /**
     * 根据行政区划查询总条数
     * @param aab301
     * @return
     */
    public String queryAllCountByAab301(@Param("ndrw")Ndrw ndrw);


    public List<Ndrw> checkNdrwByAab301(@Param("ndrw")Ndrw ndrw);


}