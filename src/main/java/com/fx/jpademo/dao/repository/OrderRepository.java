package com.fx.jpademo.dao.repository;

import com.fx.jpademo.dao.dto.OrderDto;
import com.fx.jpademo.dao.dto.OrderDtoProjection;
import com.fx.jpademo.dao.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

//@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {
    Order findByOrderNo(String orderNo);


    // 方法2：查询用户的所有订单（关联查询）
    // todo 会查出所有订单信息吗？
    @Query("SELECT o FROM Order o JOIN FETCH o.user WHERE o.user.id = :userId")
    List<Order> findOrdersByUserIdWithUser(@Param("userId") Integer userId);

    // 方法3：查询订单详情（包含用户信息）
    // todo 会查出所有订单信息吗？
    @Query("SELECT o FROM Order o JOIN FETCH o.user WHERE o.id = :orderId")
    Optional<Order> findOrderWithUser(@Param("orderId") Long orderId);

    //    @Query("select o, u.username from Order o inner join User u on o.userId = u.id where o.userId = :userId")
    @Query("""
            select new com.fx.jpademo.dao.dto.OrderDto(o.id, o.orderNo, o.totalAmount, o.orderStatus ,u.username) 
            from Order o inner join  o.user u where o.user.id = :userId
            """)
    List<OrderDto> findOrderDtoByUserId(@Param("userId") Integer userId);


    // 只能写 List<Order> 不能写 List<OrderDto> , OrderDto 不是实体类, 没有 @Entity 注解
    // 也可以使用 List<OrderDtoProjection> 接口投影
    @Query(value = "select o.*, u.username, o.id order_id from t_order o join t_user u on o.user_id = u.id where u.id = :userId", nativeQuery = true)
    Page<OrderDtoProjection> findOrderDtoByUserId2(@Param("userId") Integer userId, Pageable page);

    // 万金油
    @Query(value = "select o.id, o.order_no, o.total_amount, o.order_status ,u.username from t_order o join t_user u on o.user_id = u.id where u.id = :userId", nativeQuery = true)
    Page<Map<String, Object>> findOrderDtoByUserId3(@Param("userId") Integer userId, Pageable page);

    Page<Order> findOrderByUserId(Integer userId, Pageable page);

}
