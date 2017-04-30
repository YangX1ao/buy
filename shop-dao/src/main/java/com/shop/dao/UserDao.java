package com.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.shop.core.model.User;
@Mapper
public interface UserDao {
	
	@Insert("insert into user (uname,pwd,location,nation) values(#{uname},#{pwd},#{location},#{nation})")
	@Options(useGeneratedKeys=true,keyProperty="id")
	Integer insert(User user);
	
	@Update("update user set uname=#{uname},pwd=#{pwd},location=#{location},nation=#{nation} where id=#{id}")
	Integer update(User user);
	
	
	
	@Select("select uname,pwd,location,nation from user where id=#{id}")
	public User queryUserById(Integer id);
	
	
	@Select("select uname,pwd,location,nation from user where uname like concat('%', #{uname},'%')")
	public List<User> queryUserByUname(String uname);
	
	@Delete("delete from user where id=#{id}")
	public int delete(Integer id);
	
	/***
	 * 分页查询
	 * @param uname
	 * @param pageBounds
	 * @return
	 */
	@Select("select * from user ")
	List<User> selectForPage(@Param(value="uname")String uname,PageBounds pageBounds);
	
}
