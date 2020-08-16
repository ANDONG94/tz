package com.tdkj.dao.Information;

import com.tdkj.model.PublicServiceWorkstation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PublicServiceWorkstationMapper {
    int deleteByPrimaryKey(String psw001);

    int insert(PublicServiceWorkstation record);

    int insertSelective(PublicServiceWorkstation record);

    PublicServiceWorkstation selectByPrimaryKey(String psw001);

    int updateByPrimaryKeySelective(PublicServiceWorkstation record);

    int updateByPrimaryKey(PublicServiceWorkstation record);

    /**
     * 根据行政区划益性劳务工作站信息列表
     * @param aab301
     * @return
     */
    public List<PublicServiceWorkstation> querPublicWorkByAab301(@Param("aab301")String aab301, @Param("psw002_scan")String psw002_scan, @Param("psw003_scan")String psw003_scan, @Param("startRecord")String startRecord, @Param("pageSize")String pageSize);


    /**
     * 根据行政区划查询总共多条
     * @param aab301
     * @return
     */
    public String  queryAllByAab301(@Param("aab301")String aab301);

}