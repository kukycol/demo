package com.ezm.entity.bean;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;

@Data
@Builder
public class MenuTreeBean {

    private int id;

    private String title;

    private String field = "ruleName";

    private String rule;

    private boolean checked =false;

    private List<MenuTreeBean> children;

    @Tolerate
    public MenuTreeBean() {
    }
}
