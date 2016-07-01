package com.hank.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.hank.pojo.User;
import com.hank.pojo.UserAdmin;

public class UserAdminDaoImpl implements UserAdminDao {
	// 需要向dao实现类中注入SqlSessionFactory
	// 这里通过构造方法注入
	private SqlSessionFactory sqlSessionFactory;

	public UserAdminDaoImpl(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public User findUserWithNameAndPass(User userToFind) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();

		UserAdmin user = sqlSession.selectOne(
				"account_admin_user_admin.findUserWithNameAndPass", userToFind);
		// 释放资源
		sqlSession.close();

		return user;
	}

	@Override
	public User insert(User user) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();

		sqlSession.insert("account_admin_user_admin.insert_user", user);
		// 提交事务
		sqlSession.commit();
		// 释放资源
		sqlSession.close();
		return user;

	}
}
