package au.edu.unsw.soacourse.foundit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import au.edu.unsw.soacourse.foundit.bean.JobPosting;

@Controller
@RequestMapping("manager")
public class ManagerController {
	
	@RequestMapping("")
	public ModelAndView indexAction() {
		List<JobPosting> jplist = new ArrayList<JobPosting>();
		JobPosting jp = new JobPosting();
		jp.setCompanyName("Microsoft");
		jp.setSalaryRate(100000);
		jp.setLocation("Sydney");
		jp.setPositionType("Student");
		jp.setStatus("Created");
		jplist.add(jp);
		return new ModelAndView("manager", "jobpostings", jplist);
	}
}
