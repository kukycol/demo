package com.ezm.common.example.design.factory;

public abstract class AbstractHumanFactory {

    //这里采用了泛型，对createHuman的输入参数产生两层限制
    //1.必须是Class类型
    //2.必须是Human的实现类
    public abstract <T extends Human> T createHuman(Class<T> c);

}
