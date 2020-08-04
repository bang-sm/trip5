package com.sm.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.sm.domain.MemberVO;
import com.sm.domain.TravelVO;

@Component("fileUtils")
public class FileUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
	
	
	@Autowired
	HttpServletRequest request;
	
    public List<Map<String, Object>> parseFileInfo(TravelVO travelVO, MultipartFile[] file) throws Exception {
    	
    	String root_path = request.getSession().getServletContext().getRealPath("/");  
        String attach_path = "resources/upload/";
    	 
        int ts_id = travelVO.getTsid(); //일지아이디
        System.out.println(ts_id+ " =========================");
        List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
 
        File target = new File(root_path+attach_path);
        if(!target.exists()) target.mkdirs();
        
        for(int i=0; i<file.length; i++) {
       
            String orgFileName = file[i].getOriginalFilename();
            String orgFileExtension="";
            if(orgFileName.lastIndexOf(".")==-1) {
            	continue;
            }
            String saveFileName = UUID.randomUUID().toString().replaceAll("-", "") + orgFileExtension;
            Long saveFileSize = file[i].getSize();
            
            logger.debug("================== file start ==================");
            logger.debug("파일 실제 이름: "+orgFileName);
            logger.debug("파일 저장 이름: "+saveFileName);
            logger.debug("파일 크기: "+saveFileSize);
            logger.debug("content type: "+file[i].getContentType());
            logger.debug("================== file   END ==================");
 
            target = new File(root_path+attach_path, saveFileName);
            file[i].transferTo(target);
            
            Map<String, Object> fileInfo = new HashMap<String, Object>();
            
            fileInfo.put("photo_path", orgFileName);
            fileInfo.put("ts_id", ts_id);
            fileInfo.put("photo_original_name", orgFileName);
            fileInfo.put("photo_custom_name", saveFileName);
            fileInfo.put("photo_file_size", saveFileSize);
            fileList.add(fileInfo);
            
        }
        return fileList;
    }
    
    
	public List<Map<String, Object>> parseUserInfo(MemberVO memberVO, MultipartFile file) throws Exception {

		String root_path = request.getSession().getServletContext().getRealPath("/");
		String attach_path = "resources/upload/userProfile";

		int mbUuid = memberVO.getUuid(); // 일지아이디
		System.out.println(mbUuid + " =========================");
		List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();

		File target = new File(root_path + attach_path);
		if (!target.exists())
			target.mkdirs();

		String orgFileName = file.getOriginalFilename();
		String orgFileExtension;
		// file.getOriginalFilename()
		
		// 
		if (orgFileName.lastIndexOf(".") == -1) {
			// 아무것도 없을 때
			Map<String, Object> fileInfo = new HashMap<String, Object>();
			fileInfo.put("photoPath", "profile.jpg");
			fileInfo.put("mbUuid", mbUuid);
			fileInfo.put("photoOriginalName", "");
			fileInfo.put("photoCustomName", "profile.jpg");
			fileInfo.put("photoFileSize", "");
			fileList.add(fileInfo);
			return fileList;
		} else {
			orgFileExtension = orgFileName.substring(orgFileName.lastIndexOf("."));
		}
		
		String saveFileName = UUID.randomUUID().toString().replaceAll("-", "") + orgFileExtension;
		Long saveFileSize = file.getSize();

		logger.debug("================== file start ==================");
		logger.debug("파일 실제 이름: " + orgFileName);
		logger.debug("파일 저장 이름: " + saveFileName);
		logger.debug("파일 크기: " + saveFileSize);
		logger.debug("content type: " + file.getContentType());
		logger.debug("================== file   END ==================");

		// 저장 경로
		target = new File(root_path + attach_path, saveFileName);

		// 파일 저장 
		file.transferTo(target);

		Map<String, Object> fileInfo = new HashMap<String, Object>();

		fileInfo.put("photoPath", orgFileName);
		fileInfo.put("mbUuid", mbUuid);
		fileInfo.put("photoOriginalName", orgFileName);
		fileInfo.put("photoCustomName", saveFileName);
		fileInfo.put("photoFileSize", saveFileSize);
		fileList.add(fileInfo);
		return fileList;
	}

}
