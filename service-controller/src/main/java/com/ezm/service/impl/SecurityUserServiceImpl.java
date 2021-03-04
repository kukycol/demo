package com.ezm.service.impl;

import com.ezm.common.response.Result;
import com.ezm.common.response.ResultEnum;
import com.ezm.dao.mapper.security.SecurityRoleMapper;
import com.ezm.dao.mapper.security.SecurityRoleUserMapper;
import com.ezm.dao.mapper.security.SecurityUserMapper;
import com.ezm.entity.bean.AddUserBean;
import com.ezm.entity.bean.RoleUserBean;
import com.ezm.entity.bean.UpdateUserBean;
import com.ezm.entity.bean.UserPageBean;
import com.ezm.entity.table.SecurityRole;
import com.ezm.entity.table.SecurityRoleUser;
import com.ezm.entity.table.SecurityUser;
import com.ezm.entity.table.SecurityUser2;
import com.ezm.service.SecurityUserService;
import com.ezm.utils.CheckUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SecurityUserServiceImpl implements SecurityUserService {
    @Resource
    private SecurityUserMapper securityUserMapper;

    @Resource
    private SecurityRoleUserMapper securityRoleUserMapper;

    @Resource
    private SecurityRoleMapper securityRoleMapper;

    @Override
    public Result findAll(UserPageBean u) {

        //start page
        PageHelper.startPage(u.getPage(), u.getLimit());

        //queryAll
        List<SecurityUser2> all = securityUserMapper.findAll(u);

        List<SecurityRole> roleList = securityRoleMapper.selectAll();


        for (int i = 0; i < all.size(); i++) {
            //单个用户对象
            SecurityUser2 securityUser2 = all.get(i);
            //用户编号查用户角色
            List<SecurityRole> byUserId = securityRoleMapper.findByUserId(securityUser2.getId());

            List<SecurityRole> list = new ArrayList<>();
            List<Integer> list2 = new ArrayList<>();

            //查角色列表
            for (int j = 0; j < roleList.size(); j++) {
                //遍历角色列表后单个角色信息
                SecurityRole securityRole = roleList.get(j);
                SecurityRole securityRole1 = new SecurityRole();
                securityRole1.setRole(securityRole.getRole());
                securityRole1.setRoleName(securityRole.getRoleName());
                securityRole1.setId(securityRole.getId());
                securityRole1.setEnabled(securityRole.getEnabled());
                if (byUserId.size() > 0) {
                    boolean contains = byUserId.contains(securityRole);
                    if (contains) {
                        byUserId.remove(securityRole);
                        securityRole1.setUsed(true);
                        list2.add(1);
                    } else {
                        securityRole1.setUsed(false);
                    }

                } else {
                    //不存在用户角色了
                    securityRole1.setUsed(false);
                }
                list.add(securityRole1);

            }

            securityUser2.setRoleSize(list2);
            securityUser2.setRoleList(list);

        }


        //page details
        PageInfo<SecurityUser2> of = PageInfo.of(all);


        return new Result(ResultEnum.querySuccessful, all, of.getTotal());
    }

    @Transactional
    @Override
    public Result addUser(AddUserBean a) {

        List<SecurityUser> byAccount = securityUserMapper.findByAccount(a.getAccount());
        if (byAccount.size() > 1) {
            log.error("账号：" + a.getAccount() + "，数据异常，存在多条数据。");
            return new Result(ResultEnum.moreAccountData);
        } else if (byAccount.size() == 1) {
            return new Result(ResultEnum.accountExist);
        }


        List<SecurityUser> byUname = securityUserMapper.findByUname(a.getUname());
        if (byUname.size() > 1) {
            log.error("用户名：" + a.getUname() + "，数据异常，存在多条数据。");
            return new Result(ResultEnum.moreUnameData);
        } else if (byUname.size() == 1) {
            return new Result(ResultEnum.unameExist);
        }

        List<SecurityUser> byMobile = securityUserMapper.findByMobile(a.getMobile());
        if (byMobile.size() > 1) {
            log.error("手机号码：" + a.getMobile() + "，数据异常，存在多条数据。");

            return new Result(ResultEnum.moreMobileData);
        } else if (byMobile.size() == 1) {
            return new Result(ResultEnum.mobileExist);
        }

        List<SecurityUser> byEmail = securityUserMapper.findByEmail(a.getEmail());
        if (byEmail.size() > 1) {
            log.error("邮箱地址：" + a.getEmail() + "，数据异常，存在多条数据。");
            return new Result(ResultEnum.moreEmailData);
        } else if (byEmail.size() == 1) {
            return new Result(ResultEnum.emailExist);
        }

        Date date = new Date();
        a.setUpdateDate(date);
        a.setCreateDate(date);
        a.setState("1");
        a.setCredentialsNonExpired("1");
        a.setAccountNonExpired("1");
        a.setAccountNonLocked("1");
        a.setPassword(new BCryptPasswordEncoder().encode("123456"));
        int i = securityUserMapper.addUser(a);
        if (i == 1 || i == 0) {

             List<SecurityRole> roleList = a.getRoleList();
            for (int j = 0; j < roleList.size(); j++) {
                SecurityRole securityRole = roleList.get(j);
                SecurityUser byAccount2 = securityUserMapper.findByAccount2(a.getAccount());
                securityRoleUserMapper.addRole(RoleUserBean.builder().userId(byAccount2.getId()).createDate(new Date()).roleId(securityRole.getId()).build());
            }


            return new Result(ResultEnum.addSuccessful);
        }


        throw new RuntimeException();

    }

    @Transactional
    @Override
    public Result delUser(int userId) {

        int i = securityUserMapper.delUser(userId);
        if (i == 1 || i == 0) {

            securityRoleUserMapper.delByUserId(userId);

            return new Result(ResultEnum.deleteSuccessful);
        }

        throw new RuntimeException();
    }

    @Transactional
    @Override
    public Result stopUser(int userId) {

        SecurityUser2 byId = securityUserMapper.findById(userId);
        if (CheckUtil.isBlank(byId)) {
            return new Result(ResultEnum.queryFails);
        }

        //后端限制,防止停用后再次停用
        if (byId.getEnabled().equals("0")) {
            return new Result(ResultEnum.stopUserExist);
        }

        //账号停用
        int i = securityUserMapper.stopUser(userId, "0");
        if (i == 1 || i == 0) {
            return new Result(ResultEnum.accountStopSuccessful);
        }
        throw new RuntimeException();
    }

    @Transactional
    @Override
    public Result updatePassword(int userId) {

        SecurityUser2 byId = securityUserMapper.findById(userId);
        if (CheckUtil.isBlank(byId)) {
            return new Result(ResultEnum.queryFails);
        }

        //是否是初始化密码标识号
        if (byId.getState().equals("1")) {
            //初始化密码匹配
            boolean matches = new BCryptPasswordEncoder().matches("123456", byId.getPassword());
            if (matches) {
                return new Result(ResultEnum.initialPasswordExist);
            }
        }


        int i = securityUserMapper.updatePassword(
                SecurityUser2.builder()
                        .password(new BCryptPasswordEncoder().encode("123456"))
                        .state("1")
                        .updateDate(new Date())
                        .id(userId)
                        .build());
        if (i == 1 || i == 0) {
            return new Result(ResultEnum.RestPwdSuccessful);
        }
        throw new RuntimeException();
    }

    @Transactional
    @Override
    public Result editUser(UpdateUserBean a) {

        List<Object> list = new ArrayList<>();

        //验证是否存在用户id
        SecurityUser2 byId = securityUserMapper.findById(a.getId());
        if (CheckUtil.isBlank(byId)) {
            return new Result(ResultEnum.queryFails);
        }

        //登录名
        if (!byId.getAccount().equals(a.getAccount())) {
            list.add(1);
            List<SecurityUser> byAccount = securityUserMapper.findByAccount(a.getAccount());
            if (byAccount.size() > 1) {
                log.error("账号：" + a.getAccount() + "，数据异常，存在多条数据。");
                return new Result(ResultEnum.moreAccountData);
            } else if (byAccount.size() == 1) {
                return new Result(ResultEnum.accountExist);
            }

        }
        //用户名
        if (!byId.getUname().equals(a.getUname())) {
            list.add(1);
            List<SecurityUser> byUname = securityUserMapper.findByUname(a.getUname());
            if (byUname.size() > 1) {
                log.error("用户名：" + a.getUname() + "，数据异常，存在多条数据。");
                return new Result(ResultEnum.moreUnameData);
            } else if (byUname.size() == 1) {
                return new Result(ResultEnum.unameExist);
            }
        }
        //手机号码
        if (!byId.getMobile().equals(a.getMobile())) {
            list.add(1);
            List<SecurityUser> byMobile = securityUserMapper.findByMobile(a.getMobile());
            if (byMobile.size() > 1) {
                log.error("手机号码：" + a.getMobile() + "，数据异常，存在多条数据。");
                return new Result(ResultEnum.moreMobileData);
            } else if (byMobile.size() == 1) {
                return new Result(ResultEnum.mobileExist);
            }
        }
        //邮箱
        if (!byId.getEmail().equals(a.getEmail())) {
            list.add(1);
            List<SecurityUser> byEmail = securityUserMapper.findByEmail(a.getEmail());
            if (byEmail.size() > 1) {
                log.error("邮箱地址：" + a.getEmail() + "，数据异常，存在多条数据。");
                return new Result(ResultEnum.moreEmailData);
            } else if (byEmail.size() == 1) {
                return new Result(ResultEnum.emailExist);
            }
        }
        //账号启用
        if (!byId.getEnabled().equals(a.getEnabled())) {
            list.add(1);
        }
        //账号过期
        if (!byId.getAccountNonExpired().equals(a.getAccountNonExpired())) {
            list.add(1);
        }
        //账号锁定
        if (!byId.getAccountNonLocked().equals(a.getAccountNonLocked())) {
            list.add(1);
        }
        //密码过期
        if (!byId.getCredentialsNonExpired().equals(a.getCredentialsNonExpired())) {
            list.add(1);
        }
        //省
        if (!byId.getProvince().equals(a.getProvince())) {
            list.add(1);
        }
        //城市
        if (!byId.getCity().equals(a.getCity())) {
            list.add(1);
        }
        //区
        if (!byId.getCounty().equals(a.getCounty())) {
            list.add(1);
        }
        //详细地址
        if (!byId.getAddr().equals(a.getAddr())) {
            list.add(1);
        }
        //性别
        if (!byId.getSex().equals(a.getSex())) {
            list.add(1);
        }


        securityRoleUserMapper.delByUserId(a.getId());

        for (int i = 0; i <a.getRoleList().size() ; i++) {
            SecurityRole securityRole = a.getRoleList().get(i);
            securityRoleUserMapper.addRole(RoleUserBean.builder().userId(a.getId()).roleId(securityRole.getId()).createDate(new Date()).build());
        }

        //
        if (list.size() == 0) {
            return new Result(ResultEnum.editUser);
        }

        a.setUpdateDate(new Date());

        int i = securityUserMapper.updateEditUser(a);
        if (i == 1 || i == 0) {
            return new Result(ResultEnum.updateSuccessful);
        }

        throw new RuntimeException();
    }

    @Override
    public Result startUser(int userId) {

        SecurityUser2 byId = securityUserMapper.findById(userId);
        if (CheckUtil.isBlank(byId)) {
            return new Result(ResultEnum.queryFails);
        }

        //后端限制,防止停用后再次停用
        if (byId.getEnabled().equals("1")) {
            return new Result(ResultEnum.startUserExist);
        }

        //账号停用
        int i = securityUserMapper.stopUser(userId, "1");
        if (i == 1 || i == 0) {
            return new Result(ResultEnum.accountStartSuccessful);
        }
        throw new RuntimeException();
    }

}
