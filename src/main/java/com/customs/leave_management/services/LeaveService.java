package com.customs.leave_management.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.customs.leave_management.controller.ResponseType;
import com.customs.leave_management.model.EmpLeaveDTO;
import com.customs.leave_management.model.EmpRegisterDTO;

public interface LeaveService {
//	public EmpLeaveDTO saveEmployeeLeaveRequest(EmpLeaveDTO leave);
	public List<EmpRegisterDTO> getEmployeeList();
	public  EmpRegisterDTO getEmpById(long empId);
//	public  EmpRegisterDTO addEmpRole(EmpRegisterDTO empInfo);
	public  List<Object> getEmployeeDetailsByYear(String year);
	public  boolean addEmpRole(EmpRegisterDTO empInfo);
//	public boolean saveEmployeeLeaveRequest(EmpLeaveDTO leave);
	public ResponseType saveEmployeeLeaveRequestList(List<EmpLeaveDTO> leaveList);
//	public String saveEmployeeLeaveRequestList(List<EmpLeaveDTO> leaveList);
	public  List<EmpLeaveDTO> getEmpLeaveListById(long empId);
//	public void deleteEmpLeaveById(EmpLeaveDTO empLeaveDTO);
	public List<EmpLeaveDTO> deleteEmpLeaveById(EmpLeaveDTO empLeaveDTO);
	public ResponseType saveEmployeeLeaveRequest(EmpLeaveDTO leave);
}
