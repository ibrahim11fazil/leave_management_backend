package com.customs.leave_management.repository;    
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Map; 
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;  
import org.springframework.stereotype.Repository;

import com.customs.leave_management.model.EmpLeaveDTO; 

@Repository 
public class LeaveRepositoryCustomRepositoryImpl  implements LeaveRepositoryCustomRepository  {
	 

	@PersistenceContext
    EntityManager entityManager;
	  
	
		
		@Override
//	    public List<CourseStatusReport> getCourseStatus(CourseStatusReport courseStatusSearch, int startRecord, int maxRecords){
		  public List<Object> getLeaveList( int startRecord, int maxRecords){
	    	System.out.println("IM HERE ");
	    	 List<Object> courseStatusData = new ArrayList<Object>() ;
	    	try {
			    	Map<String,Object> conditions = new HashMap<>();
			    	StringBuilder sqlQuery =new StringBuilder();
			    	
			    	sqlQuery.append(" select * from CSTM_EMPLOYEE_LEAVE leave,CSTM_EMPLOYEE_REGISTER register  " );
		    	    sqlQuery.append(  " where leave.emp_id=reg.emp_id "  ); 
		    	    
		    	   
			    	   
			    	    System.out.println("sqlQuery" +sqlQuery);
			    	    javax.persistence.Query q;
			    	    if(maxRecords>0 )
			    	    	 q = entityManager.createNativeQuery(sqlQuery.toString());
//			    	    	 .setFirstResult(startRecord).setMaxResults(maxRecords);
			    	    else
			    	    	 q = entityManager.createNativeQuery(sqlQuery.toString());
			    	    conditions.forEach((k,v) -> {
							   q.setParameter(k,v);
						   });
		//	    	    List<CourseStatusReport>
//			    	    for(Object obj :q.getResultList()) {
//			    	    	
//			    	    	CourseStatusReport cst = (CourseStatusReport) obj;
//			    	    	System.out.println("result list O date: " + obj);
//			    	    	System.out.println("result list request date: " + cst.getRequestStatus());
//			    	    }
			    	    courseStatusData =  q.getResultList(); 
			    	
			    	     
			    	     
	    	}catch (Exception e) {
				e.printStackTrace();
			}
	    	return courseStatusData;
	    	
	    } 
		
		
		
		@Override
//	    public List<CourseStatusReport> getCourseStatus(CourseStatusReport courseStatusSearch, int startRecord, int maxRecords){
		  public void deleteLeaveListByYear( String year){
	    	System.out.println("IM HERE ");
	    	boolean status =false;
	    	try {
			    	Map<String,Object> conditions = new HashMap<>();
			    	StringBuilder sqlQuery =new StringBuilder();
			    	
			    	sqlQuery.append("Delete  from CSTM_EMPLOYEE_LEAVE leave  " );
		    	    sqlQuery.append(  " where leave.From_Applied_Year= :year "  ); 
		    	    
		    	   
			    	   
			    	    System.out.println("sqlQuery" +sqlQuery);
			    	    javax.persistence.Query q;
			    	    
			    	    	 q = entityManager.createNativeQuery(sqlQuery.toString());
			    	    conditions.forEach((k,v) -> {
							   q.setParameter(k,v);
						   }); 
//			    	    status =  q.getResultList(); 
			    	
			    	     
			    	     
	    	}catch (Exception e) {
				e.printStackTrace();
			} 
	    } 
}




























