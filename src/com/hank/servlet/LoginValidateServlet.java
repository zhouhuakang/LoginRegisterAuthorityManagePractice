package com.hank.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.hank.dao.UserAdminDaoImpl;
import com.hank.dao.UserEnterpriseDaoImpl;
import com.hank.dao.UserPersonDaoImpl;
import com.hank.pojo.User;

@WebServlet(name = "LoginValidateServlet", urlPatterns = { "/login_validate" })
public class LoginValidateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");

		String userName = req.getParameter("username");
		String passWord = req.getParameter("password");
		int userType = Integer.valueOf(req.getParameter("userType"));

		System.out.println(userName + " ---  " + passWord);
		System.out.println(checkLogin(userType, userName, passWord));
		

		if (checkLogin(userType, userName, passWord)) {
			System.out.println("forward－－－");
			req.getSession().setAttribute("username", userName);
			req.getRequestDispatcher("/LoginSuccess.html").forward(req, resp);
			return;
		} else {
			resp.sendRedirect("login.html");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("just in get ");
	}

	// check login
	public boolean checkLogin(int userType, String userName, String passWord) {

		// mybatis配置文件
		String resource = "SqlMapConfig.xml";
		// 得到配置文件流
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
		// 创建会话工厂，传入mybatis的配置文件信息
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(inputStream);

		User user = null;
		try {
			switch (userType) {
			case User.TYPE_ADMIN:
				user = new UserAdminDaoImpl(sqlSessionFactory)
						.findUserByNameWithExact(userName);
				break;
			case User.TYPE_ENTERPRISE:
				user = new UserEnterpriseDaoImpl(sqlSessionFactory)
						.findUserByNameWithExact(userName);
				break;
			case User.TYPE_PERSON:
				user = new UserPersonDaoImpl(sqlSessionFactory)
						.findUserByNameWithExact(userName);
				break;

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		// Check
		if (user != null) {
			String passwordInDB = user.getPassword();
			if (passWord.equals(passwordInDB)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

}