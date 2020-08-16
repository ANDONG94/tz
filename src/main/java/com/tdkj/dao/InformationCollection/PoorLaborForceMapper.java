package com.tdkj.dao.InformationCollection;

import com.tdkj.model.PoorHouseholds;
import com.tdkj.model.PoorHouseholdsTmp;
import com.tdkj.model.PoorLaborForce;
import com.tdkj.model.PoorLaborForceTmp;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PoorLaborForceMapper {


    /**
     * 根据贫困户id 查询劳动力信息
     * @return
     */
    @Select("select\n" +
            "    PLF001, PLF002,T.INFO AAB301, PLF004, PLF005, PLF006,\n" +
            "    (select AAA103 from aa10 where aaa100='AAC004' AND AAA102=PLF007) PLF007,\n" +
            "     PLF008, (select AAA103 from aa10 where aaa100='AAC011' AND AAA102=PLF009) PLF009,\n" +
            "      (select AAA103 from aa10 where aaa100='AAA002' AND AAA102=PLF011) PLF011,\n" +
            "      (select AAA103 from aa10 where aaa100='AAA016' AND AAA102=PLF018) PLF018, PLF019, PLF020, PLF021, PLF022,PLF023\n" +
            "    from poor_labor_force A,POOR_XZQH t ,recursion r where  A.AAB301=T.XZQHBM  and a.aab301=r.aab301 AND \n" +
            "      r.parentid =  #{aab301} and A.aae100='1' and (A.plf010 in('1','2') or A.plf010 is null) and A.plf018 in ('01','02')  and A.plf008 >= 16" +
            "    and A.plf008 < 60   and  a.plf025='0'")
     List<PoorLaborForce> queryExcel(String aab301);

    /**
     * 导入根据身份证做判断
     * @param idcard
     * @return
     */
    @Select(" select * from  poor_labor_force where plf005=  #{idcard}  and aae100='1'")
    PoorLaborForce selectByPrimaryidCard(String idcard);

    /**
     * 根据行政区划查询劳动力台账信息导出
     * @param aab301
     * @return
     */
    @Select("select PLF001, PLF002,\n" +
            "    (select info from poor_xzqh where xzqhbm = plf.AAB301 )AAB301, PLF004, PLF005, PLF006,\n" +
            "    (select AAA103 from aa10 where aaa100='AAC004' AND AAA102=PLF007) PLF007,\n" +
            "    PLF008, (select AAA103 from aa10 where aaa100='AAC011' AND AAA102=PLF009) PLF009,\n" +
            "    (select AAA103 from aa10 where aaa100='AAA004' AND AAA102=PLF010) PLF010,\n" +
            "    (select AAA103 from aa10 where aaa100='AAA002' AND AAA102=PLF011) PLF011,\n" +
            "    (select AAA103 from aa10 where aaa100='AAA003' AND AAA102=PLF012) PLF012, PLF013,\n" +
            "    (select AAA103 from aa10 where aaa100='AAA001' AND AAA102=PLF014) PLF014,\n" +
            "    AAE011, AAE036, PLF016,\n" +
            "    (select AAA103 from aa10 where aaa100='AAA017' AND AAA102=PLF015)PLF015,\n" +
            "    (select AAA103 from aa10 where aaa100='AAA011' AND AAA102=PLF017) PLF017,\n" +
            "    (select AAA103 from aa10 where aaa100='AAA016' AND AAA102=PLF018) PLF018, PLF019, PLF020, PLF021, PLF022,\n" +
            "    (select AAA103 from aa10 where aaa100='EDC441' AND AAA102=plf023) plf023,\n" +
            "    (select AAA103 from aa10 where aaa100='PLF024' AND AAA102=plf024) plf024,\n" +
            "    DATASOURCE,\n" +
            "    AAE100,UPDATEDATE,UPDATEBY,CREATEBY,AAE012,ISOVERAGE\n" +
            "    from  poor_labor_force plf ，POOR_XZQH t ,recursion r\n" +
            "    where  plf.AAB301=T.XZQHBM  and plf.aab301=r.aab301 AND    r.parentid =  #{aab301} and aae100='1' " +
            "    and (plf.plf010 in('1','2') or plf.plf010 is null) and plf.plf018 in ('01','02')  and plf.plf008 >= 16\n" +
            "    and plf.plf008< tj(plf.plf007)  and  plf.plf025='0' ")
     List<PoorLaborForce> queryPoorLaborForceImportByAab301(@Param("aab301")String aab301);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table poor_labor_force
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String plf001);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table poor_labor_force
     *
     * @mbggenerated
     */
    int insert(PoorLaborForce record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table poor_labor_force
     *
     * @mbggenerated
     */
    int insertSelective(PoorLaborForce record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table poor_labor_force
     *
     * @mbggenerated
     */
    PoorLaborForce selectByPrimaryKey(String plf001);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table poor_labor_force
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(PoorLaborForce record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table poor_labor_force
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(PoorLaborForce record);

    /**
     * 根据贫困户id 查询劳动力信息
     * @return
     */
     List<PoorLaborForce> queryPoorLaborForceByPoorWork(String plf002);





    /**
     * 根据身份证号码查询，该用户是否已经登记
     * @return
     */
     String queryWorkerByIdCard(@Param("plf005")String plf005);

    /**
     * 根据贫困户id 删除劳动力信息
     * @param plf002
     * @return
     */
     int deleteByPlf002(@Param("plf002")String plf002,@Param("realname")String realname);

    /**
     * 根据身份证号码查询，该用户是否已经登记
     * @param plf005
     * @return
     */
    PoorLaborForce queryName(@Param("plf005")String plf005);


    /**
     * 根据行政区划查询劳动力台账信息
     * @return
     */
     List<PoorLaborForce> queryPoorLaborForceAccountByAab301(@Param("poorLaborForce")PoorLaborForce poorLaborForce, @Param("startRecord")String startRecord, @Param("pageSize")String pageSize);

    /**
     * 根据行政区划查询劳动力台账信息
     * @return
     */
     List<PoorLaborForce> queryPoorLaborForceFamily(@Param("poorLaborForce")PoorLaborForce poorLaborForce);



    /**
     *根据行政区划查询台账总共多条
     * @return
     */
     String queryAllPoorLaborForceAccoutByAab301(@Param("poorLaborForce")PoorLaborForce poorLaborForce);


    /**
     *根据行政区划查询家庭户总共多条
     * @return
     */
     String queryAllPoorLaborForceFamily(@Param("poorLaborForce")PoorLaborForce poorLaborForce);


    /**
     * 根据贫困户id 查询劳动力信息  用于批量删除
     * @return
     */
     List<PoorLaborForce> queryPoorLaborForceByPoorWorkIds(@Param("plf0002s")String plf0002s[]);

    /**
     * 根据贫困户id 判断该贫困户下有多少个劳动力属于正常劳动力
     * phd001为贫困户id   plf002位劳动力表中对应的贫困户id
     */
     String queryNormalWorkerByPlf002(@Param("plf002")String plf002);

    /**
     *批量查询
     */
    List<PoorLaborForce> selectByPrimaryidCardList(@Param("selectList")List<String> selectList);

    /**
     *批量修改
     */
    int updateCardList(@Param("updatelist")List<PoorLaborForceTmp> updatelist);

    /**
     * 批量添加
     * @param list
     * @return
     */
    int addList(@Param("list") List<PoorLaborForce> list);


    /**
     *   根据贫困户id 判断该贫困户下有  有就业创业意愿人数
     *   phd001为贫困户id   plf002位劳动力表中对应的贫困户id
     */
    String havejiuyechuangyecount(String plf002);

    /**
     *   根据贫困户id 判断该贫困户下有  已就业创业人数
     *   phd001为贫困户id   plf002位劳动力表中对应的贫困户id
     */
    String yijiuyechuangyecount(String plf002);


    /**
     *   根据贫困户id 判断该贫困户下有  已就业创业人名称
     *   phd001为贫困户id   plf002位劳动力表中对应的贫困户id
     */
    String yijiuyechuangyename(String plf002);

    /**
     *   根据贫困户id 判断该贫困户下有  未就业创业人数
     *   phd001为贫困户id   plf002位劳动力表中对应的贫困户id
     */
    String weijiuyechuangyecount(String plf002);

    /**
     *   根据贫困户id 判断该贫困户下有  未就业创业人名称
     *   phd001为贫困户id   plf002位劳动力表中对应的贫困户id
     */
    String weijiuyechuangyename(String plf002);


    /**
     * 根据行政区划查询待确认劳动力信息
     * @return
     */
    List<PoorLaborForce> queryPoorLaborForceToBeComfireByAab301(@Param("poorLaborForce")PoorLaborForce poorLaborForce, @Param("startRecord")String startRecord, @Param("pageSize")String pageSize);



    /**
     *根据行政区划查询根据行政区划查询待确认劳动力信息总共多条
     * @return
     */
    String queryAllPoorLaborForceToBeComfireByAab301(@Param("poorLaborForce")PoorLaborForce poorLaborForce);



    //***************************************待帮扶查询**********************************************************************************************

    /**
     * 根据行政区划查询  待帮扶劳动力  信息
     * @return
     */
    List<PoorLaborForce> queryTobeHelpWorkerByAab301(@Param("poorLaborForce")PoorLaborForce poorLaborForce, @Param("startRecord")String startRecord, @Param("pageSize")String pageSize);



    /**
     *根据行政区划查询待帮扶劳动力 总共多条
     * @return
     */
    String queryAllTobeHelpWorkerByAab301(@Param("poorLaborForce")PoorLaborForce poorLaborForce);


    /**
     * 根据行政区划查询所有贫困人口信息
     */
    List<PoorLaborForce> queryPoorPersonAccountByAab301(@Param("poorLaborForce")PoorLaborForce poorLaborForce, @Param("startRecord")String startRecord, @Param("pageSize")String pageSize);


    /**
     *根据行政区划查询所有贫困人口  共多条
     * @return
     */
    String queryAllPoorPersonAccountByAab301(@Param("poorLaborForce")PoorLaborForce poorLaborForce);

    /**
     *根据行政区划查询所有贫困劳动力人口  共多条
     * @return
     */
    String queryAllPoorWorkerAccountByAab301(@Param("poorLaborForce")PoorLaborForce poorLaborForce);


    //*************************************************************************************************************************

    /**
     * 根据行政区划查询  重复劳动力  信息
     * @return
     */
    List<PoorLaborForce> queryRepeatPoorLaborForceByAab301(@Param("poorLaborForce")PoorLaborForce poorLaborForce, @Param("startRecord")String startRecord, @Param("pageSize")String pageSize);



    /**
     *根据行政区划查询   重复劳动力 总共多条
     * @return
     */
    String queryRepeatPoorLaborForceAccoutByAab301(@Param("poorLaborForce")PoorLaborForce poorLaborForce);



    //************************************以下是劳动力有转移就业状态,没有就业信息的  台账信息**********************************************************************

    /**
     * 根据行政区划查询劳动力转移就业状态,没有就业信息
     * @return
     */
    List<PoorLaborForce> queryErrorPoorLaborForceAccountByAab301(@Param("poorLaborForce")PoorLaborForce poorLaborForce, @Param("startRecord")String startRecord, @Param("pageSize")String pageSize);



    /**
     *根据行政区划查询台账总共多条  转移就业状态,没有就业信息
     * @return
     */
    String queryAllErrorPoorLaborForceAccoutByAab301(@Param("poorLaborForce")PoorLaborForce poorLaborForce);


    /*************************************************弱劳动力台账************************************************************************************************/

    /**
     * 根据行政区划查询弱劳动力台账信息
     * @return
     */
    List<PoorLaborForce> queryRuoPoorLaborForceAccountByAab301(@Param("poorLaborForce")PoorLaborForce poorLaborForce, @Param("startRecord")String startRecord, @Param("pageSize")String pageSize);



    /**
     *根据行政区划查询弱劳动力台账总共多条
     * @return
     */
    String queryAllRuoPoorLaborForceAccoutByAab301(@Param("poorLaborForce")PoorLaborForce poorLaborForce);

}