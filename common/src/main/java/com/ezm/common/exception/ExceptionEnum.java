package com.ezm.common.exception;


/**
 * 异常枚举
 */
public enum ExceptionEnum {

    //枚举
    ArithmeticException(1,"算术异常")
    ,ArrayIndexOutOfBoundsException (1,"数组下标越界异常")
    ,DisabledException (1,"账号禁用")
    ,BadCredentialsException (1,"账号或者密码错误")
    ,ClassCastException  (1,"类型转换异常")
    ,NullPointerException  (1,"空指针异常")
    ,NumberFormatException  (1,"字符串转换为数字异常")
    ,FileNotFoundException  (1,"文件未找到异常")
    ,RequestRejectedException  (1,"请求被拒绝，url不能包含恶意字符串")
    ,EOFException  (1,"文件已结束异常")
    ,IOException  (1,"输入输出异常")
    ,SQLException  (1,"操作数据库异常")
    ,NoSuchMethodException  (1,"方法未找到异常")
    ,BindException  (1,"字段不允许为空")
    ,UnexpectedRollbackException  (1,"数据异常")
    ,HttpMessageNotReadableException  (1,"数据类型不正确")
    ,RuntimeException  (1,"数据异常")
    ,HttpRequestMethodNotSupportedException  (1,"请求类型不对")
    ,TemplateInputException  (1,"模板错误")
    ,Exception  (1,"未知异常")
    ;

    //属性
    private int code;

    private String msg;

    //get..set
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

    //构造...
    ExceptionEnum() {
    }


    ExceptionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
