package com.tdkj.dao.CreditVillage;

import com.tdkj.model.CreditVillageSubsiby;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditVillageSubsibyMapper {

    int deleteByPrimaryKey(String cvs001);

    int insert(CreditVillageSubsiby creditVillageSubsiby);

    int insertSelective(CreditVillageSubsiby creditVillageSubsiby);

    CreditVillageSubsiby selectByPrimaryKey(String cvs001);

    int updateByPrimaryKeySelective(CreditVillageSubsiby creditVillageSubsiby);

    int updateByPrimaryKey(CreditVillageSubsiby creditVillageSubsiby);

    List<CreditVillageSubsiby> querCreditVillageSubsibyByCvs002(@Param("cvs002")String cvs002);

    /**
     * 信用乡村id  查询所有补贴之和
     * @return
     */
    public CreditVillageSubsiby selectSubsibyByCvs002(String cvs002);

}