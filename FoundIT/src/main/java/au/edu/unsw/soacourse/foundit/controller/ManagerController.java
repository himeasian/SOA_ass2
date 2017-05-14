package au.edu.unsw.soacourse.foundit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import au.edu.unsw.soacourse.foundit.model.*;

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
	
	@RequestMapping("/review")
	public ModelAndView reviewsList() {
		List<Review> revlist = new ArrayList<Review>();
		Review rev = new Review();
		rev.set_reviewID(222);
		//jp.setCompanyName("Microsoft");
		//jp.setSalaryRate(100000);
		rev.set_appID(10);
		rev.setComments("Could be better");
		rev.setDecision("Offer made");
		rev.setReviewerDetails("Bob");
		revlist.add(rev);
		return new ModelAndView("review", "reviews", revlist);
	}
	
	@RequestMapping("/detailedjob/{jobID}") 
	public ModelAndView detailedJobPosting(@PathVariable("jobID") int jobID){
		JobPosting jp = new JobPosting();
		jp.set_JobID(jobID);
		return new ModelAndView("detailedjob", "jobposting", jp);
	}
	
	@RequestMapping("/detailedapplication/{appID}") 
	public ModelAndView detailedApplication(@PathVariable("appID") int appID){
		Map<String, Object> model = new HashMap<String, Object>();
		Application app = new Application();
		app.set_appID(appID);
		Review rev = new Review();
		rev.setReviewerDetails("Bob1");
		Review rev2 = new Review();
		rev2.setReviewerDetails("Bob2");
		model.put("application", app);
		model.put("reviewer1", rev);
		model.put("reviewer2", rev2);
		return new ModelAndView("detailedapplication", "model", model);
	}
	@RequestMapping("/detailedreview/{reviewID}") 
	public ModelAndView detailedReview(@PathVariable("reviewID") int reviewID){
		Review rev = new Review();
		rev.set_reviewID(reviewID);
		return new ModelAndView("detailedreview", "review", rev);
	}
	
}
