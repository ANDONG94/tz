package com.tdkj.service.Relation;

import com.tdkj.model.Relation;

import java.util.List;

public interface RelationService {
    int deleteByPrimaryKey(String rtn001);

    int insert(Relation record);

    int insertSelective(Relation record);

    List<Relation> selectByPrimaryKey(String companyid,String startRecord,String pageSize);

    Relation selectByPrimaryKeyRtn(String rtn001);

    int updateByPrimaryKeySelective(Relation record);

    int updateByPrimaryKey(Relation record);

    String queryAllByCompanyid(String companyid);

    int updateByCompanyid(String companyid);

    String queryAllByPlf001(String peopleid);

    //根据personid 获取对象  可能有多个只取最新的一条
    List<Relation> queryRelationPlf001(String peopleid);

}
