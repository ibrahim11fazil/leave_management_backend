package com.customs.leave_management.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;  
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.math3.ml.neuralnet.twod.NeuronSquareMesh2D.HorizontalDirection;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellRange;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.customs.leave_management.controller.ResponseType;
import com.customs.leave_management.model.EmpLeaveDTO;
import com.customs.leave_management.model.EmpRegisterDTO;
import com.customs.leave_management.repository.EmployeeRepository;
import com.customs.leave_management.repository.LeaveRepository;

@Service
public class ReportGenerateService {
	@Autowired
	private LeaveRepository leaveDao;
	
	@Autowired
	private EmployeeRepository empDao;
	
	public ResponseType readUploadedFile(MultipartFile file,String empId,String uploadType) throws IOException {
		
		ResponseType responseType=new ResponseType();
		List<EmpLeaveDTO> emLeaveDAOList=new ArrayList<EmpLeaveDTO>();
		try { 
			
			
	//System.out.println("empId Value ****** "+ file);
	//System.out.println(" uploadType ***" + uploadType);
			
		InputStream inputStream = file.getInputStream();
        XSSFWorkbook myWorkBook = new XSSFWorkbook (inputStream);
       
        // Return first sheet from the XLSX workbook
        XSSFSheet mySheet = myWorkBook.getSheetAt(0);
       
        for(int i=0; i < mySheet.getNumMergedRegions(); ++i)
        {
            // Delete the region
        	mySheet.removeMergedRegion(i);
        }
        
        // Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = mySheet.iterator();
       
        	           
        // Traversing over each row of XLSX file
//        String cell2="";
//        String cell4="";
//        String cell5=""; 
        String convres=""; 
        double rowYearValue=0;
        while (rowIterator.hasNext() ) {
        	 
            Row row = rowIterator.next();
            //System.out.println( " row num ** "+ row.getRowNum());
//            //System.out.println( " row.getCell(0).toString() ** "+ row.getCell(0).toString());
            //System.out.println( " row num ** "+ +row.getRowNum());
            
         
            if(row.getRowNum()==0) { 
              	rowYearValue= Double.parseDouble(row.getCell(0).toString()); 
            	int rowconv= (int) (rowYearValue);
            	//System.out.println( " year 123 ** "+  row.getCell(0));
            	  convres=String.valueOf(rowconv);
            	//System.out.println(" convres "+convres);
	            if(uploadType!=null && !uploadType.trim().equals("")
	    				&& uploadType.trim().equals("newUpload")) {
	            		List<EmpLeaveDTO> empList=leaveDao.findEmpDetailsByYear(convres);
	            		for (EmpLeaveDTO empLeaveDTO : empList) {
	            			leaveDao.delete(empLeaveDTO);
						}
//	    			leaveDao.deleteEmpDetailsByYear(convres) ;
	    			
	            }
	         }
            
            if(row.getRowNum()<2)
            	continue;
            if(row.getCell(0)==null)
            	break;
            // For each row, iterate through each columns
            Iterator<Cell> cellIterator = row.cellIterator();
            int count=0;
            EmpLeaveDTO leaveDTO=new EmpLeaveDTO();
            Date fromDate=null; 
            Date toDate=null;
            while (cellIterator.hasNext()) {
         
                Cell cell = cellIterator.next() ;
                if(cell.getColumnIndex()==7)
                	break;;
                
//                switch (cell.getCellType()) {
//                case STRING :
//                    System.out.print(cell.getStringCellValue() + "\t"); 
//                    break;
//                case NUMERIC:
//                    System.out.print(cell.getNumericCellValue() + "\t");
//                    break;
//                case BOOLEAN:
//                    System.out.print(cell.getBooleanCellValue() + "\t");
//                    break;
//                case FORMULA:
//                    System.out.print(cell.getCellFormula());
//                    break;
//                case BLANK:
//                    System.out.print("");
//                    break;
//                default:
//                    System.out.print("");
//             
//                } 
                	leaveDTO.setFromAppliedYear(convres);
                 if(cell.getColumnIndex()==3) { 
//                	 cell2=cell.getStringCellValue().trim();
//                	 //System.out.println(" cell.getStringCellValue() ** " + cell.getStringCellValue() );
//                	 long employeeId=Long.parseLong(cell.getStringCellValue());
//                	 double employeeIdValue= Double.parseDouble(cell.getStringCellValue()); 
                	 double employeeIdValue=cell.getNumericCellValue();
                	 int empconv=(int)employeeIdValue;
 	            	//System.out.println( " year 123 ** "+  row.getCell(0));
                	 	 
                	if(empId!=null  && uploadType!=null
    	    				&& uploadType.trim().equals("emp") && !empId.trim().equals(String.valueOf(empconv)) 
                			 )
//                			&& (empRole!=null && empRole.trim().equals("") && !empRole.trim().equals("MGR")))
                	   break;
                	
//                	 //System.out.println(" employeeId ** " + employeeId);
//                	 leaveDTO.setEmpId(employeeId);
                	 leaveDTO.setEmpId(Long.parseLong(String.valueOf(empconv)));
                	 count++;
                 }
                 
           
                 if(cell.getColumnIndex()==4) {
//                	 cell4=cell.getStringCellValue().trim();
//                	 cell2=cell.getStringCellValue().trim();
                	 //System.out.println(" DATE FROM ** " + cell.getDateCellValue() );
//                	   fromDate=new Date(cell.getStringCellValue());
//                	 fromDate=cell.getDateCellValue();
                	 double fromDateDouble=cell.getNumericCellValue();
//                	 double convdoub=Double.parseDouble(fromDateString);
                	 fromDate= DateUtil.getJavaDate(fromDateDouble);
                	 leaveDTO.setFromDate((fromDate));
                	 count++;
                 }
                 
                 if(cell.getColumnIndex()==5) { 
//                	 cell5=cell.getStringCellValue().trim();
                	 //System.out.println(" DATE TO ** " + cell.getNumericCellValue() );
//                	   toDate=new Date(cell.getStringCellValue()); 
//                	 fromDate=cell.getDateCellValue();
                	 double toDateDouble=cell.getNumericCellValue();
//                	 double convdoub=Double.parseDouble(toDateString);
                	 toDate= DateUtil.getJavaDate(toDateDouble);
                	 leaveDTO.setToDate((toDate));
                	 count++;
                 }
                
                 if(cell.getColumnIndex()==6) {  
//                	 cell6=cell.getStringCellValue().trim();
                	 //System.out.println(" LEAVE TYPE ** " + cell.getStringCellValue() );
                	 leaveDTO.setLeaveType(cell.getStringCellValue());
                	 count++;
                 }
                 
                 
                 //System.out.println( " count " +count);
             if(count==4) {    
            	 

//               List<EmpRegisterDTO> registeredEmpList=new ArrayList<EmpRegisterDTO>();
               List<EmpRegisterDTO> registeredEmpList= empDao.findRegisteredEmpList();
              if(registeredEmpList!=null && registeredEmpList.size()>0) {
//               for(int i=0;i<leaveList.size();i++) { 
//           		String year = String.valueOf(leaveDTO.getFromDate().getYear()+1900);
            	  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
           	       String   subfrmDate= formatter.format(leaveDTO.getFromDate());
           	       String  subtillDate= formatter.format(leaveDTO.getToDate());
           	       List<EmpLeaveDTO> empLeaveList= leaveDao.findEmployeesAppliedDatesListByYear(subfrmDate,subtillDate);
           	       int maxLeave=0;
           	       maxLeave = (int) ((registeredEmpList.size())*((double)30/(double)100));
           	    int empSize=empLeaveList.size();
           	       if(empSize>=maxLeave) {
           	    	System.out.print(" registeredEmpList.size() ****  FILE UPLOAD"+ registeredEmpList.size());
         	       System.out.print(" empLeaveList.size() ****  FILE UPLOAD"+ empLeaveList.size());
         	      System.out.print(" maxleave ****  FILE UPLOAD"+maxLeave );
//           	    	 	responseType.setCode(200);
//               			responseType.setMessage("Error. Exceeding 30 %, Please generate and refer excel sheet for your reference ");
//               			responseType.setStatus(false); 
//               			return responseType ;
           	    	   break;
           	    	     
           	       }
//               }
              }
            	 
            	 
            	 
                 List<EmpLeaveDTO> leaveDTOList=new ArrayList<EmpLeaveDTO>();
//                 if(toDate.getYear()>fromDate.getYear()) {
//                	 if(toDate.getYear()!=fromDate.getYear()){
                 //System.out.println( "  toDate.getYear() " +  toDate.getYear());
                 //System.out.println( "  fromDate.getYear() " +  fromDate.getYear());
                 SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
                 String frmDate= formatter.format(leaveDTO.getFromDate());
                 String tillDate= formatter.format(leaveDTO.getToDate());
                 
//                 List<EmpLeaveDTO> empList=leaveDao.checkLeaveDateExists(leaveDTO.getEmpId(),frmDate,tillDate);
                 List<EmpLeaveDTO> empList=leaveDao.checkLeaveDateExistsOld(leaveDTO.getEmpId(),convres);
                 if(empList!=null && empList.size()>0) {
                	 //System.out.println( " empList.size() "+ empList.size());
                	   Date frm = formatter.parse(frmDate);
                       Date to = formatter.parse(tillDate);
                       boolean dupFound=false;
                	 for(EmpLeaveDTO emp:empList) {
                		   String empfrmDate= formatter.format(emp.getFromDate());
                           String emptillDate= formatter.format(emp.getToDate());
                           Date empfrm = formatter.parse(empfrmDate);
                           Date empto = formatter.parse(emptillDate);
//                		 if((empfrm.compareTo(frm) < 0 && empto.compareTo(to) > 0 )
//                			|| (empfrm.compareTo(frm) == 0)) {
//                			 //System.out.println(" frm to "+ frm + " ** "+ to);
//                	         //System.out.println(" empfrm empto "+ empfrm + " ** "+ empto);
//                	         dupFound=true;
//                	         break;
//                	      }
//                		 	else if(d1.compareTo(d2) < 0) {
//                	         //System.out.println("Date 1 occurs before Date 2");
//                	      } else if(d1.compareTo(d2) == 0) {
//                	         //System.out.println("Both dates are equal");
//                	      }
                           
                           if ((frm.after(empfrm) && frm.before(empto))
                          		 || (to.after(empfrm) && to.before(empto))
                      		   || (frm.before(empfrm) && to.after(empto))) {
                        	   dupFound=true;
                               //System.out.println("Date1 is after Date2");
                           }

//                           if (to.before(empto)) {
//                        	   dupFound=true;
//                               //System.out.println("Date1 is before Date2");
//                           }

                           if (empfrm.equals(frm) || empto.equals(to) ) {
                        	   dupFound=true;
                               //System.out.println("Date1 is equal Date2");
                           }
                           
                           
                	 }
                	 if (dupFound)
                	 break;
                 }
                 
                 
                 //for splitting dates and inserting seperatly in form of list
                 
                          int yearDifference = toDate.getYear()-fromDate.getYear();
  
                       if(yearDifference>0){  
                    	   //System.out.println( "  yearDifference " +  yearDifference);
                       for(int i=0;i<=yearDifference;i++){ 
                         if(i==0){
                           //System.out.println("entered yearDifference i is o"); 
                           EmpLeaveDTO leaveDTOCopy=new EmpLeaveDTO(); 

                           leaveDTOCopy.setEmpId(leaveDTO.getEmpId());
                           leaveDTOCopy.setFromDate(leaveDTO.getFromDate());
                           
                           Date newTOdate=new Date();
                           newTOdate.setMonth(11);
                           newTOdate.setYear(fromDate.getYear());
                           newTOdate.setHours(11);
                           newTOdate.setMinutes(59);
                           newTOdate.setSeconds(59);
                           newTOdate.setDate(31); 
                           leaveDTOCopy.setToDate(newTOdate);  
                           leaveDTOCopy.setLeaveType(leaveDTO.getLeaveType().trim());
                           leaveDTOCopy.setFromAppliedYear(String.valueOf(fromDate.getYear()+1900));

                           leaveDTOList.add(leaveDTOCopy);
                         }

                         if(i>0 && i<yearDifference){
                           //System.out.println("entered yearDifference i is > 1");
                           EmpLeaveDTO leaveDTOCopy=new EmpLeaveDTO(); 

                           leaveDTOCopy.setEmpId(leaveDTO.getEmpId());
                      
                           
                           Date newTOdate=new Date();
                           newTOdate.setMonth(11);
                           newTOdate.setYear(fromDate.getYear()+i);
                           newTOdate.setHours(0);
                           newTOdate.setMinutes(0);
                           newTOdate.setSeconds(0);
                           newTOdate.setDate(31); 
                           
                           Date newFromDate=new Date();
                           newFromDate.setMonth(0);
                           newFromDate.setHours(0);
                           newFromDate.setMinutes(0);
                           newFromDate.setSeconds(0);
                           newFromDate.setDate(1);  
                           newFromDate.setYear(fromDate.getYear()+i);

                           leaveDTOCopy.setFromDate(newFromDate);
                           leaveDTOCopy.setToDate(newTOdate);  
                           leaveDTOCopy.setLeaveType(leaveDTO.getLeaveType().trim());
                           leaveDTOCopy.setFromAppliedYear(String.valueOf(fromDate.getYear()+1900));
                           
                           leaveDTOList.add(leaveDTOCopy);
                    
                         }

                         if(i==(yearDifference)){
                        	 
                        	   //System.out.println("entered yearDifference i ====  year");
                               EmpLeaveDTO leaveDTOCopy=new EmpLeaveDTO();
                        	 leaveDTOCopy.setEmpId(leaveDTO.getEmpId());
                    
                         
                         Date newFromDate=new Date();

                         newFromDate.setYear(toDate.getYear());
                         newFromDate.setMonth(0);
                         newFromDate.setHours(12);
                         newFromDate.setMinutes(0);
                         newFromDate.setSeconds(0);
                         newFromDate.setDate(1);  

                         leaveDTOCopy.setFromDate(newFromDate);
                         leaveDTOCopy.setToDate(leaveDTO.getToDate());  
                         leaveDTOCopy.setLeaveType(leaveDTO.getLeaveType().trim());
                         leaveDTOCopy.setFromAppliedYear(String.valueOf(fromDate.getYear()+1900));
                         
                         leaveDTOList.add(leaveDTOCopy);} 
                       } 
                       

                       if(leaveDTOList!=null && leaveDTOList.size()>0) { 
                      	 for (EmpLeaveDTO empLeaveDTO : leaveDTOList) { 
                      		 if(empLeaveDTO.getId()==0) { 
                      			empLeaveDTO.setCreatedDate(new Date());
                            	 empLeaveDTO.setCreatedBy(empId);
                     		}else {
                     			empLeaveDTO.setUpdatedBy(leaveDTO.getEmpId().toString());
                     			empLeaveDTO.setUpdatedDate(new Date());
                     		}
                          	 leaveDao.save(empLeaveDTO);
		                      	 }
		                       }
	
	//                     }
	                	 
	//					}
            	 }
                       else {
                    	   if(leaveDTO.getId()==0) { 
                    		   leaveDTO.setCreatedDate(new Date());
                    		   leaveDTO.setCreatedBy(empId);
                    		}else {
                    			leaveDTO.setUpdatedBy(leaveDTO.getEmpId().toString());
                    			leaveDTO.setUpdatedDate(new Date());
                    		}
                    	   String trimmedLeaveType=leaveDTO.getLeaveType().trim();
                    	   leaveDTO.setLeaveType(trimmedLeaveType);
                      	 leaveDao.save(leaveDTO);
                  	 	 break;
                       }
                       
                       
						/*
						 * if(leaveDTOList!=null && leaveDTOList.size()>0) {
						 * 
						 * for (EmpLeaveDTO empLeaveDTO : leaveDTOList) {
						 * 
						 * leaveDao.save(empLeaveDTO); } } else { leaveDao.save(leaveDTO); break; }
						 */
                       
//                 //end of spliiting data in list
                 
//                 if(leaveDTO.getId()==0) { 
//                	 leaveDTO.setCreatedDate(new Date());
//                	 leaveDTO.setCreatedBy(leaveDTO.getCreatedBy());
//         		}else {
//         			leaveDTO.setUpdatedBy(leaveDTO.getEmpId().toString());
//         			leaveDTO.setUpdatedDate(new Date());
//         		}
                 
//                 leaveDao.save(leaveDTO);
//            	 break;
             }
                 
               
                   
                  
//                 if(count==4) {
//                	 leaveDao.save(leaveDTO);
//                	 break;
//                 }
                
            }
            
//            if(!cell2.trim().equals("") && !cell4.trim().equals("") && cell2.equals(cell4)
//            		&& !cell5.trim().equals("") && cell4.equals(cell5)   )
//            	break;
            //System.out.println("");
        }

		}catch (Exception e) {
			e.printStackTrace();
//			return false;
			responseType.setCode(200);
			responseType.setMessage("Error. From-Date and To-Date are Overlapping with previous submitted Leave Request.");
			responseType.setStatus(false); 
			return responseType ;
		}
		
		if(empId!=null  
    			&& uploadType!=null  ) {
//				&& uploadType.trim().equals("emp") ) {
		emLeaveDAOList= leaveDao.findEmpLeaveListById(Long.parseLong(empId));
		}
//		return true;
		responseType.setCode(200);
		responseType.setMessage("Employee leave request submitted successfully.");
		responseType.setStatus(true); 
		responseType.setData(emLeaveDAOList);
		return responseType ;


	}
	
	

