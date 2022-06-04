package com.ddup.auth.captcha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping("/index2")
	public String index() {
		return "index2";
	}

	@GetMapping("/index1")
	public String index1() {
		return "index1";
	}

}
