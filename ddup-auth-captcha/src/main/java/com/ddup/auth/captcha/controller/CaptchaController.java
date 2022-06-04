package com.ddup.auth.captcha.controller;

import com.ddup.auth.captcha.remote.HttpApi;
import com.ddup.auth.service.utils.HttpSendUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Response;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author suancyg
 */
@RestController
@Slf4j
@RequestMapping("/v1/captcha")
public class CaptchaController {

	@Autowired
	private HttpApi httpApi;

	@RequestMapping("/validate")
	@ResponseBody
	public String check(HttpServletRequest request) {

		String checkCode = request.getParameter("g-recaptcha-response");
		String secret = "6LeIV-MfAAAAAL2ax-YPkbpFx7VQkj-SaV4Pex1D";
		// String secret = "6LeEzuofAAAAALw9r01dpnbgizcO2ephgKytb8Hd";
		String param = "secret=" + secret + "&response=" + checkCode;
		Response<okhttp3.ResponseBody> execute = null;
		try {
			execute = httpApi.siteverify(secret, checkCode).execute();
			okhttp3.ResponseBody body = execute.body();
			System.out.println(Objects.requireNonNull(body).string());
		}
		catch (Exception e) {
			log.error("调用google接口异常", e);
		}

		String json = HttpSendUtil.instance().sendPost(
				"https://www.recaptcha.net/recaptcha/api/siteverify", param, "UTF-8");
		return json;
	}

}
