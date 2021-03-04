package com.ezm.example1;

public class Demo01 {

    public static void main(String[] args) {
        test01();
    }


    /**
     * 测试除数后的余数是否省略
     */
    public static void test01(){
        int a = 10;
        int b = 3;
        int c = a / b;
        System.out.println(c);
    }
}
