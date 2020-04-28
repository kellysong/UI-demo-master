package com.sjl.entity;

import java.util.List;


public class ParentEntity {
    private int id;//id
    private String area_name;//影院名称
    private List<ChildEntity> cinemas;//子影院

    public ParentEntity() {
    }

    public ParentEntity(int id, String area_name, List<ChildEntity> cinemas) {
        this.id = id;
        this.area_name = area_name;
        this.cinemas = cinemas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public List<ChildEntity> getCinemas() {
        return cinemas;
    }

    public void setCinemas(List<ChildEntity> cinemas) {
        this.cinemas = cinemas;
    }
}
