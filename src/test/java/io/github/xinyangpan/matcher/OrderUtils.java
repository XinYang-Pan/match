package io.github.xinyangpan.matcher;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import io.github.xinyangpan.matcher.bo.PlaceOrder;
import io.github.xinyangpan.matcher.enums.OrderType;
import io.github.xinyangpan.matcher.enums.Side;

public class OrderUtils {
	private static AtomicLong id = new AtomicLong(1);

	public static PlaceOrder limit(Side side, long quantity, double price) {
		PlaceOrder order = new PlaceOrder();
		order.setId(id.getAndIncrement());
		order.setOrderType(OrderType.LIMIT);
		order.setOrderQuantity(new BigDecimal(String.valueOf(quantity)));
		order.setPrice(new BigDecimal(String.valueOf(price)));
		order.setSide(side);
		return order;
	}

	public static PlaceOrder market(Side side, long quantity, double price) {
		PlaceOrder order = new PlaceOrder();
		order.setId(id.getAndIncrement());
		order.setOrderType(OrderType.MARKET);
		order.setOrderQuantity(new BigDecimal(String.valueOf(quantity)));
		order.setPrice(new BigDecimal(String.valueOf(price)));
		order.setSide(side);
		return order;
	}

	public static PlaceOrder market(Side side, long quantity) {
		PlaceOrder order = new PlaceOrder();
		order.setId(id.getAndIncrement());
		order.setOrderType(OrderType.MARKET);
		order.setOrderQuantity(new BigDecimal(String.valueOf(quantity)));
		order.setSide(side);
		return order;
	}

	public static PlaceOrder marketMargin(Side side, long margin) {
		PlaceOrder order = new PlaceOrder();
		order.setId(id.getAndIncrement());
		order.setOrderType(OrderType.MARKET);
		order.setAmount(new BigDecimal(String.valueOf(margin)));
		order.setSide(side);
		return order;
	}

}
