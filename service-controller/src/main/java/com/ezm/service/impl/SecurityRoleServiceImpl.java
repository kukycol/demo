package com.ezm.service.impl;

import com.ezm.common.response.Result;
import com.ezm.common.response.ResultEnum;
import com.ezm.dao.mapper.security.*;
import com.ezm.entity.bean.*;
import com.ezm.entity.table.*;
import com.ezm.service.SecurityRoleService;
import com.ezm.utils.CheckUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SecurityRoleServiceImpl implements SecurityRoleService {

    @Resource
    private SecurityRoleMapper securityRoleMapper;

    @Resource
    private SecurityRoleUserMapper securityRoleUserMapper;

    @Resource
    private SecurityRoleRuleMapper securityRoleRuleMapper;

    @Resource
    private SecurityUserMapper securityUserMapper;

    @Resource
    private SecurityRuleMapper securityRuleMapper;

    @Resource
    private LayuiMenuMapper layuiMenuMapper;

    @Override
    public Result findAll(RolePageBean r) {

        PageHelper.startPage(r.getPage(), r.getLimit());

        WeekendSqls<SecurityRole> sqls = WeekendSqls.custom();

        if (StringUtils.isNotBlank(r.getRole())) {
            sqls.andEqualTo(SecurityRole::getRole, r.getRole());
        }
        if (StringUtils.isNotBlank(r.getRoleName())) {
            sqls.andEqualTo(SecurityRole::getRoleName, r.getRoleName());
        }

        List<SecurityRole> securityRoles = securityRoleMapper.selectByExample(Example.builder(SecurityRole.class).where(sqls).build());

        for (int i = 0; i < securityRoles.size(); i++) {
            SecurityRole securityRole = securityRoles.get(i);

            List<SecurityRoleRule> byRoleId = securityRoleRuleMapper.findByRoleId(securityRole.getId());
            if (byRoleId.size() > 0) {
                securityRole.setRule(true);
            }

            List<SecurityRoleUser> byRoleId1 = securityRoleUserMapper.findByRoleId(securityRole.getId());
            if (byRoleId1.size() > 0) {
                securityRole.setUser(true);
            }

        }

        PageInfo<SecurityRole> of = PageInfo.of(securityRoles);

        return new Result(ResultEnum.querySuccessful, securityRoles, of.getTotal());
    }

    //添加角色
    @Override
    @Transactional
    public Result addRole(AddRoleBean addRoleBean) {

        //角色名称
        SecurityRole byRoleName = securityRoleMapper.findByRoleName(addRoleBean.getRoleName());
        if (!CheckUtil.isBlank(byRoleName)) {
            return new Result(ResultEnum.roleNameExist);
        }


        //角色
        SecurityRole byRole = securityRoleMapper.findByRole(addRoleBean.getRole());
        if (!CheckUtil.isBlank(byRole)) {
            return new Result(ResultEnum.roleExist);
        }

        addRoleBean.setCreateDate(new Date());
        addRoleBean.setUpdateDate(new Date());
        int i = securityRoleMapper.addRole(addRoleBean);
        if (i == 1) {
            return new Result(ResultEnum.addSuccessful);
        }
        throw new RuntimeException();
    }

    //删除角色
    @Transactional
    @Override
    public Result delRole(int roleId) {

        //角色是否存在
        SecurityRole byId = securityRoleMapper.findById(roleId);
        if (CheckUtil.isBlank(byId)) {
            return new Result(ResultEnum.queryFails);
        }

        //角色权限列表
        List<SecurityRoleRule> byRoleId = securityRoleRuleMapper.findByRoleId(roleId);
        if (byRoleId.size() > 0) {
            return new Result(ResultEnum.roleRuleExist);
        }

        //角色用户列表
        List<SecurityRoleUser> byRoleId1 = securityRoleUserMapper.findByRoleId(roleId);
        if (byRoleId1.size() > 0) {
            return new Result(ResultEnum.roleUserExist);
        }

        int i = securityRoleMapper.delRole(roleId);
        if (i == 1) {
            return new Result(ResultEnum.deleteSuccessful);
        }
        throw new RuntimeException();
    }

    /**
     * 停用角色也会停用角色绑定的用户
     * 如果用户只绑定一个角色则停用该用户,则绑定两个角色则不需要停用该用户
     *
     * @param roleId
     * @return
     */
    @Override
    public Result stopRole(int roleId) {
        String enabled = "0";

        //角色用户列表
        List<SecurityRoleUser> byRoleId1 = securityRoleUserMapper.findByRoleId(roleId);
        for (int i = 0; i < byRoleId1.size(); i++) {
            SecurityRoleUser securityRoleUser = byRoleId1.get(i);
            securityRoleUser.setEnabled(enabled);
            securityRoleUserMapper.stopUser(securityRoleUser);
            List<SecurityRoleUser> byUserId = securityRoleUserMapper.findByUserId(securityRoleUser.getUserId());
            if (byUserId.size() == 1) {
                securityUserMapper.stopUser(securityRoleUser.getUserId(), enabled);
            }

        }

        int i = securityRoleMapper.stopRole(SecurityRole.builder().enabled(enabled).id(roleId).build());
        if (i == 1) {
            return new Result(ResultEnum.updateSuccessful);
        }
        throw new RuntimeException();
    }

    //修改用户信息
    @Override
    public Result editRole(UpdateRoleBean updateRoleBean) {

        if (updateRoleBean.getId() == 0) {
            return new Result(ResultEnum.queryFails);
        }

        //角色是否存在
        SecurityRole byId = securityRoleMapper.findById(updateRoleBean.getId());
        if (CheckUtil.isBlank(byId)) {
            return new Result(ResultEnum.queryFails);
        }

        List<Integer> objects = new ArrayList<>();

        if (!byId.getRoleName().equals(updateRoleBean.getRoleName())) {
            objects.add(1);
            SecurityRole byRoleName = securityRoleMapper.findByRoleName(updateRoleBean.getRoleName());
            if (!CheckUtil.isBlank(byRoleName)) {
                return new Result(ResultEnum.roleNameExist);
            }
        }

        if (!byId.getRole().equals(updateRoleBean.getRole())) {
            objects.add(1);
            SecurityRole byRole = securityRoleMapper.findByRole(updateRoleBean.getRole());
            if (!CheckUtil.isBlank(byRole)) {
                return new Result(ResultEnum.roleExist);
            }
        }

        if (!byId.getEnabled().equals(updateRoleBean.getEnabled())) {
            objects.add(1);
        }

        if (objects.size() == 0) {
            return new Result(ResultEnum.editUser);
        }

        updateRoleBean.setUpdateDate(new Date());
        int i = securityRoleMapper.editRole(updateRoleBean);
        if (i == 1) {
            return new Result(ResultEnum.updateSuccessful);
        }
        throw new RuntimeException();
    }

    //授权
    @Override
    public Result findRoleApi1(RulePageBean pageBean) {


        //PageHelper分页插件开始分页，前端传page、limit两参过来
        PageHelper.startPage(pageBean.getPage(), pageBean.getLimit());

        List<SecurityRule> byRoleAndApi = securityRuleMapper.findByRoleAndApi1(pageBean);
        //结束分页，获取分页信息
        PageInfo<SecurityRule> of = PageInfo.of(byRoleAndApi);


        return new Result(ResultEnum.querySuccessful, byRoleAndApi, of.getTotal());
    }

    //取消授权
    @Override
    public Result findRoleApi2(RulePageBean pageBean) {

        //PageHelper分页插件开始分页，前端传page、limit两参过来
        PageHelper.startPage(pageBean.getPage(), pageBean.getLimit());

        List<SecurityRule> byRoleAndApi = securityRuleMapper.findByRoleAndApi2(pageBean);
        //结束分页，获取分页信息
        PageInfo<SecurityRule> of = PageInfo.of(byRoleAndApi);


        return new Result(ResultEnum.querySuccessful, byRoleAndApi, of.getTotal());
    }

    @Override
    public Result addRoleApi(int roleId, int apiId) {

        int i = securityRoleRuleMapper.insertSelective(SecurityRoleRule.builder()
                .createDate(new Date())
                .roleId(roleId)
                .ruleId(apiId)
                .build());
        if (i == 1) {
            return new Result(ResultEnum.addSuccessful);
        }
        throw new RuntimeException();
    }

    @Override
    public Result delRoleApi(int roleId, int apiId) {

        int i = securityRoleRuleMapper.delete(SecurityRoleRule.builder()
                .ruleId(apiId)
                .roleId(roleId)
                .build());

        if (i == 1) {
            return new Result(ResultEnum.deleteSuccessful);
        }
        throw new RuntimeException();
    }

    @Override
    public Result findRoleMenu(int roleId) {

        //每个角色拥有的菜单
        SecurityRole securityRole = securityRoleMapper.selectByPrimaryKey(roleId);
        if (CheckUtil.isBlank(securityRole)) {
            return new Result(ResultEnum.queryFails);
        }


        List<MenuTreeBean> list1 = new ArrayList<>();

        List<MenuTreeBean> list2 = new ArrayList<>();


        WeekendSqls<SecurityRule> sqls1 = WeekendSqls.custom();
        sqls1.andEqualTo(SecurityRule::getLevel, 1);
        List<SecurityRule> securityRules1 = securityRuleMapper.selectByExample(Example.builder(SecurityRule.class).where(sqls1).build());
        for (int j = 0; j < securityRules1.size(); j++) {
            SecurityRule securityRule = securityRules1.get(j);

            MenuTreeBean menuTreeBean1 = new MenuTreeBean();
            menuTreeBean1.setId(securityRule.getId());
            menuTreeBean1.setTitle(securityRule.getRuleName());
            list1.add(menuTreeBean1);

        }


        for (int i = 0; i < list1.size(); i++) {
            MenuTreeBean menuTreeBean = list1.get(i);
            int ruleId = menuTreeBean.getId();

            List<LayuiMenu> byParent1 = layuiMenuMapper.findByParent(ruleId);
            for (int j = 0; j < byParent1.size(); j++) {
                LayuiMenu layuiMenu = byParent1.get(j);
                Integer sunId = layuiMenu.getSun();
                SecurityRule securityRule = securityRuleMapper.selectByPrimaryKey(sunId);

                MenuTreeBean menuTreeBean2 = new MenuTreeBean();
                menuTreeBean2.setId(sunId);
                menuTreeBean2.setTitle(securityRule.getRuleName());
                menuTreeBean2.setRule(securityRule.getRule());

                list2.add(menuTreeBean2);
            }
            menuTreeBean.setChildren(list2);
        }

        long a = 0;
        List<SecurityRule> byRoleId = securityRuleMapper.findByRoleId(roleId);
        for (int i = 0; i < list2.size(); i++) {
            MenuTreeBean menuTreeBean = list2.get(i);
            int ruleId = menuTreeBean.getId();
            List<LayuiMenu> byParent2 = layuiMenuMapper.findByParent(ruleId);
            if (byParent2.size() == 0) {
                menuTreeBean.setChildren(new ArrayList<>());
            } else {
                List<MenuTreeBean> list5 = new ArrayList<>();
                for (int j = 0; j < byParent2.size(); j++) {

                    LayuiMenu layuiMenu = byParent2.get(j);
                    Integer sunId = layuiMenu.getSun();
                    SecurityRule securityRule = securityRuleMapper.selectByPrimaryKey(sunId);
                    MenuTreeBean menuTreeBean3 = new MenuTreeBean();

                    boolean contains = byRoleId.contains(securityRule);
                    if (contains) {
                        a+=1;
                        menuTreeBean3.setChecked(true);
                    }
                    menuTreeBean3.setId(sunId);
                    menuTreeBean3.setTitle(securityRule.getRuleName());
                    menuTreeBean3.setRule(securityRule.getRule());
                    menuTreeBean3.setChildren(new ArrayList<>());
                    list5.add(menuTreeBean3);

                }
                menuTreeBean.setChildren(list5);
            }
        }


        return new Result(ResultEnum.generateSuccessful, list1,a);
    }

    //角色菜单
    @Override
    public Result editMenu(UpdateMenuBean updateMenuBean) {

        List<MenuTreeBean> menuTreeBeanList = updateMenuBean.getMenuTreeBeanList();
        for (int i = 0; i < menuTreeBeanList.size(); i++) {
            MenuTreeBean menuTreeBean = menuTreeBeanList.get(i);
            int id = menuTreeBean.getId();
            updateMenuBean.setCreateDate(new Date());
            updateMenuBean.setRuleId(id);
            if (updateMenuBean.getFlag()) {
                securityRoleRuleMapper.addRoleRule(updateMenuBean);
            } else {
                securityRoleRuleMapper.delRoleRule(updateMenuBean);
            }

        }
        return new Result(ResultEnum.operationSuccessful);
    }

    @Override
    public Result findRoleList() {

        WeekendSqls<SecurityRole> sqls = WeekendSqls.custom();
        sqls.andEqualTo(SecurityRole::getEnabled, "1");
        List<SecurityRole> securityRoles = securityRoleMapper.selectByExample(Example.builder(SecurityRole.class).where(sqls).build());

        return new Result(ResultEnum.querySuccessful, securityRoles);
    }

    @Override
    public Result findUserRoleList(int userId) {

        WeekendSqls<SecurityRole> sqls = WeekendSqls.custom();
        sqls.andEqualTo(SecurityRole::getEnabled, "1");
        List<SecurityRole> securityRoles = securityRoleMapper.selectByExample(Example.builder(SecurityRole.class).where(sqls).build());
        for (int i = 0; i < securityRoles.size(); i++) {
            SecurityRole securityRole = securityRoles.get(i);
            SecurityRoleUser byUserIdAndRoleId = securityRoleUserMapper.findByUserIdAndRoleId(
                    RoleUserBean.builder().roleId(securityRole.getId()).userId(userId).build());
            if (!CheckUtil.isBlank(byUserIdAndRoleId)) {
                securityRole.setLAY_CHECKED(true);
            }

        }

        return new Result(ResultEnum.querySuccessful, securityRoles);
    }

    @Override
    public Result startRole(Integer roleId) {
        //角色用户列表
        String enabled = "1";
        List<SecurityRoleUser> byRoleId1 = securityRoleUserMapper.findByRoleId(roleId);
        for (int i = 0; i < byRoleId1.size(); i++) {
            SecurityRoleUser securityRoleUser = byRoleId1.get(i);
            securityRoleUser.setEnabled(enabled);
            securityRoleUserMapper.stopUser(securityRoleUser);
            List<SecurityRoleUser> byUserId = securityRoleUserMapper.findByUserId(securityRoleUser.getUserId());
            if (byUserId.size() == 1) {
                securityUserMapper.stopUser(securityRoleUser.getUserId(), enabled);
            }

        }

        int i = securityRoleMapper.stopRole(SecurityRole.builder().enabled(enabled).id(roleId).build());
        if (i == 1) {
            return new Result(ResultEnum.updateSuccessful);
        }
        throw new RuntimeException();
    }

}
