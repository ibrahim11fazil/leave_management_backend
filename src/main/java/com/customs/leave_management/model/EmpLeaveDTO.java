package com.customs.leave_management.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;


@Entity 
@Table(name="CSTM_EMPLOYEE_LEAVE")
public class EmpLeaveDTO {
	
	
	@Id
	@Column(name="leave_id") 
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "empleave_seq", initialValue = 5, allocationSize = 1 )
////	@GenericGenerator(name="cmrSeq", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
////    parameters = {
////    @org.hibernate.annotations.Parameter(
////            name = "ISEQ$$_73645", value = "SEQUENCE")}
////)
////@GeneratedValue(generator = "ISEQ$$_73645")
	private long id;
	 
	@Column(name="EMP_ID")
	private Long empId;
	@Column(name="START_DATE")
	private Date fromDate;
	@Column(name="END_DATE")
	private Date toDate; 
	@Column(name="LEAVE_TYPE")
	private String leaveType;  
	@Column(name="From_Applied_Year")
	private String fromAppliedYear;
	@Column(name="CREATED_DATE")
	private Date createdDate;
	@Column(name="CREATED_BY")
	private String createdBy;
	@Column(name="UPDATED_DATE")
	private Date updatedDate;
	@Column(name="UPDATED_BY")
	private String updatedBy;
	
	
	
	

//    @ManyToOne
//    @JoinColumn
//    private EmpLeaveDTO empLeaveDTO;
//	 
//	public EmpLeaveDTO getEmpLeaveDTO() {
//		return empLeaveDTO;
//	}
//	public void setEmpLeaveDTO(EmpLeaveDTO empLeaveDTO) {
//		this.empLeaveDTO = empLeaveDTO;
//	}
	 
	
	
	public long getId() {
		return id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	 
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFromAppliedYear() {
		return fromAppliedYear;
	}

	public void setFromAppliedYear(String fromAppliedYear) {
		this.fromAppliedYear = fromAppliedYear;
	}

	public Date getFromDate() {
		return fromDate;
	}
 
//	public long getId() {
//		return id;
//	}
//
//	public void setId(long id) {
//		this.id = id;
//	}

	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
 
 
	  
	
	
	
}
