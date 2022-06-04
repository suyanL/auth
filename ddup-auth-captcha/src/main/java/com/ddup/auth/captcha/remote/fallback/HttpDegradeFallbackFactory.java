package com.ddup.auth.captcha.remote.fallback;

import com.ddup.auth.captcha.remote.HttpApi;
import com.github.lianjiatech.retrofit.spring.boot.degrade.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Component;
import retrofit2.Call;

/**
 * @author Administrator
 */
@Component
@Slf4j
public class HttpDegradeFallbackFactory implements FallbackFactory<HttpApi> {

	@Override
	public HttpApi create(Throwable cause) {
		log.error("触发熔断了! {0} {1}", cause.getMessage(), cause);
		return new HttpApi() {
			@Override
			public Call<ResponseBody> siteverify(String secret, String response) {
				return null;
			}
		};
	}

}
