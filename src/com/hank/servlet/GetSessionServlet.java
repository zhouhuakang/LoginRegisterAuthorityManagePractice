package com.hank.servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.hank.pojo.User;
import com.sun.corba.se.impl.ior.WireObjectKeyTemplate;

/**
 * Servlet implementation class GetSessionServlet
 */
@WebServlet("/GetSessionServlet")
public class GetSessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetSessionServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json");

		JSONObject UserInfo = new JSONObject();
		User user = (User) request.getSession().getAttribute("user");

		try {
			UserInfo.put("userName", user.getUsername());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Writer writer = response.getWriter();
		System.out.println(UserInfo.toString());
		writer.write(UserInfo.toString());
		writer.flush();

	}
}
