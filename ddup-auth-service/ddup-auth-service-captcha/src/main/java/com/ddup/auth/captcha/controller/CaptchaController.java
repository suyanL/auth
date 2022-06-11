package com.ddup.auth.captcha.controller;

import com.ddup.auth.service.base.JsonResult;
import com.ddup.auth.user.dto.UserDto;
import com.ddup.auth.user.service.IUserInfoService;
import com.pig4cloud.captcha.GifCaptcha;
import com.pig4cloud.captcha.SpecCaptcha;
import com.pig4cloud.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.Font;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * @author suancyg
 */
@Slf4j
@RestController
@RequestMapping("/v1/auth")
public class CaptchaController {

    private static final Map<String, String> ATTR = new ConcurrentHashMap<>();

    private static final String KEY_CAPTCHA = "captcha";

    @DubboReference(group = "ddup-auth-user", version = "v1.0.0")
    private IUserInfoService userInfoService;

    @RequestMapping(value = "/code", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getCode() {
        return ATTR;
    }

    /**
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public void captcha(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        // 三个参数分别为宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        // 设置字体
        // 有默认字体，可以不用设置
        specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));
        // 设置类型，纯数字、纯字母、字母数字混合
        specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);

        // 验证码存入session
        // request.getSession().setAttribute("captcha", specCaptcha.text().toLowerCase());
        ATTR.put(KEY_CAPTCHA, specCaptcha.text().toLowerCase());
        UserDto demo = userInfoService.getUserByUserName("demo");
        log.info("用户信息打印，测试接口使用：{}", demo);
        // 输出图片流
        specCaptcha.out(response.getOutputStream());
    }

    @RequestMapping(value = "/captcha/gif", method = RequestMethod.GET)
    public void gifCaptcha(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        // 三个参数分别为宽、高、位数
        GifCaptcha gifCaptcha = new GifCaptcha(130, 48, 5);
        // 设置字体
        // 有默认字体，可以不用设置
        gifCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));
        // 设置类型，纯数字、纯字母、字母数字混合
        gifCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        // 验证码存入session
        // request.getSession().setAttribute("captcha", specCaptcha.text().toLowerCase());
        ATTR.put(KEY_CAPTCHA, gifCaptcha.text().toLowerCase());

        // 输出图片流
        gifCaptcha.out(response.getOutputStream());
    }

    @RequestMapping(value = "/captcha/gif/json", method = RequestMethod.GET)
    public JsonResult gifCaptchaJson(HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        GifCaptcha gifCaptcha = new GifCaptcha(130, 48, 5);

        gifCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));

        gifCaptcha.setCharType(Captcha.TYPE_NUM_AND_UPPER);

        String verCode = gifCaptcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
        ATTR.put("gif_captcha", verCode);
        // 存入redis并设置过期时间为30分钟
        // redisUtil.setEx(key, verCode, 30, TimeUnit.MINUTES);
        // 将key和base64返回给前端
        return JsonResult.success(gifCaptcha.toBase64()).put("key", key).put("code", verCode);
    }

}
