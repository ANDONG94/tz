package com.tdkj.dao.Relation;

import com.tdkj.model.Relation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RelationMapper {
    int deleteByPrimaryKey(String rtn001);

    int insert(Relation record);

    int insertSelective(Relation record);

    List<Relation> selectCompanyid(@Param("companyid") String companyid,@Param("startRecord")String startRecord,@Param("pageSize")String pageSize);

    Relation selectByPrimaryKeyRtn(@Param("rtn001")String rtn001);

    int updateByPrimaryKeySelective(Relation record);

    int updateByPrimaryKey(Relation record);

    String queryAllByCompanyid(@Param("companyid")String companyid);

    int updateByCompanyid(@Param("companyid")String companyid);

    String queryAllByPlf001(@Param("peopleid")String peopleid);

    List<Relation> queryRelationPlf001(@Param("peopleid")String peopleid);
}