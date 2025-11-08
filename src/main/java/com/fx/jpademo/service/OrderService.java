package com.fx.jpademo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fx.jpademo.dao.dto.OrderDto;
import com.fx.jpademo.dao.dto.OrderDtoProjection;
import com.fx.jpademo.dao.entity.Order;
import com.fx.jpademo.dao.entity.User;
import com.fx.jpademo.dao.repository.OrderRepository;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Resource
    private ObjectMapper mapper;
    @Resource
    private OrderRepository orderRepository;

    /**
     * 有时候 lombok 失效，(去掉maven 编译插件就行了)
     * 有时候 栈溢出 （转json或者toString 互相引用的原因）
     *
     * @param userId
     * @return
     */
    public List<OrderDto> findOrderDtoByUserId(Integer userId) throws JsonProcessingException {
        return orderRepository.findOrderDtoByUserId(userId);
    }


    /**
     * 通过接口映射方式 (原生sql)
     *
     * @param userId
     * @return
     */
    public PagedModel<OrderDtoProjection> findOrderDtoByUserIdProjection(Integer userId) throws JsonProcessingException {
        PageRequest pageReq = PageRequest.of(0, 5, Sort.by("user_id"));
        Page<OrderDtoProjection> page = orderRepository.findOrderDtoByUserId2(userId, pageReq);
        System.out.println("接口投影");
        System.out.println(mapper.writeValueAsString(page));
        return new PagedModel<>(page);
    }

    /**
     * 使用 map方式 (原生sql)
     *
     * @param userId
     * @return
     * @throws JsonProcessingException
     */
    public PagedModel<OrderDto> findOrderDtoByUserIdCommon(Integer userId) throws JsonProcessingException {
        PageRequest pageReq = PageRequest.of(0, 5, Sort.by("user_id"));
        Page<Map<String, Object>> page = orderRepository.findOrderDtoByUserId3(userId, pageReq);
        System.out.println("map 方式");
        System.out.println(mapper.writeValueAsString(page));
        List<OrderDto> list = page.getContent().stream().map(OrderDto::toOrderDto4Jpa).toList();
        PageImpl newPage = new PageImpl<>(list, pageReq, page.getTotalElements());
        return new PagedModel<>(newPage);
    }


    /**
     * 单表动态条件查询
     *
     * @param minAmount
     * @param addr
     * @return
     */
    public PagedModel<Order> dynamicQueryOneTable(String minAmount, String addr) {
        PageRequest page = PageRequest.of(0, 5, Sort.by("totalAmount"));
        Specification<Order> specOrder = (root, query, builder) -> {
            Predicate predicate = builder.conjunction();
            if (StringUtils.hasText(minAmount)) {
                predicate = builder.and(predicate, builder.greaterThan(root.get("totalAmount"), minAmount));
            }
            if (StringUtils.hasText(addr)) {
                predicate = builder.and(predicate, builder.equal(root.get("address"), addr));
            }
            return predicate;
        };
        Page<Order> orders = orderRepository.findAll(specOrder, page);
        return new PagedModel<>(orders);
    }

    public PagedModel<OrderDto> dynamicQueryMultiTable(String username, String minAmount, String addr) {
        PageRequest page = PageRequest.of(0, 5, Sort.by("totalAmount"));
        // Specification<T> 中的泛型 T 必须与 Repository 的实体类型一致：
        Specification<Order> specOrder = (root, query, builder) -> {
            //  root.join 的第一个参数是 实体类中的关联属性名
            Join<Order, User> userJoin = root.join("user", JoinType.INNER);
            Predicate predicate = builder.conjunction();
            if (StringUtils.hasText(minAmount)) {
                predicate = builder.and(predicate, builder.greaterThan(root.get("totalAmount"), minAmount));
            }
            if (StringUtils.hasText(addr)) {
                predicate = builder.and(predicate, builder.equal(root.get("address"), addr));
            }
            if (StringUtils.hasText(username)) {
                predicate = builder.and(predicate, builder.equal(userJoin.get("username"), username));
            }
            return predicate;
        };
        Page<Order> orders = orderRepository.findAll(specOrder, page);
        List<OrderDto> list = orders.getContent().stream().map(e -> new OrderDto(e.getId(), e.getOrderNo(),
                e.getTotalAmount(), e.getOrderStatus(), e.getUser().getUsername())).toList();
        return new PagedModel<>(new PageImpl<>(list, page, orders.getTotalElements()));
    }

    public PagedModel<OrderDto> dynamicQueryMultiTable2(String username, String minAmount, String addr) {
        PageRequest page = PageRequest.of(0, 5, Sort.by("totalAmount"));
        // Specification<T> 中的泛型 T 必须与 Repository 的实体类型一致：
        Specification<Order> specOrder = (root, query, builder) -> {
            ArrayList<Predicate> list = new ArrayList<>();
            //  root.join 的第一个参数是 实体类中的关联属性名
            Join<Order, User> userJoin = root.join("user", JoinType.INNER);
            if (StringUtils.hasText(minAmount)) {
                list.add(builder.greaterThan(root.get("totalAmount"), minAmount));
            }
            if (StringUtils.hasText(addr)) {
                list.add(builder.equal(root.get("address"), addr));
            }
            if (StringUtils.hasText(username)) {
                list.add(builder.equal(userJoin.get("username"), username));
            }
            return builder.and(list.toArray(new Predicate[0]));
//            return query.where(list.toArray(new Predicate[0])).getRestriction(); // 会直接将条件应用到查询对象上
        };
        Page<Order> orders = orderRepository.findAll(specOrder, page);
        List<OrderDto> list = orders.getContent().stream().map(e -> new OrderDto(e.getId(), e.getOrderNo(),
                e.getTotalAmount(), e.getOrderStatus(), e.getUser().getUsername())).toList();
        return new PagedModel<>(new PageImpl<>(list, page, orders.getTotalElements()));
    }

}
