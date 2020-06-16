package com.customs.leave_management.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.customs.leave_management.config.JwtTokenUtil;
import com.customs.leave_management.model.EmpLeaveDTO;
import com.customs.leave_management.model.EmpRegisterDTO;
import com.customs.leave_management.model.JwtRequest;
import com.customs.leave_management.model.JwtResponse;
import com.customs.leave_management.repository.EmployeeRepository;
import com.customs.leave_management.services.JwtUserDetailsService;
import com.customs.leave_management.services.LeaveService;

@RestController
//@CrossOrigin(origins="http://localhost:4200")
@CrossOrigin(origins="*")
public class AuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private EmployeeRepository userDao;
	
	@Autowired
	private LeaveService leaveService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		System.out.println(" authenticationRequest.getUsername() **** ");
		System.out.println(" authenticationRequest.getUsername() **** "+authenticationRequest.getUsername());
//		ResponseType responseType=
				authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		List<Object> empDetails= new ArrayList<Object>();
//		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername()); 
//if(responseType.getMessage()!=null &&( !responseType.getMessage().equals("INVALID_CREDENTIALS")
//			&& !responseType.getMessage().equals("USER_DISABLED")))
		 empDetails= userDetailsService.employeeInfoList(authenticationRequest.getUsername()); 
		System.out.println("*emInfo values" +empDetails.get(0) +"*******"+empDetails.get(1));
		final UserDetails userDetails = (UserDetails) empDetails.get(0);
		final EmpRegisterDTO empInfo =  (EmpRegisterDTO) empDetails.get(1);
		final String token = jwtTokenUtil.generateToken(userDetails);
//		return ResponseEntity.ok(new JwtResponse(token)) 
//		return ResponseEntity.ok(new JwtResponse(token,empInfo.getEmpId(),empInfo.getEmpName(),empInfo.getRole()
//				,empInfo.getMobileNo(),empInfo.getEmailId(),empInfo.getDeptName()));
//	return ResponseEntity.ok(new JwtResponse(token,empInfo,responseType));
	return ResponseEntity.ok(new JwtResponse(token,empInfo));
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody EmpRegisterDTO empRegister) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(empRegister));
	}

	private void authenticate(String username, String password) throws Exception {
//	private ResponseType authenticate(String username, String password) throws Exception {
		ResponseType responseType=new ResponseType();
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
//			responseType.setCode(200);
//			responseType.setMessage("USER_DISABLED");
//			responseType.setStatus(false); 
//			return responseType ;
//			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
//			throw new Exception("INVALID_CREDENTIALS", e);
//			responseType.setCode(200);
//			responseType.setMessage("INVALID_CREDENTIALS");
//			responseType.setStatus(false); 
//			return responseType ;
		}catch (Exception e) {
			System.out.println(" INVALID USER");
		}
//		responseType.setCode(500);
//		responseType.setMessage("SUCCESS");
//		responseType.setStatus(true); 
//		return responseType ;
	}
	
	
//	@PostMapping("/employeeLeaveRequest")
//	    public EmpLeaveDTO employeeLeaveRequest(@Valid @RequestBody EmpLeaveDTO  empLeaveDTO) throws FileNotFoundException {	
//		EmpLeaveDTO result=new EmpLeaveDTO();
//	    	try { 
//			    
////			    result = service.exportCourseStatusReport(format,courseStatusData);
//			    result = leaveService.saveEmployeeLeaveRequest(empLeaveDTO);
//			    
//		    }catch(Exception ex) {
//		    	ex.printStackTrace();
//		    }
//	    	return result; 
//	    }
}