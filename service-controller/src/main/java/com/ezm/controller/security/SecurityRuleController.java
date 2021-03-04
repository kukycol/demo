package com.ezm.controller.security;

import com.ezm.common.response.Result;
import com.ezm.entity.bean.*;
import com.ezm.entity.table.SecurityRule;
import com.ezm.service.SecurityRuleService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
@Api(tags = {"权限Controller"}, value = "权限操作接口")
@ApiSupport(author = "梁港生")
@ApiResponses({
        @ApiResponse(code = 0, message = "成功"),
        @ApiResponse(code = 1, message = "失败"),
        @ApiResponse(code = 404, message = "找不到请求路径"),
        @ApiResponse(code = 500, message = "服务器异常")
})
@RestController
@RequestMapping("/rule")
public class SecurityRuleController {

    @Resource
    private SecurityRuleService securityRuleService;



    @ApiOperation(value = "获取权限列表", notes = "列表、分页、搜索三合一")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rulePageBean", value = "搜索条件和分页条件dto", dataTypeClass = RulePageBean.class)
    })
    @GetMapping("/findAll")
    public Result findAll(@Valid RulePageBean rulePageBean){
        return securityRuleService.findAll(rulePageBean);
    }



    @ApiOperation(value = "停用权限", notes = "停用权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ruleId", value = "权限标识号", dataTypeClass = Integer.class, required = true)
    })
    @PutMapping("/stopRule/{ruleId}")
    public Result stopRule(@PathVariable("ruleId")Integer ruleId){
        return securityRuleService.stopRule(ruleId);
    }



    @ApiOperation(value = "停用权限", notes = "停用权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ruleId", value = "权限标识号", dataTypeClass = Integer.class, required = true)
    })
    @PutMapping("/startRule/{ruleId}")
    public Result startRule(@PathVariable("ruleId")Integer ruleId){
        return securityRuleService.startRule(ruleId);
    }


    @ApiOperation(value = "添加权限", notes = "所有字段都需要validation数据校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "addRuleBean", value = "添加权限dto", dataTypeClass = AddRuleBean.class)
    })
    @PostMapping("/addRule")
    public Result addRule(@RequestBody @Validated AddRuleBean addRuleBean){
        return securityRuleService.addRule(addRuleBean);
    }


    @ApiOperation(value = "删除权限", notes = "删除权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ruleId", value = "权限标识号", dataTypeClass = Integer.class, required = true)
    })
    @DeleteMapping("/delRule/{ruleId}")
    public Result delRule(@PathVariable("ruleId")Integer ruleId){
        return securityRuleService.delRule(ruleId);
    }



    @ApiOperation(value = "编辑权限", notes = "所有字段都需要validation数据校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "updateRuleBean", value = "编辑权限dto", dataTypeClass = UpdateRuleBean.class)
    })
    @PutMapping("/editRule")
    public Result editRule(@RequestBody @Validated UpdateRuleBean updateRuleBean){
        return securityRuleService.editRule(updateRuleBean);
    }



    @ApiOperation(value = "树形菜单列表", notes = "树形菜单列表")
    @GetMapping("/findMenuList")
    public Result findMenuList(){
        return securityRuleService.findMenuList();
    }



    @ApiOperation(value = "添加菜单", notes = "添加菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单标识号", dataTypeClass = Integer.class, required = true)
    })
    @PostMapping("/addMenu/{menuId}")
    public Result addMenu(@PathVariable("menuId") Integer menuId){
        return securityRuleService.addMenu(menuId);
    }



    @ApiOperation(value = "更新菜单名称", notes = "所有字段都需要validation数据校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "updateRuleNameBean", value = "更新菜单名称", dataTypeClass = UpdateRuleNameBean.class)
    })
    @PutMapping("/updateMenuName")
    public Result updateMenuName(@RequestBody @Validated UpdateRuleNameBean updateRuleNameBean){
        return securityRuleService.updateMenuName(updateRuleNameBean);
    }



    @ApiOperation(value = "编辑菜单地址", notes = "所有字段都需要validation数据校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "updateRuleAddrBean", value = "编辑菜单地址dto", dataTypeClass = UpdateRuleAddrBean.class)
    })
    @PutMapping("/updateMenuAddr")
    public Result updateMenuAddr(@RequestBody @Validated UpdateRuleAddrBean updateRuleAddrBean){
        return securityRuleService.updateMenuAddr(updateRuleAddrBean);
    }



    @ApiOperation(value = "删除菜单", notes = "删除菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单标识号", dataTypeClass = Integer.class, required = true)
    })
    @DeleteMapping("/delMenu/{menuId}")
    public Result delMenu(@PathVariable("menuId") Integer menuId){
        return securityRuleService.delMenu(menuId);
    }



}
