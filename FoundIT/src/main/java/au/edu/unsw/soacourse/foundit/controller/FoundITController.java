package au.edu.unsw.soacourse.foundit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FoundITController {
	
	@RequestMapping("/")
	public ModelAndView indexAction() {
		return new ModelAndView("index");
	}
	
	@RequestMapping(value = "/demo", method = RequestMethod.GET)
	public ModelAndView demoAction() {
		return new ModelAndView("index", "link", "http://www.baeldung.com/spring-requestmapping");
	}
}
