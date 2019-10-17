package com.utils.controller;

import com.utils.service.verify.VerifyCodeService;
import com.utils.vo.ParamsCheckResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/verify")
@Api("图片验证码")
@Slf4j
public class VerifyCodeController {

    @Autowired
    private VerifyCodeService verifyCodeService;

    /**
     * 获取验证码图片和文本
     */
    @GetMapping("/getVerifyCodeImg")
    @ApiOperation("获取图片验证码")
    public void getVerifyCodeImg(HttpServletRequest request, HttpServletResponse response,
                                 @ApiParam("标识符") @RequestParam("identifier") String identifier) {
        verifyCodeService.getVerifyCodeImg(response, identifier);
    }

    /**
     * 验证图片验证码
     */
    @GetMapping("/validateGraphVerifyCode")
    @ApiOperation("验证图片验证码")
    public ParamsCheckResultVO validateGraphVerifyCode(
            @ApiParam("标识符") @RequestParam(value = "identifier") String identifier,
            @ApiParam("验证码") @RequestParam(value = "verifyCode") String verifyCode) {
        return verifyCodeService.validateGraphVerifyCode(identifier, verifyCode);
    }
}
