package com.customs.leave_management.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity 
@Table(name="CSTM_EMPLOYEE_REGISTER")
public class EmpRegisterDTO {
	@Id
	@Column(name="EMP_ID")
	private long empId;
	@Column(name="EMP_NAME")
	private String empName;
	@Column(name="PASSWRD")
	private String password;
	@Column(name="EMP_ROLE")
	private String role;
	@Column(name="EMAIL_ID")
	private String emailId;
	@Column(name="MOBILE_NO")
	private String mobileNo;
	@Column(name="DEPT_NAME")
	private String deptName;
	
 
	//	@OneToMany(mappedBy = "empLeaveDTO", cascade = CascadeType.ALL)
//    private List<EmpLeaveDTO> empLeaveDTO;
//	
//	
//	public List<EmpLeaveDTO> getEmpLeaveDTO() {	
//		return empLeaveDTO;
//	}
//	public void setEmpLeaveDTO(List<EmpLeaveDTO> empLeaveDTO) {
//		this.empLeaveDTO = empLeaveDTO;
//	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	 
	 
	public long getEmpId() {
		return empId;
	}
	public void setEmpId(long empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	 
	 
}
