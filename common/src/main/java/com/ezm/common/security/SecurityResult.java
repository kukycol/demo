package com.ezm.common.security;


import com.ezm.common.response.ResultEnum;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class SecurityResult implements Serializable {


    private static final long serialVersionUID = 1725159680599612404L;

    //成功返回特定的状态码和信息
    public final static  Map<String, Object> result(ResultEnum respCode,String url) {
        return getStringObjectMap(respCode,url);
    }

    //成功返回特定的状态码和信息
    public final static  Map<String, Object> result(ResultEnum respCode) {
        return getStringObjectMap(respCode);
    }


    private static Map<String, Object> getStringObjectMap(ResultEnum respCode,String url) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", respCode.getCode());
        map.put("msg", respCode.getMsg());
        map.put("url", url);
        return map;
    }


    private static Map<String, Object> getStringObjectMap(ResultEnum respCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", respCode.getCode());
        map.put("msg", respCode.getMsg());
        return map;
    }





}
