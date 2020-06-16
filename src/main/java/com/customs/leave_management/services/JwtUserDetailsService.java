package com.customs.leave_management.services;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.customs.leave_management.controller.ResponseType;
import com.customs.leave_management.model.EmpRegisterDTO;
import com.customs.leave_management.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	@Autowired
	private EmployeeRepository userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	EmpRegisterDTO user;

	@Override
	public UserDetails loadUserByUsername(String empName) throws UsernameNotFoundException {
		System.out.println("**Employee Name"+empName);
//		EmpRegisterDTO user = userDao.findByEmpName(empName);
		user =  employeeInfo( empName);
		
		System.out.println("USER VALUE IS ***"+user);
		if (user == null) {
//			throw new UsernameNotFoundException("User not found with username: " + empName);
		} 
		
//		return new org.springframework.security.core.userdetails.User(user.getEmpName(), user.getPassword(),
		return new org.springframework.security.core.userdetails.User(String.valueOf(user.getEmpId()), user.getPassword(),
				new ArrayList<>());
	}

	public EmpRegisterDTO employeeInfo(String empName) throws UsernameNotFoundException{ 
//		 EmpRegisterDTO employeeDetails = userDao.findByEmpName(empName);
		long empId=Long.parseLong(empName);
			EmpRegisterDTO employeeDetails = userDao.findByEmpId(empId);
		 return employeeDetails;
	}
	
	public List<Object> employeeInfoList(String empName) throws UsernameNotFoundException{ 
		 UserDetails employeeDetails = loadUserByUsername( empName);  
		 ArrayList<Object> empDetailslist=new ArrayList<Object>();
		 empDetailslist.add(employeeDetails);
		 user.setPassword(null);
		 empDetailslist.add(user);
		 
		 return empDetailslist;
	}
//	public boolean save(EmpRegisterDTO user) {
	public ResponseType save(EmpRegisterDTO user) {
		
		EmpRegisterDTO newUser = new EmpRegisterDTO();
		ResponseType responseType=new ResponseType();
		try {	
			
			List<EmpRegisterDTO> employeeDetails=userDao.findByEmpIdorEmail(user.getEmpId(),user.getEmailId());
			 if(employeeDetails!=null && employeeDetails.size()>0) {
				 
				 responseType.setCode(100);
				 responseType.setData(employeeDetails);
				 responseType.setMessage("DATA_EXISTS");
				 responseType.setStatus(false);
				 return responseType;
				 
			 } 
				newUser.setEmpId(user.getEmpId());
				newUser.setEmpName(user.getEmpName());
				newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
				newUser.setDeptName(user.getDeptName());
				newUser.setEmailId(user.getEmailId());
				newUser.setMobileNo(user.getMobileNo());
				newUser.setRole(user.getRole());
			    userDao.save(newUser);
			    
		}catch(Exception ex) {
//			return false;
			 responseType.setCode(100);
			 responseType.setData(null);
			 responseType.setMessage("EXCEPTION");
			 responseType.setStatus(false);
			 return responseType;
		}
		 
//		return true;
		 responseType.setCode(100);
		 responseType.setData(null);
		 responseType.setMessage("REGISTERED");
		 responseType.setStatus(true);
		 return responseType;
	}

	 
}