package au.edu.unsw.soacourse.foundit.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import au.edu.unsw.soacourse.foundit.bean.JobApplication;
import au.edu.unsw.soacourse.foundit.model.Application;
import au.edu.unsw.soacourse.foundit.model.JobPosting;
import au.edu.unsw.soacourse.foundit.model.User;
import au.edu.unsw.soacourse.foundit.services.JobService;

/**
 * Applicant specific controller
 * 
 * @author trungisme
 *
 */
@Controller
@RequestMapping("applicant")
public class ApplicantController {

	@RequestMapping("")
	public ModelAndView indexAction(HttpServletRequest request) {
		Map<Integer, String> salary = new LinkedHashMap<Integer, String>();
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
		ModelAndView mv = new ModelAndView("applicant/applicant", "jobSearch", new JobPosting());
		mv.addObject("salaryList", salary);
		mv.addObject("numNotifs", 4);
		mv.addObject("currentApplications",
				new JobService().getCurrentApplications(((User) request.getSession().getAttribute("user")).getEmail()));
		return mv;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searchAction(@ModelAttribute("jobSearch") JobPosting j, BindingResult result) {
		if (result.hasErrors())
			return new ModelAndView("errors");

		return new ModelAndView("applicant/results", "jobpostings", new JobService().getJobPosts(j));
	}

	@RequestMapping("/notifications")
	public ModelAndView viewNotifications() {
		return new ModelAndView("applicant/notification");
	}

	@RequestMapping("/job/{id}")
	public ModelAndView viewJobPosting(@PathVariable("id") Integer id) {
		ModelAndView mv = new ModelAndView("applicant/jobdetails");
		mv.addObject("jobPosting", new JobService().getJobPost(id));
		mv.addObject("jobApplication", new JobApplication());
		return mv;
	}

	@RequestMapping(value = "/job/{id}/application", method = RequestMethod.GET)
	public ModelAndView showApplicationForm(@PathVariable("id") Integer id) {
		ModelAndView mv = new ModelAndView("applicant/jobdetails");
		mv.addObject("jobPosting", new JobService().getJobPost(id));
		mv.addObject("apply", true);
		mv.addObject("jobApplication", new JobApplication());
		return mv;
	}

	@RequestMapping(value = "/job/{jid}/application/{aid}", method = RequestMethod.GET)
	public ModelAndView updateApplicationForm(@PathVariable("jid") Integer jid, @PathVariable("aid") Integer aid) {
		ModelAndView mv = new ModelAndView("applicant/jobdetails");

		JobService js = new JobService();

		mv.addObject("jobPosting", js.getJobPost(jid));
		mv.addObject("update", true);
		JobApplication ja = js.getApplication(jid, aid);
		
		int n = 0;
		try {
			String[] details = ja.getCandidatesDetails().split(",");
			n = Integer.parseInt(details[details.length - 1]);
		} catch (Exception e) {
			n = 0;
		}
		ja.setPhoneNo(n);		
		mv.addObject("jobApplication", ja);
		return mv;
	}

	@RequestMapping(value = "/job/{id}/application", method = RequestMethod.POST)
	public ModelAndView sendApplication(@ModelAttribute("jobApplication") JobApplication ja, BindingResult result,
			@PathVariable("id") Integer id, HttpServletRequest request) {
		if (result.hasErrors())
			return new ModelAndView("errors");

		User u = (User) request.getSession().getAttribute("user");
		String details = "";
		if (ja.getPhoneNo() == 0)
			details = u.getEmail() + "," + u.getFname() + "," + u.getLname();
		else
			details = u.getEmail() + "," + u.getFname() + "," + u.getLname() + "," + ja.getPhoneNo();
		ja.setCandidatesDetails(details);
		ja.set_jobID(id);
		new JobService().createApplication(ja);
		return new ModelAndView("redirect:/applicant");
	}

	@RequestMapping(value = "/job/{jid}/application/{aid}", method = RequestMethod.POST)
	public ModelAndView updateApplication(@ModelAttribute("jobApplication") JobApplication ja, BindingResult result,
			@PathVariable("jid") Integer jid, @PathVariable("aid") Integer aid, HttpServletRequest request) {
		if (result.hasErrors())
			return new ModelAndView("errors");
		
		User u = (User) request.getSession().getAttribute("user");
		String details = "";
		if (ja.getPhoneNo() == 0)
			details = u.getEmail() + "," + u.getFname() + "," + u.getLname();
		else
			details = u.getEmail() + "," + u.getFname() + "," + u.getLname() + "," + ja.getPhoneNo();
		ja.setCandidatesDetails(details);
		ja.set_jobID(jid);
		ja.set_appID(aid);
		
		new JobService().updateApplication(ja);
		return new ModelAndView("redirect:/applicant/job/" + jid + "/application/" + aid);
	}
}
