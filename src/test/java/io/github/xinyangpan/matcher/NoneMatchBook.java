package io.github.xinyangpan.matcher;

import static io.github.xinyangpan.matcher.OrderUtils.limit;

import io.github.xinyangpan.matcher.bo.OrderBook;
import io.github.xinyangpan.matcher.bo.OrderBookConfig;
import io.github.xinyangpan.matcher.enums.Side;

public class NoneMatchBook {
	//        1000  120.61
	//        1000  120.59
	//        1000  120.58
	// 120.57 1000        
	// 120.56 1000        
	// 120.55 1000        
	public static OrderBook bookSample1() {
		OrderBook orderBook = new OrderBook(new OrderBookConfig(2, 2));
		// ASK
		orderBook.place(limit(Side.SELL, 1000, 120.59));
		orderBook.place(limit(Side.SELL, 1000, 120.61));
		orderBook.place(limit(Side.SELL, 1000, 120.58));
		// BID
		orderBook.place(limit(Side.BUY, 1000, 120.57));
		orderBook.place(limit(Side.BUY, 1000, 120.55));
		orderBook.place(limit(Side.BUY, 1000, 120.56));
		return orderBook;
	}

}
