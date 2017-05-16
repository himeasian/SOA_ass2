package au.edu.unsw.soacourse.foundit.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import au.edu.unsw.soacourse.foundit.model.User;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("Prehandling request");
		String uri = request.getRequestURI();
		if (!uri.equals("/FoundIT/") &&
			!uri.equals("/FoundIT/login") &&
			!uri.equals("/FoundIT/logout") &&
			!uri.equals("/FoundIT/register")) {
			User u = (User) request.getSession().getAttribute("user");
			if (u == null) {
				request.setAttribute("logoutmsg", "You have been logged out");
				request.getRequestDispatcher("../logout").forward(request, response);
				return false;
			}
			String[] uril = uri.split("/");
			String role = u.getRole();
			System.out.println("role: " + role + " uril: " + uril[2]);
			if (!uril[2].equals(role)) {
				request.setAttribute("authmsg", "You do not have access to this page!");
				request.getRequestDispatcher(role).forward(request, response);
			}
		}
		return true;
	}
}