	public   ByteArrayInputStream  generateExcelReport(long empId,String year) throws IOException {
		Locale saudi = new Locale("ar","SA");
		String[] months = new DateFormatSymbols(saudi).getMonths(); 
//		String[] COLUMNs =  new String[19];
		String[] COLUMNs =  new String[30];
//	     COLUMNs[0] = {"Sl No","Department", "Employee Name", "Job Number"};
	     COLUMNs[0] ="م";
	    		 COLUMNs[1]="قسم";
	    				 COLUMNs[2] =  "الرقم الوظيفي";
	    	    				 COLUMNs[3] = "اسم الموظف";
//	    	    				 COLUMNs[4] = "From Date";
//	    	    				 COLUMNs[5] = "To Date";
//	    	    				 COLUMNs[6] = "Leave Type";

	      List<Object> LeaveDetails = leaveDao.findEmployeesLeaveList(year);;
	      
//	      Date fromDate=new Date();
//
//	      int yearnum=Integer.valueOf(year);
//	      fromDate.setYear(yearnum-1900);
//	        fromDate.setMonth(0);
//	        fromDate.setHours(12);
//	        fromDate.setMinutes(0);
//	        fromDate.setSeconds(0);
//	        fromDate.setDate(1);
//	      Date toDates=new Date();
//	      toDates.setMonth(11);
//	        toDates.setYear(yearnum-1900);
//	        toDates.setHours(0);
//	        toDates.setMinutes(0);
//	        toDates.setSeconds(0);
//	        toDates.setDate(31);
//	        
//	        
//	        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
//            String frmDate= formatter.format(fromDate);
//            String tillDate= formatter.format(toDates);
//            System.out.println("year"+yearnum);
//            System.out.println("frmDate" + frmDate);
//            System.out.println("tillDate" + tillDate);
//	        List<Object> LeaveDetails = leaveDao.findEmpDetailsByfromandToDate( frmDate, tillDate);
//	    int columnLength=7;
	    int columnLength=4;
	    for(int i=0;i < months.length-1; i++) {
	    			 COLUMNs[i+columnLength]=months[i].toString();
//	    			YearMonth yearMonthObject = YearMonth.of(2020, i++);
//	    			int daysInMonth = yearMonthObject.lengthOfMonth();
	    		 
//	    			columnLength++; 
	    		}
	    		 
	    try(
	        XSSFWorkbook workbook = new XSSFWorkbook();
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ){
	      CreationHelper createHelper = workbook.getCreationHelper();
	   
	      XSSFSheet  sheet = workbook.createSheet("Leave Attendance");
	      sheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setRightToLeft(true);
	   
	      Font headerFont = workbook.createFont();
	      headerFont.setBold(true);
	      headerFont.setColor(IndexedColors.BLUE.getIndex());
	      Calendar cal = Calendar.getInstance();
//	      cal.getInstance(aLocale)
//	      DateFormatSymbols.getInstance(locale).getMonths()[month];
	   
	      CellStyle headerCellStyle = workbook.createCellStyle();
	      headerCellStyle.setFont(headerFont);
	   
	      // Row for Header
	      Row headerRow = sheet.createRow(0); 
	      Row headerRow2 = sheet.createRow(1);
	      
	      // Header
//	      for (int col = 0; col < COLUMNs.length; col++) {
//	        Cell cell = headerRow.createCell(col);
//	        cell.setCellValue(COLUMNs[col]);
//	        cell.setCellStyle(headerCellStyle);
//	      }
	      
	      for (int col = 0; col < columnLength; col++) {
		        Cell cell = headerRow.createCell(col);
		        Cell cell2 = headerRow2.createCell(col);
		        CellStyle colCellStyle = workbook.createCellStyle();
		        colCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		        colCellStyle.setAlignment(HorizontalAlignment.CENTER); 
		        colCellStyle.setBorderTop(BorderStyle.THIN);
		        colCellStyle.setBorderBottom(BorderStyle.THIN);
		        colCellStyle.setBorderLeft(BorderStyle.THIN);
		        colCellStyle.setBorderRight(BorderStyle.THIN);
		        cell.setCellStyle(colCellStyle);
		        cell.setCellValue(year);
		        cell2.setCellStyle(colCellStyle);
		        cell2.setCellValue(COLUMNs[col]);
		        
		       
		  }
	      sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));  
//	      for(int i=0;i<columnLength;i++) {
////	    	   sheet.autoSizeColumn(i,true);  
//	    	  sheet.autoSizeColumn(i);
//	      }
	      
	      
	      int daysInMonth=0;
//	      int k=7;
	      int k=columnLength; 
	      Map<String,Integer> excelMonths= new HashMap<>();
	      for(int i=1 ;i<=12;i++) { 
	       YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(year), i);
	        daysInMonth = yearMonthObject.lengthOfMonth();
//		   String month= COLUMNs[i+6];
		   String monthHeaderColumn= COLUMNs[i+(columnLength-1)];
		      
