package com.sjl.entity;


public class ChildEntity {
    /**
     * id : 1
     * cinema_name : DMC昌平保利影剧院
     * cinema_address : 昌平区鼓楼南街佳莲时代广场4楼
     * area_id : 117
     * area_name : 昌平区
     * logo : http://img.komovie.cn/cinema/14205106672446.jpg
     */

    private String id;
    private String cinema_name;
    private String cinema_address;
    private int area_id;
    private String area_name;
    private String logo;

    public ChildEntity() {
    }

    public ChildEntity(String id, String cinema_name, String cinema_address, int area_id, String area_name, String logo) {
        this.id = id;
        this.cinema_name = cinema_name;
        this.cinema_address = cinema_address;
        this.area_id = area_id;
        this.area_name = area_name;
        this.logo = logo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCinema_name() {
        return cinema_name;
    }

    public void setCinema_name(String cinema_name) {
        this.cinema_name = cinema_name;
    }

    public String getCinema_address() {
        return cinema_address;
    }

    public void setCinema_address(String cinema_address) {
        this.cinema_address = cinema_address;
    }

    public int getArea_id() {
        return area_id;
    }

    public void setArea_id(int area_id) {
        this.area_id = area_id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

}
