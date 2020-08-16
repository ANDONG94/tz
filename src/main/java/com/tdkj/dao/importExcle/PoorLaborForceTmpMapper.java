package com.tdkj.dao.importExcle;

import com.tdkj.model.PoorHouseholdsTmp;
import com.tdkj.model.PoorLaborForceTmp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoorLaborForceTmpMapper {
    int deleteByPrimaryKey(String plf001);

    int insert(PoorLaborForceTmp record);

    int insertSelective(PoorLaborForceTmp record);

    int addList(@Param("list") List<PoorLaborForceTmp> list);



    int updateCardList(@Param("updatelist") List<PoorLaborForceTmp> list);

    PoorLaborForceTmp selectByPrimaryKey(String plf001);

    int updateByPrimaryKeySelective(PoorLaborForceTmp record);

    int updateByPrimaryKey(PoorLaborForceTmp record);
    @Select("SELECT * from POOR_LABOR_FORCE_TMP a ,poor_households b  where a.plf002=b.phd001 and a.batch=#{batch} and   a.identification='1'")
    List<PoorLaborForceTmp> PutPoorHouseholds(String batch);


    @Select("SELECT a.message,Plf002,aac003,Plf004,Plf005,Plf006,f_getcodename('AAC011',plf009) plf009 \n" +
            ",f_getcodename('AAA004',plf010) plf010,f_getcodename('AAA002',plf011) plf011,f_getcodename('AAA003',plf012) plf012,f_getcodename('PLF024',plf024) plf024, f_getcodename('AAA016',plf018) plf018, \n" +
            " f_getcodename('AAA003',plf012) plf012, f_getcodename('EDC441',plf023) plf023, (case when plf015 ='1' then '是' else '否' end) plf015,Plf019" +
            " from POOR_LABOR_FORCE_TMP a ,poor_households b  where a.plf002=b.phd001 and a.batch=#{batch} and   a.identification='1'")
    List<PoorLaborForceTmp> getPoorLaborForceTmpCorrect(String batch);

    @Select("SELECT a.message,Plf002,aac003,Plf004,Plf005,Plf006,f_getcodename('AAC011',plf009) plf009" +
            ",f_getcodename('AAA004',plf010) plf010,f_getcodename('AAA002',plf011) plf011,f_getcodename('AAA003',plf012) plf012,f_getcodename('PLF024',plf024) plf024, f_getcodename('AAA016',plf018) plf018, " +
            "  f_getcodename('AAA003',plf012) plf012 , f_getcodename('EDC441',plf023) plf023, (case when plf015 ='1' then '是' else '否' end) plf015,Plf019 " +
            " from POOR_LABOR_FORCE_TMP a   where  a.batch=#{batch} and a.identification='2'")
    List<PoorLaborForceTmp> getPoorLaborForceTmpError(String batch);

    @Insert("INSERT INTO POOR_LABOR_FORCE SELECT * from POOR_LABOR_FORCE_TMP where batch=#{batch} and identification='1'")
    int SavePoorLaborForce(String batch);

    @Select("SELECT * from  POOR_LABOR_FORCE_TMP a  where  a.batch=#{batch} and   a.identification='1'")
     List<PoorLaborForceTmp> getPoorLaborForceTmpSave(String batch);

}