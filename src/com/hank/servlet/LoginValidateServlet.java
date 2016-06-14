package com.hank.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginValidateServlet", urlPatterns = { "/login_validate" })
public class LoginValidateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	// Mock Username and Password
	private Map<String, String> user = new HashMap<String, String>();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String account = req.getParameter("account");
		String password = req.getParameter("password");
		if (checkLogin(account, password)) {
			req.getSession().setAttribute("account", "account");
			req.getRequestDispatcher("/LoginSuccess.html").forward(req, resp);
		}
		{
			resp.sendRedirect("login.html");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("just in get ");
	}

	// check login
	public boolean checkLogin(String account, String password) {
		String passwordFromDB = getPasswordFromDB(account);
		if (passwordFromDB != null && !"".equals(passwordFromDB)) {
			if (password.equals(passwordFromDB)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	// Find the password from the database
	public String getPasswordFromDB(String account) {
		// TODO change to get the password from the db later
		user.put("zhk", "zhk");

		String password = user.get(account);
		return password;
	}
}