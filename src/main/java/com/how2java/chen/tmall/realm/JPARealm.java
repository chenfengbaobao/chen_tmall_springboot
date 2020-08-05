package com.how2java.chen.tmall.realm;

import com.how2java.chen.tmall.pojo.User;
import com.how2java.chen.tmall.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author apple
 */
public class JPARealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {


        String userName = authenticationToken.getPrincipal().toString();
        User user = userService.getByName(userName);

        String passwordInDB = user.getPassword();
        String salt = user.getSalt();

        SimpleAuthenticationInfo authenticationInfo =
                new SimpleAuthenticationInfo(userName, passwordInDB, ByteSource.Util.bytes(salt), getName());


        return authenticationInfo;
    }
}
