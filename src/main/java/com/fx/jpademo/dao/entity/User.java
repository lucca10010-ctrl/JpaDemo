package com.fx.jpademo.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private Short age;
    /**
     * 0:未知, 1:男, 2:女
     */
    private Short sex;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    // 一对多关系：一个用户有多个订单
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;
}
