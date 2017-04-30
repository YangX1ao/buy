package com.shop.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.shop.core.base.exception.ParamException;
import com.shop.core.dto.UserDto;
import com.shop.core.model.User;
import com.shop.dao.UserDao;


@Service
@SuppressWarnings("all")
public class UserService {
	
	@Resource
	private UserDao userDao;
	
	
	private static Logger logger=LoggerFactory.getLogger(UserService.class);
	
	
	public Integer addUser(User user){
		//返回受影响的行数和id
		Integer count = userDao.insert(user);
		Integer id = user.getId();
		logger.info("count:"+count+"--"+"id:"+id);
		return id;
	}
	
	public Integer update(User user){
		return userDao.update(user);
		
	}
	
	
	public User queryUserById(Integer id){
		if(null==id&&id==0){
			throw new ParamException("请输入要查询的用户！");
		}
		 User user = userDao.queryUserById(id);
		 return user;
	}
	
	public List<User> queryUserByUname(String uname){
		return userDao.queryUserByUname(uname);
	}
	
	public int delete(Integer id){
		return userDao.delete(id);
	}
	
	public PageList<User> selectForPage(UserDto userDto){
		//构造pagebounds
		PageBounds pageBounds=userDto.toPageBounds();
		PageList<User> result=(PageList<User>) userDao.selectForPage(userDto.getUname(), pageBounds);
		logger.info("paginator:{}",result.getPaginator());
		return result;
		
	}
	
}
