package au.edu.unsw.soacourse.foundit.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import au.edu.unsw.soacourse.foundit.bean.Login;
import au.edu.unsw.soacourse.foundit.bean.Register;
import au.edu.unsw.soacourse.foundit.database.DatabaseHandler;
import au.edu.unsw.soacourse.foundit.model.User;

@Controller
public class FoundITController {
	

	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView indexAction(HttpServletRequest request) {
		
		// check if session exists, try to bypass login, otherwise just link to login page.
		HttpSession session = request.getSession(false);
		if (session != null) {
			User u = (User) session.getAttribute("user");
			if (u != null)
				return roleRedirect(u.getRole());
			session.invalidate();
		}
		return new ModelAndView("default/index", "login", new Login());
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginAction(@ModelAttribute("login") Login login, BindingResult result, HttpServletRequest request) {
		if (result.hasErrors())
			return new ModelAndView("default/error");
		
		User u = new DatabaseHandler().getUser(login.getEmail());
		if (u == null || !u.getPassword().equals(login.getPassword()))
			return new ModelAndView("default/index", "errmsg", "Invalid email or password");
		
		// create a user object
		HttpSession session = request.getSession(true);
		session.setAttribute("user", u);
		
		return roleRedirect(u.getRole());
	}

	@RequestMapping("/logout")
	public ModelAndView logoutAction(HttpServletRequest request) {
		request.getSession().invalidate();
		String msg = (String) request.getAttribute("logoutmsg");
		if (msg == null)
			return new ModelAndView("default/loggedout", "logoutmsg", "You have logged out successfully!");
		return new ModelAndView("default/loggedout", "logoutmsg", msg);
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView registerPageAction() {
		return new ModelAndView("default/register", "register", new Register());
	}
	

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerUserAction(@Validated @ModelAttribute("register") Register m, BindingResult result) {
		if (result.hasErrors()) return new ModelAndView("error");
		
		// if there is any null values, besides company, fail submit
		if (m.getConfirmPassword() == null || m.getEmail() == null || m.getFname() == null || 
				m.getLname() == null || m.getPassword() == null || m.getRole() == null) {
			m.setConfirmPassword(null);
			m.setPassword(null);
			return new ModelAndView("default/register", "errmsg", "Please fill out the required fields!").addObject(m);
		}
		
		if (!m.getConfirmPassword().equals(m.getPassword()))
			return new ModelAndView("default/register", "errmsg", "Passwords do not match!").addObject(m);
		
		DatabaseHandler dbh = new DatabaseHandler();
		if (dbh.getUser(m.getEmail()) != null)
			return new ModelAndView("default/register", "errmsg", "Email already exists!").addObject(m);
		
		
		int userid = dbh.createUser(m);
		if (userid == -1)
			return new ModelAndView("register", "errmsg", "Something went wrong! Please try again later.").addObject(m);
		return new ModelAndView("default/registered");
	}
	
	/**
	 * redirects user to home page based on their role
	 * @param role
	 * @return
	 */
	private ModelAndView roleRedirect(String role) {
		switch (role) {
		case "applicant":
			return new ModelAndView("redirect:/applicant");
		case "manager":
			return new ModelAndView("redirect:/manager");
		case "reviewer":
			return new ModelAndView("redirect:/reviewer");
		default:
			return new ModelAndView("default/error");
		}
	}
}
