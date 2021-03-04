package com.ezm.comom.help.validation;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

@Data
public class Validation {

    /**注意属性类型的默认值*/

    //不允许为空格、null、长度大于零
    @NotBlank
    private String notBlank;

    //不允许为null、长度大于零
    @NotEmpty
    private String notEmpty;

    //不允许为null
    @NotNull
    private String notNull;
    //以上3个注解是必填的参数,@NotNull可以是任何封装类型,@NotEmpty、@NotBlank只能是String类型

    //只允许为false
    @NotNull
    @AssertFalse
    private Boolean assertFalse;

    //只允许为true
    @NotNull
    @AssertTrue
    private Boolean assertTrue;

    //范围
    @NotNull
    @Range(min = 12,max = 121)
    private Integer range;

    //限制小数
    @NotNull
    @Digits(integer = 1,fraction = 2)
    private Double digits;

    //未来的时间
    @NotNull
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date future;

    //过去的时间
    @NotNull
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date past;

    //String
    @NotNull
    @Pattern(regexp = "^\\d*$")
    private String pattern;

    @Null
    private String anull;

    //最大值只能是整数
    @NotNull
    @Max(value = 12)
    private Double max;

    //最小值只能是整数
    @NotNull
    @Min(value = 12)
    private Integer min;

    //最大值可以是精确到小数
    @NotNull
    @DecimalMax(value = "121.8989")
    private Double decimalMax;

    //最小值可以是精确到小数
    @NotNull
    @DecimalMin(value = "121.54545")
    private Double decimalMin;
    //@Max、@Min、@DecimalMin、@DecimalMax允许String、Number,数值最大值和最小值

    //String字符串长度,Array集合、Collection集合、Map键值对的长度
    @NotNull
    @Size(max = 121,min = 20)
    private String asize;

    //只能是限制String类型的字符串长度
    @Length(max = 12,min = 12)
    private String length;

    //url
    @NotNull
    @URL(port = 8080,host = "127.0.0.1",protocol = "http")
    private String url;

    //邮箱地址
    @NotNull
    @Email(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")
    private String email;

}
