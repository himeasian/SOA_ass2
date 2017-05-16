package au.edu.unsw.soacourse.foundit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import au.edu.unsw.soacourse.foundit.bean.Register;
import au.edu.unsw.soacourse.foundit.database.DatabaseHandler;
import au.edu.unsw.soacourse.foundit.model.*;
import au.edu.unsw.soacourse.foundit.services.*;


/**
 * Manager specific controller
 * 
 * @author gtkm
 *
 */
@Controller
@RequestMapping("manager")
public class ManagerController {

	@RequestMapping("")
	public ModelAndView jobsList(HttpSession session) {

		// ServletRequestAttributes attr = (ServletRequestAttributes)
		// RequestContextHolder.currentRequestAttributes();
		// HttpSession session = attr.getRequest().getSession();
		User u = new User();
		u = (User) session.getAttribute("user");
		String email = u.getEmail();
		List<JobPosting> jplist = new ArrayList<JobPosting>();
		/*
		 * JobPosting jp = new JobPosting(); jp.set_jobID(24);
		 * //jp.setCompanyName("Microsoft"); //jp.setSalaryRate(100000);
		 * jp.setLocation("Sydney"); jp.setPositionType("Student");
		 * jp.setStatus("Created"); jplist.add(jp);
		 */

		DatabaseHandler db = new DatabaseHandler();
		User main = db.getUser(email);
		String companyname = main.getCompany();
		JobService js = new JobService();
		jplist = js.getAllJobPosts(companyname);

		return new ModelAndView("manager/manager", "jobpostings", jplist);

	}

	@RequestMapping("/application")
	public ModelAndView applicationsList(HttpSession session) {
		List<Application> applist = new ArrayList<Application>();
		List<Application> filteredapplist = new ArrayList<Application>();
		List<JobPosting> jplist = new ArrayList<JobPosting>();
		User u = new User();
		u = (User) session.getAttribute("user");
		String email = u.getEmail();

		/*
		 * Application app = new Application(); app.set_jobID(24);
		 * //jp.setCompanyName("Microsoft"); //jp.setSalaryRate(100000);
		 * app.set_appID(10); app.setCandidatesDetails("Bob The Builder");
		 * app.setCoverLetter("Heres my letter"); app.setAttachment1("1");
		 * app.setAttachment2("2"); app.setStatus("Considered");
		 * applist.add(app);
		 */

		DatabaseHandler db = new DatabaseHandler();
		User main = db.getUser(email);
		String companyname = main.getCompany();
		JobService js = new JobService();
		jplist = js.getAllJobPosts(companyname);
		applist = js.getAllApplications();

		for (Application app : applist) {
			for (JobPosting job : jplist) {
				if (app.get_jobID() == job.get_jobID()) {
					filteredapplist.add(app);
				}
			}
		}
		return new ModelAndView("manager/application", "applications", filteredapplist);
	}

	@RequestMapping("/review")
	public ModelAndView reviewsList(HttpSession session) {
		List<Application> applist = new ArrayList<Application>();
		List<Application> filteredapplist = new ArrayList<Application>();
		List<JobPosting> jplist = new ArrayList<JobPosting>();
		List<Review> revlist = new ArrayList<Review>();
		List<Review> filteredrevlist = new ArrayList<Review>();
		User u = new User();
		u = (User) session.getAttribute("user");
		String email = u.getEmail();

		JobService js = new JobService();
		/*
		 * Review rev = new Review(); rev.set_reviewID(222);
		 * //jp.setCompanyName("Microsoft"); //jp.setSalaryRate(100000);
		 * rev.set_appID(10); rev.setComments("Could be better");
		 * rev.setDecision("Offer made"); rev.setReviewerDetails("Bob");
		 * revlist.add(rev);
		 */
		DatabaseHandler db = new DatabaseHandler();
		User main = db.getUser(email);
		String companyname = main.getCompany();
		jplist = js.getAllJobPosts(companyname);
		applist = js.getAllApplications();

		for (Application app : applist) {
			for (JobPosting job : jplist) {
				if (app.get_jobID() == job.get_jobID()) {
					filteredapplist.add(app);
				}
			}
		}

		revlist = js.getAllReviews();
		for (Review rev : revlist) {
			for (Application app : filteredapplist) {
				if (app.get_appID() == rev.get_appID()) {
					filteredrevlist.add(rev);
				}
			}
		}

		return new ModelAndView("manager/review", "reviews", filteredrevlist);
	}

