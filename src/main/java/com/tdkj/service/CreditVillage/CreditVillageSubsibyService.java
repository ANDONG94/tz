package com.tdkj.service.CreditVillage;

import com.tdkj.model.CommunityFactorySubsidy;
import com.tdkj.model.CreditVillageSubsiby;

import java.util.List;

/**
 * Created by hedd on 2019/12/7.
 */
public interface CreditVillageSubsibyService {

    int deleteByPrimaryKey(String cvs001);

    int insert(CreditVillageSubsiby creditVillageSubsiby);

    int insertSelective(CreditVillageSubsiby creditVillageSubsiby);

    CreditVillageSubsiby selectByPrimaryKey(String cvs001);

    int updateByPrimaryKeySelective(CreditVillageSubsiby creditVillageSubsiby);

    int updateByPrimaryKey(CreditVillageSubsiby creditVillageSubsiby);

    List<CreditVillageSubsiby> querCreditVillageSubsibyByCvs002(String cvs002);


    /**
     * 信用乡村id  查询所有补贴之和
     * @return
     */
    public CreditVillageSubsiby selectSubsibyByCvs002(String cvs002);

}
