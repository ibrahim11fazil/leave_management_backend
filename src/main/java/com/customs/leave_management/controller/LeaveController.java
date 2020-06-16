package com.customs.leave_management.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.customs.leave_management.model.EmpLeaveDTO;
import com.customs.leave_management.model.EmpRegisterDTO;
import com.customs.leave_management.services.LeaveService; 
 
@RestController
@CrossOrigin(origins = "*")
//@CrossOrigin(origins="http://localhost:4200")
public class LeaveController {
	
	@Autowired
	private LeaveService leaveService;
	 
	
	@PostMapping("/employeeLeaveRequest")
	    public ResponseType saveEmployeeLeaveRequest(@Valid @RequestBody EmpLeaveDTO  employee) throws FileNotFoundException {	
//		EmpLeaveDTO result=new EmpLeaveDTO();
//		boolean result=false;
		ResponseType responseType=new ResponseType();
	    	try { 
			    System.out.println(" employee *** "+ employee.getEmpId());
//			    result = service.exportCourseStatusReport(format,courseStatusData);
			    responseType  = leaveService.saveEmployeeLeaveRequest(employee);
			    
		    }catch(Exception ex) {
//		    	ex.printStackTrace();
//		    	return false;
		    	return responseType ;
		    }
//	    	return result; 
	    	return responseType ;
	    }
	
	
	@PostMapping("/employeeLeaveRequestList")
    public ResponseType saveEmployeeLeaveRequestList(@Valid @RequestBody List<EmpLeaveDTO>  employeeList) throws FileNotFoundException {	
//	EmpLeaveDTO result=new EmpLeaveDTO();
//	boolean result=false;
//		String result="";
		ResponseType responseType=new ResponseType();
    	try { 
//		    System.out.println(" employee *** "+ employee.getEmpId());
//		    result = service.exportCourseStatusReport(format,courseStatusData);
    		responseType = leaveService.saveEmployeeLeaveRequestList(employeeList);
		    
	    }catch(Exception ex) {
//	    	ex.printStackTrace();
//	    	return false;
//	    	return result; 
			return responseType ;
	    }
    	return responseType; 
    }
	
	@GetMapping("/regEmpList")
    public List<EmpRegisterDTO> getRegEmpList() throws FileNotFoundException {	
		List<EmpRegisterDTO> result=new ArrayList<EmpRegisterDTO>();
    	try { 
//		    System.out.println(" employee *** "+ employeze.getEmpId());
//		    result = service.exportCourseStatusReport(format,courseStatusData);
		    result = leaveService.getEmployeeList();
		    
	    }catch(Exception ex) {
	    	ex.printStackTrace();
	    }
    	return result; 
    }	 
    	
    	
    	@GetMapping("/employeeDetails/{empId}")
        public  EmpRegisterDTO getEmpById(@PathVariable long empId) throws FileNotFoundException {	
    		 EmpRegisterDTO result=new  EmpRegisterDTO();
        	try { 
//    		    System.out.println(" employee *** "+ employeze.getEmpId());
//    		    result = service.exportCourseStatusReport(format,courseStatusData);
    		    result = leaveService.getEmpById(empId);
    		    
    	    }catch(Exception ex) {
    	    	ex.printStackTrace();
    	    }
        	return result; 
        }
    	
    	@GetMapping("/getLeaveByYear/{year}")
    	public  List<Object> getEmpLeaveByYear(@PathVariable String year) {
    		List<Object> result=new ArrayList<Object>();
        	try { 
//    		    System.out.println(" employee *** "+ employeze.getEmpId());
//    		    result = service.exportCourseStatusReport(format,courseStatusData);
    		    result = leaveService.getEmployeeDetailsByYear(year);
    		    
    	    }catch(Exception ex) {
    	    	ex.printStackTrace();
    	    }
        	return result; 
    	}
    	 
    	@PostMapping("/addEmpRole")
    	public  boolean addEmpRole(@Valid @RequestBody EmpRegisterDTO empInfo){
    		try {
//    		setEmployeeDetails();
//    		return empRegisterDTO.getEmployeeList();
    			leaveService.addEmpRole(empInfo);
     
    		}catch(Exception ex) {
    			return false;
    		}
    		return true;
    	}
    	
    	
    	
    	@GetMapping("/employeeLeaveListById/{empId}")
    	public  List<EmpLeaveDTO> getEmpLeaveListById(@PathVariable  String empId){
    		
    		List<EmpLeaveDTO> result=new ArrayList<EmpLeaveDTO>();
        	try { 
//    		    System.out.println(" employee *** "+ employeze.getEmpId());
//    		    result = service.exportCourseStatusReport(format,courseStatusData);
        		long empid=0l;
        		if(empId!=null  && !empId.trim().equals("null") &&  !empId.trim().equals(""))
         		  empid=Long.parseLong(empId);
    		    result = leaveService.getEmpLeaveListById(empid);
    		    
    	    }catch(Exception ex) {
    	    	ex.printStackTrace();
    	    }
        	return result; 
    	}
    	
    	
    	
    	@PostMapping("/deleteEmpLeaveById")
    	public   List<EmpLeaveDTO> deleteEmpLeaveById(@Valid @RequestBody  EmpLeaveDTO empLeaveDTO){
    		List<EmpLeaveDTO> result=new ArrayList<EmpLeaveDTO>();
        	try { 
        		result=leaveService.deleteEmpLeaveById(empLeaveDTO);
    		    
    	    }catch(Exception ex) {
    	    	ex.printStackTrace();
    	    } 
        	return result;
    	}
    	
}
