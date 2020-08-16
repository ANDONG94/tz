package com.tdkj.dao.IpLogin;

import com.tdkj.model.IpLogin;
import org.springframework.stereotype.Repository;

@Repository
public interface IpLoginMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(IpLogin record);

    int insertSelective(IpLogin record);

    IpLogin selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(IpLogin record);

    int updateByPrimaryKey(IpLogin record);
}