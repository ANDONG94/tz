package com.tdkj.ServiceImpl.Information;

import com.tdkj.dao.Information.EntrepreneurialTeachersMapper;
import com.tdkj.model.EntrepreneurialTeachers;
import com.tdkj.service.Information.EntrepreneurialTeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by len on 2019-05-13.
 */
@Service
public class EntrepreneurialTeachersServiceImpl implements EntrepreneurialTeachersService {

  @Autowired
  private EntrepreneurialTeachersMapper entrepreneurialTeachersMapper;

    @Override
    public int deleteByPrimaryKey(String ets001) {
        return entrepreneurialTeachersMapper.deleteByPrimaryKey(ets001);
    }

    @Override
    public int insert(EntrepreneurialTeachers record) {
        return 0;
    }

    @Override
    public int insertSelective(EntrepreneurialTeachers record) {
        return entrepreneurialTeachersMapper.insertSelective(record);
    }

    @Override
    public EntrepreneurialTeachers selectByPrimaryKey(String ets001) {
        return entrepreneurialTeachersMapper.selectByPrimaryKey(ets001);
    }

    @Override
    public int updateByPrimaryKeySelective(EntrepreneurialTeachers record) {
        return entrepreneurialTeachersMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(EntrepreneurialTeachers record) {
        return 0;
    }

    /**
     * 根据行政区划查询创业师资力量列表
     * @return
     */
    @Override
    public List<EntrepreneurialTeachers> querChuangyeTeacherByAab301(EntrepreneurialTeachers entrepreneurialTeachers, String startRecord, String pageSize) {
        return entrepreneurialTeachersMapper.querChuangyeTeacherByAab301(entrepreneurialTeachers,startRecord,pageSize);
    }

    /**
     * 根据行政区划查询总共多条
     * @return
     */
    @Override
    public String queryAllByAab301(EntrepreneurialTeachers entrepreneurialTeachers) {
        return entrepreneurialTeachersMapper.queryAllByAab301(entrepreneurialTeachers);
    }
}