		   int firstDateCell=k;
			    for (int j = 1; j <= daysInMonth; j++) {
			        Cell cell = headerRow.createCell((k));
			        cell.setCellValue(monthHeaderColumn);
			        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			        headerCellStyle.setBorderTop(BorderStyle.THICK);
			        headerCellStyle.setBorderBottom(BorderStyle.THICK);
			        headerCellStyle.setBorderLeft(BorderStyle.THICK);
			        headerCellStyle.setBorderRight(BorderStyle.THICK);
			        cell.setCellStyle(headerCellStyle);
			        k++;
			    }
			    
			    int lastDateCell=k-1;  
			    
//			    	   int lastColumn=daysInMonth+k;
			    	   sheet.addMergedRegion(new CellRangeAddress(0,0,firstDateCell,lastDateCell));
			    
			 
			    excelMonths.put(yearMonthObject.getMonth().toString().substring(0,3), firstDateCell);
	     }
	      
	      
	      
	      
	        daysInMonth=0;
//	         k=7;
	          k=columnLength;
	          
	     for(int i=1 ;i<=12;i++) { 
	       YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(year), i);
		    daysInMonth = yearMonthObject.lengthOfMonth(); 
			    for (int j = 1; j <= daysInMonth; j++) {
			        Cell cell = headerRow2.createCell((k));
			        cell.setCellValue(j); 
			        
			        CellStyle headerCellStyle2 = workbook.createCellStyle();
			        headerCellStyle2.setBorderTop(BorderStyle.THIN);
			        headerCellStyle2.setBorderBottom(BorderStyle.THIN);
			        headerCellStyle2.setBorderLeft(BorderStyle.THIN);
			        headerCellStyle2.setBorderRight(BorderStyle.THIN);
			        
			        if(j==1) {
			        	  headerCellStyle2.setBorderLeft(BorderStyle.THICK);
			        }
			        
			        cell.setCellStyle(headerCellStyle2);
//			        sheet.autoSizeColumn(k);
			        k++;  
			    }
			    
	     }
	     
	      
	   
	      // CellStyle for Age
	      CellStyle ageCellStyle = workbook.createCellStyle();
	      ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));
	      
	      
	      int rowIdx = 2;
	      long oldId=0l;
	      String oldDept=null;
	  	boolean rowStyle1=true;
    	boolean rowStyle2=false;
     	boolean rowDeptStyle1=true;
    	boolean rowDeptStyle2=false;
    	int slNo=0;
    	int firstRow=2;
    	int firstDeptRow=2;
    	int lastRow=0;
    	double employeeCount=1;
    	 Row row = null;
	      for (Object leaveData : LeaveDetails) { 
	    	
	    	   
	        lastRow++;
	        
//	        Row row = sheet.createRow(rowIdx);
	        
	        CellStyle style = workbook.createCellStyle(); 
	      
	        Object[] result=(Object[]) leaveData;
	        BigDecimal bg1 = (BigDecimal) result[2];
	        long   currentEmpId =bg1.longValue();
	        String currentDept=result[1].toString();
	   
	       
	        if((oldId != currentEmpId || row == null)) {
	        	slNo++;
	          row = sheet.createRow(rowIdx); 
	        }
	        
	        if((oldId != currentEmpId && oldId!=0l && rowStyle1==false )
	        		|| (oldId == currentEmpId && oldId!=0l && rowStyle1==true )) { 
	        	rowStyle2=false;
	        	rowStyle1=true; 
		    }else if((oldId != currentEmpId && oldId!=0l && rowStyle2==false )
	        		|| (oldId == currentEmpId && oldId!=0l && rowStyle2==true )){
		    	rowStyle2=true;
	        	rowStyle1=false; 
		    }
	         
	   
	        if(rowStyle1) {

	        	style.setAlignment(HorizontalAlignment.CENTER);
	            style.setVerticalAlignment(VerticalAlignment.CENTER);
		        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index); 
		        style.setFillPattern(FillPatternType.FINE_DOTS );
		        style.setBorderTop(BorderStyle.THIN);
		        style.setBorderBottom(BorderStyle.THIN);
		        style.setBorderLeft(BorderStyle.THIN);
		        style.setBorderRight(BorderStyle.THIN);
		        System.out.println("excelMonths.keySet()"+excelMonths.keySet());
		   	    for(String month: excelMonths.keySet()) {
		        	int startingMonthCellNo = excelMonths.get(month);
//		        	Cell cell1=row.createCell(startingMonthCellNo); 
//		        	CellStyle style3= workbook.createCellStyle();
//		        	style3.setAlignment(HorizontalAlignment.CENTER);
//		            style3.setVerticalAlignment(VerticalAlignment.CENTER);
//		        	style3.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index); 
//		        	style3.setFillPattern(FillPatternType.SQUARES );
//		        	style3.setBorderLeft(BorderStyle.THICK);
//		        	style3.setBorderBottom(BorderStyle.THIN);
//		        	cell1.setCellStyle(style3);
		        	
		        	Cell cell1=null;
		        	CellStyle style3= workbook.createCellStyle();
		        	if(row.getCell(startingMonthCellNo)==null) {
		        		  cell1=row.createCell(startingMonthCellNo);
		        		  style3.setAlignment(HorizontalAlignment.CENTER);
				            style3.setVerticalAlignment(VerticalAlignment.CENTER);
				        	style3.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index); 
				        	style3.setFillPattern(FillPatternType.FINE_DOTS );
				        	style3.setBorderLeft(BorderStyle.THICK);
				        	style3.setBorderBottom(BorderStyle.THIN);
				        	cell1.setCellStyle(style3);
		        	} else {
		        		
//		        		 row.getCell(startingMonthCellNo).getCellStyle().setBorderLeft(BorderStyle.THICK);;

		        	}
		        	
		        	
		        }
		   	    

		      
		        row.setRowStyle(style);
		        
	        }
	        
	        if(rowStyle2) {
	        	style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index); 
		        style.setFillPattern(FillPatternType.FINE_DOTS );

		        style.setAlignment(HorizontalAlignment.CENTER);
		        style.setVerticalAlignment(VerticalAlignment.CENTER);
		        style.setBorderTop(BorderStyle.THIN);
		        style.setBorderBottom(BorderStyle.THIN);
		        style.setBorderLeft(BorderStyle.THIN);
		        style.setBorderRight(BorderStyle.THIN);
		        
		        for(String month: excelMonths.keySet()) {
		        	int startingMonthCellNo = excelMonths.get(month);
//		        	Cell cell1=row.createCell(startingMonthCellNo); 
//		        	CellStyle style3= workbook.createCellStyle();  
//		        	style3.setAlignment(HorizontalAlignment.CENTER);
//		        	style3.setVerticalAlignment(VerticalAlignment.CENTER);
//		        	style3.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index); 
//			        style3.setFillPattern(FillPatternType.SQUARES );
//		        	style3.setBorderLeft(BorderStyle.THICK);
////		        	style3.setBorderRight(BorderStyle.THICK);
//		        	style3.setBorderBottom(BorderStyle.THIN);
//		        	cell1.setCellStyle(style3); 
		        	
		        	Cell cell1=null;
		        	CellStyle style3= workbook.createCellStyle();
		        	if(row.getCell(startingMonthCellNo)==null) {
		        		  cell1=row.createCell(startingMonthCellNo);
		        		  style3.setAlignment(HorizontalAlignment.CENTER);
				            style3.setVerticalAlignment(VerticalAlignment.CENTER);
				            style3.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);  
				        	style3.setFillPattern(FillPatternType.FINE_DOTS );
				        	style3.setBorderLeft(BorderStyle.THICK);
				        	style3.setBorderBottom(BorderStyle.THIN);
				        	cell1.setCellStyle(style3);
		        	} else {
		        		
//		        		 row.getCell(startingMonthCellNo).getCellStyle().setBorderLeft(BorderStyle.THICK);;

		        	}
		        	
		        
		        	
		        }
		        
 
		        row.setRowStyle(style) ;
	        }
	         
	        
