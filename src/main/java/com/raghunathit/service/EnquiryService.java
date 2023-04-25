package com.raghunathit.service;

import java.util.List;

import com.raghunathit.binding.DashboardResponse;
import com.raghunathit.binding.EnquiryForm;
import com.raghunathit.binding.EnquirySearchCriteria;

public interface EnquiryService {
	
	public List<String> getCourseNames();
	
	public List<String> getEnqStatus();

	public DashboardResponse getDashboardData(Integer userId);
	
	public String upsertEnquiry(EnquiryForm form);
	
	public List<EnquiryForm> getEnquries(Integer userId,EnquirySearchCriteria criteria);
	
	public EnquiryForm getEnquiry(Integer userId);
	
	
}
