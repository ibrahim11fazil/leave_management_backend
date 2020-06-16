package com.customs.leave_management.model;

import java.io.Serializable;

import com.customs.leave_management.controller.ResponseType;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private EmpRegisterDTO empInfo;
	private ResponseType responseType;
 
	
	
	public EmpRegisterDTO getEmpInfo() {
		return empInfo;
	}

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}
	
	public JwtResponse(String jwttoken,EmpRegisterDTO empInfo) {
		this.jwttoken = jwttoken;
		this.empInfo=empInfo; 
	}
	
	public JwtResponse(String jwttoken,EmpRegisterDTO empInfo,ResponseType responseType) {
		this.jwttoken = jwttoken;
		this.empInfo=empInfo;
		this.responseType=responseType; 
	}
	public JwtResponse(String jwttoken,int empId, String empName ,String role, String mobileNo,
			String emailId,String deptName) {
		this.jwttoken = jwttoken;
//		this.empId = empId;
//		this.role = role;
//		this.mobileNo = mobileNo;
//		this.emailId = emailId;
//		this.deptName = deptName; 
	}
	
	public String getToken() {
		return this.jwttoken;
	}
	
	
}