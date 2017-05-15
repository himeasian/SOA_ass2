package au.edu.unsw.soacourse.foundit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import au.edu.unsw.soacourse.foundit.model.Application;
import au.edu.unsw.soacourse.foundit.model.JobPosting;
import au.edu.unsw.soacourse.foundit.model.Review;
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
		/*Application app = new Application();
		app.set_jobID(24);
		//jp.setCompanyName("Microsoft");
		//jp.setSalaryRate(100000);
		app.set_appID(10);
		app.setCandidatesDetails("Bob The Builder");
		app.setCoverLetter("Heres my letter");
		app.setAttachment1("1");
		app.setAttachment2("2");
		app.setStatus("Considered");
		applist.add(app);*/
		JobService js = new JobService();
		applist=js.getAllApplications();
		return new ModelAndView("manager/application", "applications", applist);
	}
	
	@RequestMapping("/review")
	public ModelAndView reviewsList() {
		List<Review> revlist = new ArrayList<Review>();
		JobService js = new JobService();
		/*Review rev = new Review();
		rev.set_reviewID(222);
		//jp.setCompanyName("Microsoft");
		//jp.setSalaryRate(100000);
		rev.set_appID(10);
		rev.setComments("Could be better");
		rev.setDecision("Offer made");
		rev.setReviewerDetails("Bob");
		revlist.add(rev);*/
		revlist = js.getAllReviews();
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
	
	@RequestMapping("/jobupdate")
	public ModelAndView jobUpdateAction(@RequestParam(value="jobupdatebutton") int jobID) {
		JobPosting jp = new JobPosting();
		jp.set_jobID(jobID);
		JobService js = new JobService();
		jp = js.getJobPost(jobID);
		
		return new ModelAndView("manager/jobupdate", "JobPosting", jp);
	}
	
	@RequestMapping("/jobarchive")
	public ModelAndView jobArchiveAction(@RequestParam(value="jobarchivebutton") int jobID) {
		JobPosting jp = new JobPosting();
		jp.set_jobID(jobID);
		JobService js = new JobService();
		js.archiveJobPost(jobID);
		String msg = "Delete of "+jobID + " has been successful!";
		return jobsList().addObject("errmsg", msg);
	}
	
	@RequestMapping("/createJobPosting")
	public String createJobPostingAction(@ModelAttribute("JobPosting") JobPosting jp, BindingResult result){
		if (result.hasErrors())
			return null;
		
		JobService js = new JobService();
		js.createJobPosting(jp);
		
		return "manager/jobcreatesuccess";	
	}
	
	@RequestMapping("/updateJobPosting/{jobid}")
	public String updatejobPostingAction(@ModelAttribute("JobPosting") JobPosting jp, @PathVariable("jobid") int jobID){
		jp.set_jobID(jobID);
		JobService js = new JobService();
		js.updateJobPosting(jp);
		return "manager/jobupdatesuccess";
	}
	
	@RequestMapping("/{jobID}/detailedapplication/{appID}") 
	public ModelAndView detailedApplication(@PathVariable("appID") int appID,@PathVariable("jobID") int jobID){
		Map<String, Object> model = new HashMap<String, Object>();
		Application app = new Application();
		app.set_appID(appID);
		app.set_jobID(jobID);
		Review rev = new Review();
		rev.setReviewerDetails("Bob1");
		Review rev2 = new Review();
		rev2.setReviewerDetails("Bob2");
		model.put("application", app);
		model.put("reviewer1", rev);
		model.put("reviewer2", rev2);
		return new ModelAndView("manager/detailedapplication", "model", model);
	}
	@RequestMapping("/{appID}/detailedreview/{reviewID}") 
	public ModelAndView detailedReview(@PathVariable("reviewID") int reviewID,@PathVariable("appID") int appID){
		Review rev = new Review();
		JobService js = new JobService();
		rev=js.getReview(appID, reviewID);
		
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
	
	
	
	@RequestMapping(value = "/archiving", method = RequestMethod.POST)
	public ModelAndView archiveJobPosting(@ModelAttribute("JobPosting") JobPosting jp){
		// Need to access jobservice and perform delete on jobid
		int jobid = jp.get_jobID();
		JobService js = new JobService();
		js.archiveJobPost(jobid);
		//get boolean result from jobservice;
		String msg = "Delete of "+jobid + " has been successful!";
		return new ModelAndView("manager/manager", "errmsg", msg);
	}
	
}
