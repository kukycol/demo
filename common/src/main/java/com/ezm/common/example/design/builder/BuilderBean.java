package com.ezm.common.example.design.builder;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BuilderBean {

    //属性
    private Integer code;
    private String msg;

    //构造
    public BuilderBean() {
    }

    public BuilderBean(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

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

    //toString
    @Override
    public String toString() {
        return "BuilderBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

    public static class BuilderBeanBuilder{
        //属性
        private Integer code;
        private String msg;

        //赋值
        public BuilderBean.BuilderBeanBuilder code(final Integer code){
            this.code = code;
            return this;
        }
        public BuilderBean.BuilderBeanBuilder msg(final String msg){
            this.msg = msg;
            return this;
        }

        //构造
        public BuilderBeanBuilder() {
        }

        //build
        public BuilderBean build(){
            return new BuilderBean(this.code,this.msg);
        }

        @Override
        public String toString() {
            return "BuilderBeanBuilder{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }

    //builder
    public static BuilderBean.BuilderBeanBuilder builder(){
        return new BuilderBean.BuilderBeanBuilder();
    }

}
