package com.sjl.ui.gridviewpage;

/**
 * TODO
 *
 * @author Kelly
 * @version 1.0.0
 * @filename ProductListBean.java
 * @time 2018/5/22 10:37
 * @copyright(C) 2018 深圳市北辰德科技股份有限公司
 */
public class ProductListBean {
    private String proName;
    private int imgUrl;

    public ProductListBean(String proName, int imgUrl) {
        this.proName = proName;
        this.imgUrl = imgUrl;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }
}
