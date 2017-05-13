package au.edu.unsw.soacourse.foundit.controller;

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
	public ModelAndView indexAction() {
		return new ModelAndView("index", "login", new Login());
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginAction(@ModelAttribute("login") Login login, BindingResult result) {
		
		if (result.hasErrors())
			return new ModelAndView("error");
		
		User u = new DatabaseHandler().getUser(login.getEmail());
		if (u == null || !u.getPassword().equals(login.getPassword()))
			return new ModelAndView("index", "errmsg", "Invalid email or password");
		
		switch (u.getRole()) {
		case "applicant":
			return new ModelAndView("redirect:/applicant");
		case "manager":
			return new ModelAndView("redirect:/manager");
		case "reviewer":
			return new ModelAndView("redirect:/reviewer");
		}
		
		return new ModelAndView("home"); //error
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView registerPageAction() {
		return new ModelAndView("register", "register", new Register());
	}
	

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerUserAction(@Validated @ModelAttribute("register") Register m, BindingResult result) {
		if (result.hasErrors()) return new ModelAndView("error");
		
		// if there is any null values, besides company, fail submit
		if (m.getConfirmPassword() == null || m.getEmail() == null || m.getFname() == null || 
				m.getLname() == null || m.getPassword() == null || m.getRole() == null) {
			m.setConfirmPassword(null);
			m.setPassword(null);
			return new ModelAndView("register", "errmsg", "Please fill out the required fields!").addObject(m);
		}
		
		if (!m.getConfirmPassword().equals(m.getPassword()))
			return new ModelAndView("register", "errmsg", "Passwords do not match!").addObject(m);
		
		DatabaseHandler dbh = new DatabaseHandler();
		if (dbh.getUser(m.getEmail()) != null)
			return new ModelAndView("register", "errmsg", "Email already exists!").addObject(m);
		
		
		int userid = dbh.createUser(m);
		if (userid == -1)
			return new ModelAndView("register", "errmsg", "Something went wrong! Please try again later.").addObject(m);
		return new ModelAndView("registered");
	}
}
