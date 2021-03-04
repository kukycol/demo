package com.ezm.controller.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @projectName(项目名称)： dawdle
 * @Description(描述)： 页面映射层
 * @Author(开发人员)： Kuky
 * @date(日期)： 2021.01.11 05:19
 * @Version(版本)： V1.0
 */
@Controller
public class PageController {

    //user
    @GetMapping("/adminUserList")
    public String adminList(){return "/admin/admin-user-list";}


    @GetMapping("/adminUserEdit")
    public String adminUserEdit(){return "/admin/admin-user-edit";}


    @GetMapping("/adminUserAdd")
    public String adminUserAdd(){return "/admin/admin-user-add";}

    //role
    @GetMapping("/adminRoleList")
    public String adminRoleList(){return "/admin/admin-role-list";}


    @GetMapping("/adminRoleEdit")
    public String adminRoleEdit(){return "/admin/admin-role-edit";}


    @GetMapping("/adminRoleAdd")
    public String adminRoleAdd(){return "/admin/admin-role-add";}


    @GetMapping("/adminRoleMenu")
    public String adminRoleMenu(){return "/admin/admin-role-menu";}


    @GetMapping("/adminRoleApi")
    public String adminRoleApi(){return "/admin/admin-role-api";}


    //rule
    @GetMapping("/adminRuleList")
    public String adminRuleList(){return "/admin/admin-rule-list";}


    @GetMapping("/adminRuleEdit")
    public String adminRuleEdit(){return "/admin/admin-rule-edit";}


    @GetMapping("/adminRuleAdd")
    public String adminRuleAdd(){return "/admin/admin-rule-add";}


    @GetMapping("/adminRuleMenu")
    public String adminMenuList(){return "/admin/admin-rule-menu";}




    //echarts

    @GetMapping("/echarts/{page}")
    public String echarts(@PathVariable("page")int page){return "/echarts/echarts"+page;}

    //it

    @GetMapping("/itCate")
    public String itCate(){return "/it/cate";}


    @GetMapping("/itCity")
    public String itCity(){return "/it/city";}


    @GetMapping("/itDemo")
    public String itDemo(){return "/it/demo";}


    @GetMapping({"/itIndex","/"})
    public String itIndex(){return "/it/index";}


    @GetMapping("/Error")
    public String itError(){return "/error";}


    @GetMapping("/itLog")
    public String itLog(){return "/it/log";}


    @GetMapping("/Login")
    public String itLogin(){return "/login";}


    @GetMapping("/itRoleAdd")
    public String itRoleAdd(){return "/it/role-add";}


    @GetMapping("/itUnicode")
    public String itUnicode(){return "/it/unicode";}


    @GetMapping("/itWelcome/{page}")
    public String itWelcome(@PathVariable("page")int page){return "/it/welcome"+page;}


    //member

    @GetMapping("/memberAdd")
    public String memberAdd(){return "/member/member-add";}


    @GetMapping("/memberDel")
    public String memberDel(){return "/member/member-del";}


    @GetMapping("/memberEdit")
    public String memberEdit(){return "/member/member-edit";}


    @GetMapping("/memberPassword")
    public String memberPassword(){return "/member/member-password";}


    @GetMapping("/memberList/{page}")
    public String memberList(@PathVariable("page")int page){return "/member/member-list"+page;}


    //order

    @GetMapping("/orderAdd")
    public String orderAdd(){return "/order/order-add";}


    @GetMapping("/orderList/{page}")
    public String orderList(@PathVariable("page")int page){return "/order/order-list"+page;}



}
