package com.ezm.common.example.design.factory;

public class YellowHuman implements Human{


    @Override
    public void getColor() {
        System.out.println("黄色");
    }

    @Override
    public void talk() {
        System.out.println("我是黄种人");
    }
}
