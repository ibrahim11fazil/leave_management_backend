package com.customs.leave_management.repository;   
import java.util.Date;
import java.util.List;
 

 
public interface LeaveRepositoryCustomRepository   {

	
//	   public List<CourseStatusReport> generateCourseStatusReport(String employee, String jobNo
//	    		,String courseName,
//	    		Date startDate,Date endDate,
//	    		Long duration,
//	    		String job,
//	    		String jobTitle,String department,
//	    		Long jobCardNo,
//	    		String requestStatus,
//	    		String courseStatus);

		public List<Object> getLeaveList(int startRecord,
				int maxRecords);

		void deleteLeaveListByYear(String year); 
	  
	   
}