//	   	    for(int i=0;i<6;i++) { 
//        	Cell cell1=row.createCell(i); 
//        	CellStyle style3= workbook.createCellStyle();
//        	style3.setAlignment(HorizontalAlignment.CENTER);
//            style3.setVerticalAlignment(VerticalAlignment.CENTER);
//        	style3.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index); 
//        	style3.setFillPattern(FillPatternType.SQUARES );
//        	style3.setBorderRight(BorderStyle.THICK);
//        	style3.setBorderBottom(BorderStyle.THIN);
//        	cell1.setCellStyle(style3); 
//        }
	        
	        if(oldId!=currentEmpId && oldId!=0l)
	        	employeeCount++;
	        
	        if(oldId==currentEmpId && oldId!=0l) {
	        	 //System.out.println("LeaveDetails.lastIndexOf(leaveData) ***  " + LeaveDetails.lastIndexOf(leaveData));
				  	//System.out.println("LeaveDetails.size() ***  " +LeaveDetails.size());
	   			  if( LeaveDetails.size()-1==LeaveDetails.lastIndexOf(leaveData)) {
	   				int minMergeValue=rowIdx-1-firstRow;
	   				if(minMergeValue>0 ) {
	   				  	//System.out.println("firstRow " +firstRow);
	   				  	//System.out.println("rowIdx " +rowIdx);
	   		        	sheet.addMergedRegion(new CellRangeAddress(firstRow,rowIdx-1,2,2));
	   		        	sheet.addMergedRegion(new CellRangeAddress(firstRow,rowIdx-1,3,3));
//	   		        	sheet.addMergedRegion(new CellRangeAddress(firstRow,rowIdx,0,0));
	   				}
	   			  }
	   		  }
	           	
			
			  if(oldId!=currentEmpId && oldId!=0l  ) { 
				 int minMergeValue=(rowIdx-1)-firstRow;
					//System.out.println("minMergeValue " +minMergeValue);
				  if( minMergeValue>0) {
					  	//System.out.println("firstRow " +firstRow);
					  	//System.out.println("rowIdx " +rowIdx);
			        	sheet.addMergedRegion(new CellRangeAddress(firstRow,rowIdx-1,2,2));
			        	sheet.addMergedRegion(new CellRangeAddress(firstRow,rowIdx-1,3,3));
//			        	sheet.addMergedRegion(new CellRangeAddress(firstRow,rowIdx-1,0,0));
				  }
				  firstRow=rowIdx;
		      }
			   
				  
				  
				  	 if( oldDept!=null && oldDept.trim().equals(currentDept.trim()) ) {
			        	 //System.out.println("LeaveDetails.lastIndexOf(leaveData) ***  " + LeaveDetails.lastIndexOf(leaveData));
						  	//System.out.println("LeaveDetails.size() ***  " +LeaveDetails.size());
			   			  if( LeaveDetails.size()-1==LeaveDetails.lastIndexOf(leaveData)) {
			   				int minMergeValue=(rowIdx-1)-firstDeptRow;
			   				if(minMergeValue>0 ) { 
			   				  	//System.out.println("firstRow " +firstDeptRow);
			   				  	//System.out.println("rowIdx " +rowIdx);  
			   		        	sheet.addMergedRegion(new CellRangeAddress(firstDeptRow,rowIdx,1,1)); 
			   				}
			   			 }
			   		  } 
					
					  if( oldDept!=null && !(oldDept.trim().equals(currentDept.trim()))){ 
						 int minMergeValue=(rowIdx-1)-firstDeptRow;
							//System.out.println("minMergeValue " +minMergeValue);
						  if( minMergeValue>0) {
							  	//System.out.println("firstRow " +firstDeptRow);
							  	//System.out.println("rowIdx " +rowIdx);
					        	sheet.addMergedRegion(new CellRangeAddress(firstDeptRow,rowIdx-1,1,1));
						  }
						  firstDeptRow=rowIdx;
				      }
			  
			 
	        
	        Cell cell=row.createCell(0);
        	style.setAlignment(HorizontalAlignment.CENTER);
        	cell.setCellStyle(style); 
        	cell.setCellValue(slNo);
//        	sheet.autoSizeColumn(0,true);
        	
        	     cell=row.createCell(1);

        	     
        	     if( oldDept!=null && (!(oldDept.trim().equals(currentDept.trim())) && rowDeptStyle1==false )
     	        		|| (oldDept!=null && (oldDept.trim().equals(currentDept.trim())) && rowDeptStyle1==true )) { 
     	        	rowDeptStyle2=false;
     	        	rowDeptStyle1=true; 
     		    }else if(oldDept!=null && (!(oldDept.trim().equals(currentDept.trim())) && rowDeptStyle2==false )
     	        		|| (oldDept!=null && (oldDept.trim().equals(currentDept.trim())) && rowDeptStyle2==true )){
     		    	rowDeptStyle2=true;
     	        	rowDeptStyle1=false; 
     		    }
        	     
        	     
        	     if(rowDeptStyle1) {
            CellStyle styleDept= workbook.createCellStyle(); 
            styleDept.setAlignment(HorizontalAlignment.CENTER);
            styleDept.setVerticalAlignment(VerticalAlignment.CENTER);
            styleDept.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.index); 
            styleDept.setFillPattern(FillPatternType.FINE_DOTS );
//            styleDept.setBorderLeft(BorderStyle.THICK);
            styleDept.setBorderBottom(BorderStyle.THIN);
           	cell.setCellStyle(styleDept); 
           	cell.setCellValue(result[1].toString());
//           	sheet.autoSizeColumn(1,true);
        	     }
        	     
        	     if(rowDeptStyle2) {
        	            CellStyle styleDept= workbook.createCellStyle(); 
        	            styleDept.setAlignment(HorizontalAlignment.CENTER);
        	            styleDept.setVerticalAlignment(VerticalAlignment.CENTER); 
        	            styleDept.setFillForegroundColor(IndexedColors.GOLD.index); 
        	            styleDept.setFillPattern(FillPatternType.FINE_DOTS );
//        	            styleDept.setBorderLeft(BorderStyle.THICK);
        	            styleDept.setBorderBottom(BorderStyle.THIN);
        	           	cell.setCellStyle(styleDept); 
        	           	cell.setCellValue(result[1].toString());
//        	           	sheet.autoSizeColumn(1,true);
        	       }
	       
	        for(int i=2;i<4;i++) {
	        	  cell=row.createCell(i);
	        	style.setAlignment(HorizontalAlignment.CENTER);	
	        	cell.setCellStyle(style); 
	        	if(i==0)
	        		cell.setCellValue(slNo);
	        	else
	        		cell.setCellValue(result[i].toString());
//	        	sheet.autoSizeColumn(i,true);
	        }
	        
	        
	        
//	          cell=row.createCell(4);
//        	style.setAlignment(HorizontalAlignment.CENTER);
//        	cell.setCellStyle(style);
	        String startDate="";
	        if(result[4]!=null)
        	  startDate =  returnDate((Date)  result[4]);
//        	cell.setCellValue(startDate);
//        	sheet.autoSizeColumn(4,true);
        	
//        	cell=row.createCell(5);
//        	style.setAlignment(HorizontalAlignment.CENTER);
//        	cell.setCellStyle(style);
	        String toDate="";
	        if(result[5]!=null)
  	          toDate =  returnDate((Date)  result[5]);
