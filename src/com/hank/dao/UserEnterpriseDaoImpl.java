package com.hank.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.hank.pojo.User;
import com.hank.pojo.UserEnterprise;

public class UserEnterpriseDaoImpl implements UserEnterpriseDao {
	// 需要向dao实现类中注入SqlSessionFactory
	// 这里通过构造方法注入
	private SqlSessionFactory sqlSessionFactory;

	public UserEnterpriseDaoImpl(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public User findUserWithNameAndPass(User userToFind) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();

		User user = sqlSession.selectOne(
				"account_admin_user_enterprise.findUserWithNameAndPass",
				userToFind);
		// 释放资源
		sqlSession.close();

		return user;
	}

	@Override
	public User insert(User user) throws Exception {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.insert("account_admin_user_enterprise.insert_user", user);
		// 提交事务
		sqlSession.commit();
		// 释放资源
		sqlSession.close();
		return user;

	}

}
