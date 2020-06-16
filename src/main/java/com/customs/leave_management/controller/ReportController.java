package com.customs.leave_management.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.customs.leave_management.model.EmpLeaveDTO;
import com.customs.leave_management.services.FileService;
import com.customs.leave_management.services.ReportGenerateService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
//@CrossOrigin(origins="http://localhost:4200")
@CrossOrigin(origins = "*")
public class ReportController {
    @Autowired
    ReportGenerateService reportGenerateService;
    
    private final FileService fileService;
 
      
      public ReportController(FileService fileService) {
      	this.fileService = fileService;
  	}
    private static final Logger logger = Logger.getLogger(ReportController.class.getName());

//     
//    @PostMapping("/upload")
	@PostMapping(value ="/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
//	public ResponseEntity<String> uploadData(@Valid @RequestBody MultipartFile file) throws Exception {
    public ResponseType uploadData(@RequestParam("file") MultipartFile file
    		,@RequestParam("empId") String empId, 
    		@RequestParam("uploadType") String uploadType) throws Exception {
		ResponseType responseType=new ResponseType();
		List<EmpLeaveDTO> emLeaveDAOList=new ArrayList<EmpLeaveDTO>();
		try {
		if (file == null) {
			throw new RuntimeException("You must select the a file for uploading");
		}
//		InputStream inputStream = file.getInputStream();
		String originalName = file.getOriginalFilename();
//		String name = file.getName();
//		String contentType = file.getContentType();
//		long size = file.getSize();
//		logger.info("inputStream: " + inputStream);
		logger.info("originalName: " + originalName);
//		logger.info("name: " + name);
//		logger.info("contentType: " + contentType);
//		logger.info("size: " + size);
//		 return reportGenerateService.readUploadedFile(file);
		 responseType= reportGenerateService.readUploadedFile(file,  empId, uploadType);
		// Do processing with uploaded file data in Service layer
//		return new ResponseEntity<String>(status, HttpStatus.OK);
		}catch(Exception ex) {
//			return false;
			return responseType;
		}
//		return status;
		return responseType;
		
	}
    
   
    
//    @GetMapping(value = "/generateExcelReport/{year}/customers.xlsx")
	@GetMapping(value = "/generateExcelReport/{year}")
	 public void generateExcelReport(HttpServletResponse response,@PathVariable String year) throws IOException{
//    public void generateExcelReport(HttpServletResponse response,@PathVariable long empId,@PathVariable String year) throws IOException{
    	int empId=0;
    	System.out.println("Emp ID "+empId);
     
    	 ByteArrayInputStream in = reportGenerateService.generateExcelReport(empId,year);
    	   response.setContentType("application/octet-stream");
           response.setHeader("Content-Disposition", "attachment; filename=Employees_Leave.xlsx"); 
           IOUtils.copy(in, response.getOutputStream());
//    	  HttpHeaders headers = new HttpHeaders();
//          headers.add("Content-Disposition", "attachment; filename=customers.xlsx");
//      
//       return ResponseEntity
//                    .ok()
//                    .headers(headers)
//                    .body(new InputStreamResource(in));
    }
	
	
//	@GetMapping(value = "/excelReportDownload/{filename}", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; charset=utf-8")
// 	public Resource getExcelFileFromFileSystem(@PathVariable String filename, HttpServletResponse response) {
//		logger.info("filename*********" +filename);
////		return fileService.getFileSystem(filename, response);
//	} 
	
}
