package com.example.updateuserper.mapper;

import com.example.updateuserper.pojo.Sys_user;
import com.example.updateuserper.util.DelStu;
import com.example.updateuserper.util.Permission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysUserMapper {
    @Select("select * from Sys_user where login_name = #{loginName}")
    public Sys_user findUserByLoginName(String loginName);

    @Select("select * from Sys_user where user_id = #{user_id}")
    public Sys_user findUserByUserId(Integer user_id);

    @Insert("insert into Sys_user(login_name,login_pwd,user_name,status,last_login_time,last_login_ip,create_time,email,tel,role_id,bindingRole,uuid,is_examine) values (#{login_name},#{login_pwd},#{user_name},#{status},#{last_login_time},#{last_login_ip},#{create_time},#{email},#{tel},#{role_id},#{bindingRole},#{uuid},#{is_examine})")
    public int addUser(Sys_user sys_user);

    @Update("update Sys_user set last_login_time=#{last_login_time} where user_id=#{user_id}")
    public int updateUserByLastLoginTime(Sys_user sys_user);

    @Select("select login_name from Sys_user where user_id = #{userId}")
    public String findLoginNameByUserId(Integer userId);

    @Update("update Sys_user set status=#{status} where user_id = #{user_id}")
    public void DeleteUserByUserId(DelStu delStu);

    @Update("update Sys_user set login_name = #{login_name}, login_pwd=#{login_pwd}, user_name=#{user_name}, email=#{email}, tel=#{tel} where user_id=#{user_id}")
    public int updateUser(Sys_user sys_user);

    @Update("update Sys_user set role_id=#{role_id} where user_id=#{user_id}")
    public int updateUserByRoleId(Permission permission);
}
