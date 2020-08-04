package com.sm.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sm.Utils.FileUtils;
import com.sm.dao.TravelDAO;
import com.sm.domain.PhotoVO;
import com.sm.domain.TravelInfoRootVO;
import com.sm.domain.TravelInfoVO;
import com.sm.domain.TravelReplyVO;
import com.sm.domain.TravelVO;

@Service
public class TravelService {

	@Autowired
	TravelDAO travelDAO;
	@Autowired
	FileUtils fileUtils;
	@Autowired
	HttpServletRequest request;

	// 일지 등록
	public void storyRegist(TravelVO travelVO, TravelInfoVO travelinfoVO) {
		// MemberVO memberVO=new MemberVO();
		// memberVO = (MemberVO) session.getAttribute("userInfo");
		// travelVO.setUuid(memberVO.getUuid());
		travelDAO.storyRegist(travelVO, travelinfoVO);
	}

	/**
	 * @author smbang 일지의 기본 데이터 저장 하기 위한 매소드 제목 , 시작날짜 , 마지막날짜 , 기간 만큼 미리 DAY별
	 *         임시저장하고 키를 미리 받아 리턴시킨다.
	 */
	public void travel_firstSave(TravelVO travelVO) {
		travelDAO.travel_firstSave(travelVO);
	}

	/**
	 * @author smbang 일지작성을 위해서 tsid,uuid 값을 통해 해당 일지의 정보를 받아온다.
	 * @param uuid 
	 */
	public TravelVO getTravelStory(String tsid, int uuid) {
		return travelDAO.getTravelStory(tsid, uuid);
	}

	/**
	 * @author smbang 일지의 디테일 getTravelInfo 기본 정보를 받아오기위해서 일지의 디테일 TravelInfoRootVO
	 *         루트정보를 받아오기위해서
	 */
	public List<TravelInfoVO> getTravelInfo(String tsid) {
		return travelDAO.getTravelInfo(tsid);
	}

	public List<TravelInfoRootVO> getTravelRootInfo(String tsid, int uuid) {
		// TODO Auto-generated method stub
		return travelDAO.getTravelRootInfo(tsid);
	}

	/**
	 * @author smbang 일지 임시저장 데이터를 저장합니다.
	 */
	public void tempSave(TravelInfoVO travelInfoVO, TravelInfoRootVO travelInfoRootVO) {
		travelDAO.tempSave(travelInfoVO, travelInfoRootVO);
	}

