package com.tdkj.dao.importExcle;

import com.tdkj.model.EmploymentSituation;
import com.tdkj.model.EmploymentSituationTmp;
import com.tdkj.model.PoorLaborForceTmp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmploymentSituationTmpMapper {
    int deleteByPrimaryKey(String eys001);

    int insert(EmploymentSituationTmp record);

    int insertSelective(EmploymentSituationTmp record);

    int addList(@Param("list") List<EmploymentSituationTmp> list);


    int updateCardList(@Param("updatelist") List<EmploymentSituationTmp> list);

    EmploymentSituationTmp selectByPrimaryKey(String eys001);

    int updateByPrimaryKeySelective(EmploymentSituationTmp record);

    int updateByPrimaryKey(EmploymentSituationTmp record);
    @Select("SELECT   Eys002,aac003,f_getcodename('AAA013',eys003) eys003, f_getcodename('AAA008',eys012) eys012, " +
            " f_getcodename('AAA006',eys004) eys004, EYS015, EYS006,  f_getcodename('ECC207',eys007) eys007" +
            " ,f_getcodename('AAA018',eys005) eys005,  f_getcodename('AAA005',eys011) eys011, EYS009, \n" +
            " EYS010 ,EYS016, EYS019, EYS020, EYS021, EYS008 ,EYS013 \n" +
            " FROM EMPLOYMENT_SITUATION_TMP S WHERE S.BATCH=#{BATCH} AND S.IDENTIFICATION in ('1','9')\n")
    List<EmploymentSituationTmp> getEmploymentSintuationCorrect(String batch);

    @Select("SELECT  message,Eys002,aac003,f_getcodename('AAA013',eys003) eys003, f_getcodename('AAA008',eys012) eys012," +
            " f_getcodename('AAA006',eys004) eys004, EYS015, EYS006,  f_getcodename('ECC207',eys007) eys007" +
            " ,f_getcodename('AAA018',eys005) eys005,  f_getcodename('AAA005',eys011) eys011, EYS009, \n" +
            "EYS010 ,EYS016, EYS019, EYS020, EYS021, EYS008 ,EYS013 \n" +
            " FROM EMPLOYMENT_SITUATION_TMP S WHERE S.BATCH=#{BATCH} AND S.IDENTIFICATION='2'\n")
    List<EmploymentSituationTmp> getEmploymentSintuationError(String batch);


    @Insert("INSERT INTO EMPLOYMENT_SITUATION SELECT * FROM EMPLOYMENT_SITUATION_TMP s WHERE S.BATCH=#{BATCH} AND S.IDENTIFICATION ='1'")
    int SaveEmploymentSintuation(String batch);


    @Update(" call Import_ESN_Check(#{0},#{1})")
    int stored_procedure(String batch,String type_1);



    @Select("SELECT * FROM EMPLOYMENT_SITUATION_TMP S WHERE S.BATCH=#{BATCH} AND S.IDENTIFICATION in ('1','9')\n")
    List<EmploymentSituation> getEmploymentSintuation(String batch);

}