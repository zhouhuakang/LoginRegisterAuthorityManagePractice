package com.hank.dao;

import com.hank.pojo.User;

public interface UserPersonDao {

	// 根据名字查询用户信息
	public  User findUserWithNameAndPass(User userToFind) throws Exception;
	public User insert(User user) throws Exception;
}
