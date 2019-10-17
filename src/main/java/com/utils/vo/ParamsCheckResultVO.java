package com.utils.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author yanxue
 * @Desc
 * @Date Created in 2019/8/9 11:13
 */
@Data
public class ParamsCheckResultVO<T> {
    @ApiModelProperty(value = "业务码", required = true, example = "1")
    private Integer businessCode;
    @ApiModelProperty(value = "返回信息", required = true, example = "success,error")
    private String msg;
    @ApiModelProperty(value = "返回数据", required = true, example = "page data...")
    private T data;
}
