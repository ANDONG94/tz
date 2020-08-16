package com.tdkj.service.Information;

import com.tdkj.model.EntrepreneurialTeachers;

import java.util.List;

/**
 * Created by len on 2019-05-13.
 */
public interface EntrepreneurialTeachersService {
    int deleteByPrimaryKey(String ets001);

    int insert(EntrepreneurialTeachers entrepreneurialTeachers);

    int insertSelective(EntrepreneurialTeachers entrepreneurialTeachers);

    EntrepreneurialTeachers selectByPrimaryKey(String ets001);

    int updateByPrimaryKeySelective(EntrepreneurialTeachers entrepreneurialTeachers);

    int updateByPrimaryKey(EntrepreneurialTeachers entrepreneurialTeachers);

    /**
     * 根据行政区划查询创业师资力量列表
     * @param aab301
     * @return
     */
    public List<EntrepreneurialTeachers> querChuangyeTeacherByAab301(EntrepreneurialTeachers entrepreneurialTeachers, String startRecord, String pageSize);


    /**
     * 根据行政区划查询总共多条
     * @param aab301
     * @return
     */
    public String  queryAllByAab301(EntrepreneurialTeachers entrepreneurialTeachers);
}
