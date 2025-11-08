package com.fx.jpademo.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private List<Order> orders;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
//                ", orders=" + orders +                   // 去掉循环打印，避免栈溢出
                '}';
    }
}
