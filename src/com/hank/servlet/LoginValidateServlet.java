package com.hank.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import com.hank.dao.UserAdminDaoImpl;
import com.hank.dao.UserEnterpriseDaoImpl;
import com.hank.dao.UserPersonDaoImpl;
import com.hank.pojo.User;
import com.hank.pojo.UserAdmin;
import com.hank.pojo.UserEnterprise;
import com.hank.pojo.UserPerson;

@WebServlet(name = "LoginValidateServlet", urlPatterns = { "/login_validate" })
public class LoginValidateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		resp.setContentType("application/json");

		String userName = req.getParameter("username");
		String password = req.getParameter("password");
		int userType = Integer.valueOf(req.getParameter("userType"));

		Writer writer = resp.getWriter();
		User user = null;
		user = getUserFromDB(userType, userName, password);

		if (user != null) {
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("num", 3);
				jsonObject.put("userType", userType);
				jsonObject.put("userName", userName);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			req.getSession().setAttribute("user", user);
			try{
			writer.write(jsonObject.toString());}
			catch(Exception e){
				e.printStackTrace();
			}
			System.out.println(jsonObject.toString());
		} else {
			System.out.println("{num:1}");
			writer.write("{ \"num\": 1 }");
		}
		writer.flush();
		writer.close();
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("just in get ");
	}

	// check login
	public User getUserFromDB(int userType, String userName, String passWord) {

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
				UserAdmin userAdmin = new UserAdmin();
				userAdmin.setUsername(userName);
				userAdmin.setPassword(passWord);
				user = new UserAdminDaoImpl(sqlSessionFactory)
						.findUserWithNameAndPass(userAdmin);
				break;
			case User.TYPE_ENTERPRISE:
				UserEnterprise userEnterprise = new UserEnterprise();
				userEnterprise.setUsername(userName);
				userEnterprise.setPassword(passWord);
				user = new UserEnterpriseDaoImpl(sqlSessionFactory)
						.findUserWithNameAndPass(userEnterprise);
				break;
			case User.TYPE_PERSON:
				UserPerson userPerson = new UserPerson();
				userPerson.setUsername(userName);
				userPerson.setPassword(passWord);
				user = new UserPersonDaoImpl(sqlSessionFactory)
						.findUserWithNameAndPass(userPerson);
				break;

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return user;
	}

}