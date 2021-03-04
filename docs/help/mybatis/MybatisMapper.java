package com.ezm.dao.example;


import org.apache.ibatis.annotations.*;
import org.junit.platform.commons.util.StringUtils;

import java.util.List;

public interface MybatisMapper {

    //静态查询
    @Select("select * from user")
    List<User> findUser();

    //条件查询
    @Select("select * from user where id = #{id}")
    List<User> findUserById(int id);

    //模糊查询
    @Select("select * from user where username like '%#{username}%'")
    List<User> findUserById(String username);



    //动态查询
    @SelectProvider(method = "findUserSearch",type = MybatisMapperProvider.class)
    List<User> findUserSearch(User user);



    //静态删除
    @Delete("delete from user where id = #{id}")
    int delUser(int id);

    //动态删除
    @DeleteProvider(method = "delBat",type  = MybatisMapperProvider.class)
    int delBat(List<Integer> ids);



    //静态更新
    @Update("update user set username = #{username}")
    int updateByUsername(String username);

    //批量更新
    @UpdateProvider(method = "updateByIds",type = MybatisMapperProvider.class)
    int updateByIds(List<Integer> ids);



    //静态添加
    @Insert("insert into user(username,email,mobile) values(#{username},#{email},#{mobile})")
    int addUser(User user);

    //批量添加
    @InsertProvider(method = "addUsers",type = MybatisMapperProvider.class)
    int addUsers(List<User> users);


    class MybatisMapperProvider{

        //动态查询
        public String findUserSearch(User user) {
            StringBuffer sb = new StringBuffer();
            sb.append("select * from user where 1 = 1");
            if (StringUtils.isNotBlank(user.getEmail())){
                sb.append(" and email = #{email}");
            }
            if (StringUtils.isNotBlank(user.getMobile())){
                sb.append(" and mobile = #{mobile}");
            }
            if (StringUtils.isNotBlank(user.getUsername())){
                sb.append(" and username = #{username}");
            }
            return sb.toString();
        }

        //动态删除(批量删除)
        public String delBat(List<Integer> ids){
            String s =  ids.toString().replaceAll("[\\[\\]]", "");
            StringBuffer sb = new StringBuffer();
            sb.append("delete from user where id in (");
            sb.append(s);
            sb.append(")");
            return sb.toString();
        }

        //动态更新
        public String updateByIds(List<Integer> ids){
          return null;
        }

        //批量添加
        public String addUsers(List<User> users){
            StringBuffer sb = new StringBuffer();
            sb.append("insert into user(username,email,mobile) values");
            for (User user : users) {
                sb.append("(#{username},#{email},#{mobile}),");
            }
            sb.substring(0,sb.length()-1);
            return sb.toString();
        }


    }

}
