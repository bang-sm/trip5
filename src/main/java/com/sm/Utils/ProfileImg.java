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

@Component("profileImg")
public class ProfileImg {

	private static final Logger logger = LoggerFactory.getLogger(ProfileImg.class);

	@Autowired
	HttpServletRequest request;

	public List<Map<String, Object>> parseFileInfo(MemberVO memberVO, MultipartFile file) throws Exception {

		String root_path = request.getSession().getServletContext().getRealPath("/");
		System.out.println(root_path+"zzzzzzzzzzzzzzzzzzzzzzzzzzz");
		String attach_path = "resources/upload/profile";
		System.out.println(attach_path+"zzzzzzzzzzzzzzzzzzzzzzzzzzz");

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
			fileInfo.put("photoCustomName", "");
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
