package com.customs.leave_management.repository;
 

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.customs.leave_management.model.EmpLeaveDTO;
import com.customs.leave_management.model.EmpRegisterDTO; 

//public class LeaveRepository extends Repository<EmpRegisterDTO, Integer> {
public interface LeaveRepository extends JpaRepository<EmpLeaveDTO, Integer> {

// 	 @Query(value=" select leave.leave_id,reg.dept_name,leave.emp_id,reg.emp_name,leave.start_date,leave.end_date,leave.leave_type"
// 	 		+ " from CSTM_EMPLOYEE_LEAVE leave,CSTM_EMPLOYEE_REGISTER reg "
// 	 		+ " where leave.emp_id=reg.emp_id "
// 	 		+ " and reg.emp_role is not null "
// 	 		+ " and EXTRACT(year FROM leave.start_date)= :year "
////			+ " and leave.From_Applied_Year= :year "
// 	 		+ " ORDER BY  reg.dept_name,leave.emp_id" ,nativeQuery = true)
//	 List<Object>  findEmployeesLeaveList(@Param("year") String year);
	
	
	 @Query(value=" select leave.leave_id,reg.dept_name,leave.emp_id,reg.emp_name,leave.start_date,leave.end_date,leave.leave_type"
		+ " from CSTM_EMPLOYEE_LEAVE leave,CSTM_EMPLOYEE_REGISTER reg "
		+ " where leave.emp_id=reg.emp_id "
		+ " and reg.emp_role is not null "
		+ " and EXTRACT(year FROM leave.start_date)= :year "
//	+ " and leave.From_Applied_Year= :year "
		+ " ORDER BY  reg.dept_name,leave.emp_id " ,nativeQuery = true)
List<Object>  searchEmployeeDetailsByYear(@Param("year") String year);
	
	
	 @Query(value=" select leave.leave_id,reg.dept_name,reg.emp_id,reg.emp_name,leave.start_date,leave.end_date,leave.leave_type "
	 	 		+ " FROM cstm_employee_register reg "  
	 	 		+ " LEFT JOIN cstm_employee_leave leave ON "
	 	 		+ " leave.emp_id=reg.emp_id "
//	 	 		+ " and reg.emp_role is not null "
	 	 		+ " and EXTRACT(year FROM leave.start_date)= :year "
				+ " where reg.emp_role is not  null "
	 	 		+ " ORDER BY  reg.dept_name,leave.emp_id" ,nativeQuery = true)
		 List<Object>  findEmployeesLeaveList(@Param("year") String year);
 	 
  
 	 
	 @Query(value=" select * "
	 	 		+ " from CSTM_EMPLOYEE_LEAVE leave "
	 	 		+ " where trunc(leave.start_date)  " 
	 	 		+ "  between TO_DATE(:fromDate, 'DD-MM-YYYY') "  
	 	 		+ " AND   TO_DATE(:tillDate,'DD-MM-YYYY') "  
	 	 		+ " or trunc(leave.end_date) "  
	 	 		+ " between TO_DATE(:fromDate, 'DD-MM-YYYY') "  
	 	 		+ " AND   TO_DATE(:tillDate ,'DD-MM-YYYY') "
	 	 		+ " OR " 
	 	 		 +" (trunc(leave.start_date) <= TO_DATE(:fromDate, 'DD-MM-YYYY') " 
	 	 		 +"   AND  trunc(leave.end_date) >= TO_DATE(:tillDate, 'DD-MM-YYYY')) ",nativeQuery = true)  
		 List<EmpLeaveDTO>  findEmployeesAppliedDatesListByYear( @Param("fromDate") String fromDate,@Param("tillDate") String tillDate);
 	 
