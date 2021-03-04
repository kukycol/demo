package com.ezm.rule;



import com.ezm.dao.mapper.security.SecurityRoleMapper;
import com.ezm.dao.mapper.security.SecurityRuleMapper;
import com.ezm.entity.table.SecurityRole;
import com.ezm.entity.table.SecurityRule;
import com.ezm.utils.CheckUtil;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;


@Component
public class SecuritySource implements FilterInvocationSecurityMetadataSource {



    @Resource
    private SecurityRuleMapper securityRuleMapper;
    @Resource
    private SecurityRoleMapper securityRoleMapper;



    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        //请求路径
        String requestUrl = ((FilterInvocation) object).getRequestUrl();

        //查表是否存在请求路径
        SecurityRule securityRule = securityRuleMapper.findByRule(requestUrl);

        //请求路径不存在表中，赋予游客角色
        if (CheckUtil.isBlank(securityRule)){
            return SecurityConfig.createList("ROLE_guest");
        }

        //查请求路径拥有的角色
        List<SecurityRole> byRuleId = securityRoleMapper.findByRuleId(securityRule.getId());

        //资源路径角色赋予框架
        String[] roleNameArray = new String[byRuleId.size()];
        for (int i = 0; i < byRuleId.size(); i++) {
            roleNameArray[i] = byRuleId.get(i).getRole();
        }

        return SecurityConfig.createList(roleNameArray);


    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }


}
