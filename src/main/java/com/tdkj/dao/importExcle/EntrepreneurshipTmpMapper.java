package com.tdkj.dao.importExcle;

import com.tdkj.model.EmploymentSituationTmp;
import com.tdkj.model.EntrepreneurshipTmp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntrepreneurshipTmpMapper {
    int deleteByPrimaryKey(String epp001);

    int insert(EntrepreneurshipTmp record);

    int insertSelective(EntrepreneurshipTmp record);

    int addList(@Param("list") List<EntrepreneurshipTmp> list);

    int updateCardList(@Param("updatelist") List<EntrepreneurshipTmp> list);

    EntrepreneurshipTmp selectByPrimaryKey(String epp001);

    int updateByPrimaryKeySelective(EntrepreneurshipTmp record);

    int updateByPrimaryKey(EntrepreneurshipTmp record);
    /**
     * 查询正确信息
     * @param batch
     */
    @Select("select  epp002,aac003,f_getcodename('AAA019',epp003) epp003, epp004,epp005,f_getcodename('EDC441',epp010) epp010 ,epp006,\n" +
            "   epp011, epp012,epp014,epp015,epp013  from ENTREPRENEURSHIP_TMP S WHERE S.BATCH=#{BATCH} AND S.IDENTIFICATION='1' ")
    List<EntrepreneurshipTmp> getEntrepreneurshipCorrect(String batch) ;

    /**
     * 查询错误信息
     * @param batch
     */
    @Select("select message, epp002,aac003,f_getcodename('AAA019',epp003) epp003, epp004,epp005,f_getcodename('EDC441',epp010) epp010 ,epp006,\n" +
            "  epp011, epp012,epp014,epp015,epp013  from ENTREPRENEURSHIP_TMP S WHERE S.BATCH=#{BATCH} AND S.IDENTIFICATION='2' ")
    List<EntrepreneurshipTmp> getEntrepreneurshipError(String batch) ;
    /**
     * 保存正确信息
     * @param batch
     */
    @Insert("INSERT INTO ENTREPRENEURSHIP SELECT * FROM  ENTREPRENEURSHIP_TMP S  WHERE S.BATCH=#{BATCH} AND S.IDENTIFICATION='1'")
    int SaveEntrepreneurship(String batch) ;

    @Update(" call Import_ERP_Check(#{0},#{1})")
    int stored_procedure(String batch,String type_1);
}