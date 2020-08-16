package com.tdkj.dao.importExcle;

import com.tdkj.model.EntrepreneurshipTmp;
import com.tdkj.model.TechnicalSchoolsTmp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnicalSchoolsTmpMapper {
    int deleteByPrimaryKey(String ths001);

    int insert(TechnicalSchoolsTmp record);

    int insertSelective(TechnicalSchoolsTmp record);

    int addList(@Param("list") List<TechnicalSchoolsTmp> list);


    int updateCardList(@Param("updatelist") List<TechnicalSchoolsTmp> list);

    TechnicalSchoolsTmp selectByPrimaryKey(String ths001);

    int updateByPrimaryKeySelective(TechnicalSchoolsTmp record);

    int updateByPrimaryKey(TechnicalSchoolsTmp record);
    /**
     * 查询正确信息
     * @param batch
     */
    @Select("select  ths002,aac003, ths003, ths004 ,ths007, ths005, ths006, ths008， ths013, f_getcodename('EDC441',ths010) ths010,\n" +
            "                    ths014, ths016, ths015 ,ths011   from TECHNICAL_SCHOOLS_TMP S WHERE S.BATCH=#{BATCH} AND S.IDENTIFICATION='1' ")
    List<TechnicalSchoolsTmp> getTechnicalSchoolsCorrect(String batch) ;

    /**
     * 查询错误信息
     * @param batch
     */
    @Select("select message,ths002,aac003, ths003, ths004 ,ths007, ths005, ths006, ths008， ths013, f_getcodename('EDC441',ths010) ths010,\n" +
            "  ths014, ths016, ths015 ,ths011   from TECHNICAL_SCHOOLS_TMP  S WHERE S.BATCH=#{BATCH} AND S.IDENTIFICATION='2' ")
    List<TechnicalSchoolsTmp> getTechnicalSchoolsError(String batch) ;
    /**
     * 保存正确信息
     * @param batch
     */
    @Insert("INSERT INTO TECHNICAL_SCHOOLS SELECT * FROM  TECHNICAL_SCHOOLS_TMP S  WHERE S.BATCH=#{BATCH} AND S.IDENTIFICATION='1'")
    int SaveTechnicalSchools(String batch) ;

    @Update(" call Import_TSS_Check(#{0},#{1})")
    int stored_procedure(String batch,String type_1);
}