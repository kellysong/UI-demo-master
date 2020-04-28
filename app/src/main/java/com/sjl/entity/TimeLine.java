package com.sjl.entity;

import java.io.Serializable;

/**
 * 时间轴实体
 * Created by song on 2017/8/26.
 */

public class TimeLine implements Serializable {
    private String desc;
    private String time;

    public TimeLine(String desc, String time) {
        this.desc = desc;
        this.time = time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
