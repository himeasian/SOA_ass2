package au.edu.unsw.soacourse.foundit.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import au.edu.unsw.soacourse.foundit.model.JobPosting;
import au.edu.unsw.soacourse.foundit.services.JobService;

@Controller
@RequestMapping("applicant")
public class ApplicantController {
	
	@RequestMapping("")
	public ModelAndView indexAction() {
		Map<Integer, String> salary = new LinkedHashMap<Integer,String>();
		salary.put(0, "");
		salary.put(30000, "$30K");
		salary.put(40000, "$40K");
		salary.put(50000, "$50K");
		salary.put(60000, "$60K");
		salary.put(70000, "$70K");
		salary.put(80000, "$80K");
		salary.put(90000, "$90K");
		salary.put(100000, "$100K");
		salary.put(110000, "$110K");
		salary.put(120000, "$120K");
		salary.put(130000, "$130K");
		salary.put(140000, "$140K");
		salary.put(150000, "$150K");
		return new ModelAndView("applicant/applicant", "jobSearch", new JobPosting()).addObject("salaryList", salary).addObject("numNotifs", 4);
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searchAction(@ModelAttribute("jobSearch") JobPosting j, BindingResult result) {
		if (result.hasErrors())
			return new ModelAndView("errors");
		
		JobService js = new JobService();
		
		return new ModelAndView("applicant/results");
	}
	
	@RequestMapping("/notifications")
	public ModelAndView viewNotifications() {
		return new ModelAndView("applicant/notification");
	}

}
