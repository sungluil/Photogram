package com.example.photogram.controller;

import com.example.photogram.config.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class UserController {

	@GetMapping("/user/{id}")
	public String profile(@PathVariable int id) {
		return "user/profile";
	}

	@GetMapping("/user/{id}/update")
	public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) {

		System.out.println("세션 정보 : "+principalDetails.getUser());

		return "user/update";
	}

}
