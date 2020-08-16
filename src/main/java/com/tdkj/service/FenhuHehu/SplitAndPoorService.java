package com.tdkj.service.FenhuHehu;

import com.tdkj.model.PoorLaborForce;
import com.tdkj.model.SplitAndPoor;

import java.util.List;

/**
 * Created by hedd on 2019/10/9.
 */
public interface SplitAndPoorService {

    int deleteByPrimaryKey(String sap001);

    int insert(SplitAndPoor splitAndPoor);

    int insertSelective(SplitAndPoor splitAndPoor);

    SplitAndPoor selectByPrimaryKey(String sap001);

    int updateByPrimaryKeySelective(SplitAndPoor splitAndPoor);

    int updateByPrimaryKey(SplitAndPoor splitAndPoor);

    /**
     * 根据行政区划查询  分户合户记录  信息
     * @return
     */
    List<SplitAndPoor> querySplitAndPoorByAab301(SplitAndPoor splitAndPoor, String startRecord, String pageSize);



    /**
     *根据行政区划查询分户合户记录 总共多条
     * @return
     */
    String queryAllSplitAndPoorByAab301(SplitAndPoor splitAndPoor);


}
