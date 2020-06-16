package com.customs.leave_management.services; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
 
import javax.servlet.http.HttpServletResponse;
 
@Component
public class FileService {
//
//	private static final Logger logger = LoggerFactory.getLogger(FileService.class);
//	private enum ResourceType {
//		FILE_SYSTEM,
//		CLASSPATH
//	}
//	    @Value("${file.upload-dir}")
//	    private String folderLocation; 
//	    
//	    String path = folderLocation + "/" ;
//	    
//	    private static final String FILE_DIRECTORY = "C:\\Users\\ibrahim.fazil\\Desktop\\Reportss\\";
// 
//	public Resource getFileSystem(String filename, HttpServletResponse response) { 
//		return getResource(filename, response, ResourceType.FILE_SYSTEM);
//	} 
//	
//	public Resource getClassPathFile(String filename, HttpServletResponse response) {
//		return getResource(filename, response, ResourceType.CLASSPATH);
//	}
// 
//	private Resource getResource(String filename, HttpServletResponse response, ResourceType resourceType) {
//		logger.info("resource is "+filename);
//		if(filename.trim().contains(".pdf")){
//			response.setContentType("application/pdf");
//			response.setHeader("Content-Disposition", "attachment; filename=" + filename);
//			response.setHeader("filename", filename);
//		}else { 
//			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//			response.setHeader("Content-Disposition", "attachment; filename=" + filename);
//			response.setHeader("filename", filename);
//		} 
//		Resource resource = null;
//		switch (resourceType) {
//			case FILE_SYSTEM:
////				resource = new FileSystemResource(FILE_DIRECTORY+filename);
//				resource = new FileSystemResource(folderLocation + "/"+filename);
//				System.out.println("resource is "+resource);
//				break;
//			case CLASSPATH:
//				resource = new ClassPathResource("data/" + filename);
//				break;
//		} 
//		return resource;
//	}
}