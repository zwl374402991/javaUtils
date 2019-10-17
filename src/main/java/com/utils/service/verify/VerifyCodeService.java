package com.utils.service.verify;

import com.utils.utils.RedisUtil;
import com.utils.utils.VerifyCodeUtil;
import com.utils.vo.ParamsCheckResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 图片验证码服务
 * @author archerzhang
 * @date 2019.10.17
 */
@Slf4j
@Service
public class VerifyCodeService {

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 生成图片验证码
     * @param response
     * @param identifier
     */
    public void getVerifyCodeImg(HttpServletResponse response, String identifier) {
        // 设置相应类型，告诉浏览器输入的类容为图片
        response.setContentType("image/jpeg");
        // 设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        String verifyCode = VerifyCodeUtil.generateVerifyCode(4, VerifyCodeUtil.VERIFY_CODES);
        if (redisUtil.existsKey(identifier)) {
            redisUtil.set(identifier, verifyCode, 600L, TimeUnit.SECONDS);
        } else {
            redisUtil.addKey(identifier, verifyCode, 600, TimeUnit.SECONDS);
        }
        log.info("本次生成的图片验证码已放入缓存中，验证码为:" + verifyCode);
        log.info("缓存的键值对为：" + redisUtil.getValue(identifier) + " 过期时间为：" + redisUtil.getExpire(identifier, TimeUnit.SECONDS)
                + TimeUnit.SECONDS.toString());
        try {
            VerifyCodeUtil.outputImage(214, 80, response.getOutputStream(), verifyCode);
        } catch (IOException e) {
            log.error("IOException: {}", e);
        }
    }

    /**
     * 验证图片验证码
     * @param identifier
     * @param verifyCode
     * @return
     */
    public ParamsCheckResultVO validateGraphVerifyCode(String identifier, String verifyCode) {
        ParamsCheckResultVO vo = new ParamsCheckResultVO();
        String cachedGraphVerifyCode = (String) redisUtil.getValue(identifier);
        if (StringUtils.isEmpty(cachedGraphVerifyCode)) {
            vo.setMsg("验证码已过期");
            vo.setBusinessCode(500);
            return vo;
        } else {
            if (!verifyCode.equalsIgnoreCase(cachedGraphVerifyCode)) {
                vo.setMsg("验证码错误");
                vo.setBusinessCode(500);
                return vo;
            }
        }
        vo.setMsg("验证码正确");
        vo.setBusinessCode(200);
        return vo;
    }

}
