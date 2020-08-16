package com.tdkj.service.Information;

import com.tdkj.model.PublicServiceWorkstation;

import java.util.List;

/**
 * Created by len on 2019-05-13.
 */
public interface PublicServiceWorkstationService {

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
    public List<PublicServiceWorkstation> querPublicWorkByAab301(String aab301, String psw002_scan, String psw003_scan, String startRecord, String pageSize);


    /**
     * 根据行政区划查询总共多条
     * @param aab301
     * @return
     */
    public String  queryAllByAab301(String aab301);
}
