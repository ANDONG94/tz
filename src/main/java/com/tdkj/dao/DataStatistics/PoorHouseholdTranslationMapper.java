package com.tdkj.dao.DataStatistics;

import com.tdkj.model.PoorHouseholdTranslation;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoorHouseholdTranslationMapper {
    int deleteByPrimaryKey(String aab301);

    int insert(PoorHouseholdTranslation record);

    int insertSelective(PoorHouseholdTranslation record);

    PoorHouseholdTranslation selectByPrimaryKey(String aab301);

    int updateByPrimaryKeySelective(PoorHouseholdTranslation record);

    int updateByPrimaryKey(PoorHouseholdTranslation record);


   @Select("select xzqhmc aab301," +
           "       f_tj_pht001(xzqhbm, '2',#{1}, #{2}, #{3}) pht001," +
           "       f_tj_pht002(xzqhbm, '2',#{1}, #{2}, #{3}) pht002," +
           "       f_tj_pht003(xzqhbm, '2',#{1}, #{2}, #{3}) pht003," +
           "       f_tj_pht004(xzqhbm, '2',#{1}, #{2}, #{3}) pht004," +
           "       f_tj_pht005(xzqhbm, '2',#{1}, #{2}, #{3}) pht005," +
           "       f_tj_pht006(xzqhbm, '2',#{1}, #{2}, #{3}) pht006," +
           "       f_tj_pht007(xzqhbm, '2',#{1}, #{2}, #{3}) pht007," +
           "       f_tj_pht008(xzqhbm, '2',#{1}, #{2}, #{3}) pht008," +
           "       f_tj_pht009(xzqhbm, '2',#{1}, #{2}, #{3}) pht009," +
           "       f_tj_pht010(xzqhbm, '2',#{1}, #{2}, #{3}) pht010" +
           "  from poor_xzqh" +
           " where (fxzqhbm =#{0} or xzqhbm=#{0}) order by sort")
    List<PoorHouseholdTranslation> queryAllByAab301Statistics(String aab301,String phd013,String phd012,String phd014);
    @Select("select count(1) from POOR_HOUSEHOLD_TRANSLATION t where (parentid=#{aab301} or aab301=#{aab301})")
    String queryAllByAab301(String aab301);
}