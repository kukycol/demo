package com.ezm.common.example.design.factory;

public class BlackHuman implements Human{


    @Override
    public void getColor() {
        System.out.println("黑色");
    }

    @Override
    public void talk() {
        System.out.println("我是黑种人");
    }
}
