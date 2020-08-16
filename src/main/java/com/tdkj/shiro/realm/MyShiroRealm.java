package com.tdkj.shiro.realm;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;
import com.tdkj.model.FpUser;
import com.tdkj.model.IpLogin;
import com.tdkj.service.IpLoing.IpLoginService;
import com.tdkj.service.UserManager.FpUserService;
import com.tdkj.util.IpUtil;
import com.tdkj.util.UUIDGenerator;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author
 * @date
 */
public class MyShiroRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    @Resource
    private FpUserService fpUserService;
    @Autowired
    private IpLoginService ipLoginService;
    // 权限授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        FpUser userInfo  = (FpUser)principals.getPrimaryPrincipal();
		/*for(SysRole sysRole : userInfoService.findSysRoleListByUsername(userInfo.getUsername())){
			authorizationInfo.addRole(sysRole.getRolename());
			logger.info(sysRole.toString());
			for(SysPermission sysPermission : userInfoService.findSysPermissionListByRoleId(sysRole.getId())){
				logger.info(sysPermission.toString());
				authorizationInfo.addStringPermission(sysPermission.getUrl());
			}
		};*/
        return authorizationInfo;
    }

    //主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String)token.getPrincipal();
        logger.info("对用户[{}]进行登录验证..验证开始",username);
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        FpUser userInfo =  fpUserService.queryUserByUserName(username);

        if(userInfo == null){
        	// 抛出 帐号找不到异常  
            throw new UnknownAccountException();  
        }
      /*  String ip=IpUtil.getIpAddr(request);
        IpLogin ipLogin = new IpLogin();
        ipLogin.setUuid(UUIDGenerator.generate().toString());
        ipLogin.setIntodate(new Date());
        ipLogin.setUsercardid(userInfo.getIdcard());
        ipLogin.setUserid(userInfo.getId());
        ipLogin.setUsername(userInfo.getUsername());
        ipLogin.setLog006(userInfo.getAab301());
        ipLogin.setLog007(ip);
        ipLoginService.insert(ipLogin);*/
        return new SimpleAuthenticationInfo(userInfo, userInfo.getPassword(), getName());
    }
}
