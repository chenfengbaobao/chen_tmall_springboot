package com.how2java.chen.tmall.configure;

import java.util.Objects;

/**
 * 订单状态枚举类
 *
 * @Author : haifeng.wu
 * @Date : 2020-07-26 15:31:51
 */

public enum OrderStatusEnum {

    /**
     * 待付
     */
    WAIT_PAY("waitPay", "待付"),


    /**
     * 待发
     */
    WAIT_DELIVERY("waitDelivery", "待发"),


    /**
     * 待收
     */
    WAIT_CONFIRM("waitConfirm", "待收"),

    /**
     * 等评
     */
    WAIT_REVIEW("waitReview", "等评"),


    /**
     * 完成
     */
    FINISH("finish", "完成"),

    /**
     * 删除
     */
    DELETE("delete", "删除"),

    /**
     * 未知
     */
    UNKNOWN("unknown", "未知");


    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 订单状态描述
     */
    private String describe;


    OrderStatusEnum(String orderStatus, String describe) {
        this.orderStatus = orderStatus;
        this.describe = describe;
    }

    /**
     * 获取订单状态
     *
     * @param orderStatus
     * @return
     */
    public static String getOrderStatus(String orderStatus) {

        for (OrderStatusEnum value : OrderStatusEnum.values()) {
            if (Objects.equals(orderStatus, value.getOrderStatus())) {
                return value.getOrderStatus();
            }
        }

        return "未知订单状态";

    }


    /**
     * 根据订单状态获取订单状态描述
     *
     * @param orderStatus
     * @return
     */
    public static String getOrderStatusDesc(String orderStatus) {

        for (OrderStatusEnum value : OrderStatusEnum.values()) {
            if (Objects.equals(orderStatus, value.getOrderStatus())) {
                return value.getDescribe();
            }
        }

        return "未知";

    }


    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public static void main(String[] args) {


        String orderStatus = getOrderStatus("11");
        String desc = getOrderStatusDesc("11");
        String orderStatus1 = getOrderStatus("waitConfirm");
        String desc1 = getOrderStatusDesc("waitConfirm");


        System.out.println(desc);
        System.out.println(desc1);
        System.out.println(orderStatus);
        System.out.println(orderStatus1);


    }
}
