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

import com.hank.dao.UserEnterpriseDao;
import com.hank.dao.UserEnterpriseDaoImpl;
import com.hank.dao.UserPersonDao;
import com.hank.dao.UserPersonDaoImpl;
import com.hank.pojo.User;
import com.hank.pojo.UserAdmin;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet(name = "RegisterServlet", urlPatterns = { "/register" })
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			System.out.println("post success");
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("application/json");

			String userType = request.getParameter("userType");
			String userName = request.getParameter("userName");
			String userPassword = request.getParameter("userPassword");

			User user = new UserAdmin();
			user.setUsername(userName);
			user.setPassword(userPassword);

			System.out.println("userType:" + userType + "\n userName:"
					+ userName + "\n userPassword:" + userPassword + "\n User:"
					+ user.toString());

			// TODO 抽离出这部分不变的代码，放在一个Servlet中？？？？
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

			if (Integer.valueOf(userType) == (User.TYPE_ENTERPRISE)) {
				UserEnterpriseDao userEnterpriseDao = new UserEnterpriseDaoImpl(
						sqlSessionFactory);
				user = userEnterpriseDao.insert(user);
				System.out.println(user.getId());
			}
			if (Integer.valueOf(userType) == (User.TYPE_PERSON)) {

				UserPersonDao userPersonDao = new UserPersonDaoImpl(
						sqlSessionFactory);
				user = userPersonDao.insert(user);
				System.out.println(user.getId());
			}

			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("isSuccess", true);
				jsonObject.put("userType", userType);
				jsonObject.put("userName", userName);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Writer writer = response.getWriter();
			writer.write(jsonObject.toString());
			// 提交事务
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
