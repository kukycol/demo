package com.ezm.service.impl;


import com.ezm.common.response.Result;
import com.ezm.common.response.ResultEnum;
import com.ezm.dao.mapper.security.SecurityRoleMapper;
import com.ezm.dao.mapper.security.SecurityUserMapper;
import com.ezm.entity.table.SecurityRole;
import com.ezm.entity.table.SecurityUser;
import com.ezm.utils.CheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户登录逻辑
 */
@Slf4j
@Service
public class SecurityImpl implements UserDetailsService {

    //user mapper
    @Resource
    private SecurityUserMapper securityUserMapper;

    //role mapper
    @Resource
    private SecurityRoleMapper securityRoleMapper;


    //验证用户名是否存在
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {

        //查库用户名是否存在
        List<SecurityUser> byAccount = securityUserMapper.findByAccount(account);
        if (byAccount.size() > 1) {
            log.error("账号：" + account + "，数据异常，存在多条数据。");
            throw new UsernameNotFoundException(account);

        } else if (byAccount.size() == 0) {
            throw new UsernameNotFoundException(account);
        }

        SecurityUser securityUser = byAccount.get(0);
        //用户角色列表
        byAccount.get(0).setRoleList(securityRoleMapper.findByUserId(securityUser.getId()));


        return securityUser;
    }
}
