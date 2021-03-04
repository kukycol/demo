package com.ezm.service.impl;

import com.ezm.common.response.Result;
import com.ezm.common.response.ResultEnum;
import com.ezm.dao.mapper.security.LayuiMenuMapper;
import com.ezm.dao.mapper.security.SecurityRoleRuleMapper;
import com.ezm.dao.mapper.security.SecurityRuleMapper;
import com.ezm.entity.bean.*;
import com.ezm.entity.table.LayuiMenu;
import com.ezm.entity.table.SecurityRoleRule;
import com.ezm.entity.table.SecurityRule;
import com.ezm.service.SecurityRuleService;
import com.ezm.utils.CheckUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SecurityRuleServiceImpl implements SecurityRuleService {

    @Resource
    private SecurityRuleMapper securityRuleMapper;

    @Resource
    private SecurityRoleRuleMapper securityRoleRuleMapper;

    @Resource
    private LayuiMenuMapper layuiMenuMapper;

    //api列表
    @Override
    public Result findAll(RulePageBean rulePageBean) {

        PageHelper.startPage(rulePageBean.getPage(), rulePageBean.getLimit());

        List<SecurityRule> securityRules = securityRuleMapper.findAll(rulePageBean);

        PageInfo<SecurityRule> of = PageInfo.of(securityRules);

        return new Result(ResultEnum.querySuccessful, securityRules, of.getTotal());
    }

    //停用api
    @Override
    public Result stopRule(int ruleId) {

        SecurityRule byId = securityRuleMapper.findById(ruleId);
        if (CheckUtil.isBlank(byId)) {
            return new Result(ResultEnum.queryFails);
        }

        securityRoleRuleMapper.stopRoleRules(ruleId);

        SecurityRule build = SecurityRule.builder().updateDate(new Date()).enabled("0").id(ruleId).build();
        int i = securityRuleMapper.stopRule(build);
        if (i == 1) {
            return new Result(ResultEnum.updateSuccessful);
        }
        throw new RuntimeException();
    }

    //添加api
    @Override
    public Result addRule(AddRuleBean addRuleBean) {

        SecurityRule byRule = securityRuleMapper.findByRule(addRuleBean.getRule());
        if (!CheckUtil.isBlank(byRule)) {
            return new Result(ResultEnum.queryFails);
        }

        SecurityRule byRule1 = securityRuleMapper.findByRuleName(addRuleBean.getRuleName());
        if (!CheckUtil.isBlank(byRule1)) {
            return new Result(ResultEnum.queryFails);
        }

        Date date = new Date();
        addRuleBean.setCreateDate(date);
        addRuleBean.setUpdateDate(date);
        int i = securityRuleMapper.addRule(addRuleBean);
        if (i == 1) {
            return new Result(ResultEnum.addSuccessful);
        }
        throw new RuntimeException();
    }

    //删除api
    @Override
    public Result delRule(int ruleId) {

        SecurityRule byId = securityRuleMapper.findById(ruleId);
        if (CheckUtil.isBlank(byId)) {
            return new Result(ResultEnum.queryFails);
        }

        securityRoleRuleMapper.delRoleRuleByRuleID(ruleId);

        int i = securityRuleMapper.delRule(ruleId);
        if (i == 1 || i == 0) {
            return new Result(ResultEnum.deleteSuccessful);
        }
        throw new RuntimeException();
    }

    @Override
    public Result editRule(UpdateRuleBean updateRuleBean) {

        SecurityRule byId = securityRuleMapper.findById(updateRuleBean.getId());
        if (CheckUtil.isBlank(byId)) {
            return new Result(ResultEnum.queryFails);
        }

        if (!byId.getRule().equals(updateRuleBean.getRule())) {
            SecurityRule byRule = securityRuleMapper.findByRule(updateRuleBean.getRule());
            if (!CheckUtil.isBlank(byRule)) {
                return new Result(ResultEnum.queryFails);
            }
        }


        if (!byId.getRuleName().equals(updateRuleBean.getRuleName())) {
            SecurityRule byRule1 = securityRuleMapper.findByRuleName(updateRuleBean.getRuleName());
            if (!CheckUtil.isBlank(byRule1)) {
                return new Result(ResultEnum.queryFails);
            }
        }

        updateRuleBean.setUpdateDate(new Date());
        int i = securityRuleMapper.editRule(updateRuleBean);
        if (i == 1){
            return new Result(ResultEnum.updateSuccessful);
        }

        throw new RuntimeException();
    }

    /**
     * 查询菜单列表
     *
     * @return
     */
    @Override
    public Result findMenuList() {

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
                    menuTreeBean3.setId(sunId);
                    menuTreeBean3.setTitle(securityRule.getRuleName());
                    menuTreeBean3.setRule(securityRule.getRule());
                    menuTreeBean3.setChildren(new ArrayList<>());
                    list5.add(menuTreeBean3);

                    //
                    List<LayuiMenu> byParent3 = layuiMenuMapper.findByParent(sunId);
                    if (byParent3.size() == 0) {
                        menuTreeBean.setChildren(new ArrayList<>());
                    } else {
                        List<MenuTreeBean> list6 = new ArrayList<>();
                        for (int k = 0; k < byParent3.size(); k++) {

                            LayuiMenu layuiMenu2 = byParent3.get(k);
                            Integer sunId2 = layuiMenu2.getSun();
                            SecurityRule securityRule2 = securityRuleMapper.selectByPrimaryKey(sunId2);
                            MenuTreeBean menuTreeBean4 = new MenuTreeBean();
                            menuTreeBean4.setId(sunId2);
                            menuTreeBean4.setTitle(securityRule2.getRuleName());
                            menuTreeBean4.setRule(securityRule2.getRule());
                            list6.add(menuTreeBean4);
                            menuTreeBean4.setChildren(new ArrayList<>());
                        }
                        menuTreeBean3.setChildren(list6);

                    }


                }
                menuTreeBean.setChildren(list5);
            }
        }


        return new Result(ResultEnum.querySuccessful, list1);
    }


    /**
     * 添加菜单
     *
     * @param menuId
     * @return
     */
    @Override
    public Result addMenu(int menuId) {

        //父级菜单
        SecurityRule securityRule = securityRuleMapper.findById(menuId);
        if (CheckUtil.isBlank(securityRule)) {
            return new Result(ResultEnum.queryFails);
        }

        //等级 +1
        Integer level = securityRule.getLevel();
        Integer i = level + 1;
        Date date = new Date();


        securityRuleMapper.addMenu(SecurityRule.builder()
                .enabled("1")
                .updateDate(date)
                .createDate(date)
                .ruleName("新建默认节点")
                .level(i)
                .build());


        WeekendSqls<SecurityRule> sqls = WeekendSqls.custom();
        sqls.andEqualTo(SecurityRule::getCreateDate, date);


        SecurityRule securityRule2 = securityRuleMapper.selectOneByExample(Example.builder(SecurityRule.class).where(sqls).build());
        Integer id = securityRule2.getId();
        int i1 = layuiMenuMapper.insertSelective(LayuiMenu.builder().parent(menuId).sun(id).createDate(new Date()).build());
        if (i1 == 1) {
            return new Result(ResultEnum.addSuccessful);
        }
        throw new RuntimeException();
    }


    /**
     * 修改节点名称
     * @param updateRuleNameBean
     * @return
     */
    @Override
    public Result updateMenuName(UpdateRuleNameBean updateRuleNameBean) {

        //查询权限id
        SecurityRule byId = securityRuleMapper.findById(updateRuleNameBean.getId());
        if (CheckUtil.isBlank(byId)) {
            return new Result(ResultEnum.queryFails);
        }


        if (byId.getRuleName().equals(updateRuleNameBean.getRuleName())){
            return new Result(ResultEnum.editUser);
        }


        updateRuleNameBean.setUpdateDate(new Date());
        int i = securityRuleMapper.updateRuleName(updateRuleNameBean);
        if (i == 1){
            return new Result(ResultEnum.updateSuccessful);
        }
        throw new RuntimeException();
    }


    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    @Override
    public Result delMenu(int menuId) {
        //存在子菜单不能删除
        List<LayuiMenu> byParent = layuiMenuMapper.findByParent(menuId);
        if (byParent.size() >0 ){
            return new Result(ResultEnum.deleteFails);
        }

        int i = securityRuleMapper.deleteByPrimaryKey(menuId);

        int i1 = layuiMenuMapper.delMenu(menuId);

        if (i1 == 1){
            return new Result(ResultEnum.deleteSuccessful);
        }
        throw new RuntimeException();

    }

    @Override
    public Result startRule(Integer ruleId) {
        SecurityRule byId = securityRuleMapper.findById(ruleId);
        if (CheckUtil.isBlank(byId)) {
            return new Result(ResultEnum.queryFails);
        }

        securityRoleRuleMapper.stopRoleRules(ruleId);

        SecurityRule build = SecurityRule.builder().updateDate(new Date()).enabled("1").id(ruleId).build();
        int i = securityRuleMapper.stopRule(build);
        if (i == 1) {
            return new Result(ResultEnum.updateSuccessful);
        }
        throw new RuntimeException();
    }

    @Override
    public Result updateMenuAddr(UpdateRuleAddrBean updateRuleAddrBean) {
        //查询权限id
        SecurityRule byId = securityRuleMapper.findById(updateRuleAddrBean.getId());
        if (CheckUtil.isBlank(byId)) {
            return new Result(ResultEnum.queryFails);
        }


        if (byId.getRule().equals(updateRuleAddrBean.getRule())){
            return new Result(ResultEnum.editUser);
        }


        updateRuleAddrBean.setUpdateDate(new Date());
        int i = securityRuleMapper.updateRuleAddr(updateRuleAddrBean);
        if (i == 1){
            return new Result(ResultEnum.updateSuccessful);
        }
        throw new RuntimeException();
    }


}
