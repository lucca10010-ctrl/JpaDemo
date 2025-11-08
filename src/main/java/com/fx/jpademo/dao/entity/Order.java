package com.fx.jpademo.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "t_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNo;
//    private Integer userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
//    @JsonIgnore // 去掉循环引用，避免栈溢出
    private User user;
    private BigDecimal totalAmount;
    /**
     * '订单状态: 0-待支付, 1-已支付, 2-已发货, 3-已收货, 4-已完成, 5-已取消, 9-已关闭';
     */
    private Short orderStatus;
    /**
     * '支付状态: 0-未支付, 1-支付中, 2-已支付';
     */
    private Short payStatus;
    private Short payMethod;
    /**
     * '支付方式: 1-支付宝, 2-微信, 3-银行卡, 4-现金';
     */
    private ZonedDateTime payTime;
    /**
     * '发货状态: 0-未发货, 1-已发货, 2-运输中, 3-已签收';
     */
    private Short deliveryStatus;
    private String phone;
    private String address;
    private String remark;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", user=" + user +
                ", totalAmount=" + totalAmount +
                ", orderStatus=" + orderStatus +
                ", payStatus=" + payStatus +
                ", payMethod=" + payMethod +
                ", payTime=" + payTime +
                ", deliveryStatus=" + deliveryStatus +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", remark='" + remark + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
