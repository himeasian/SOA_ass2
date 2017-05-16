package au.edu.unsw.soacourse.foundit.services;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import au.edu.unsw.soacourse.foundit.bean.JobApplication;

public class ApplicationComparator implements Comparator<JobApplication> {
	
	
	public final static List<String> APP_LIST = Arrays.asList(JobService.APP_STATUSES);
	@Override
	public int compare(JobApplication o1, JobApplication o2) {
		if (APP_LIST.indexOf(o1.getStatus()) > APP_LIST.indexOf(o2.getStatus())) {
			
		}
		return APP_LIST.indexOf(o1.getStatus()) - (APP_LIST.indexOf(o2.getStatus()));
	}
}