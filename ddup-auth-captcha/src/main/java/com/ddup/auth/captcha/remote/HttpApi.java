package com.ddup.auth.captcha.remote;

import com.ddup.auth.captcha.ext.Sign;
import com.ddup.auth.captcha.remote.fallback.HttpApiFallback;
import com.ddup.auth.captcha.remote.fallback.HttpDegradeFallbackFactory;
import com.github.lianjiatech.retrofit.spring.boot.core.OkHttpClientBuilder;
import com.github.lianjiatech.retrofit.spring.boot.core.RetrofitClient;
import com.github.lianjiatech.retrofit.spring.boot.degrade.sentinel.SentinelDegrade;
import com.github.lianjiatech.retrofit.spring.boot.log.LogStrategy;
import com.github.lianjiatech.retrofit.spring.boot.log.Logging;
import com.github.lianjiatech.retrofit.spring.boot.retry.Retry;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.apache.http.conn.HttpInetSocketAddress;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * v2.2.0 增加熔断器
 * v2.2.14 未配置baseUrl并且未配置serviceId忽视该接口注册(baseurl 和 serviceId 必选一个)
 * v2.2.15 ========> 2.1.13和2.2.14对于配置中心有bug，回退到2.2.12版本或升级到2.2.17
 * v2.2.16 默认关闭全局请求重试
 * v2.2.17 修复RetrofitClient在Apollo配置中心出现的bug(其中@RetrofitClient去掉@service注解)
 * <p>
 * <p>
 * https://github.com/LianjiaTech/retrofit-spring-boot-starter#%E7%86%94%E6%96%AD%E9%99%8D%E7%BA%A7
 *
 * @author wangyuxi
 * @date 2020/08/24
 **/

/*
 *
 *   拦截器顺序为标记的顺序，其中全局过滤器排在最后
 *
 *
 */

//@Sign
///*2.2.2  @Intercept支持多拦截器配置*/
//@Intercept(handler = TimeStampInterceptor.class, include = {"/api/**"})
//@Intercept(handler = AnotherTimeStampInterceptor.class, include = {"/api/**"})

/**
 * 请求重试 retrofit-spring-boot-starter支持请求重试功能，只需要在接口或者方法上加上@Retry注解即可。
 *
 * @author suancyg
 * @Retry支持重试次数maxRetries、重试时间间隔intervalMs以及重试规则retryRules配置。重试规则支持三种配置：
 *
 * RESPONSE_STATUS_NOT_2XX：响应状态码不是2xx时执行重试； OCCUR_IO_EXCEPTION：发生IO异常时执行重试；
 * OCCUR_EXCEPTION：发生任意异常时执行重试；
 * 默认响应状态码不是2xx或者发生IO异常时自动进行重试。需要的话，你也可以继承BaseRetryInterceptor实现自己的请求重试拦截器，然后将其配置上去。
 *
 *
 * logStrategy = LogStrategy.BODY 把请求第三方接口的收到的返回值,打印出日志
 *
 */
@RetrofitClient(baseUrl = "${test.baseUrl}", fallback = HttpApiFallback.class,
		fallbackFactory = HttpDegradeFallbackFactory.class)
@Sign(accessKeyId = "${test.accessKeyId}", accessKeySecret = "${test.accessKeySecret}")
@Logging(logStrategy = LogStrategy.BODY)
/* 默认策略情况下，每5s平均响应时间不得超过500ms，否则启用熔断降级 */
@SentinelDegrade(timeWindow = 20, count = 10000)
public interface HttpApi {

	/**
	 * 构建出的这个方法一定一定要加 @OkHttpClientBuilder 这个注解
	 * @return
	 */
	@OkHttpClientBuilder
	static OkHttpClient.Builder okhttpClientBuilder() {
		return new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
				.readTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS);

	}

	@POST("/recaptcha/api/siteverify")
	Call<ResponseBody> siteverify(@Query("secret") String secret,
			@Query("response") String response);

}
