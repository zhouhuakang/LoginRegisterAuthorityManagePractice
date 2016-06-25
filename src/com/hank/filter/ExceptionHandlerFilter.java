package com.hank.filter;

import java.io.IOException;

import javax.security.auth.login.AccountException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class ExceptionHandlerFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			Throwable rootCause = e;// 根异常
			while (rootCause.getCause() != null) {
				// 循环，直到寻找到根异常为止
				rootCause = rootCause.getCause();
			}

			String message = rootCause.getMessage();// 异常原因

			message = message == null ? "异常：" + rootCause.getClass().getName()
					: message;

			request.setAttribute("message", message);// request中传递异常原因
			request.setAttribute("e", e);// request中传递异常

			if (rootCause instanceof AccountException) {
				((HttpServletResponse) response)
						.sendRedirect("/LoginRegisterAuthorityManagePractice/login.html");

			} else {
				// Other Exception
			}

		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
