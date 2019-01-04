package io.github.xinyangpan.matcher.util;

import java.util.HashMap;
import java.util.Map;

import io.github.xinyangpan.matcher.bo.BookOrder;
import io.github.xinyangpan.matcher.cache.OrderCache;

public class MatchUtils {
	private static final HashMap<Long, BookOrder> ORDER_INDEX = new HashMap<>();
	private static final OrderCache ORDER_CACHE = new OrderCache();

	public static int quantityScale() {
		return 2;
	}

	public static int amountScale() {
		return 2;
	}

	public static Map<Long, BookOrder> orderIndex() {
		return ORDER_INDEX;
	}

	public static OrderCache orderCache() {
		return ORDER_CACHE;
	}

}
