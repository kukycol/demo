package com.ezm.controller.security;

import com.ezm.common.response.Result;
import com.ezm.entity.bean.*;
import com.ezm.service.SecurityRoleService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
@Api(tags = {"角色Controller"}, value = "角色操作接口")
@ApiSupport(author = "梁港生")
@ApiResponses({
        @ApiResponse(code = 0, message = "成功"),
        @ApiResponse(code = 1, message = "失败"),
        @ApiResponse(code = 404, message = "找不到请求路径"),
        @ApiResponse(code = 500, message = "服务器异常")
})
@RestController
@RequestMapping("/role")
public class SecurityRoleController {

    @Resource
    private SecurityRoleService securityRoleService;


    @ApiOperation(value = "获取角色列表", notes = "列表、分页、搜索三合一")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rolePageBean", value = "搜索条件和分页条件dto", dataTypeClass = RolePageBean.class)
    })
    @GetMapping("/findAll")
    public Result findAll(@Valid RolePageBean rolePageBean){
        return securityRoleService.findAll(rolePageBean);
    }



    @ApiOperation(value = "添加角色", notes = "所有字段都需要validation数据校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "addRoleBean", value = "搜索条件和分页条件dto", dataTypeClass = AddRoleBean.class)
    })
    @PostMapping("/addRole")
    public Result addRole(@RequestBody @Validated AddRoleBean addRoleBean){
        return securityRoleService.addRole(addRoleBean);
    }



    @ApiOperation(value = "停用角色", notes = "停用角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色标识号", dataTypeClass = Integer.class,required = true)
    })
    @PutMapping("/stopRole/{roleId}")
    public Result stopRole(@PathVariable("roleId")Integer roleId){
        return securityRoleService.stopRole(roleId);
    }



    @ApiOperation(value = "启用角色", notes = "启用角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色标识号", dataTypeClass = Integer.class,required = true)
    })
    @PutMapping("/startRole/{roleId}")
    public Result startRole(@PathVariable("roleId")Integer roleId){
        return securityRoleService.startRole(roleId);
    }



    @ApiOperation(value = "取消授权列表",  notes = "列表、分页、搜索三合一")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色标识号", dataTypeClass = Integer.class,required = true),
            @ApiImplicitParam(name = "pageBean", value = "角色标识号", dataTypeClass = RulePageBean.class)
    })
    @GetMapping("/findRoleApi1/{roleId}")
    public Result findRoleApi1(@PathVariable("roleId") Integer roleId, @Valid RulePageBean pageBean) {
        pageBean.setRoleId(roleId);
        return securityRoleService.findRoleApi1(pageBean);
    }



    @ApiOperation(value = "取消授权",  notes = "取消授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色标识号", dataTypeClass = Integer.class,required = true),
            @ApiImplicitParam(name = "apiId", value = "权限标识号", dataTypeClass = Integer.class,required = true)
    })
    @DeleteMapping("/delRoleApi/{roleId}")
    public Result delRoleApi(@PathVariable("roleId") Integer roleId, @RequestParam("apiId") int apiId) {
        return securityRoleService.delRoleApi(roleId,apiId);
    }



    @ApiOperation(value = "授权列表",  notes = "列表、分页、搜索三合一")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色标识号", dataTypeClass = Integer.class,required = true),
            @ApiImplicitParam(name = "pageBean", value = "角色标识号", dataTypeClass = RulePageBean.class)
    })
    @GetMapping("/findRoleApi2/{roleId}")
    public Result findRoleApi2(@PathVariable("roleId") Integer roleId,@Valid RulePageBean pageBean) {
        pageBean.setRoleId(roleId);
        return securityRoleService.findRoleApi2(pageBean);
    }



    @ApiOperation(value = "授权",  notes = "授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色标识号", dataTypeClass = Integer.class,required = true),
            @ApiImplicitParam(name = "apiId", value = "权限标识号", dataTypeClass = Integer.class,required = true)
    })
    @PostMapping("/addRoleApi/{roleId}")
    public Result addRoleApi(@PathVariable("roleId") Integer roleId, @RequestParam("apiId") int apiId) {
        return securityRoleService.addRoleApi(roleId,apiId);
    }




    @ApiOperation(value = "编辑角色", notes = "所有字段都需要validation数据校验")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "updateRoleBean", value = "搜索条件和分页条件dto", dataTypeClass = UpdateRoleBean.class)
    })
    @PutMapping("/editRole")
    public Result editRole(@RequestBody @Validated UpdateRoleBean updateRoleBean){
        return securityRoleService.editRole(updateRoleBean);
    }



    @ApiOperation(value = "删除角色", notes = "删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色标识号", dataTypeClass = Integer.class,required = true)
    })
    @DeleteMapping("/delRole/{roleId}")
    public Result delRole(@PathVariable("roleId")Integer roleId){
        return securityRoleService.delRole(roleId);
    }




    @ApiOperation(value = "角色树形菜单",  notes = "角色树形菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色标识号", dataTypeClass = Integer.class,required = true)
    })
    @GetMapping("/findRoleMenu/{roleId}")
    public Result findRoleMenu(@PathVariable("roleId") Integer roleId) {
        return securityRoleService.findRoleMenu(roleId);
    }



    @ApiOperation(value = "授权菜单",  notes = "授权菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "updateMenuBean", value = "更新菜单dto", dataTypeClass = UpdateMenuBean.class),
    })
    @PutMapping("/editMenu")
    public Result editMenu( @RequestBody @Validated UpdateMenuBean updateMenuBean){
        return securityRoleService.editMenu(updateMenuBean);
    }



    @ApiOperation(value = "用户添加角色列表", notes = "角色列表")
    @GetMapping("/findRoleList")
    public Result findRoleList(){
        return securityRoleService.findRoleList();
    }



    @ApiOperation(value = "用户编辑角色列表", notes = "角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户标识号", dataTypeClass = Integer.class,required = true)
    })
    @GetMapping("/findUserRoleList/{userId}")
    public Result findUserRoleList(@PathVariable("userId")Integer userId){
        return securityRoleService.findUserRoleList(userId);
    }


}
