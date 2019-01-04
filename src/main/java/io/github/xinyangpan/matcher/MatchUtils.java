package io.github.xinyangpan.matcher;

import java.util.HashMap;
import java.util.Map;

import io.github.xinyangpan.matcher.bo.BookOrder;

public class MatchUtils {
	private static final HashMap<Long, BookOrder> ORDER_INDEX = new HashMap<>();

	public static int quantityScale() {
		return 2;
	}

	public static int amountScale() {
		return 2;
	}

	public static Map<Long, BookOrder> orderIndex() {
		return ORDER_INDEX;
	}

}
