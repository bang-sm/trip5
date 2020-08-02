package com.sm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sm.domain.MemberVO;
import com.sm.domain.TravelInfoRootVO;
import com.sm.domain.TravelInfoVO;
import com.sm.domain.TravelReplyVO;
import com.sm.domain.TravelVO;
import com.sm.service.TravelService;

/**
 * @author smbang 작업 컨트롤러 일지등록 관련한 컨트롤러입니다.
 */
@Controller
@RequestMapping(value = "/travel")
public class TravelController {
	private static final Logger logger = LoggerFactory.getLogger(TravelController.class);

	@Autowired
	HttpSession session;

	@Autowired
	private TravelService travelService;

	@GetMapping(value = "/travel_main")
	public String travel_main() {
		logger.info("travel_main");

		return "/travel/travel_main";
	}

	// 유저키와 일지 키를 가지고 일지 모든 데이터 가져오기
	@GetMapping(value = "/travel_blog")
	public String travel_blog(int uuid, int tsid, Model model) {
		logger.info("travel_blog");
		model.addAttribute("travel", travelService.getTravelBlogData(uuid, tsid));

		return "/travel/travel_blog";
	}
	
	// 유저를 기준으로 일지 표출
	@GetMapping(value = "/share_travel")
	public String share_travel(int uuid,Model model) {
		logger.info("share_travel");
		
		if(travelService.findUser(uuid)==false) {
			return "/error/notUser";
		}
		model.addAttribute("data",travelService.share_travel(uuid));
		
		return "/travel/share_travel";
	}


	@GetMapping(value = "/intro_date")
	public String intro_date(HttpSession session, Model model) {
		logger.info("intro_date");
		int count;
		if (session.getAttribute("userInfo") != null) {
			MemberVO vo = new MemberVO();
			vo = (MemberVO) session.getAttribute("userInfo");
			int uuid = vo.getUuid();
			count = travelService.tempTravelCheck(uuid);
		} else {
			count = 0;
		}
		model.addAttribute("count", count);

		return "/travel/intro_date";
	}

	/**
	 * @author smbang 첫 등록화면에서 날짜와 제목 선택후 저장 시킬 맵핑 제목과 날짜 우선으로 일지를 우선저장 합니다.
	 */
	@ResponseBody
	@PostMapping("/travel_firstSave")
	public int travel_firstSave(@ModelAttribute TravelVO travelVO) {
		logger.info("travel_firstSave");
		
		MemberVO memberVO = new MemberVO();
		memberVO = (MemberVO) session.getAttribute("userInfo");
		travelVO.setUuid(memberVO.getUuid());
		travelService.travel_firstSave(travelVO);
		
		return travelVO.getTsid();
	}

	/**
	 * @author smbang 일지디테일 임시저장용 맵핑
	 */
	@ResponseBody
	@PostMapping("/travel_temp_save")
	public String travel_temp_save(@ModelAttribute TravelInfoVO travelInfoVO,
			@ModelAttribute TravelInfoRootVO travelInfoRootVO) {
		logger.info("travel_temp_save");

		travelService.tempSave(travelInfoVO, travelInfoRootVO);

		return "다녀왔습니다";
	}

	@ResponseBody
	@PostMapping(value = "/travel_root_delete")
	public String travel_root_delete(int tsirootorder, int tsid) throws Exception {
		logger.info("travel_root_delete");

		System.out.println(tsirootorder + " /  " + tsid);
		travelService.travel_root_delete(tsirootorder, tsid);

		return "삭제되었습니다";
	}

	@ResponseBody
	@PostMapping(value = "/travel_detail_data")
	public List<TravelInfoVO> travel_detail_data(String tsid) throws Exception {
		logger.info("travel_detail_data");
		return travelService.getTravelInfo(tsid);
	}

	/**
	 * @author smbang 일지 디테일 등록할 화면 맵핑
	 */
	@GetMapping(value = "/regist")
	public String regist_get(Model model, String tsid, HttpSession session) {
		logger.info("regist_get");
		logger.info(tsid + "========================================");
		int uuid;
		if (session.getAttribute("userInfo") != null) {
			if (tsid == "" || tsid != null) {
				MemberVO vo = new MemberVO();
				vo = (MemberVO) session.getAttribute("userInfo");
				uuid = vo.getUuid();
				model.addAttribute("travel", travelService.getTravelStory(tsid, uuid)); // 기본정보
				model.addAttribute("travelInfo", travelService.getTravelInfo(tsid)); // 임시저장된 상세정보
				model.addAttribute("travelRootInfo", travelService.getTravelRootInfo(tsid, uuid)); // 임시저장된 루트정보
				model.addAttribute("travelImage", travelService.getTravelImage(tsid, uuid)); // 임시저장된 이미지
			}
		} else {
			return "redirect:/index";
		}
		return "/travel/regist";
	}

