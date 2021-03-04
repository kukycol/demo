package com.ezm.common.example.design.builder.exercise;



public class Demo01 {

    private String msg;
    private Integer code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Demo01() {
    }

    public Demo01(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }

    @Override
    public String toString() {
        return "Demo01{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }

    public static Demo01.Demo01Builder builder(){
        return new Demo01.Demo01Builder();
    }

    public static class Demo01Builder{
        private String msg;
        private Integer code;

        public Demo01Builder() {
        }

        public Demo01.Demo01Builder msg(final String msg){
            this.msg = msg;
            return this;
        }

        public Demo01.Demo01Builder code(final Integer code){
            this.code = code;
            return this;
        }

        //build
        public Demo01 build(){
            return new Demo01(this.msg,this.code);
        }

        @Override
        public String toString() {
            return "Demo01Builder{" +
                    "msg='" + msg + '\'' +
                    ", code=" + code +
                    '}';
        }
    }


}
