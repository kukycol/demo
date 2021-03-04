package com.ezm.common.example.design.factory;

public class HumanFactory extends AbstractHumanFactory{


    @Override
    public <T extends Human> T createHuman(Class<T> c) {
        Human human=null;
        try {
            human = (T) Class.forName(c.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("人种不存在");
        }
        return (T)human;
    }


}
