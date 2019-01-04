package io.github.xinyangpan.matcher.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import io.github.xinyangpan.matcher.bo.OrderBook;
import io.github.xinyangpan.matcher.bo.OrderBookConfig;
import io.github.xinyangpan.matcher.bo.PlaceOrder;
import io.github.xinyangpan.matcher.dao.OrderDao;
import io.github.xinyangpan.matcher.po.OrderPo;
import io.github.xinyangpan.matcher.vo.CancelRequest;
import io.github.xinyangpan.matcher.vo.OrderRequest;

public class OrderService {
	@Autowired
	private OrderDao orderDao;
	// 
	private Map<String, OrderBook> symbolToOrderBook;

	@PostConstruct
	public void init() {
		symbolToOrderBook = new HashMap<>();
		OrderBook orderBook = new OrderBook(new OrderBookConfig(2, 2));
		symbolToOrderBook.put("btcusdt", orderBook);
	}

	public void placeOrder(OrderRequest orderRequest) {
		OrderPo orderPo = this.build(orderRequest);
		orderPo = orderDao.save(orderPo);
		OrderBook orderBook = symbolToOrderBook.get(orderPo.getSymbol());
		PlaceOrder placeOrder = this.build(orderPo);
		orderBook.place(placeOrder);
	}

	public void cancelOrder(CancelRequest cancelRequest) {
		String symbol = Objects.requireNonNull(cancelRequest.getSymbol());
		OrderBook orderBook = symbolToOrderBook.get(symbol);
		Long orderId = cancelRequest.getOrderId();
		if (orderId == null) {
			int brokerId = cancelRequest.getBrokerId();
			String clientOrderId = Objects.requireNonNull(cancelRequest.getClientOrderId());
			orderId = orderBook.getOrderBookConfig().getOrderCache().getId(brokerId, clientOrderId);
		}
		orderBook.cancel(orderId);
	}

	private OrderPo build(OrderRequest orderRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	private PlaceOrder build(OrderPo orderPo) {
		// TODO Auto-generated method stub
		return null;
	}

}
