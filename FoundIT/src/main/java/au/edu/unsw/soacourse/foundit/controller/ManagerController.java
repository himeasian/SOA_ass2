package au.edu.unsw.soacourse.foundit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import au.edu.unsw.soacourse.foundit.model.Application;
import au.edu.unsw.soacourse.foundit.model.JobPosting;

@Controller
@RequestMapping("manager")
public class ManagerController {
	
	@RequestMapping("")
	public ModelAndView indexAction() {
		List<JobPosting> jplist = new ArrayList<JobPosting>();
		JobPosting jp = new JobPosting();
		jp.set_JobID(24);
		//jp.setCompanyName("Microsoft");
		//jp.setSalaryRate(100000);
		jp.setLocation("Sydney");
		jp.setPositionType("Student");
		jp.setStatus("Created");
		jplist.add(jp);
		return new ModelAndView("manager", "jobpostings", jplist);
	}
	
	@RequestMapping("/application")
	public ModelAndView applicationsList() {
		List<Application> applist = new ArrayList<Application>();
		Application app = new Application();
		app.set_jobID(24);
		//jp.setCompanyName("Microsoft");
		//jp.setSalaryRate(100000);
		app.set_appID(10);
		app.setCandidatesDetails("Bob The Builder");
		app.setCoverLetter("Heres my letter");
		app.setAttachment1("1");
		app.setAttachment2("2");
		app.setStatus("Considered");
		applist.add(app);
		return new ModelAndView("application", "applications", applist);
	}
	
	@RequestMapping("/detailedjob/{jobID}") 
	public ModelAndView detailedJobPosting(@PathVariable("jobID") int jobID){
		JobPosting jp = new JobPosting();
		jp.set_JobID(jobID);
		return new ModelAndView("detailedjob", "jobposting", jp);
	}
	
	@RequestMapping("/detailedapplication/{appID}") 
	public ModelAndView detailedApplication(@PathVariable("appID") int appID){
		Application app = new Application();
		app.set_appID(appID);
		return new ModelAndView("detailedapplication", "application", app);
	}
	
	
}