//        	cell.setCellValue(toDate);
//        	sheet.autoSizeColumn(5,true);
        	
//        	cell=row.createCell(6);
//        	style.setAlignment(HorizontalAlignment.CENTER);
//        	cell.setCellStyle(style); 
//        	cell.setCellValue(result[6].toString());
//        	sheet.autoSizeColumn(6,true);
	      
        
//      	  if(oldId!=currentEmpId  && oldId!=0l)
//			  firstRow=rowIdx;
        	
	        	String startDay="";
	        	String startMonth="";
	        	String endDay="";
	        	String endMonth="";
        	 	if(!startDate.trim().equals("") || !toDate.trim().equals("")) {
				     startDay=startDate.substring(0, 2);
				     startMonth=startDate.substring(3, 6); 
				     endDay=toDate.substring(0, 2);
				     endMonth=toDate.substring(3, 6); 
				   
        	 	
				   int startingExcelValue=0;
					 int endExcelValue=0;
				  for (String month: excelMonths.keySet() ) {
					  if(startMonth.trim().equalsIgnoreCase(month)) {
						  startingExcelValue= excelMonths.get(month);
					  }
					  if(endMonth.trim().equalsIgnoreCase(month)) {
						  endExcelValue= excelMonths.get(month);
					  }
					  
				  }
				  startingExcelValue= (startingExcelValue+Integer.parseInt(startDay))-1;	
				  endExcelValue= endExcelValue+Integer.parseInt(endDay);
				  
				  
				  //System.out.println("startMonth**" + startMonth);
				  //System.out.println("end **" + endMonth);
				  //System.out.println("startingExcelValue**" + startingExcelValue);
				  //System.out.println("endExcelValue**" + endExcelValue);
				  XSSFCellStyle style2 = workbook.createCellStyle(); 
				  for(int i=startingExcelValue;i<endExcelValue;i++) {
//					  if(startingExcelValue <= endExcelValue) {
					  if(i <= endExcelValue) {
//						  CellStyle style2 = workbook.createCellStyle(); 
						   
							  switch (result[6].toString().trim()) {

//							  case "Annual": 
							  case "إجازة دوريه":
							   style2.setFillForegroundColor(IndexedColors.BLUE.index); 
							  break;
							  
//							  case "Overtime":
							  case "إجازة العمل الإضافي":
//							     byte[] blueRGB = new byte[]{(byte)201, (byte)129, (byte)177};
								  byte[] blueRGB = new byte[]{(byte)128, (byte)128, (byte)128};
//								  style2.setFillForegroundColor(IndexedColors.RED1.index);
								  style2.setFillForegroundColor(new XSSFColor(blueRGB, null));
							  break; 
							  
//							  case "Course":
							  case "الدورات":
								  style2.setFillForegroundColor(IndexedColors.GREEN.index);
								 blueRGB = new byte[]{(byte)111, (byte)247, (byte)9};
								  style2.setFillForegroundColor(new XSSFColor(blueRGB, null));
							  break;
							  
//							  case "Motherhood":
							  case "إجازة أمومة":
								  style2.setFillForegroundColor(IndexedColors.PINK.index); 
							  break;
							  
//							  case "Accompanying Sick Leave":
							  case "إجازة مرافق مريض": 
								  style2.setFillForegroundColor(IndexedColors.RED1.index);
							  break;
							  
							  }
							  //statement sequence
//						  style2.setFillForegroundColor(IndexedColors.ORANGE.index); 
						  style2.setBorderTop(BorderStyle.THIN);
						  style2.setBorderBottom(BorderStyle.THIN);
						  if(i==startingExcelValue)
							  style2.setBorderLeft(BorderStyle.THICK);
						  else
							  style2.setBorderLeft(BorderStyle.THIN);
						  style2.setBorderRight(BorderStyle.THIN);
						  style2.setFillPattern(FillPatternType.FINE_DOTS );
//			        	  cell=row.createCell(startingExcelValue);

			        		  cell=row.createCell(i);
			        	  
			        	cell.setCellStyle(style2); 
			        	cell.setCellValue(1);
//			        	startingExcelValue++;
					  }
			       }
        	 	}
				  if(oldId != currentEmpId) 
				  rowIdx++;	  
	       oldId=currentEmpId;
	       oldDept=currentDept;
	       
	      }
	      for(int i=0;i<columnLength;i++)
	    	  sheet.autoSizeColumn(i,true);
//	    	  sheet.autoSizeColumn(i,true);
	      
//	      sheet.autoSizeColumn(0,true);
//	      Row totalRow = sheet.createRow(LeaveDetails.size()+2);
	      Row totalRow = sheet.createRow(rowIdx+1);
	      k=columnLength;  
	      double sumOfEachCol=0;
//	      for(int i=0 ;i<=6;i++) {
	      for(int i=0 ;i<=columnLength-1;i++) {
	    	 Cell cell=totalRow.createCell(i);
	    	 CellStyle style2 = workbook.createCellStyle();
	    	 style2.setAlignment(HorizontalAlignment.CENTER);
	    	   style2.setVerticalAlignment(VerticalAlignment.CENTER);
	    	 style2.setBorderTop(BorderStyle.THIN);
			  style2.setBorderBottom(BorderStyle.THIN);
			  style2.setBorderLeft(BorderStyle.THIN);
			  style2.setBorderRight(BorderStyle.THIN); 
	    	 cell.setCellValue("المجموع");
	    	 cell.setCellStyle(style2);
	    	 
	      }
//	      sheet.addMergedRegion(new CellRangeAddress(totalRow.getRowNum(),totalRow.getRowNum(),0,6));
	      sheet.addMergedRegion(new CellRangeAddress(totalRow.getRowNum(),totalRow.getRowNum(),0,columnLength-1));
	       //System.out.println(" ** employeeCount"+employeeCount);
	      
	       List<EmpRegisterDTO> registeredEmpList= empDao.findRegisteredEmpList();
	       employeeCount=registeredEmpList.size();
//	       double maxLeave=0.0;
//	       maxLeave = (employeeCount)*((double)30/(double)100);
	       int maxLeave=0;
	       maxLeave = (int) ((employeeCount)*((double)30/(double)100));
	       
	     //System.out.println("**maxLeave "+maxLeave);
		     for(int i=1 ;i<=12;i++) { 
		       YearMonth yearMonthObject = YearMonth.of(Integer.parseInt(year), i);
			    daysInMonth = yearMonthObject.lengthOfMonth(); 
				    for (int j = 1; j <= daysInMonth; j++) {
				        Cell  cell = totalRow.createCell((k));
				        sumOfEachCol=0.0;
				        for(int m=2;m<(rowIdx);m++) {
				         
//				         //System.out.println(" ** sheet.getRow(m).getCell(k);"+sheet.getRow(m).getCell(25));
				         double sheets=0.0;
				         
				         if(sheet.getRow(m).getCell(k)!=null)
				           sheets=sheet.getRow(m).getCell(k).getNumericCellValue();
				         sumOfEachCol=sumOfEachCol+sheets;
				          
				         
				        }
				        
				        CellStyle headerCellStyle2 = workbook.createCellStyle();
				        headerCellStyle2.setBorderTop(BorderStyle.THIN);
				        headerCellStyle2.setBorderBottom(BorderStyle.THIN);
				        headerCellStyle2.setBorderLeft(BorderStyle.THIN);
				        headerCellStyle2.setBorderRight(BorderStyle.THIN);
				        int checkMax=(int) (sumOfEachCol);
				        if(checkMax>maxLeave) {
				        	System.out.print(" registeredEmpList.size() ****  GENERATE REPORT "+  employeeCount);
			        	       System.out.print(" empLeaveList.size() ****  GENRATE REPORT "+ sumOfEachCol);
			        	       System.out.print(" maxleave ****  GENERATE REPORT "+maxLeave );
//				        	headerCellStyle2.setfi
				        	headerCellStyle2.setFillForegroundColor(IndexedColors.RED1.index);
				        	headerCellStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND );
				        }
				        
				        if(j==1) {
				        	  headerCellStyle2.setBorderLeft(BorderStyle.THICK);
				        }
				        cell.setCellValue(sumOfEachCol);
				        cell.setCellStyle(headerCellStyle2);
				        sheet.autoSizeColumn(k);
				        k++;  
				    }
				  
