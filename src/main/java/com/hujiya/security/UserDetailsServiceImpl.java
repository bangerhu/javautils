package com.hujiya.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author 陌上丶天琊
 * @date 2019-10-31 10:54
 * 描述：spring security登陆处理（根据用户登录名，加载用户信息）
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    @Lazy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 根据用户登录名，加载用户信息
     *
     * @param username
     * @return 返回实现UserDetails的用户对象
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysLoginUser sysLoginUser = new SysLoginUser("hujiya","hujiya");
        return sysLoginUser;
    }
}
