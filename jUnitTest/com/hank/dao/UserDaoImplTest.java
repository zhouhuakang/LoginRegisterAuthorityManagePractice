package com.hank.dao;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.hank.pojo.User;
import com.hank.pojo.UserAdmin;

public class UserDaoImplTest {

	@Test
	public void test() {
		try {
			// mybatis配置文件
			String resource = "SqlMapConfig.xml";
			// 得到配置文件流
			InputStream inputStream = Resources.getResourceAsStream(resource);

			// 创建会话工厂，传入mybatis的配置文件信息
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
					.build(inputStream);

			User userToFind = new UserAdmin();
			userToFind.setUsername("admin1");
			userToFind.setPassword("123");
			
			User user = new UserAdminDaoImpl(sqlSessionFactory)
					.findUserWithNameAndPass(userToFind);

			System.out.println(user.getUsername() + "!!!!!"
					+ user.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
