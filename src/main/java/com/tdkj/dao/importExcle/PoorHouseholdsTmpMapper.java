package com.tdkj.dao.importExcle;

import com.tdkj.model.PoorHouseholdsTmp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoorHouseholdsTmpMapper {
    int deleteByPrimaryKey(String phd001);

    int insert(PoorHouseholdsTmp record);

    int addList(@Param("list") List<PoorHouseholdsTmp> list);

    int insertSelective(PoorHouseholdsTmp record);

    PoorHouseholdsTmp selectByPrimaryKey(String phd001);

    int updateByPrimaryKeySelective(PoorHouseholdsTmp record);

    int updateByPrimaryKey(PoorHouseholdsTmp record);

    @Select("SELECT phd012,phd002,phd003, phd006, phd007,f_getcodename('EDC441',phd013) plf013 ,f_getcodename('EDC441',phd016) phd016," +
            "  f_getcodename('AAA012',phd014) phd014,phd011,F_centralized(phd010) phd010,Phd011" +
            "  FROM poor_households_tmp  where batch=#{batch} and identification='1'")
    List<PoorHouseholdsTmp> getPoorHouseholdsTmpCorrect(String batch);

    @Select("SELECT  message,phd012,phd002,phd003, phd006, phd007,f_getcodename('EDC441',phd013) phd013,f_getcodename('EDC441',phd016) phd016," +
            "  f_getcodename('AAA012',phd014) phd014,phd011,F_centralized(phd010) phd010,Phd011" +
            "  FROM  poor_households_tmp where batch=#{batch} and identification='2'")
    List<PoorHouseholdsTmp> getPoorHouseholdsTmpError(String batch);

    @Insert("INSERT INTO Poor_Households SELECT * FROM poor_households_tmp  where batch=#{batch} and identification='1'")
    int SavePoorHouseholds(String batch);

    List<PoorHouseholdsTmp> CoppyselectByPrimaryKey(String batch);

}