//@Override
//public List<CourseStatusReport> generateCourseStatusReport(String employee, String jobNo
//		,String courseName,Date startDate,Date endDate,Long duration,String job,
//		String jobTitle,String department,Long jobCardNo,String requestStatus,
//		String courseStatus){
//	System.out.println("IM HERE ");
//	 List<CourseStatusReport> courseStatusData = new ArrayList<CourseStatusReport>() ;
//	try {
//	    	Map<String,Object> conditions = new HashMap<>();
//	    	StringBuilder sqlQuery =new StringBuilder();
//	    	
//	    	sqlQuery.append(" select * from TAC_COURSE_STATUS_VIEW  " );
//    	    sqlQuery.append(  " where ( UPPER(EMPLOYEE) like UPPER('%'||:employee||'%')   or trim(:employee)  is null) "  );
//    	    conditions.put("employee", employee);
//    	    sqlQuery.append(  " AND ( UPPER(JOBNO) like   UPPER('%'||:jobNo||'%')    or trim(:jobNo)  is null) "  );
//    	    conditions.put("jobNo", jobNo);
//    	    sqlQuery.append(  " AND ( UPPER(COURSE_NAME) like UPPER('%'||:courseName||'%') or trim(:courseName) is null) ");
//    	    conditions.put("courseName", courseName);
//    	    if(startDate!=null && endDate!=null)
//    	    { 
//    	    	
//    	    	System.out.println("*****Start and end date " + startDate +"...."+ endDate);
//    	    	  sqlQuery.append( " AND ( (START_DATE BETWEEN :startDate AND :endDate )) " );
//    	    	  conditions.put("startDate", startDate);
//    	    	  conditions.put("endDate", endDate);
//    		}
//    	    else if(startDate!=null)
//    	    { 
//    	    	  sqlQuery.append( " AND ( START_DATE= :startDate or :startDate is   null) " );
//		    	  conditions.put("startDate", startDate);
//		    }
//    	    if(startDate!=null && endDate!=null)
//    	    { 
//    	    	
//    	    	System.out.println("*****Start and end date " + startDate +"...."+ endDate);
//    	    	  sqlQuery.append( " AND ( (END_DATE BETWEEN :startDate AND :endDate )) " );
//    	    	  conditions.put("startDate", startDate);
//    	    	  conditions.put("endDate", endDate);
//    		}else 
//    		if(endDate!=null) {
//    		    	    	sqlQuery.append(  " AND ( END_DATE= :endDate  or :endDate is   null) " );
//    		    	    	conditions.put("endDate", endDate);
//	    		    	    }
//			sqlQuery.append(  " AND ( DURATION like  '%'||:duration||'%'   or  :duration = '0'  or trim(:duration) is   null) "   );
//			conditions.put("duration", duration);
//			sqlQuery.append(  " AND ( UPPER(JOB) like UPPER('%'||:job||'%') or trim(:job) is  null) "  );
//			conditions.put("job", job);
//			sqlQuery.append(  " AND ( UPPER(JOB_TITLE)  like UPPER('%'||:jobTitle||'%')   or trim(:jobTitle) is   null) "  );
//			conditions.put("jobTitle", jobTitle);
//			sqlQuery.append(  " AND ( UPPER(DEPARTMENT)   like UPPER('%'||:department||'%')   or trim(:department) is   null) "   );
//			conditions.put("department", department);
//			sqlQuery.append(  " AND ( JOBCARD_NO  like  '%'||:jobCardNo||'%'   or  :jobCardNo = '0' or trim(:jobCardNo) is   null) "  ); 
//			conditions.put("jobCardNo", jobCardNo);
////			if(requestStatus!=null && !requestStatus.trim().equals("")) {
////				sqlQuery.append(  " AND ( UPPER(REQUEST_STATUS) like UPPER('%'||:requestStatus||'%')  ) "  );
////				conditions.put("requestStatus", requestStatus);
////			}
//			sqlQuery.append(  " AND ( UPPER(REQUEST_STATUS) like UPPER('%'||:requestStatus||'%')  or trim(:requestStatus) is  null) "  );
//			conditions.put("requestStatus", requestStatus);
//			sqlQuery.append(  " AND ( UPPER(COURSE_STATUS)  like UPPER('%'||:courseStatus||'%')  or  trim(:courseStatus) is  null) ");
//			conditions.put("courseStatus", courseStatus);
//	    	
//	    	   
//	    	    System.out.println("sqlQuery" +sqlQuery);
//	    	    javax.persistence.Query q = entityManager.createNativeQuery(sqlQuery.toString(),CourseStatusReport.class);
//	    	    conditions.forEach((k,v) -> {
//					   q.setParameter(k,v);
//				   });
////	    	    List<CourseStatusReport>
////	    	    for(Object obj :q.getResultList()) {
////	    	    	
////	    	    	CourseStatusReport cst = (CourseStatusReport) obj;
////	    	    	System.out.println("result list O date: " + obj);
////	    	    	System.out.println("result list request date: " + cst.getRequestStatus());
////	    	    }
//	    	    courseStatusData =  q.getResultList(); 
//	    	
//	    	     
//	    	     
//	}catch (Exception e) {
//		e.printStackTrace();
//	}
//	return courseStatusData;
//	
//} 


//Query
 
//	    	StringBuffer sqlQuery =new StringBuffer();
//    	    sqlQuery.append("select s from CourseStatusReport s "
//    	    		+ "where (s.employee = :employee or :employee  is null) "
//    	    		+ " AND (s.jobNo = :jobNo or :jobNo  is null) "
//    	    		+ " AND (s.courseName= :courseName or :courseName is null) " 
////		+ " AND ( DURATION = :duration or :duration = '0' or :duration is   null) " 
//		+ " AND ( s.job = :job or :job is  null) "
//		+ " AND ( s.jobTitle= :jobTitle  or :jobTitle is   null) "
//		+ " AND ( s.department= :department or :department is   null) " 
////		+ " AND ( JOBCARD_NO= :jobCardNo or  :jobCardNo = '0' or :jobCardNo is   null) "  
//		+ " AND ( s.requestStatus= :requestStatus or :requestStatus is  null) "
//		+ " AND ( s.courseStatus= :courseStatus or  :courseStatus is  null) ");
//    	    if(startDate!=null) {
//    	    	sqlQuery.append( " AND (s.startDate= :startDate or :startDate is   null) " );
//    	    }
//    	    if(endDate!=null) {
//    	    	sqlQuery.append(  " AND ( s.endDate= :endDate  or :endDate is   null) " );
//    	    }
//    	    System.out.println("sqlQuery" +sqlQuery);
//    	    javax.persistence.Query q = entityManager.createQuery(sqlQuery.toString(),CourseStatusReport.class);
//    	    List<CourseStatusReport> authors = q.getResultList();
//    	  
//    	     System.out.println(authors);

