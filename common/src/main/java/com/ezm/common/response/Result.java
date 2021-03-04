package com.ezm.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 请求响应类
 */
@ApiModel(description = "响应dto")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Result {


    //属性
    @ApiModelProperty(value = "标识码")
    private Integer code;

    @ApiModelProperty(value = "提示信息")
    private String msg;

    @ApiModelProperty(value = "响应数据")
    private Object data;

    @ApiModelProperty(value = "数据条数")
    private Long count;

    //get...set
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    //构造
    public Result() {
    }

    public Result(ResultEnum r, Object data, Long count) {
        this.code = r.getCode();
        this.msg = r.getMsg();
        this.data = data;
        this.count = count;
    }

    public Result(ResultEnum r, Object data) {
        this.code = r.getCode();
        this.msg = r.getMsg();
        this.data = data;
    }

    public Result(ResultEnum r) {
        this.code = r.getCode();
        this.msg = r.getMsg();
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