//				    Row indicatorRow = sheet.createRow(LeaveDetails.size()+4);
				    
				    
		     }
		     
		     
		     for( int i=0 ;i<5;i++) {
		    	 Row indicatorRow = sheet.createRow(rowIdx+4+i);
		    	 Cell cell=indicatorRow.createCell(2);
		    	 Cell cell2=indicatorRow.createCell(3);
		    	 XSSFCellStyle  style2 = workbook.createCellStyle();
		    	  
		    	 style2.setAlignment(HorizontalAlignment.CENTER);
		    	 style2.setVerticalAlignment(VerticalAlignment.CENTER);
		    	 style2.setBorderTop(BorderStyle.THIN);
				  style2.setBorderBottom(BorderStyle.THIN);
				  style2.setBorderLeft(BorderStyle.THIN);
				  style2.setBorderRight(BorderStyle.THIN); 
				  if(i==0) {
					  cell.setCellValue("Annual Leave");
//					  byte[] blueRGB = new byte[]{(byte)142, (byte)169, (byte)219};
					  style2.setFillForegroundColor(IndexedColors.BLUE.index);
//					  style2.setFillForegroundColor(new XSSFColor(blueRGB, null));
					  cell2.setCellValue("إجازة دوريه");
				  }else  
				  if(i==1) {
					  cell.setCellValue("Overtime Leave");
//					  byte[] blueRGB = new byte[]{(byte)201, (byte)129, (byte)177};
//					  byte[] blueRGB = new byte[]{(byte)117, (byte)113, (byte)113};
//					  style2.setFillForegroundColor(IndexedColors.RED1.index);
					  byte[] blueRGB = new byte[]{(byte)128, (byte)128, (byte)128};
					  style2.setFillForegroundColor(new XSSFColor(blueRGB, null));
					  cell2.setCellValue("إجازة العمل الإضافي");
				  }else  
				  if(i==2) {
					  cell.setCellValue("Course Leave");
//					  style2.setFillForegroundColor(IndexedColors.GREEN.index);
					  byte[] blueRGB = new byte[]{(byte)111, (byte)247, (byte)9};
					  style2.setFillForegroundColor(new XSSFColor(blueRGB, null));
					  cell2.setCellValue("الدورات");
				  }else
				  if(i==3) {
					  cell.setCellValue("Motherhood Leave");
					  style2.setFillForegroundColor(IndexedColors.PINK.index);
					  cell2.setCellValue("إجازة أمومة");
				  }
				  else
					  if(i==4) {
						  cell.setCellValue("Sick Leave");
						  style2.setFillForegroundColor(IndexedColors.RED1.index);
						  cell2.setCellValue("إجازة مرافق مريض");
					  }
				  style2.setFillPattern(FillPatternType.FINE_DOTS );
				  cell2.setCellStyle(style2);
		    	 
		      }
//	      File outputFile = new File( path+filename+".xlsx");
//	      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//			OutputStream fileOutputStream = new FileOutputStream(outputFile);
//			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//			byteArrayOutputStream.writeTo(fileOutputStream);
	      workbook.write(out);
	      return new ByteArrayInputStream(out.toByteArray());
	      
	    }
//	    return "Success";
	  }
	
	  
	
	public String returnDate(Date convertdate) {
			String pattern = "dd-MMM-yyyy";
		  	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		  	String formattedDate = simpleDateFormat.format(convertdate);
		  	//System.out.println("*** formattedDate**"+formattedDate);
		  	return formattedDate;
	}
}






