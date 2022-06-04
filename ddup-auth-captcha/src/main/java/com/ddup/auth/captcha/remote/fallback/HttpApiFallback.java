package com.ddup.auth.captcha.remote.fallback;

import com.ddup.auth.captcha.remote.HttpApi;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;
import retrofit2.Call;

@Component
@Slf4j
public class HttpApiFallback implements HttpApi {

	@Override
	public Call<ResponseBody> siteverify(String secret, String response) {
		log.info("调用回调方法！");
		return null;
	}

}