	/**
	 * @author smbang 경로삭제
	 */
	public void travel_root_delete(int tsirootorder, int tsid) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("tsirootorder", tsirootorder);
		map.put("tsid", tsid);
		travelDAO.travel_root_delete(map);
	}

	public HashMap<String, Object> getTravelBlogData(int uuid, int tsid) {
		HashMap<String, Integer> param = new HashMap<>();
		param.put("tsid", tsid);
		param.put("uuid", uuid);

		HashMap<String, Object> map = new HashMap<String, Object>();

		map = travelDAO.getTravelBlogData(param);

		return map;
	}

	// 리플 등록하기
	public void travel_reply_save(HashMap<String, Object> param) {
		travelDAO.travel_reply_save(param);
	}

	public List<TravelReplyVO> travel_reply_list(int tsId) {
		return travelDAO.travel_reply_list(tsId);
	}

	// 리플삭제
	public void travel_reply_delete(int uuid, int ts_reply_id) {
		HashMap<String, Integer> param = new HashMap<>();
		param.put("uuid", uuid);
		param.put("ts_reply_id", ts_reply_id);

		travelDAO.travel_reply_delete(param);
	}

	// 좋아요
	public void travel_like(int tsid) {

		travelDAO.travel_like(tsid);
	}

	// 좋아요 개수
	public int likeCount(int tsid) {
		// TODO Auto-generated method stub
		return travelDAO.likeCount(tsid);
	}

	// 북마크
	public int bookmark(int tsid, int uuid) {
		HashMap<String, Integer> param = new HashMap<>();
		param.put("uuid", uuid);
		param.put("tsid", tsid);
		// 있는지 없는지 확인
		int check = travelDAO.bookmarkCheck(param);
		int status = 0;
		if (check == 1) {
			// 이미있으므로 삭제
			travelDAO.bookmarkDelete(param);
			status = 1;
		} else if (check == 0) {
			// 없으므로 insert
			travelDAO.bookmark(param);
			status = 2;
		}
		return status;
	}

	// 팔로우
	public int follow(int followId, int uuid) {
		HashMap<String, Integer> param = new HashMap<>();
		param.put("uuid", uuid);
		param.put("followId", followId);

		// 있는지 없는지 확인
		int check = travelDAO.followCheck(param);
		int status = 0;
		if (check == 1) {
			// 이미있으므로 삭제
			travelDAO.follow_delete(param);
			status = 1;
		} else if (check == 0) {
			// 없으므로 insert
			travelDAO.follow(param);
			status = 2;
		}
		return status;
	}

	public int tempTravelCheck(int uuid) {
		return travelDAO.tempTravelCheck(uuid);
	}

	// 일지저장
	public void registSave(TravelVO travelVO, TravelInfoVO travelinfoVO, TravelInfoRootVO travelinfoRootVO,
			MultipartFile[] mfiles, int uuid) throws Exception {

		List<Map<String, Object>> fileList = fileUtils.parseFileInfo(travelVO, mfiles);

		for (int i = 0; i < fileList.size(); i++) {
			System.out.println(fileList.get(i));
			travelDAO.photo_insert(fileList.get(i));
		}
		List<TravelInfoVO> infoList=new ArrayList<>();
		for (int i = 1; i < travelinfoVO.getList().size(); i++) {
			infoList.add(travelinfoVO.getList().get(i));
		}
		
		List<TravelInfoRootVO> rootList=new ArrayList<>();
		if(travelinfoRootVO.getRootlist()!=null) {
			for (int i = 0; i < travelinfoRootVO.getRootlist().size(); i++) {
				rootList.add(travelinfoRootVO.getRootlist().get(i));
			}
		}
		HashMap<String , Object> map=new HashMap<>();
		map.put("infoList", infoList);
		map.put("rootList", rootList);
		int tsid=travelVO.getTsid();
		travelDAO.finalSave(map,tsid);
	}

	// 일지 이미지 리스트
	public List<PhotoVO> getTravelImage(String tsid, int uuid) {
		return travelDAO.getTravelImage(tsid);
	}

	// 이미지삭제
	public int imageDelete(int photoId, String customName) {
		String root_path = request.getSession().getServletContext().getRealPath("/");  
        String attach_path = "resources/upload/";
		travelDAO.imageDelete(photoId);
		int status;
		File file = new File(root_path+attach_path+customName);
		
		if (file.exists()) {
			if (file.delete()) {
				System.out.println("파일삭제 성공");
				status=1;
			} else {
				System.out.println("파일삭제 실패");
				status=2;
			}
		} else {
			System.out.println("파일이 존재하지 않습니다.");
			status=999;
		}

		return status;
	}

	//공유 일지 화면 데이터
	public HashMap<String, Object> share_travel(int uuid) {
		HashMap<String, Object> param=new HashMap<>();
		
		param.put("userInfo", travelDAO.getUserInfo(uuid));
		param.put("mainList", travelDAO.getMyTravelList(uuid));
		param.put("top3",travelDAO.getMyTravelTop3(uuid));
		param.put("MyFollow",travelDAO.getMyFollow(uuid));
		param.put("MyTotalLike",travelDAO.getMyTotalLike(uuid));
		param.put("MyTravelCount",travelDAO.getMyTravelCount(uuid));
		
		return param;
	}

	public boolean findUser(int uuid) {
		
		int count=travelDAO.findUser(uuid);
		
		if(count==0) {
			return false;
		}else {
			return true;
		}
	}

	// 메인 - 정세헌 건드림 
	//메인에서 표출된 일지 리스트
	public List<TravelVO> mainTravleList(int buttonNum) {
		
		List<TravelVO> returnDAO = new ArrayList<TravelVO>();
		
		switch (buttonNum) {
		case 0:
			returnDAO = travelDAO.mainTravleList();
			break;
		case 1:
			returnDAO = travelDAO.selectmaintravelListOrderbyTslike();
			break;
		case 2:
			returnDAO = travelDAO.selectmaintravelListOrderbyTsView();
			break;
		}
		return returnDAO;
	}

}
