package com.ezm.common.example.design.builder;


import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
public class LombokBuilderBean {

    //属性
    private Integer code;

    private String msg;

    //构造
    @Tolerate
    public LombokBuilderBean() {
    }


}
