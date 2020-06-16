package com.customs.leave_management.repository;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.customs.leave_management.model.EmpRegisterDTO;


@Repository
public interface EmployeeRepository extends JpaRepository<EmpRegisterDTO, Long> {
//    UserDao findByUsername(String username);
	
	 @Query("SELECT u FROM EmpRegisterDTO u WHERE u.empName = :empName")
	 EmpRegisterDTO  findByEmpName(@Param("empName") String empName);
	 
	 @Query("SELECT u FROM EmpRegisterDTO u WHERE u.empId = :empId and u.role is not null")
	 EmpRegisterDTO  findByEmpId(@Param("empId") long empId);
	 
	 @Query("SELECT u FROM EmpRegisterDTO u WHERE u.empId = :empId or  u.emailId = :emailId")
	 List<EmpRegisterDTO>  findByEmpIdorEmail(@Param("empId") long empId,@Param("emailId") String emailId);
	 
		@Query(value="Select * "
	 	 		+ " from CSTM_EMPLOYEE_REGISTER    " 
	 	 		+ " where  emp_role is not null "  
	 	 		,nativeQuery = true)
	 	 List<EmpRegisterDTO> findRegisteredEmpList();
}
