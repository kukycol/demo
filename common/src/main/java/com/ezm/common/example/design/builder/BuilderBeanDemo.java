package com.ezm.common.example.design.builder;

public class BuilderBeanDemo {

    /**
     * 测试:
     * 1.lombok方式
     * 2.手写方式
     * @param args
     */
    public static void main(String[] args) {
        LombokBuilderBean lombokBuild = LombokBuilderBean.builder().code(12).msg("deded").build();
        BuilderBean build = BuilderBean.builder().code(13).msg("deded").build();
        System.out.println(lombokBuild);
        System.out.println(build);
    }
}
