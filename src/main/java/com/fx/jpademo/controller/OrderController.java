package com.fx.jpademo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fx.jpademo.dao.dto.OrderDto;
import com.fx.jpademo.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    @Resource
    private OrderService orderService;

    @GetMapping("/getByUserId")
    List<OrderDto> findOrderDtoByUserId() throws JsonProcessingException {
        List<OrderDto> list = orderService.findOrderDtoByUserId(1);
        return list;
    }
}
