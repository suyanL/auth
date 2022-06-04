package com.ddup.auth.captcha.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author suancyg
 */
@RestController
@RequestMapping("/validate")
public class ValidateController {

	@GetMapping
	public Object validate(HttpServletRequest request,
			@RequestParam("token") String token) {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(HttpHeaders.CONTENT_TYPE,
				MediaType.APPLICATION_FORM_URLENCODED_VALUE);

		MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
		requestBody.add("secret", "6LeIV-MfAAAAAL2ax-YPkbpFx7VQkj-SaV4Pex1D");
		requestBody.add("response", token);
		requestBody.add("remoteip", request.getRemoteAddr()); // 客户的ip地址，不是必须的参数。
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<JSONObject> responseEntity = restTemplate.postForEntity(
				"https://www.recaptcha.net/recaptcha/api/siteverify",
				new HttpEntity<>(requestBody, httpHeaders), JSONObject.class);

		if (!responseEntity.getStatusCode().is2xxSuccessful()) {
			// TODO 异常的HTTP状态码
		}

		return responseEntity.getBody();
	}

}