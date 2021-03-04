package com.ezm.common.example.validation;

import lombok.Data;

import java.util.Date;

@Data
public class BasicData {


    //内置基本数据类型
    private int anInt = 0;

    private long aLong = 0;

    private byte aByte = 0;

    private short aShort = 0;

    private boolean aBoolean = false;

    private char aChar = '\u0000';

    private float aFloat = 0;

    private double aDouble = 0;

    //封装类型
    private Integer anInt2 = null;

    private Byte aByte2 = null;

    private Short aShort2 = null;

    private Long aLong2 = null;

    private Boolean aBoolean2 = null;

    private Character aChar2 = null;

    private Float aFloat2 = null;

    private Double aDouble2 = null;

    //其他数据类型
    private String string = null;

    private Date date = null;



}
