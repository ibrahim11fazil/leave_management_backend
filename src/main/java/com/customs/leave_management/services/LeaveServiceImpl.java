package com.customs.leave_management.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customs.leave_management.controller.ResponseType;
import com.customs.leave_management.model.EmpLeaveDTO;
import com.customs.leave_management.model.EmpRegisterDTO;
import com.customs.leave_management.repository.EmployeeRepository;
import com.customs.leave_management.repository.LeaveRepository; 

@Service
public class LeaveServiceImpl implements LeaveService {
	
	@Autowired
	private LeaveRepository leaveDao;
	
	@Autowired
	private EmployeeRepository empDao;
	
	public ResponseType saveEmployeeLeaveRequest(EmpLeaveDTO leave) {
		ResponseType responseType=new ResponseType();
		List<EmpLeaveDTO> emLeaveDAOList=new ArrayList<EmpLeaveDTO>();
		try {
			Date fromDate = leave.getFromDate();
    		Date toDate = leave.getToDate();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
            String frmDate= formatter.format(fromDate);
            String tillDate= formatter.format(toDate);
            
         
            
            List<EmpLeaveDTO> empList=leaveDao.checkLeaveDateExists(leave.getEmpId(),frmDate,tillDate);
//            List<EmpLeaveDTO> empList=leaveDao.checkLeaveDateExists( leaveList.get(0).getEmpId(),leaveList.get(0).getFromAppliedYear());
            if(empList!=null && empList.size()>0) {

          	 System.out.println( " empList.size() "+ empList.size());
          	   Date frm = formatter.parse(frmDate);
                 Date to = formatter.parse(tillDate);
                 boolean dupFound=false;
          	 for(EmpLeaveDTO emp:empList) {
          		   String empfrmDate= formatter.format(emp.getFromDate());
                     String emptillDate= formatter.format(emp.getToDate());
                     Date empfrm = formatter.parse(empfrmDate);
                     Date empto = formatter.parse(emptillDate); 
                     
                     if ((frm.after(empfrm) && frm.before(empto))
                   		 || (to.after(empfrm) && to.before(empto))
                   		   || (frm.before(empfrm) && to.after(empto))) {
                  	   dupFound=true;
                         System.out.println("Date1 is after Date2");

//                     	   return "DUP";
//                         return false;
                         
                     	responseType.setCode(200);
           			responseType.setMessage("Error. From-Date and To-Date are Overlapping with previous submitted Leave Request.");
           			responseType.setStatus(false); 
           			return responseType ;
                     }


                     if (empfrm.equals(frm) || empto.equals(to) ) {
                  	   dupFound=true;
                         System.out.println("Date1 is equal Date2");
//                         return "DUP";
//                         return false;
                         
                     	responseType.setCode(200);
           			responseType.setMessage("Error. From-Date and To-Date are Overlapping with previous submitted Leave Request.");
           			responseType.setStatus(false); 
           			return responseType ;
                     }
                     
                     
          	 }
//          	 if (dupFound)
//          	 break;
           }
		
		if(leave.getId()==0) { 
			leave.setCreatedDate(new Date());
//			leave.setCreatedBy(leave.getEmpId().toString());
		}else {
//			leave.setUpdatedBy(leave.getEmpId().toString());
			leave.setUpdatedDate(new Date());
		}
		
		
		  leaveDao.save(leave);
		  long createdBy=Long.valueOf(leave.getCreatedBy());
		  emLeaveDAOList= leaveDao.findEmpLeaveListById(createdBy);
//		  emLeaveDAOList= leaveDao.findEmpLeaveListById(leave.getEmpId(),leave.getCreatedBy());
		 
		}catch(Exception ex) {
			
			ex.printStackTrace();
			responseType.setCode(200);
			responseType.setMessage("EXCEPTION_getCourseDetails");
			responseType.setStatus(false); 
			return responseType ;
			
//			return false;
		}
//		return true;
		responseType.setCode(200);
		responseType.setMessage("Employee leave request submitted successfully.");
		responseType.setStatus(true); 
		responseType.setData(emLeaveDAOList);
		return responseType ;
	}
	 public   Date addDays(Date date, int days)
	    {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.add(Calendar.DATE, days); //minus number would decrement the days
	        return cal.getTime();
	    }
	
