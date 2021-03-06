package com.tdkj.dao.IncubatorBase;

import com.tdkj.model.IncubatorBase;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IncubatorBaseMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table incubator_base
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String ibb001);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table incubator_base
     *
     * @mbggenerated
     */
    int insert(IncubatorBase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table incubator_base
     *
     * @mbggenerated
     */
    int insertSelective(IncubatorBase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table incubator_base
     *
     * @mbggenerated
     */
    IncubatorBase selectByPrimaryKey(String ibb001);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table incubator_base
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(IncubatorBase record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table incubator_base
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(IncubatorBase record);

    /**
     * 根据aab301 查询创业基地园区中心
     */
    public List<IncubatorBase> queryIncubatorBaseByAab301(@Param("incubatorBase")IncubatorBase incubatorBase, @Param("startRecord")String startRecord, @Param("pageSize")String pageSize);

    /**
     * 根据行政区划查询总条数
     * @param aab301
     * @return
     */
    public String queryAllByAab301(@Param("incubatorBase")IncubatorBase incubatorBase);

}