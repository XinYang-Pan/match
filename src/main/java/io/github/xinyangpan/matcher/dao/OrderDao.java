package io.github.xinyangpan.matcher.dao;

import org.springframework.data.repository.CrudRepository;

import io.github.xinyangpan.matcher.po.OrderPo;

public interface OrderDao extends CrudRepository<OrderPo, Long> {

}
