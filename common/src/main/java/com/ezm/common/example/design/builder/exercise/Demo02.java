package com.ezm.common.example.design.builder.exercise;

/**
 * 1、对象
 * 1.1、属性
 * 1.2、get...set
 * 1.3、构造
 * 1.4、toString
 * 2、builder
 * 3、build
 * 3.1、属性
 * 3.2、赋值
 * 3.3、空构造
 * 3.4、build
 */
public class Demo02 {

    //对象
    private Integer code;
    private String msg;

    public Demo02() {
    }

    public Demo02(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

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

    @Override
    public String toString() {
        return "Demo02{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    //builder
    public static Demo02.Demo02Builder builder(){
        return new Demo02.Demo02Builder();
    }


    //build
    public static class Demo02Builder{
        private Integer code;
        private String msg;

        public Demo02Builder() {
        }

        public  Demo02.Demo02Builder code(final Integer code){
            this.code = code;
            return this;
        }


        public  Demo02.Demo02Builder msg(final String msg){
            this.msg = msg;
            return this;
        }

        public  Demo02 build(){
            return new Demo02(this.code,this.msg);
        }

        @Override
        public String toString() {
            return "Demo02Builder{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }





}
