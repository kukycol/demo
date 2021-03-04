package com.ezm.dao.mapper.security;

import com.ezm.dao.base.BaseMapper;
import com.ezm.entity.bean.AddUserBean;
import com.ezm.entity.bean.UpdateUserBean;
import com.ezm.entity.bean.UserPageBean;
import com.ezm.entity.table.SecurityUser;
import com.ezm.entity.table.SecurityUser2;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface SecurityUserMapper extends BaseMapper<SecurityUser> {


    @Select("select * from security_user where id = #{id}")
    SecurityUser2 findById(int id);

    @Update("update security_user SET password = #{password},state = #{state},update_date = #{updateDate} WHERE id = #{id}")
    int updatePassword(SecurityUser2 securityUser2);


    @Update("update security_user SET enabled = #{enabled} WHERE id = #{userId}")
    int stopUser(@Param("userId") int userId,@Param("enabled")String enabled);

    @Delete("DELETE from security_user WHERE id =  #{userId}")
    int delUser(int userId);

    @Insert("insert into security_user" +
            " (`uname`, `account`, `password`, `enabled`, `sex`, `credentials_non_expired`, `account_non_locked`, `account_non_expired`, `email`, `mobile`, `state`, `create_date`, `update_date`, `addr`, `province`, `city`, `county`) " +
            " VALUES(#{uname},#{account},#{password},#{enabled},#{sex},#{credentialsNonExpired},#{accountNonLocked},#{accountNonExpired},#{email},#{mobile},#{state},#{createDate},#{updateDate},#{addr},#{province},#{city},#{county})")
    int addUser(AddUserBean a);

    @Select("SELECT `id`, `uname`, `account`, `password`, `enabled`, `sex`, `credentials_non_expired`, `account_non_locked`, `account_non_expired`, `email`, `mobile`, `state`, `create_date`, `update_date`, `addr`, `province`, `city`, `county` from security_user where account = #{account}")
    List<SecurityUser> findByAccount(String account);

    @Select("SELECT `id`, `uname`, `account`, `password`, `enabled`, `sex`, `credentials_non_expired`, `account_non_locked`, `account_non_expired`, `email`, `mobile`, `state`, `create_date`, `update_date`, `addr`, `province`, `city`, `county` from security_user where account = #{account}")
    SecurityUser findByAccount2(String account);

    @Select("SELECT * from security_user where uname = #{uname}")
    List<SecurityUser> findByUname(String uname);

    @Select("SELECT * from security_user where mobile = #{mobile}")
    List<SecurityUser> findByMobile(String mobile);

    @Select("SELECT * from security_user where email = #{email}")
    List<SecurityUser> findByEmail(String email);

    
    @SelectProvider(method = "findAll", type = SecurityUserProvider.class)
    List<SecurityUser2> findAll(UserPageBean u);

    @Update("update security_user set uname = #{uname}, account = #{account},  enabled = #{enabled}, sex = #{sex}, credentials_non_expired = #{credentialsNonExpired}, account_non_locked = #{accountNonLocked}, account_non_expired = #{accountNonExpired}, email = #{email},\n" +
            "mobile = #{mobile}, update_date = #{updateDate}, addr = #{addr}, province = #{province}, city = #{city}, county = #{county} where id = #{id}")
    int updateEditUser(UpdateUserBean a);


    class SecurityUserProvider {

        public String findAll(UserPageBean u) {
            StringBuffer sb = new StringBuffer();

            sb.append("SELECT * from security_user WHERE 1 = 1");

            if (StringUtils.isNotBlank(u.getAccount())){
                sb.append(" and account = #{account}");
            }

            if (StringUtils.isNotBlank(u.getUname())){
                sb.append(" and uname = #{uname}");
            }

            if (StringUtils.isNotBlank(u.getEmail())){
                sb.append(" and email = #{email}");
            }

            if (StringUtils.isNotBlank(u.getMobile())){
                sb.append(" and mobile = #{mobile}");
            }

            if (StringUtils.isNotBlank(u.getSex())){
                sb.append(" and sex = #{sex}");
            }

            if (StringUtils.isNotBlank(u.getEnabled())){
                sb.append(" and enabled = #{enabled}");
            }

            if (StringUtils.isNotBlank(u.getCredentialsNonExpired())){
                sb.append(" and credentials_non_expired = #{credentialsNonExpired}");
            }

            if (StringUtils.isNotBlank(u.getAccountNonLocked())){
                sb.append(" and account_non_locked = #{accountNonLocked}");
            }

            if (StringUtils.isNotBlank(u.getAccountNonExpired())){
                sb.append(" and account_non_expired = #{accountNonExpired}");
            }

            if (StringUtils.isNotBlank(u.getState())){
                sb.append(" and state = #{state}");
            }

            if (StringUtils.isNotBlank(u.getCity())){
                sb.append(" and city = #{city}");
            }

            if (StringUtils.isNotBlank(u.getCounty())){
                sb.append(" and county = #{county}");
            }

            if (StringUtils.isNotBlank(u.getProvince())){
                sb.append(" and province = #{province}");
            }

            return sb.toString();
        }


    }

}
