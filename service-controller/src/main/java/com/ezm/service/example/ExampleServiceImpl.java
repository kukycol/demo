package com.ezm.service.example;

import com.ezm.common.response.Result;
import com.ezm.dao.mapper.security.SecurityUserMapper;
import com.ezm.entity.table.SecurityUser;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ExampleServiceImpl implements ExampleService {


    @Resource
    private SecurityUserMapper securityUserMapper;

    @Override
    public Result findAll(int page, int limit) {
        RowBounds rowBounds = new RowBounds(page,limit);
        SecurityUser securityUser = new SecurityUser();
        securityUser.setSex("男");

        List<SecurityUser> securityUsers = securityUserMapper.selectByRowBounds(securityUser, rowBounds);
        System.err.println(securityUsers);
        return null;
    }



}