//Working Different report format
//
//public   ByteArrayInputStream  generateExcelReport(long empId,String year) throws IOException {
//	Locale saudi = new Locale("ar","SA");
//	String[] months = new DateFormatSymbols(saudi).getMonths(); 
//	String[] COLUMNs =  new String[19];
////     COLUMNs[0] = {"Sl No","Department", "Employee Name", "Job Number"};
//     COLUMNs[0] ="Sl No";
//    		 COLUMNs[1]="Department";
//    				 COLUMNs[2] =  "Employee Id";
//    	    				 COLUMNs[3] = "Employee Name";
//    	    				 COLUMNs[4] = "From Date";
//    	    				 COLUMNs[5] = "To Date";
//    	    				 COLUMNs[6] = "Leave Type";
//
//      List<Object> LeaveDetails = leaveDao.findEmployeesLeaveList(year);; 
//    int columnLength=7;
//    for(int i=0;i < months.length-1; i++) {
//    			 COLUMNs[columnLength]=months[i].toString();
////    			YearMonth yearMonthObject = YearMonth.of(2020, i++);
////    			int daysInMonth = yearMonthObject.lengthOfMonth();
//    		 
//    			columnLength++; 
//    		}
//    		 
//    try(
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//    ){
//      CreationHelper createHelper = workbook.getCreationHelper();
//   
//      XSSFSheet  sheet = workbook.createSheet("Leave Attendance");
//      sheet.getCTWorksheet().getSheetViews().getSheetViewArray(0).setRightToLeft(true);
//   
//      Font headerFont = workbook.createFont();
//      headerFont.setBold(true);
//      headerFont.setColor(IndexedColors.BLUE.getIndex());
//      Calendar cal = Calendar.getInstance();
////      cal.getInstance(aLocale)
////      DateFormatSymbols.getInstance(locale).getMonths()[month];
//   
//      CellStyle headerCellStyle = workbook.createCellStyle();
//      headerCellStyle.setFont(headerFont);
//   
//      // Row for Header
//      Row headerRow = sheet.createRow(0); 
//      Row headerRow2 = sheet.createRow(1);
//      
//      // Header
////      for (int col = 0; col < COLUMNs.length; col++) {
////        Cell cell = headerRow.createCell(col);
////        cell.setCellValue(COLUMNs[col]);
////        cell.setCellStyle(headerCellStyle);
////      }
//      
//      for (int col = 0; col < 7; col++) {
//	        Cell cell = headerRow.createCell(col);
//	        Cell cell2 = headerRow2.createCell(col);
//	        CellStyle colCellStyle = workbook.createCellStyle();
//	        colCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//	        colCellStyle.setAlignment(HorizontalAlignment.CENTER); 
//	        colCellStyle.setBorderTop(BorderStyle.THIN);
//	        colCellStyle.setBorderBottom(BorderStyle.THIN);
//	        colCellStyle.setBorderLeft(BorderStyle.THIN);
//	        colCellStyle.setBorderRight(BorderStyle.THIN);
//	        cell.setCellStyle(colCellStyle);
//	        cell.setCellValue(COLUMNs[col]);
//	        cell2.setCellStyle(colCellStyle);
//	        cell2.setCellValue(COLUMNs[col]);
//	        sheet.addMergedRegion(new CellRangeAddress(0,1,col,col));  
//	        sheet.autoSizeColumn(col,true);
//	  }
////      for(int i=0;i<6;i++) {
////    	  sheet.addMergedRegion(new CellRangeAddress(0,1,i,i));  
////      }
////      
//      
//      int daysInMonth=0;
//      int k=7;
//      Map<String,Integer> excelMonths= new HashMap<>();
//      for(int i=1 ;i<=12;i++) { 
//       YearMonth yearMonthObject = YearMonth.of(2020, i);
//        daysInMonth = yearMonthObject.lengthOfMonth();
//	   String month= COLUMNs[i+6];
//	      
//	   int firstDateCell=k;
//		    for (int j = 1; j <= daysInMonth; j++) {
//		        Cell cell = headerRow.createCell((k));
//		        cell.setCellValue(month);
//		        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
//		        headerCellStyle.setBorderTop(BorderStyle.THICK);
//		        headerCellStyle.setBorderBottom(BorderStyle.THICK);
//		        headerCellStyle.setBorderLeft(BorderStyle.THICK);
//		        headerCellStyle.setBorderRight(BorderStyle.THICK);
//		        cell.setCellStyle(headerCellStyle);
//		        k++;
//		    }
//		    int lastDateCell=k-1;  
////		    	   int lastColumn=daysInMonth+k;
//		    	   sheet.addMergedRegion(new CellRangeAddress(0,0,firstDateCell,lastDateCell));
//		    
//		 
//		    excelMonths.put(yearMonthObject.getMonth().toString().substring(0,3), firstDateCell);
//     }
//      
//      
//      
//      
//        daysInMonth=0;
//         k=7;
//     for(int i=1 ;i<=12;i++) { 
//       YearMonth yearMonthObject = YearMonth.of(2020, i);
//	    daysInMonth = yearMonthObject.lengthOfMonth(); 
//		    for (int j = 1; j <= daysInMonth; j++) {
//		        Cell cell = headerRow2.createCell((k));
//		        cell.setCellValue(j); 
//		        
//		        CellStyle headerCellStyle2 = workbook.createCellStyle();
//		        headerCellStyle2.setBorderTop(BorderStyle.THIN);
//		        headerCellStyle2.setBorderBottom(BorderStyle.THIN);
//		        headerCellStyle2.setBorderLeft(BorderStyle.THIN);
//		        headerCellStyle2.setBorderRight(BorderStyle.THIN);
//		        
//		        if(j==1) {
//		        	  headerCellStyle2.setBorderLeft(BorderStyle.THICK);
//		        }
//		        
//		        cell.setCellStyle(headerCellStyle2);
//		        sheet.autoSizeColumn(k);
//		        k++;  
//		    }
//		    
//     }
//     
//      
//   
//      // CellStyle for Age
//      CellStyle ageCellStyle = workbook.createCellStyle();
//      ageCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#"));
//      
//      
//      int rowIdx = 2;
//      long oldId=0l;
//      String oldDept=null;
//  	boolean rowStyle1=true;
//	boolean rowStyle2=false;
// 	boolean rowDeptStyle1=true;
//	boolean rowDeptStyle2=false;
//	int slNo=0;
//	int firstRow=2;
//	int firstDeptRow=2;
//	int lastRow=0;
//	double employeeCount=1;
//      for (Object leaveData : LeaveDetails) { 
//    	slNo++;
//        lastRow++;
//        
//        Row row = sheet.createRow(rowIdx);
//        
//        CellStyle style = workbook.createCellStyle(); 
//      
//        Object[] result=(Object[]) leaveData;
//        BigDecimal bg1 = (BigDecimal) result[2];
//        long   currentEmpId =bg1.longValue();
//        String currentDept=result[1].toString();
//   
//        
//        if((oldId != currentEmpId && oldId!=0l && rowStyle1==false )
//        		|| (oldId == currentEmpId && oldId!=0l && rowStyle1==true )) { 
//        	rowStyle2=false;
//        	rowStyle1=true; 
//	    }else if((oldId != currentEmpId && oldId!=0l && rowStyle2==false )
//        		|| (oldId == currentEmpId && oldId!=0l && rowStyle2==true )){
//	    	rowStyle2=true;
//        	rowStyle1=false; 
//	    }
//         
//   
//        if(rowStyle1) {
//
//        	style.setAlignment(HorizontalAlignment.CENTER);
//            style.setVerticalAlignment(VerticalAlignment.CENTER);
//	        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index); 
//	        style.setFillPattern(FillPatternType.SQUARES );
//	        style.setBorderTop(BorderStyle.THIN);
//	        style.setBorderBottom(BorderStyle.THIN);
//	        style.setBorderLeft(BorderStyle.THIN);
//	        style.setBorderRight(BorderStyle.THIN);
//	        
//	   	    for(String month: excelMonths.keySet()) {
//	   	    	
//	        	int startingMonthCellNo = excelMonths.get(month);
//	        	Cell cell1=row.createCell(startingMonthCellNo); 
//	        	CellStyle style3= workbook.createCellStyle();
//	        	style3.setAlignment(HorizontalAlignment.CENTER);
//	            style3.setVerticalAlignment(VerticalAlignment.CENTER);
//	        	style3.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index); 
//	        	style3.setFillPattern(FillPatternType.SQUARES );
//	        	style3.setBorderLeft(BorderStyle.THICK);
//	        	style3.setBorderBottom(BorderStyle.THIN);
//	        	cell1.setCellStyle(style3);
//	        	
//	        }
//	   	    
//
//	      
//	        row.setRowStyle(style);
//	        
//        }
//        
//        if(rowStyle2) {
//        	style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index); 
//	        style.setFillPattern(FillPatternType.SQUARES );
//
//	        style.setAlignment(HorizontalAlignment.CENTER);
//	        style.setVerticalAlignment(VerticalAlignment.CENTER);
//	        style.setBorderTop(BorderStyle.THIN);
//	        style.setBorderBottom(BorderStyle.THIN);
//	        style.setBorderLeft(BorderStyle.THIN);
//	        style.setBorderRight(BorderStyle.THIN);
//	        
//	        for(String month: excelMonths.keySet()) {
//	        	int startingMonthCellNo = excelMonths.get(month);
//	        	Cell cell1=row.createCell(startingMonthCellNo); 
//	        	CellStyle style3= workbook.createCellStyle();  
//	        	style3.setAlignment(HorizontalAlignment.CENTER);
//	        	style3.setVerticalAlignment(VerticalAlignment.CENTER);
//	        	style3.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index); 
//		        style3.setFillPattern(FillPatternType.SQUARES );
//	        	style3.setBorderLeft(BorderStyle.THICK);
////	        	style3.setBorderRight(BorderStyle.THICK);
//	        	style3.setBorderBottom(BorderStyle.THIN);
//	        	cell1.setCellStyle(style3); 
//	        	
//	        }
//	        
//
//	        row.setRowStyle(style) ;
//        }
//         
//        
////   	    for(int i=0;i<6;i++) { 
////    	Cell cell1=row.createCell(i); 
////    	CellStyle style3= workbook.createCellStyle();
////    	style3.setAlignment(HorizontalAlignment.CENTER);
////        style3.setVerticalAlignment(VerticalAlignment.CENTER);
////    	style3.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index); 
////    	style3.setFillPattern(FillPatternType.SQUARES );
////    	style3.setBorderRight(BorderStyle.THICK);
////    	style3.setBorderBottom(BorderStyle.THIN);
////    	cell1.setCellStyle(style3); 
////    }
//        
//        if(oldId!=currentEmpId && oldId!=0l)
//        	employeeCount++;
//        
//        if(oldId==currentEmpId && oldId!=0l) {
//        	 //System.out.println("LeaveDetails.lastIndexOf(leaveData) ***  " + LeaveDetails.lastIndexOf(leaveData));
//			  	//System.out.println("LeaveDetails.size() ***  " +LeaveDetails.size());
//   			  if( LeaveDetails.size()-1==LeaveDetails.lastIndexOf(leaveData)) {
//   				int minMergeValue=rowIdx-firstRow;
//   				if(minMergeValue>0 ) {
//   				  	//System.out.println("firstRow " +firstRow);
//   				  	//System.out.println("rowIdx " +rowIdx);
//   		        	sheet.addMergedRegion(new CellRangeAddress(firstRow,rowIdx,2,2));
//   		        	sheet.addMergedRegion(new CellRangeAddress(firstRow,rowIdx,3,3));
////   		        	sheet.addMergedRegion(new CellRangeAddress(firstRow,rowIdx,0,0));
//   				}
//   			  }
//   		  }
//           	
//		
//		  if(oldId!=currentEmpId && oldId!=0l  ) { 
//			 int minMergeValue=(rowIdx-1)-firstRow;
//				//System.out.println("minMergeValue " +minMergeValue);
//			  if( minMergeValue>0) {
//				  	//System.out.println("firstRow " +firstRow);
//				  	//System.out.println("rowIdx " +rowIdx);
//		        	sheet.addMergedRegion(new CellRangeAddress(firstRow,rowIdx-1,2,2));
//		        	sheet.addMergedRegion(new CellRangeAddress(firstRow,rowIdx-1,3,3));
////		        	sheet.addMergedRegion(new CellRangeAddress(firstRow,rowIdx-1,0,0));
//			  }
//			  firstRow=rowIdx;
//	      }
//		   
//			  
//			  
//			  	 if( oldDept!=null && oldDept.trim().equals(currentDept.trim()) ) {
//		        	 //System.out.println("LeaveDetails.lastIndexOf(leaveData) ***  " + LeaveDetails.lastIndexOf(leaveData));
//					  	//System.out.println("LeaveDetails.size() ***  " +LeaveDetails.size());
//		   			  if( LeaveDetails.size()-1==LeaveDetails.lastIndexOf(leaveData)) {
//		   				int minMergeValue=rowIdx-firstDeptRow;
//		   				if(minMergeValue>0 ) { 
//		   				  	//System.out.println("firstRow " +firstDeptRow);
//		   				  	//System.out.println("rowIdx " +rowIdx);  
//		   		        	sheet.addMergedRegion(new CellRangeAddress(firstDeptRow,rowIdx,1,1)); 
//		   				}
//		   			 }
//		   		  } 
//				
//				  if( oldDept!=null && !(oldDept.trim().equals(currentDept.trim()))){ 
//					 int minMergeValue=(rowIdx-1)-firstDeptRow;
//						//System.out.println("minMergeValue " +minMergeValue);
//					  if( minMergeValue>0) {
//						  	//System.out.println("firstRow " +firstDeptRow);
//						  	//System.out.println("rowIdx " +rowIdx);
//				        	sheet.addMergedRegion(new CellRangeAddress(firstDeptRow,rowIdx-1,1,1));
//					  }
//					  firstDeptRow=rowIdx;
//			      }
//		  
//		 
//        
//        Cell cell=row.createCell(0);
//    	style.setAlignment(HorizontalAlignment.CENTER);
//    	cell.setCellStyle(style); 
//    	cell.setCellValue(slNo);
////    	sheet.autoSizeColumn(0,true);
//    	
//    	     cell=row.createCell(1);
//
//    	     
//    	     if( oldDept!=null && (!(oldDept.trim().equals(currentDept.trim())) && rowDeptStyle1==false )
// 	        		|| (oldDept!=null && (oldDept.trim().equals(currentDept.trim())) && rowDeptStyle1==true )) { 
// 	        	rowDeptStyle2=false;
// 	        	rowDeptStyle1=true; 
// 		    }else if(oldDept!=null && (!(oldDept.trim().equals(currentDept.trim())) && rowDeptStyle2==false )
// 	        		|| (oldDept!=null && (oldDept.trim().equals(currentDept.trim())) && rowDeptStyle2==true )){
// 		    	rowDeptStyle2=true;
// 	        	rowDeptStyle1=false; 
// 		    }
//    	     if(rowDeptStyle1) {
//        CellStyle styleDept= workbook.createCellStyle(); 
//        styleDept.setAlignment(HorizontalAlignment.CENTER);
//        styleDept.setVerticalAlignment(VerticalAlignment.CENTER);
//        styleDept.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.index); 
//        styleDept.setFillPattern(FillPatternType.SQUARES );
////        styleDept.setBorderLeft(BorderStyle.THICK);
//        styleDept.setBorderBottom(BorderStyle.THIN);
//       	cell.setCellStyle(styleDept); 
//       	cell.setCellValue(result[1].toString());
////       	sheet.autoSizeColumn(1,true);
//    	     }
//    	     
//    	     if(rowDeptStyle2) {
//    	            CellStyle styleDept= workbook.createCellStyle(); 
//    	            styleDept.setAlignment(HorizontalAlignment.CENTER);
//    	            styleDept.setVerticalAlignment(VerticalAlignment.CENTER); 
//    	            styleDept.setFillForegroundColor(IndexedColors.GOLD.index); 
//    	            styleDept.setFillPattern(FillPatternType.SQUARES );
////    	            styleDept.setBorderLeft(BorderStyle.THICK);
//    	            styleDept.setBorderBottom(BorderStyle.THIN);
//    	           	cell.setCellStyle(styleDept); 
//    	           	cell.setCellValue(result[1].toString());
////    	           	sheet.autoSizeColumn(1,true);
//    	       }
//       
//        for(int i=2;i<5;i++) {
//        	  cell=row.createCell(i);
//        	style.setAlignment(HorizontalAlignment.CENTER);	
//        	cell.setCellStyle(style); 
//        	if(i==0)
//        		cell.setCellValue(slNo);
//        	else
//        		cell.setCellValue(result[i].toString());
////        	sheet.autoSizeColumn(i,true);
//        }
//        
//        
//        
//          cell=row.createCell(4);
//    	style.setAlignment(HorizontalAlignment.CENTER);
//    	cell.setCellStyle(style);
//    	String startDate =  returnDate((Date)  result[4]);
//    	cell.setCellValue(startDate);
////    	sheet.autoSizeColumn(4,true);
//    	
//    	cell=row.createCell(5);
//    	style.setAlignment(HorizontalAlignment.CENTER);
//    	cell.setCellStyle(style); 
//	        String toDate =  returnDate((Date)  result[5]);
//    	cell.setCellValue(toDate);
////    	sheet.autoSizeColumn(5,true);
//    	
//    	cell=row.createCell(6);
//    	style.setAlignment(HorizontalAlignment.CENTER);
//    	cell.setCellStyle(style); 
//    	cell.setCellValue(result[6].toString());
////    	sheet.autoSizeColumn(6,true);
//      
//    
////  	  if(oldId!=currentEmpId  && oldId!=0l)
////		  firstRow=rowIdx;
//    	
// 
//    	
////       String startDate =  returnDate((Date)  result[2]);
////       String toDate =  returnDate((Date)  result[3]);
//			   String startDay=startDate.substring(0, 2);
//			   String startMonth=startDate.substring(3, 6);
////			   String startYear=startDate.substring(7, 11);
//			   String endDay=toDate.substring(0, 2);
//			   String endMonth=toDate.substring(3, 6);
////			   String endYear=toDate.substring(7, 11);
//			   int startingExcelValue=0;
//				 int endExcelValue=0;
//			  for (String month: excelMonths.keySet() ) {
//				  if(startMonth.trim().equalsIgnoreCase(month)) {
//					  startingExcelValue= excelMonths.get(month);
//				  }
//				  if(endMonth.trim().equalsIgnoreCase(month)) {
//					  endExcelValue= excelMonths.get(month);
//				  }
//				  
//			  }
//			  startingExcelValue= (startingExcelValue+Integer.parseInt(startDay))-1;	
//			  endExcelValue= endExcelValue+Integer.parseInt(endDay);
//			  
//			  
//			  //System.out.println("startMonth**" + startMonth);
//			  //System.out.println("end **" + endMonth);
//			  //System.out.println("startingExcelValue**" + startingExcelValue);
//			  //System.out.println("endExcelValue**" + endExcelValue);
//			  for(int i=startingExcelValue;i<endExcelValue;i++) {
//				  if(startingExcelValue <= endExcelValue) {
//					  CellStyle style2 = workbook.createCellStyle(); 
//					   
//						  switch (result[6].toString()) {
//
//						  case "Annual":
//						   style2.setFillForegroundColor(IndexedColors.GREEN.index); 
//						  break;
//						  case "Overtime":
//						   style2.setFillForegroundColor(IndexedColors.RED.index); 
//						  break; 
//						  case "Course":
//							  style2.setFillForegroundColor(IndexedColors.BROWN.index); 
//						  break;
//						  case "Motherhood":
//							  style2.setFillForegroundColor(IndexedColors.PINK.index); 
//						  break;
//						  }
//						  //statement sequence
////					  style2.setFillForegroundColor(IndexedColors.ORANGE.index); 
//					  style2.setBorderTop(BorderStyle.THIN);
//					  style2.setBorderBottom(BorderStyle.THIN);
//					  style2.setBorderLeft(BorderStyle.THIN);
//					  style2.setBorderRight(BorderStyle.THIN);
//					  style2.setFillPattern(FillPatternType.BRICKS );
//		        	  cell=row.createCell(startingExcelValue);
//		        	  
//		        	cell.setCellStyle(style2); 
//		        	cell.setCellValue(1);
//		        	startingExcelValue++;
//				  }
//		       }
//		
//		          
//			  rowIdx++;	  
//       oldId=currentEmpId;
//       oldDept=currentDept;
//       
//      }
//      for(int i=0;i<7;i++)
//    	  sheet.autoSizeColumn(i,true);
//      sheet.autoSizeColumn(0,true);
//      Row totalRow = sheet.createRow(LeaveDetails.size()+2);
//      k=7;double sumOfEachCol=0;
//      for(int i=0 ;i<=6;i++) {
//    	 Cell cell=totalRow.createCell(i);
//    	 CellStyle style2 = workbook.createCellStyle();
//    	 style2.setAlignment(HorizontalAlignment.CENTER);
//    	   style2.setVerticalAlignment(VerticalAlignment.CENTER);
//    	 style2.setBorderTop(BorderStyle.THIN);
//		  style2.setBorderBottom(BorderStyle.THIN);
//		  style2.setBorderLeft(BorderStyle.THIN);
//		  style2.setBorderRight(BorderStyle.THIN); 
//    	 cell.setCellValue("Total");
//    	 cell.setCellStyle(style2);
//    	 
//      }
//      sheet.addMergedRegion(new CellRangeAddress(totalRow.getRowNum(),totalRow.getRowNum(),0,6));
//       //System.out.println(" ** employeeCount"+employeeCount);
//       double maxLeave=0.0;
//       maxLeave = (employeeCount)*((double)30/(double)100);
//     //System.out.println("**maxLeave "+maxLeave);
//	     for(int i=1 ;i<=12;i++) { 
//	       YearMonth yearMonthObject = YearMonth.of(2020, i);
//		    daysInMonth = yearMonthObject.lengthOfMonth(); 
//			    for (int j = 1; j <= daysInMonth; j++) {
//			        Cell  cell = totalRow.createCell((k));
//			        sumOfEachCol=0.0;
//			        for(int m=2;m<(LeaveDetails.size()+2);m++) {
//			         
////			         //System.out.println(" ** sheet.getRow(m).getCell(k);"+sheet.getRow(m).getCell(25));
//			         double sheets=0.0;
//			         
//			         if(sheet.getRow(m).getCell(k)!=null)
//			           sheets=sheet.getRow(m).getCell(k).getNumericCellValue();
//			         sumOfEachCol=sumOfEachCol+sheets;
//			         
//			        }
//			        
//			        CellStyle headerCellStyle2 = workbook.createCellStyle();
//			        headerCellStyle2.setBorderTop(BorderStyle.THIN);
//			        headerCellStyle2.setBorderBottom(BorderStyle.THIN);
//			        headerCellStyle2.setBorderLeft(BorderStyle.THIN);
//			        headerCellStyle2.setBorderRight(BorderStyle.THIN);
//			        if(sumOfEachCol>maxLeave) {
////			        	headerCellStyle2.setfi
//			        	headerCellStyle2.setFillForegroundColor(IndexedColors.RED1.index);
//			        	headerCellStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND );
//			        }
//			        
//			        if(j==1) {
//			        	  headerCellStyle2.setBorderLeft(BorderStyle.THICK);
//			        }
//			        cell.setCellValue(sumOfEachCol);
//			        cell.setCellStyle(headerCellStyle2);
////			        sheet.autoSizeColumn(k);
//			        k++;  
//			    }
//			  
////			    Row indicatorRow = sheet.createRow(LeaveDetails.size()+4);
//			    
//			    
//	     }
//	     
//	     
//	     for( int i=0 ;i<4;i++) {
//	    	 Row indicatorRow = sheet.createRow(LeaveDetails.size()+4+i);
//	    	 Cell cell=indicatorRow.createCell(5);
//	    	 Cell cell2=indicatorRow.createCell(6);
//	    	 CellStyle style2 = workbook.createCellStyle();
//	    	  
//	    	 style2.setAlignment(HorizontalAlignment.CENTER);
//	    	 style2.setVerticalAlignment(VerticalAlignment.CENTER);
//	    	 style2.setBorderTop(BorderStyle.THIN);
//			  style2.setBorderBottom(BorderStyle.THIN);
//			  style2.setBorderLeft(BorderStyle.THIN);
//			  style2.setBorderRight(BorderStyle.THIN); 
//			  if(i==0) {
//				  cell.setCellValue("Annual Leave");
//				  style2.setFillForegroundColor(IndexedColors.GREEN.index);
//			  }else  
//			  if(i==1) {
//				  cell.setCellValue("Overtime Leave");
//				  style2.setFillForegroundColor(IndexedColors.RED.index);
//			  }else  
//			  if(i==2) {
//				  cell.setCellValue("Course Leave");
//				  style2.setFillForegroundColor(IndexedColors.BROWN.index);
//			  }else
//			  if(i==3) {
//				  cell.setCellValue("Motherhood Leave");
//				  style2.setFillForegroundColor(IndexedColors.PINK.index);
//			  }
//			  style2.setFillPattern(FillPatternType.BRICKS );
//			  cell2.setCellStyle(style2);
//	    	 
//	      }
////      File outputFile = new File( path+filename+".xlsx");
////      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
////		OutputStream fileOutputStream = new FileOutputStream(outputFile);
////		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
////		byteArrayOutputStream.writeTo(fileOutputStream);
//      workbook.write(out);
//      return new ByteArrayInputStream(out.toByteArray());
//      
//    }
////    return "Success";
//  }
//
