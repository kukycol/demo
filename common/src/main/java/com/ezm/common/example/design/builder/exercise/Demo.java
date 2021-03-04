package com.ezm.common.example.design.builder.exercise;

public class Demo {


    public static void main(String[] args) {
        Demo01 demo01 = Demo01.builder().code(1212).msg("dededed").build();
        Demo01 demo011 = Demo01.builder().code(1212).msg("dededed").build();

        Demo02 demo02 = Demo02.builder().code(121221321).msg("测试").build();

        Demo01 demo012 = new Demo01("dede",324);
        Demo01 demo013 = new Demo01("dede",324);
        System.out.println(demo01);
        System.out.println(demo02);
    }
}
