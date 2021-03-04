package com.ezm.dao.mapper.security;

import com.ezm.dao.base.BaseMapper;
import com.ezm.entity.table.LayuiMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface LayuiMenuMapper extends BaseMapper<LayuiMenu> {

    @Select("select * from layui_menu where parent = #{parent}")
    List<LayuiMenu> findByParent(int parent);



    @Delete("delete from layui_menu where sun = #{sun}")
    int delMenu(int sun);

}
