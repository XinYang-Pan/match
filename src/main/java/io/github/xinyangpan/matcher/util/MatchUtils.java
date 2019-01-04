package io.github.xinyangpan.matcher.util;

import io.github.xinyangpan.matcher.cache.OrderCache;

public class MatchUtils {
	private static final OrderCache ORDER_CACHE = new OrderCache();

	public static int quantityScale() {
		return 2;
	}

	public static int amountScale() {
		return 2;
	}

	public static OrderCache orderCache() {
		return ORDER_CACHE;
	}

}
