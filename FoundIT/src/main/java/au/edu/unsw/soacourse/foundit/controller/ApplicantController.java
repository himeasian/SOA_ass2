package au.edu.unsw.soacourse.foundit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("applicant")
public class ApplicantController {
	
	@RequestMapping("")
	public ModelAndView indexAction() {
		return new ModelAndView("applicant");
	}

}