	@PostMapping(value = "/registSave")
	public String regist_get(@ModelAttribute TravelVO travelVO, @ModelAttribute TravelInfoVO travelinfoVO,
			@ModelAttribute TravelInfoRootVO travelinfoRootVO, HttpSession session, @RequestPart MultipartFile[] mfiles)
			throws Exception {
		logger.info("registSave");
		int uuid;
		if (session.getAttribute("userInfo") != null) {
			MemberVO vo = new MemberVO();
			vo = (MemberVO) session.getAttribute("userInfo");
			uuid = vo.getUuid();
			travelService.registSave(travelVO, travelinfoVO, travelinfoRootVO, mfiles, uuid);
			return "redirect:/mypage";
		} else {
			return "redirect:/index";
		}
	}

	// 댓글저장
	@ResponseBody
	@PostMapping(value = "/travel_reply_save")
	public List<TravelReplyVO> travel_reply_save(@ModelAttribute TravelReplyVO travelReplyVO) throws Exception {
		logger.info("travel_reply_save");
		int uuid;
		List<TravelReplyVO> list = new ArrayList<TravelReplyVO>();

		HashMap<String, Object> param = new HashMap<>();
		if (session.getAttribute("userInfo") != null) {
			MemberVO vo = new MemberVO();
			vo = (MemberVO) session.getAttribute("userInfo");
			uuid = vo.getUuid();
			param.put("uuid", uuid);
			param.put("reply", travelReplyVO);
			// 댓글 insert
			travelService.travel_reply_save(param);
			// 댓글리스트목록가져오기
			list = travelService.travel_reply_list(travelReplyVO.getTsId());
		} else {
			return null;
		}
		return list;
	}

	// 댓글삭제
	@ResponseBody
	@PostMapping(value = "/travel_reply_delete")
	public int travel_reply_delete(int uuid, int ts_reply_id, HttpSession session) throws Exception {

		if (session.getAttribute("userInfo") != null) {
			MemberVO vo = new MemberVO();
			vo = (MemberVO) session.getAttribute("userInfo");

			if (vo.getUuid() == uuid) {
				travelService.travel_reply_delete(uuid, ts_reply_id);
			}
		} else {
			return 0;
		}

		return 1;
	}

	// 좋아요
	@ResponseBody
	@PostMapping(value = "/travel_like")
	public int travel_like(int tsid) throws Exception {

		travelService.travel_like(tsid);
		int like = travelService.likeCount(tsid);

		return like;
	}

	// 북마크
	@ResponseBody
	@PostMapping(value = "/bookmark")
	public int bookmark(int tsid, HttpSession session) throws Exception {

		int status;

		if (session.getAttribute("userInfo") != null) {
			MemberVO vo = new MemberVO();
			vo = (MemberVO) session.getAttribute("userInfo");
			int uuid = vo.getUuid();
			status = travelService.bookmark(tsid, uuid);
		} else {
			status = 999;
		}
		// 1 : 삭제 2: 인서트 999: 로그인필요

		return status;
	}

	// 팔로우
	@ResponseBody
	@PostMapping(value = "/follow")
	public int follow(int followId, HttpSession session) throws Exception {
		int status;
		if (session.getAttribute("userInfo") != null) {
			MemberVO vo = new MemberVO();
			vo = (MemberVO) session.getAttribute("userInfo");
			int uuid = vo.getUuid();
			status = travelService.follow(followId, uuid);
		} else {
			status = 999;
		}
		// 1 : 삭제 2: 인서트 999: 로그인필요

		return status;
	}

	// 이미지 삭제
	@ResponseBody
	@PostMapping(value = "/imageDelete")
	public int imageDelete(int photoId, String customName) throws Exception {

		int status = travelService.imageDelete(photoId, customName);

		return status;
	}
}
