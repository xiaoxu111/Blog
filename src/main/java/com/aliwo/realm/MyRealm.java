package com.aliwo.realm;

import com.aliwo.entity.Blogger;
import com.aliwo.service.BloggerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * 自定义Realm
 * @author java1234_小锋
 *
 */
public class MyRealm extends AuthorizingRealm {

	@Autowired
	private BloggerService bloggerService;
	
	/**
	 * 为当前登录的用户授予角色和权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	/**
	 * 验证当前登录的用户
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName=(String)token.getPrincipal();
		Blogger blogger=bloggerService.getByUserName(userName);
		if(blogger == null){
			/** 当前用户信息存到session中*/
			/*
			SecurityUtils.getSubject().getSession().setAttribute("currentUser", blogger);
			AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(userName,blogger.getPassword(),
					ByteSource.Util.bytes(blogger.getUserName()),getName());
			return authcInfo;*/
			throw new UnknownAccountException("未知的账户");
		}
			/** 当前用户信息存到session中*/
			SecurityUtils.getSubject().getSession().setAttribute("currentUser",blogger );
			 return new SimpleAuthenticationInfo(userName,blogger.getPassword(),
					ByteSource.Util.bytes(blogger.getUserName()),getName());
	}

}
