package com.sm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.dao.NoticeDAO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NoticeService{
	
	@Autowired
	NoticeDAO noticeDao;
	
	// 슬라이드 공지 출력
    public List<String> slideNotice(){

    	List<String> snContent = null;
    	int count = 0;
    	
    	try {
    		snContent = noticeDao.slideContent();
			count = noticeDao.slideCount();
			
	    	for(int i = 0; i< count; i++) {
	    		System.out.println(snContent.get(i));
	    	}
	    	
	    	return snContent;
		} catch (Exception e) {
			e.printStackTrace();
		} // end try catch
    	
    	return snContent;
    } // end slideNotice
}






































