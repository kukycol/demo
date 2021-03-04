package com.ezm.common.exception;


/**
 * 异常响应类
 */
public class ExceptionResult {

    //属性
    private int code;

    private String msg;


    //get...set
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

   //构造
    public ExceptionResult() {
    }

    public ExceptionResult(ExceptionEnum e) {
        this.code = e.getCode();
        this.msg = e.getMsg();
    }

    public ExceptionResult(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

}