	public ResponseType saveEmployeeLeaveRequestList(List<EmpLeaveDTO> leaveList) {
//	public String saveEmployeeLeaveRequestList(List<EmpLeaveDTO> leaveList) {
		ResponseType responseType=new ResponseType();
		List<EmpLeaveDTO> emLeaveDAOList=new ArrayList<EmpLeaveDTO>();
		try {
			Date fromDate = leaveList.get(0).getFromDate();
    		Date toDate = leaveList.get(leaveList.size()-1).getToDate();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
            String frmDate= formatter.format(fromDate);
            String tillDate= formatter.format(toDate);
            
//            List<EmpRegisterDTO> registeredEmpList=new ArrayList<EmpRegisterDTO>();
            List<EmpRegisterDTO> registeredEmpList= empDao.findRegisteredEmpList();
           if(registeredEmpList!=null && registeredEmpList.size()>0) {
            for(int i=0;i<leaveList.size();i++) { 
        		String year = String.valueOf(leaveList.get(i).getFromDate().getYear()+1900);
        	       formatter = new SimpleDateFormat("dd-MM-yyyy");  
        	       String   subfrmDate= formatter.format(leaveList.get(i).getFromDate());
        	       String  subtillDate= formatter.format(leaveList.get(i).getToDate());
        	       List<EmpLeaveDTO> empLeaveList= leaveDao.findEmployeesAppliedDatesListByYear(subfrmDate,subtillDate);
        	       int maxLeave=0;
        	       System.out.print(" registeredEmpList.size() ****  SAVE"+ registeredEmpList.size());
        	       System.out.print(" empLeaveList.size() ****  SAVE"+ empLeaveList.size());
        	       maxLeave = (int) ((registeredEmpList.size())*((double)30/(double)100));
        	       
        	       if(empLeaveList.size()>=maxLeave) {
        	    	 	responseType.setCode(200);
            			responseType.setMessage("Error: Leave for Specified Dates has exceeded 30 %, Please generate excel report for reference purpose ");
            			responseType.setStatus(false); 
            			return responseType ;
        	    	     
        	       }
            }
           }
            
            //for getting list of employees exceeding 30 percent
//            Map<EmpLeaveDTO,Map<Date, ArrayList<Integer>>>  empDetailsToVerifyPercent=new HashMap<>();
//            //jan to feb , mar to apr
//            for(int i=0;i<leaveList.size();i++) { 
//            		String year = String.valueOf(leaveList.get(i).getFromDate().getYear()+1900);
//            	       formatter = new SimpleDateFormat("dd-MM-yyyy");  
//            	       String   subfrmDate= formatter.format(leaveList.get(i).getFromDate());
//            	       String  subtillDate= formatter.format(leaveList.get(i).getToDate());
//            	       ArrayList<Integer> totalEmpCountOnEachAppliedLeaveDate=new ArrayList<>();
//            		List<EmpLeaveDTO> empLeaveList= leaveDao.findEmployeesAppliedDatesListByYear(subfrmDate,subtillDate);
//            		Date currentdate=empLeaveList.get(0).getFromDate();
//            		
//            		for(EmpLeaveDTO emp:empLeaveList) {  
//                         String empfrmDate= formatter.format(emp.getFromDate());
//                         String emptillDate= formatter.format(emp.getToDate());
//                         String currDate = formatter.format(currentdate);
//                         
////            			 if(leaveList.get(i).getFromDate().after(currentdate)  && leaveList.get(i).getToDate().before(emp.getToDate()) 
////            					 || (subfrmDate.equals(empfrmDate)  ||subtillDate.equals(emptillDate) )) {
//                         
//                       if(emp.getToDate().before(currentdate)) 
//                	   break;
//            			 if(leaveList.get(i).getFromDate().after(currentdate)  && leaveList.get(i).getToDate().before(currentdate) 
//            					 || (subfrmDate.equals(currDate)  ||subtillDate.equals(currDate) )) { 
////            				 for(int i=	)
//            					int day= currentdate.getDay();
//            					int totalEmpAppliedforaday=	 totalEmpCountOnEachAppliedLeaveDate.get(day-1);
//            					totalEmpAppliedforaday +=day;
//            					totalEmpCountOnEachAppliedLeaveDate.set(day-1, totalEmpAppliedforaday);
//            					Map <Date, ArrayList<Integer>>  empDate=new HashMap<Date, ArrayList<Integer>>();
//            					empDate.put(currentdate, totalEmpCountOnEachAppliedLeaveDate);
//            					empDetailsToVerifyPercent.put(emp, empDate);
//            			 }
//            			 currentdate = addDays(currentdate, 1);
//            		} 
//            }
//             
//            Iterator<Entry<EmpLeaveDTO, Map<Date, ArrayList<Integer>>>> iterator = empDetailsToVerifyPercent.entrySet().iterator();
//    		while (iterator.hasNext()) {
//    			Map.Entry entry = iterator.next();
//    			System.out.println("Key : " + entry.getKey() + " value : " + entry.getValue());
//    		}
            
//            List<EmpLeaveDTO> empList=leaveDao.checkLeaveDateExists(leaveDTO.getEmpId(),frmDate,tillDate);
            List<EmpLeaveDTO> empList=leaveDao.checkLeaveDateExistsOld( leaveList.get(0).getEmpId(),leaveList.get(0).getFromAppliedYear());
            if(empList!=null && empList.size()>0) {
           	 System.out.println( " empList.size() "+ empList.size());
           	   Date frm = formatter.parse(frmDate);
                  Date to = formatter.parse(tillDate);
                  boolean dupFound=false;
           	 for(EmpLeaveDTO emp:empList) {
           		   String empfrmDate= formatter.format(emp.getFromDate());
                      String emptillDate= formatter.format(emp.getToDate());
                      Date empfrm = formatter.parse(empfrmDate);
                      Date empto = formatter.parse(emptillDate); 
                      
                      if ((frm.after(empfrm) && frm.before(empto))
                    		 || (to.after(empfrm) && to.before(empto))
                    		   || (frm.before(empfrm) && to.after(empto))) {
                   	   dupFound=true;
                          System.out.println("Date1 is after Date2");

//                      	   return "DUP";
//                          return false;
                          
                      	responseType.setCode(200);
            			responseType.setMessage("Error: From-Date and To-Date are Overlapping with previous submitted Leave Request.");
            			responseType.setStatus(false); 
            			return responseType ;
                      }
 

                      if (empfrm.equals(frm) || empto.equals(to) ) {
                   	   dupFound=true;
                          System.out.println("Date1 is equal Date2");
//                          return "DUP";
//                          return false;
                          
                      	responseType.setCode(200);
            			responseType.setMessage("Error. From-Date and To-Date are Overlapping with previous submitted Leave Request.");
            			responseType.setStatus(false); 
            			return responseType ;
                      }
                      
                      
           	 }
//           	 if (dupFound)
//           	 break;
            }
			
			
			
			for(EmpLeaveDTO leaveDTO:leaveList) {
				if(leaveDTO.getId()==0) { 
					leaveDTO.setCreatedDate(new Date());
//					leaveDTO.setCreatedBy(leaveDTO.getEmpId().toString());
				}else {
//					leaveDTO.setUpdatedBy(leaveDTO.getEmpId().toString());
					leaveDTO.setUpdatedDate(new Date());
				}
				leaveDao.save(leaveDTO);
			}
			
//			emLeaveDAOList= leaveDao.findEmpLeaveListById(leaveList.get(0).getEmpId());
			 long createdBy=Long.valueOf(leaveList.get(0).getCreatedBy());
			 emLeaveDAOList= leaveDao.findEmpLeaveListById(createdBy);
//			emLeaveDAOList= leaveDao.findEmpLeaveListById(empLeaveDTO.getEmpId(),empLeaveDTO.getCreatedBy());
		 
		}catch(Exception ex) {
			ex.printStackTrace();
//			return false; 
//        	   return "ERR";
			responseType.setCode(200);
			responseType.setMessage("EXCEPTION_getCourseDetails");
			responseType.setStatus(false); 
			return responseType ;
			
		}
//		return true;
//		 return "TRUE";
		responseType.setCode(200);
		responseType.setMessage("Employee leave request submitted successfully.");
		responseType.setStatus(true); 
		responseType.setData(emLeaveDAOList);
		return responseType ;
	}
	private EmpRegisterDTO empRegisterDTO=new EmpRegisterDTO();
	private void setEmployeeDetails() {
		
		List<EmpRegisterDTO> emRegisterDAO= empDao.findAll();
		for (EmpRegisterDTO empRegisterDTO : emRegisterDAO) {
			empRegisterDTO.setPassword(null);
		}
//		empRegisterDTO.setEmployeeList(emRegisterDAO); 
		 
	}
	