//concat

//sqlQuery.append(" select * from TAC_COURSE_STATUS_VIEW  " );
//sqlQuery.append(  " where (EMPLOYEE LIKE CONCAT('%',:employee ,'%')   or :employee  is null) "  );
//conditions.put("employee", employee);
//sqlQuery.append(  " AND (JOBNO LIKE CONCAT('%',:jobNo ,'%')    or :jobNo  is null) "  );
//conditions.put("jobNo", jobNo);
//sqlQuery.append(  " AND (COURSE_NAME LIKE CONCAT('%',:courseName ,'%')  or :courseName is null) ");
//conditions.put("courseName", courseName);
//if(startDate!=null) {
//    	  sqlQuery.append( " AND (START_DATE= :startDate or :startDate is   null) " );
//    	  conditions.put("startDate", startDate);
//    }
//if(endDate!=null) {
//    	    	sqlQuery.append(  " AND ( END_DATE= :endDate  or :endDate is   null) " );
//    	    	conditions.put("endDate", endDate);
//	    	    }
//sqlQuery.append(  " AND ( DURATION LIKE CONCAT('%',:duration ,'%')  or  :duration = '0'  or :duration is   null) "   );
//conditions.put("duration", duration);
//sqlQuery.append(  " AND ( JOB LIKE CONCAT('%',:job ,'%')  or :job is  null) "  );
//conditions.put("job", job);
//sqlQuery.append(  " AND ( JOB_TITLE  LIKE CONCAT('%',:jobTitle ,'%')  or :jobTitle is   null) "  );
//conditions.put("jobTitle", jobTitle);
//sqlQuery.append(  " AND ( DEPARTMENT  LIKE CONCAT('%',:department ,'%')  or :department is   null) "   );
//conditions.put("department", department);
//sqlQuery.append(  " AND ( JOBCARD_NO  LIKE CONCAT('%',:jobCardNo ,'%')  or  :jobCardNo = '0' or :jobCardNo is   null) "  ); 
//conditions.put("jobCardNo", jobCardNo);
//sqlQuery.append(  " AND ( REQUEST_STATUS LIKE CONCAT('%',:requestStatus ,'%')  or :requestStatus is  null) "  );
//conditions.put("requestStatus", requestStatus);
//sqlQuery.append(  " AND ( COURSE_STATUS LIKE CONCAT('%',:courseStatus ,'%') or  :courseStatus is  null) ");
//conditions.put("courseStatus", courseStatus);


//sqlQuery.append(" select * from TAC_COURSE_STATUS_VIEW  " );
//sqlQuery.append(  " where (EMPLOYEE  =:employee   or :employee  is null) "  );
//conditions.put("employee", employee);
//sqlQuery.append(  " AND (JOBNO  =:jobNo    or :jobNo  is null) "  );
//conditions.put("jobNo", jobNo);
//sqlQuery.append(  " AND (COURSE_NAME =:courseName or :courseName is null) ");
//conditions.put("courseName", courseName);
//if(startDate!=null) {
//    	  sqlQuery.append( " AND (START_DATE= :startDate or :startDate is   null) " );
//    	  conditions.put("startDate", startDate);
//    }
//if(endDate!=null) {
//    	    	sqlQuery.append(  " AND ( END_DATE= :endDate  or :endDate is   null) " );
//    	    	conditions.put("endDate", endDate);
//	    	    }
//sqlQuery.append(  " AND ( DURATION  =:duration  or  :duration = '0'  or :duration is   null) "   );
//conditions.put("duration", duration);
//sqlQuery.append(  " AND ( JOB =:job or :job is  null) "  );
//conditions.put("job", job);
//sqlQuery.append(  " AND ( JOB_TITLE  =:jobTitle   or :jobTitle is   null) "  );
//conditions.put("jobTitle", jobTitle);
//sqlQuery.append(  " AND ( DEPARTMENT   =:department   or :department is   null) "   );
//conditions.put("department", department);
//sqlQuery.append(  " AND ( JOBCARD_NO   =:jobCardNo  or  :jobCardNo = '0' or :jobCardNo is   null) "  ); 
//conditions.put("jobCardNo", jobCardNo);
//sqlQuery.append(  " AND ( REQUEST_STATUS =:requestStatus  or :requestStatus is  null) "  );
//conditions.put("requestStatus", requestStatus);
//sqlQuery.append(  " AND ( COURSE_STATUS  =:courseStatus  or  :courseStatus is  null) ");
//conditions.put("courseStatus", courseStatus);