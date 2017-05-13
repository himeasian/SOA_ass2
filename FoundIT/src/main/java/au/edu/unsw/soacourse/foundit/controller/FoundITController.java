package au.edu.unsw.soacourse.foundit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import au.edu.unsw.soacourse.foundit.bean.Login;
import au.edu.unsw.soacourse.foundit.bean.Register;
import au.edu.unsw.soacourse.foundit.bean.JobPosting;

@Controller
public class FoundITController {
	
	@RequestMapping("/")
	public ModelAndView indexAction() {
		return new ModelAndView("index");
	}
	
	@RequestMapping("/login")
	public String loginAction(@ModelAttribute("login") Login login) {
		return "home";
	}
	
	@RequestMapping("/manager")
	public String manageAction() {
		return "manager";
	}
	
	@RequestMapping("/jobposting")
	public String jobPostingAction() {
		return "jobposting";
	}
	
	@RequestMapping("/createJobPosting")
	public String createJobPostingAction(@ModelAttribute("JobPosting") JobPosting jp){
		return "success";	
	}
	
	@RequestMapping("/application")
	public String applicationAction() {
		return "application";
	}
	
	@RequestMapping("/review")
	public String reviewAction() {
		return "review";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerPageAction() {
		return "register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUserAction(@ModelAttribute("register") Register register) {
		return "home";
	}
	
	@RequestMapping(value = "/demo", method = RequestMethod.GET)
	public ModelAndView demoAction() {
		return new ModelAndView("index", "link", "http://www.baeldung.com/spring-requestmapping");
	}
}
