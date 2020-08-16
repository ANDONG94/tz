package com.tdkj.dao.UserManager;

import com.tdkj.model.SysRole;
import com.tdkj.model.SysUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: UserDao
 * @Description TODO
 * @Author and
 * @Date 2019/5/821:16
 * @Version 1.0
 */
@Repository
public interface UserDao {

    public SysUser findByUserName(String username);
    //权限
    @Select("select r.name from FP_USER u  LEFT JOIN sys_role_user sru on u.id= sru.Sys_User_id LEFT JOIN Sys_Role r on sru.Sys_Role_id=r.id\n" +
            "        where u.id= (#{id})")
    public List<SysRole> findByRole(String id);
}
