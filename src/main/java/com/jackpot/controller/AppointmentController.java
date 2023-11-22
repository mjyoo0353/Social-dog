package com.jackpot.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jackpot.domain.AppointmentCriteria;
import com.jackpot.domain.AppointmentPageDTO;
import com.jackpot.domain.AppointmentVO;
import com.jackpot.service.AppointmentService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/appointment")
@AllArgsConstructor
public class AppointmentController {
	
	private AppointmentService service;
	
	@ModelAttribute("searchTypes")
	public Map<String, String> searchTypes() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("", "-- 검색대상선택 --");
		map.put("N", "약속 이름");
		map.put("H", "주최자");
		map.put("L", "약속 장소");
		map.put("NH", "약속 이름 + 주최자");
		map.put("NL", "약속 이름 + 약속 장소");
		map.put("NHL", "약속 이름 + 주최자 + 약속 장소");
		
		return map;		
	}

	
	@GetMapping("/list") // View이름: appointment/list (앞뒤 "/"과 확장자는 prefix, surfix가 붙여줌)
	public void list(@ModelAttribute("cri") AppointmentCriteria cri, Model model) {
		log.info("list: " + cri);
		model.addAttribute("list", service.getList(cri));
		
		int total = service.getTotal(cri);
		log.info("total: " + total);
		
		model.addAttribute("pageMaker", new AppointmentPageDTO(cri, total));
	}
	
	@GetMapping("/register") // 로직이 없어서 Test X
	public void register(@ModelAttribute("appointment") AppointmentVO appointment) {
		log.info("register");
	}

	@PostMapping("/register") // POST 요청의 리턴 타입은 String
	public String register(
			@Valid @ModelAttribute("appointment") AppointmentVO appointment,
			Errors errors,
			RedirectAttributes rttr) throws Exception {
		log.info("register: " + appointment);
		if(errors.hasErrors()) {
			return "appointment/register";
		}
		
		service.register(appointment);
		
		rttr.addFlashAttribute("result", appointment.getAppointmentId());
		
		return "redirect:/appointment/list"; // 요청 url
	}
	
	@GetMapping({"/get", "/modify"}) //get : 상세보기, modify: 수정 화면으로 가기
	public void get(@RequestParam("appointmentId") Long appointmentId, @ModelAttribute("cri") AppointmentCriteria cri, Model model) {
		log.info("/get or modify");
		model.addAttribute("appointment", service.get(appointmentId));
		
		log.info("getParticipantList");
		model.addAttribute("list", service.getParticipantList(appointmentId));
	}
	
	@PostMapping("/modify")
	public String modify(
			@Valid @ModelAttribute("appointment") AppointmentVO appointment,
			Errors errors,
			@ModelAttribute("cri") AppointmentCriteria cri,
			RedirectAttributes rttr) throws Exception{
		log.info("modify:" + appointment);
		if(errors.hasErrors()) {
			return "appointment/modify";
		}
		if (service.modify(appointment)) {
			// Flash --> 1회성
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:" + cri.getLinkWithAppointmentId("/appointment/get", appointment.getAppointmentId());
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("appointmentId") Long appointmentId,
			@ModelAttribute("cri") AppointmentCriteria cri,
			RedirectAttributes rttr) {
		log.info("remove..." + appointmentId);
		
		service.remove(appointmentId);
		
		return "redirect:" + cri.getLink("/appointment/list"); // 요청 url
	}
}