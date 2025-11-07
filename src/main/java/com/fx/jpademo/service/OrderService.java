package com.fx.jpademo.service;

import com.fx.jpademo.dao.dto.OrderDto;
import com.fx.jpademo.dao.entity.Order;
import com.fx.jpademo.dao.repository.OrderRepository;
import jakarta.annotation.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Resource
    private OrderRepository orderRepository;

    public List<OrderDto> findOrderDtoByUserId(Integer userId) {
        PageRequest page = PageRequest.of(0, 5);
        List<Order> orderByUserId = orderRepository.findOrderByUserId(userId, page);
        System.out.println(orderByUserId);

        return orderRepository.findOrderDtoByUserId(userId);
//        return List.of();
    }

}
