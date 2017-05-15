package au.edu.unsw.soacourse.foundit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Reviewer Specific Controller
 * @author trungisme
 *
 */
@Controller
@RequestMapping("reviewer")
public class ReviewerController {
	
	@RequestMapping("")
	public ModelAndView indexAction() {
		return new ModelAndView("reviewer");
	}

}
