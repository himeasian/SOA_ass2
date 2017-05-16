package au.edu.unsw.soacourse.foundit.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import au.edu.unsw.soacourse.foundit.bean.JobApplication;
import au.edu.unsw.soacourse.foundit.database.DatabaseHandler;
import au.edu.unsw.soacourse.foundit.model.Review;
import au.edu.unsw.soacourse.foundit.model.User;
import au.edu.unsw.soacourse.foundit.services.ApplicationComparator;
import au.edu.unsw.soacourse.foundit.services.JobService;

/**
 * Reviewer Specific Controller
 * @author trungisme
 *
 */
@Controller
@RequestMapping("reviewer")
public class ReviewerController {
	
	@RequestMapping("")
	public ModelAndView indexAction(HttpServletRequest request) {
		List<Integer> jobs = new DatabaseHandler().getAllocations(((User) request.getSession().getAttribute("user")).getEmail());
		System.out.println("#jobs:" + jobs.size());
		JobService js = new JobService();
		List<JobApplication> apps = new ArrayList<>();
		for (int j  : jobs) {
			apps.addAll(js.getJobApplicationPerJob(j));
		}
		Collections.sort(apps, new ApplicationComparator());
		
		return new ModelAndView("reviewer/reviewer", "applications", apps);
	}
	
	@RequestMapping(value = "/review/job/{jid}/application/{aid}", method = RequestMethod.GET)
	public ModelAndView showApplicationDetails(HttpServletRequest request, @PathVariable("jid") Integer jid, @PathVariable("aid") Integer aid) {
		
		ModelAndView mv = new ModelAndView("reviewer/reviewdetails");
		JobService js = new JobService();
		// add job posting info
		mv.addObject("jobPosting", js.getJobPost(jid));
		
		// add application info
		JobApplication ja = js.getJobApplication(jid, aid);
		int n = 0;
		String[] details = ja.getCandidatesDetails().split(",");
		mv.addObject("appEmail", details[0]);
		mv.addObject("appFName", details[1]);
		mv.addObject("appLName", details[2]);
		try {
			n = Integer.parseInt(details[details.length - 1]);
			
		} catch (Exception e) {
			n = 0;
		}
		ja.setPhoneNo(n);
		mv.addObject("jobApplication", ja);
		Review r = js.getReviewIdEmail(aid, ((User) request.getSession().getAttribute("user")).getEmail());
		return mv.addObject("reviewForm", r);
	}
	
	@RequestMapping(value = "/review/job/{jid}/application/{aid}", method = RequestMethod.POST)
	public ModelAndView updateReview(HttpServletRequest request, @ModelAttribute("reviewForm") Review r, @PathVariable("jid") Integer jid, @PathVariable("aid") Integer aid) {
		
		new JobService().updateReview(r);
		request.setAttribute("updated", "Has been successfully updated");
		return new ModelAndView("redirect:/reviewer/review/job/" +jid+ "/application/" +aid);
	}

}
