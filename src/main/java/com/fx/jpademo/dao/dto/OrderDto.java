package com.fx.jpademo.dao.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
public class OrderDto implements Serializable {
    private Long orderId;
    private String orderNo;
    private BigDecimal totalAmount;
    private Short orderStatus;
    private Short payStatus;
    private String payMethod;
    private String phone;
    private String address;
    private ZonedDateTime createdAt;
    private String username;

    public OrderDto(Long orderId, String orderNo, BigDecimal totalAmount, Short orderStatus, String username) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.username = username;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "orderId=" + orderId +
                ", orderNo='" + orderNo + '\'' +
                ", totalAmount=" + totalAmount +
                ", orderStatus=" + orderStatus +
                ", payStatus=" + payStatus +
                ", payMethod='" + payMethod + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", createdAt=" + createdAt +
                ", username='" + username + '\'' +
                '}';
    }
}
