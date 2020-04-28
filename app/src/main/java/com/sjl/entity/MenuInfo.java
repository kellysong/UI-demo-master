package com.sjl.entity;

import java.util.List;

/**
 * TODO
 *
 * @author Kelly
 * @version 1.0.0
 * @filename MenuInfo.java
 * @time 2018/7/18 15:44
 * @copyright(C) 2018 深圳市北辰德科技股份有限公司
 */
public class MenuInfo {
    private String header;
    private List<String> dataList;

    public String getHeader() {
        return header;
    }

    public List<String> getDataList() {
        return dataList;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }

    public static class ChildListBean {
        private String childName;
        private boolean isGroup;

        /**
         * 添加child
         *
         * @param childName
         */
        public ChildListBean(String childName) {
            this.childName = childName;
        }

        /**
         * 把group当成child
         *
         * @param childName
         * @param isGroup
         */
        public ChildListBean(String childName, boolean isGroup) {
            this.childName = childName;
            this.isGroup = isGroup;
        }

        public boolean isGroup() {
            return isGroup;
        }

        public void setGroup(boolean group) {
            isGroup = group;
        }

        public String getChildName() {
            return childName;
        }

        public void setChildName(String childName) {
            this.childName = childName;
        }

    }
}