 	@Query(value="Delete "
 	 		+ " from CSTM_EMPLOYEE_LEAVE leave  " 
 	 		+ " where "
 	 		+ " leave.From_Applied_Year= :year " 
 	 		,nativeQuery = true)
 	 void deleteEmpDetailsByYear(@Param("year") String year );
 	
 	
	@Query(value="Select * "
 	 		+ " from CSTM_EMPLOYEE_LEAVE leave " 
 	 		+ " where leave.From_Applied_Year= :year " 
//			+ " EXTRACT(year FROM leave.start_date)= :year "
 	 		,nativeQuery = true)
 	 List<EmpLeaveDTO> findEmpDetailsByYear(@Param("year") String year );
	
	@Query(value=" select leave.leave_id,reg.dept_name,leave.emp_id,reg.emp_name,leave.start_date,leave.end_date,leave.leave_type"
 	 		+ " from CSTM_EMPLOYEE_LEAVE leave,CSTM_EMPLOYEE_REGISTER reg "
 	 		+ " where leave.emp_id=reg.emp_id"
// 	 		+ " and EXTRACT(year FROM leave.start_date)= :year " 
 	 		+ " and trunc(leave.start_date) >= TO_DATE(:fromDate, 'DD-MM-YYYY') "
 	 		+ " AND trunc(leave.end_date) <= TO_DATE(:tillDate ,'DD-MM-YYYY') "
 	 		+ " ORDER BY  reg.dept_name,leave.emp_id" ,nativeQuery = true)
// 	 List<Object> findEmpDetailsByfromandToDate(@Param("year") String year, @Param("fromDate") String fromDate,@Param("tillDate") String tillDate);
	List<Object> findEmpDetailsByfromandToDate( @Param("fromDate") String fromDate,@Param("tillDate") String tillDate);
 	
 	@Query(value="SELECT  * "
 	 		+ " from CSTM_EMPLOYEE_LEAVE leave " 
 	 		+ " where "
// 	 		+ " trunc(leave.start_date) >= TO_DATE(:fromDate, 'DD-MM-YYYY') "
// 	 		+ " AND trunc(leave.end_date) <= TO_DATE(:tillDate ,'DD-MM-YYYY') "
 	 		+ " leave.From_Applied_Year= :year"
 	 		+ " and leave.emp_id = :empId " 
 	 		,nativeQuery = true)
// 	 List<EmpLeaveDTO> checkLeaveDateExists(@Param("empId") long empId, @Param("fromDate") String fromDate,@Param("tillDate") String tillDate  );
 	 List<EmpLeaveDTO> checkLeaveDateExistsOld(@Param("empId") long empId, @Param("year") String year  );
 	
 	@Query(value="SELECT  * "
 	 		+ " from CSTM_EMPLOYEE_LEAVE leave " 
 	 		+ " where "
 	 		+ " trunc(leave.start_date) >= TO_DATE(:fromDate, 'DD-MM-YYYY') "
 	 		+ " AND trunc(leave.end_date) <= TO_DATE(:tillDate ,'DD-MM-YYYY') "
// 	 		+ " leave.From_Applied_Year= :year"
 	 		+ " and leave.emp_id = :empId " 
 	 		,nativeQuery = true)
 	 List<EmpLeaveDTO> checkLeaveDateExists(@Param("empId") long empId, @Param("fromDate") String fromDate,@Param("tillDate") String tillDate  );
// 	 List<EmpLeaveDTO> checkLeaveDateExists(@Param("empId") long empId, @Param("year") String year  );
 	
 	
 	@Query(value="Select * "
 	 		+ " from CSTM_EMPLOYEE_LEAVE leave  " 
 	 		+ " where leave.emp_id= :empId "  
 	 		+ " or leave.created_by= :empId "
 	 		,nativeQuery = true)
 	 List<EmpLeaveDTO> findEmpLeaveListById(@Param("empId") long empId);
 	
 	@Query(value="Select * "
 	 		+ " from CSTM_EMPLOYEE_LEAVE leave  " 
 	 		+ " where leave.emp_id= :empId"
 	 		+ " or leave.created_by= :createdBy " 
 	 		,nativeQuery = true)
 	 List<EmpLeaveDTO> findEmpLeaveListById(@Param("empId") long empId,@Param("createdBy") String createdBy);
 	  
}
