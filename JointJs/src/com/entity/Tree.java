package com.entity;

import java.util.List;

import oracle.net.aso.e;

public class Tree {
    private Integer level;
    private String id;
    private String pid;
    private Boolean border;
    private Integer border_x;
    private Integer border_y;
    private Integer border_width;
    private Integer border_height;
    private Integer self_x;
    private Integer self_y;
    private String bg_color;
    private Integer node;
    private String[] value;
    private String value0;
    private String value1;
    private String value2;
    private String value3;
    private String value4;
    private String value5;
    private String value6;
    private String value7;
    private String value8;
    private String value9;
    private String value10;
    private String value11;
    private String type;
    private List<Tree> Children;
    private String pName;
    
    
    public String getpName() {
        return pName;
    }
    public void setpName(String pName) {
        this.pName = pName;
    }
    public Tree() {
    }
    public Tree(String pid,Integer level,String v1) {
        this.pid = pid;
        this.level=level;
    }
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String[] getValue() {
        return value;
    }
    public void setValue(String[] value) {
        this.value = value;
    }
    public Integer getNode() {
        return node;
    }
    public void setNode(Integer node) {
        this.node = node;
    }
    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }
   
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPid() {
        return pid;
    }
    public void setPid(String pid) {
        this.pid = pid;
    }

    
    public Boolean getBorder() {
        return border;
    }
    public void setBorder(Boolean border) {
        this.border = border;
    }
   
    public Integer getBorder_x() {
        return border_x;
    }
    public void setBorder_x(Integer border_x) {
        this.border_x = border_x;
    }
    public Integer getBorder_y() {
        return border_y;
    }
    public void setBorder_y(Integer border_y) {
        this.border_y = border_y;
    }
    public Integer getBorder_width() {
        return border_width;
    }
    public void setBorder_width(Integer border_width) {
        this.border_width = border_width;
    }
    public Integer getBorder_height() {
        return border_height;
    }
    public void setBorder_height(Integer border_height) {
        this.border_height = border_height;
    }
    public Integer getSelf_x() {
        return self_x;
    }
    public void setSelf_x(Integer self_x) {
        this.self_x = self_x;
    }
    public Integer getSelf_y() {
        return self_y;
    }
    public void setSelf_y(Integer self_y) {
        this.self_y = self_y;
    }
    public String getBg_color() {
        return bg_color;
    }
    public void setBg_color(String bg_color) {
        this.bg_color = bg_color;
    }
    public List<Tree> getChildren() {
        return Children;
    }
    public void setChildren(List<Tree> children) {
        Children = children;
    }
    
    public String getValue0() {
        return value0;
    }
    public void setValue0(String value0) {
        this.value0 = value0;
    }
    public String getValue1() {
        return value1;
    }
    public void setValue1(String value1) {
        this.value1 = value1;
    }
    public String getValue2() {
        return value2;
    }
    public void setValue2(String value2) {
        this.value2 = value2;
    }
    public String getValue3() {
        return value3;
    }
    public void setValue3(String value3) {
        this.value3 = value3;
    }
    public String getValue4() {
        return value4;
    }
    public void setValue4(String value4) {
        this.value4 = value4;
    }
    public String getValue5() {
        return value5;
    }
    public void setValue5(String value5) {
        this.value5 = value5;
    }
    public String getValue6() {
        return value6;
    }
    public void setValue6(String value6) {
        this.value6 = value6;
    }
    public String getValue7() {
        return value7;
    }
    public void setValue7(String value7) {
        this.value7 = value7;
    }
    public String getValue8() {
        return value8;
    }
    public void setValue8(String value8) {
        this.value8 = value8;
    }
    public String getValue9() {
        return value9;
    }
    public void setValue9(String value9) {
        this.value9 = value9;
    }
    public String getValue10() {
        return value10;
    }
    public void setValue10(String value10) {
        this.value10 = value10;
    }
    public String getValue11() {
        return value11;
    }
    public void setValue11(String value11) {
        this.value11 = value11;
    }
    @Override
    public String toString() {
        return "Tree [level=" + level + ", id=" + id + ", pid=" + pid +",node="+node+ "]";
    }
    

}
