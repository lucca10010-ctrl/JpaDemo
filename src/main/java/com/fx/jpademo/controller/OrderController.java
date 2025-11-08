package com.fx.jpademo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fx.jpademo.dao.dto.OrderDto;
import com.fx.jpademo.service.OrderService;
import jakarta.annotation.Resource;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /**
     * 动态查询多表数据
     *
     * @param username 用户名
     * @param minAmount 最小金额
     * @param addr 地址
     * @return 分页的订单数据
     */
    @GetMapping("/orders/dynamicQuery")
    public PagedModel<OrderDto> dynamicQueryMultiTable2(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String minAmount,
            @RequestParam(required = false) String addr) {
        return orderService.dynamicQueryMultiTable2(username, minAmount, addr);
    }
}