	@RequestMapping("/detailedjob/{jobID}")
	public ModelAndView detailedJobPosting(@PathVariable("jobID") int jobID) {
		JobPosting jp = new JobPosting();
		jp.set_jobID(jobID);

		JobService js = new JobService();
		jp = js.getJobPost(jobID);

		return new ModelAndView("manager/detailedjob", "jobposting", jp);
	}

	@RequestMapping("/jobposting")
	public ModelAndView jobPostingAction() {
		return new ModelAndView("manager/jobposting", "JobPosting", new JobPosting());
	}

	@RequestMapping("/jobupdate")
	public ModelAndView jobUpdateAction(@RequestParam(value = "jobupdatebutton") int jobID) {
		JobPosting jp = new JobPosting();
		jp.set_jobID(jobID);
		JobService js = new JobService();
		jp = js.getJobPost(jobID);

		return new ModelAndView("manager/jobupdate", "JobPosting", jp);
	}

	@RequestMapping("/jobarchive")
	public ModelAndView jobArchiveAction(@RequestParam(value = "jobarchivebutton") int jobID) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		JobPosting jp = new JobPosting();
		jp.set_jobID(jobID);
		JobService js = new JobService();
		js.archiveJobPost(jobID);
		String msg = "Delete of " + jobID + " has been successful!";
		return jobsList(session).addObject("errmsg", msg);
	}

	@RequestMapping("/createJobPosting")
	public ModelAndView createJobPostingAction(@ModelAttribute("JobPosting") JobPosting jp, BindingResult result) {
		String msg = "";
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();

		if (result.hasErrors()) {
			msg = "Job Posting not created!";
			return jobsList(session).addObject("errmsg", msg);
		}

		JobService js = new JobService();
		js.createJobPosting(jp);
		msg = "Job Posting created!";

		return jobsList(session).addObject("errmsg", msg);
	}

	@RequestMapping("/updateJobPosting/{jobid}")
	public ModelAndView updatejobPostingAction(@ModelAttribute("JobPosting") JobPosting jp,
			@PathVariable("jobid") int jobID) {
		String msg = "Job Post Updated!";
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		jp.set_jobID(jobID);
		JobService js = new JobService();
		js.updateJobPosting(jp);
		return jobsList(session).addObject("errmsg", msg);
	}

	@RequestMapping("/{jobID}/detailedapplication/{appID}")
	public ModelAndView detailedApplication(@PathVariable("appID") int appID, @PathVariable("jobID") int jobID) {
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
	public ModelAndView detailedReview(@PathVariable("reviewID") int reviewID, @PathVariable("appID") int appID) {
		Review rev = new Review();
		JobService js = new JobService();
		rev = js.getReview(appID, reviewID);

		return new ModelAndView("manager/detailedreview", "review", rev);
	}

	@RequestMapping("/hiringteam")
	public ModelAndView hiringTeamAction(@RequestParam(value = "jobbutton") int jobID) {
		Map<String, String> reviewers = new LinkedHashMap<String, String>();
		List<User> rList = new DatabaseHandler().getReviewers();
		if (rList.size() > 0) reviewers.put("", "");
		
		for (User u : rList) {
			String e = u.getEmail();
			reviewers.put(e, e);
		}		
		return new ModelAndView("manager/hiringteam", "jobID", jobID).addObject("HiringTeam", new HiringTeam()).addObject("reviewers", reviewers);
	}

	@RequestMapping("/hiringteam/assign")
	public ModelAndView assignhiringTeamAction(@ModelAttribute("HiringTeam") HiringTeam ht,
			@RequestParam(value = "assignteam") int jobID) {
		String msg = "";
		String email1 = ht.getEmail1();
		String email2 = ht.getEmail2();

		if (email1.isEmpty() || email2.isEmpty()) {
			msg = "Please fill out both email fields.";
			return hiringTeamAction(jobID).addObject("errmsg", msg);
		}
		DatabaseHandler db = new DatabaseHandler();
		User u1 = db.getUser(email1);
		User u2 = db.getUser(email2);

		if (u1 == null || u2 == null) {
			msg = "Please enter two valid reviewer emails";
			return hiringTeamAction(jobID).addObject("errmsg", msg);
		}

		String role1 = u1.getRole();
		String role2 = u2.getRole();

		if (!role1.equals("reviewer") || !role2.equals("reviewer")) {
			msg = "Can't assign either or both emails as reviewers for this job";
			return hiringTeamAction(jobID).addObject("errmsg", msg);
		}

		JobService js = new JobService();
		List<Application> la = new ArrayList<Application>();
		la = js.getApplicationPerJob(jobID);
		for (Application app : la) {
			int currentapp = app.get_appID();
			Review rev1 = new Review();
			Review rev2 = new Review();
			rev1.set_appID(currentapp);
			rev1.setReviewerDetails(email1);
			rev2.set_appID(currentapp);
			rev2.setReviewerDetails(email2);
			js.createReview(rev1);
			js.createReview(rev2);
		}
		msg = "Hiring team succesfully created!";
		return hiringTeamAction(jobID).addObject("errmsg", msg);
	}

	@RequestMapping("/createreviewer")
	public ModelAndView createReviewer() {
		return new ModelAndView("manager/createreviewer", "register", new Register());
	}

	@RequestMapping(value = "/revieweraddition", method = RequestMethod.POST)
	public ModelAndView reviewerAddition(HttpServletRequest request, @Validated @ModelAttribute("register") Register usr) {
		
		usr.setRole("reviewer");
		if (!usr.getPassword().equals(usr.getConfirmPassword()))
			return new ModelAndView("manager/createreviewer", "errmsg", "Your passwords do not match!").addObject(usr);
				
		DatabaseHandler dbh = new DatabaseHandler();
		if (dbh.getUser(usr.getEmail()) != null)
			return new ModelAndView("manager/createreviewer", "errmsg", "Email already exists!").addObject(usr);
		
		if (new DatabaseHandler().createUser(usr) > 0) {
			return new ModelAndView("manager/createreviewer", "successmsg", "Reviewer succesfully created!").addObject("register", new Register());
		}
		return new ModelAndView("manager/createreviewer", "errmsg", "ERROR: Reviewer could not be created!");
	}

	@RequestMapping(value = "/archiving", method = RequestMethod.POST)
	public ModelAndView archiveJobPosting(@ModelAttribute("JobPosting") JobPosting jp) {
		// Need to access jobservice and perform delete on jobid
		int jobid = jp.get_jobID();
		JobService js = new JobService();
		js.archiveJobPost(jobid);
		// get boolean result from jobservice;
		String msg = "Delete of " + jobid + " has been successful!";
		return new ModelAndView("manager/manager", "errmsg", msg);
	}

	@RequestMapping("/detailedjob/{jobID}/candidateshortlist")
	public ModelAndView getCandidateShortlist(@PathVariable("jobID") int jobID, HttpSession session) {
		List<Application> applist = new ArrayList<Application>();
		List<Application> filteredapplist = new ArrayList<Application>();
		List<Review> revlist = new ArrayList<Review>();
		List<Review> filteredrevlist = new ArrayList<Review>();

		JobService js = new JobService();
		applist = js.getAllApplications();

		for (Application app : applist) {
			if (app.get_jobID() == jobID) {
				filteredapplist.add(app);
			}
		}

		revlist = js.getAllReviews();
		for (Review rev : revlist) {
			for (Application app : filteredapplist) {
				if (app.get_appID() == rev.get_appID()) {
					filteredrevlist.add(rev);
				}
			}
		}
		
		Map<Integer, Review> recommended = new HashMap<Integer, Review>();
		for (Review r : filteredrevlist) {
			if (r.getDecision().equals("Recommend") && !recommended.containsKey(r.get_appID())) {
				recommended.put(r.get_appID(), r);
			}
		}
		
		List<Review> finalList = new ArrayList<>(recommended.values());

		return new ModelAndView("manager/candidateshortlist", "reviewlist", finalList);
	}
	
	@RequestMapping("detailedapplication/createpoll")
	public ModelAndView createPollForApplicant(@ModelAttribute("Poll") Poll poll){
		DatabaseHandler db = new DatabaseHandler();
		PollingService ps = new PollingService();
		ps.createPoll(poll);
		
		return new ModelAndView();
	}
}
