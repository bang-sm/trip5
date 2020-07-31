package com.sm.Handler;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.sm.domain.MemberVO;
import com.sm.domain.TravelViewVO;
import com.sm.service.PlacelistService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyinfoInterceptor implements HandlerInterceptor{
		
	@Autowired
	PlacelistService service;
	
	private String saveDestination(HttpServletRequest request) {
		String uri = request.getRequestURI();
		String query = request.getQueryString();
		String destination = "";
		
		if(query == null || query.equals("null")) {
			query = "";
		} else {
			query = "?" + query;
		}
		
		if(request.getMethod().equals("GET")) {
			log.info("destination : " + (uri + query));
			destination = uri + query;
		}
		return destination;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String query = request.getQueryString();
		
		if(query != null) {
			
			StringTokenizer stringTokenizer = new StringTokenizer(query, "=&");
			
			HttpSession httpSession = request.getSession();
			TravelViewVO travelViewVO = new TravelViewVO();
			MemberVO memberVO = new MemberVO();
			
			memberVO = (MemberVO) httpSession.getAttribute("userInfo");
			
			if(memberVO != null) { // 로그인 되어 있을 때!! 
				
				int uuid = memberVO.getUuid();
				String tsUrl = saveDestination(request);
				String tstitle = "";
				int tsid = 0;
				
				while (stringTokenizer.hasMoreTokens()) {
					String data = stringTokenizer.nextToken();
					if(data.equals("tsid")) {
						break;
					}
				}
				
				tsid = Integer.parseInt(stringTokenizer.nextToken());
				tstitle = service.selectTravelStoryByTsid(tsid).getTstitle();
				int dbUuid = service.selectTravelStoryByTsid(tsid).getUuid();
				
				if(dbUuid != uuid) {
					
					travelViewVO.setUuid(uuid);
					travelViewVO.setTsid(tsid);
					travelViewVO.setTstitle(tstitle);
					travelViewVO.setTsUrl(tsUrl);
					
					service.insertTravelView(travelViewVO, uuid);
				}
			}
		}
		return true;
	}
}
