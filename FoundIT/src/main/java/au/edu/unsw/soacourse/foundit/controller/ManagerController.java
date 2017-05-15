package au.edu.unsw.soacourse.foundit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import au.edu.unsw.soacourse.foundit.model.*;
import au.edu.unsw.soacourse.foundit.services.JobService;

/**
 * Manager specific controller
 * @author gtkm
 *
 */
@Controller
@RequestMapping("manager")
public class ManagerController {
	
	@RequestMapping("")
	public ModelAndView jobsList() {
		List<JobPosting> jplist = new ArrayList<JobPosting>();
		/*JobPosting jp = new JobPosting();
		jp.set_jobID(24);
		//jp.setCompanyName("Microsoft");
		//jp.setSalaryRate(100000);
		jp.setLocation("Sydney");
		jp.setPositionType("Student");
		jp.setStatus("Created");
		jplist.add(jp);
		*/
		
		JobService js = new JobService();
		jplist=js.getAllJobPosts();
		
		return new ModelAndView("manager/manager", "jobpostings", jplist);
		
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
		return new ModelAndView("manager/application", "applications", applist);
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
		return new ModelAndView("manager/review", "reviews", revlist);
	}
	
	@RequestMapping("/detailedjob/{jobID}") 
	public ModelAndView detailedJobPosting(@PathVariable("jobID") int jobID){
		JobPosting jp = new JobPosting();
		jp.set_jobID(jobID);
		
		JobService js = new JobService();
		jp=js.getJobPost(jobID);
		
		return new ModelAndView("manager/detailedjob", "jobposting", jp);
	}
	
	@RequestMapping("/jobposting")
	public ModelAndView jobPostingAction() {
		return new ModelAndView("manager/jobposting", "JobPosting", new JobPosting());
	}
	
	@RequestMapping("/createJobPosting")
	public String createJobPostingAction(@ModelAttribute("JobPosting") JobPosting jp){
		JobService js = new JobService();
		js.createJobPosting(jp);
		
		return "manager/jobcreatesuccess";	
	}
	
	@RequestMapping("/updateJobPosting")
	public String updatejobPostingAction(@ModelAttribute("UpdateJob") JobPosting jp){
		JobService js = new JobService();
		js.updateJobPosting(jp);
		return "manager/jobupdatesuccess";
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
		return new ModelAndView("manager/detailedapplication", "model", model);
	}
	@RequestMapping("/detailedreview/{reviewID}") 
	public ModelAndView detailedReview(@PathVariable("reviewID") int reviewID){
		Review rev = new Review();
		rev.set_reviewID(reviewID);
		return new ModelAndView("manager/detailedreview", "review", rev);
	}
	
	@RequestMapping("/hiringteam")
	public ModelAndView hiringTeamAction(@RequestParam(value="jobbutton") int jobID) {
		return new ModelAndView("manager/hiringteam", "jobID", jobID);
	}
	
	@RequestMapping("/hiringteam/assign")
	public String assignhiringTeamAction(@RequestParam(value="assignteam") int jobID) {
		// YOU ARE UP TO HERE NOW IN THE WORK!!!!!!!!!!!!!
		return "manager/hiringteam";
	}
	
	@RequestMapping("/archiving")
	public String archiveJobPosting() {
		return "manager/archiving";
	}
	
	@RequestMapping(value = "/archiving", method = RequestMethod.POST)
	public ModelAndView archiveJobPosting(@ModelAttribute("JobPosting") JobPosting jp){
		// Need to access jobservice and perform delete on jobid
		int jobid = jp.get_jobID();
		boolean result = false;
		//get boolean result from jobservice;
		return new ModelAndView("manager/archiving", "result", result);
	}
	
}
