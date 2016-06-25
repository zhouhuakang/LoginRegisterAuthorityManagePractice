package com.hank.dao;

import com.hank.pojo.User;

public interface UserPersonDao {

	// 根据名字查询用户信息
	public  User findUserByNameWithExact(String name) throws Exception;
}