package com.tdkj.dao.Information;

import com.tdkj.model.Information;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface InformationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table information
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String ift001);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table information
     *
     * @mbggenerated
     */
    int insert(Information record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table information
     *
     * @mbggenerated
     */
    int insertSelective(Information record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table information
     *
     * @mbggenerated
     */
    Information selectByPrimaryKey(String ift001);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table information
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(Information record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table information
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Information record);

    /**
     * 根据aab301查询综合信息
     */
    public List<Information> queryInformationByAab301(@Param("aab301")String aab301, @Param("niandu")String niandu, @Param("startRecord")String startRecord, @Param("pageSize")String pageSize);

    /**
     * 根据行政区划查询总条数
     * @param aab301
     * @return
     */
    public String queryAllByAab301(@Param("aab301")String aab301);
}