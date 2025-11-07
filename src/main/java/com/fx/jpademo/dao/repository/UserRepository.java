package com.fx.jpademo.dao.repository;

import com.fx.jpademo.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    // 方法2：自定义查询 - 查询用户及其订单
    // todo 会查出所有订单信息吗？
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.orders WHERE u.id = :userId")
    User findUserWithOrders(@Param("userId") Integer userId);

    // todo 会查出所有订单信息吗？
    User findUserByUsername(String userName);
}
