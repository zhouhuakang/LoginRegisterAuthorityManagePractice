package com.hank.dao;

import com.hank.pojo.User;
import com.hank.pojo.UserAdmin;

public interface UserAdminDao {

	// 根据名字查询用户信息
	public User findUserWithNameAndPass(User userToFind) throws Exception;
	public User insert(User user) throws Exception;
}
