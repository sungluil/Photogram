package com.example.photogram.controller;

import com.example.photogram.config.PrincipalDetails;
import com.example.photogram.dto.UserProfileDto;
import com.example.photogram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserService userService;

	@GetMapping("/user/{id}")
	public String profile(@PathVariable int id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) throws Exception {
		UserProfileDto dto = userService.회원프로필(id, principalDetails.getUser().getId());
		model.addAttribute("dto", dto);
		return "user/profile";
	}

	@GetMapping("/user/{id}/update")
	public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) {

		System.out.println("세션 정보 : "+principalDetails.getUser());

		return "user/update";
	}

}
