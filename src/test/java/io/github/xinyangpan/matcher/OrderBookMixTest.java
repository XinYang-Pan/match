package io.github.xinyangpan.matcher;

import static io.github.xinyangpan.matcher.OrderUtils.marketMargin;

import org.junit.Test;

import io.github.xinyangpan.matcher.bo.OrderBook;
import io.github.xinyangpan.matcher.bo.PlaceOrder;
import io.github.xinyangpan.matcher.enums.Side;

public class OrderBookMixTest {

	@Test
	public void several() {
		OrderBook orderBook = NoneMatchBook.bookSample1();
		// 
		System.out.println(orderBook.toOrderBoardStr());
		// 
//		PlaceOrder placeOrder = limit(Side.BUY, 1500, 120.58);
		PlaceOrder placeOrder = marketMargin(Side.BUY, 1500000);
		orderBook.place(placeOrder);
		System.out.println(orderBook.toOrderBoardStr());
		System.out.println(placeOrder);
		System.out.println(orderBook.getOrderBookConfig().getOrderCache());
		orderBook.cancel(6);
		System.out.println(orderBook.toOrderBoardStr());
	}

}