	public List<EmpRegisterDTO> getEmployeeList(){
//		setEmployeeDetails();
//		return empRegisterDTO.getEmployeeList();
		List<EmpRegisterDTO> emRegisterDAO= empDao.findAll();
		for (EmpRegisterDTO empRegisterDTO : emRegisterDAO) {
			empRegisterDTO.setPassword(null);
		}
		return emRegisterDAO;
	}
	
	public  EmpRegisterDTO getEmpById(long empId){
//		setEmployeeDetails();
//		return empRegisterDTO.getEmployeeList();
		 EmpRegisterDTO emRegisterDAO= empDao.findByEmpId(empId);
//		for (EmpRegisterDTO empRegisterDTO : emRegisterDAO) {
			empRegisterDTO.setPassword(null);
//		}
		return emRegisterDAO;
	}
	
	
	public  boolean addEmpRole(EmpRegisterDTO empInfo){
		try {
//		setEmployeeDetails();
//		return empRegisterDTO.getEmployeeList();
		   empDao.save(empInfo);
 
		}catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public  List<Object> getEmployeeDetailsByYear(String year){
//		setEmployeeDetails();
//		return empRegisterDTO.getEmployeeList();
		 List<Object> emLeaveDAOList= leaveDao.searchEmployeeDetailsByYear(year);
 
		return emLeaveDAOList;
	}
	
	
	public  List<EmpLeaveDTO> getEmpLeaveListById(long empId){
//		setEmployeeDetails();
//		return empRegisterDTO.getEmployeeList();
		 List<EmpLeaveDTO> emLeaveDAOList=new ArrayList<EmpLeaveDTO>();
		try {
		 emLeaveDAOList= leaveDao.findEmpLeaveListById(empId);
		 System.out.println(emLeaveDAOList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return emLeaveDAOList;
	}
	
	
	public List<EmpLeaveDTO> deleteEmpLeaveById(EmpLeaveDTO empLeaveDTO){
//		setEmployeeDetails();
//		return empRegisterDTO.getEmployeeList();
		 List<EmpLeaveDTO> emLeaveDAOList=new ArrayList<EmpLeaveDTO>();
		try {
  System.out.println("empLeaveDTO *** " +empLeaveDTO);
  System.out.println(" empLeaveDTO "+ empLeaveDTO.getEmpId());
			leaveDao.delete(empLeaveDTO); 
			String empId =String.valueOf(empLeaveDTO.getEmpId());
//			emLeaveDAOList= leaveDao.findEmpLeaveListById(empLeaveDTO.getEmpId());
//			if(empId!=null && empLeaveDTO.getCreatedBy()!=null && empId.trim().equals(empLeaveDTO.getCreatedBy()))
				int deletedBy = Integer.parseInt(empLeaveDTO.getUpdatedBy());
				emLeaveDAOList= leaveDao.findEmpLeaveListById(deletedBy);
//			else
//				emLeaveDAOList= leaveDao.findEmpLeaveListById(empLeaveDTO.getEmpId(),empLeaveDTO.getCreatedBy());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return emLeaveDAOList;
	}
	
//	public List<EmpLeaveDTO> getexceededListDetails()
	 
}
