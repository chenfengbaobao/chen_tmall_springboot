package com.how2java.chen.tmall.configure;

/**
 * 图片类型枚举类
 *
 * @Author : haifeng.wu
 * @Date : 2020-07-25 17:30:24
 */

public enum PictureEnum {

    /**
     * 单个图片
     */
    TYPE_SINGLE("single", "单个图片"),


    /**
     * 图片详情
     */
    TYPE_DETAIL("detail", "图片详情");

    private String key;
    private String describe;


    PictureEnum(String key, String describe) {
        this.key = key;
        this.describe = describe;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

}
