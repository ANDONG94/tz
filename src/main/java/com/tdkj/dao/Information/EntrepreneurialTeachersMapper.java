package com.tdkj.dao.Information;

import com.tdkj.model.EntrepreneurialTeachers;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EntrepreneurialTeachersMapper {
    int deleteByPrimaryKey(String ets001);

    int insert(EntrepreneurialTeachers entrepreneurialTeachers);

    int insertSelective(EntrepreneurialTeachers entrepreneurialTeachers);

    EntrepreneurialTeachers selectByPrimaryKey(String ets001);

    int updateByPrimaryKeySelective(EntrepreneurialTeachers entrepreneurialTeachers);

    int updateByPrimaryKey(EntrepreneurialTeachers entrepreneurialTeachers);

    /**
     * 根据行政区划查询创业师资力量列表
     * @return
     */
    public List<EntrepreneurialTeachers> querChuangyeTeacherByAab301(@Param("entrepreneurialTeachers")EntrepreneurialTeachers entrepreneurialTeachers, @Param("startRecord")String startRecord, @Param("pageSize")String pageSize);


    /**
     * 根据行政区划查询总共多条
     * @return
     */
    public String  queryAllByAab301(@Param("entrepreneurialTeachers")EntrepreneurialTeachers entrepreneurialTeachers);
}