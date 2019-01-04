package io.github.xinyangpan.matcher.bo;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import com.google.common.base.Strings;

import io.github.xinyangpan.matcher.enums.Side;
import io.github.xinyangpan.matcher.util.MatchUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BookEntry {

	protected final BigDecimal price;
	protected final Deque<BookOrder> orders = new LinkedList<>();
	
	public BookEntry(BigDecimal price) {
		this.price = price;
	}

	public boolean isEmpty() {
		return orders.isEmpty();
	}

	public void take(PlaceOrder taker) {
		log.debug("Taking order {}", taker);
		Iterator<BookOrder> it = orders.iterator();
		while (it.hasNext()) {
			BookOrder bookOrder = it.next();
			taker.match(bookOrder);
			if (bookOrder.isDone()) {
				it.remove();
				BookOrder removed = MatchUtils.orderCache().removeById(bookOrder.getId());
				if (removed == null) {
					log.warn("Removed null. ref={}", bookOrder);
				}
			}
			if (taker.isCompleted()) {
				break;
			}
		}
		log.debug("Taking order over. this={}, taker={}", this, taker);
	}

	public void rest(BookOrder bookOrder) {
		log.debug("Resting order {}", bookOrder);
		orders.add(bookOrder);
		MatchUtils.orderCache().put(bookOrder);
		log.debug("Resting order over. this={}", this);
	}

	public void cancel(BookOrder bookOrder) {
		log.debug("Canceling order {}", bookOrder);
		boolean success = orders.remove(bookOrder);
		if (!success) {
			log.warn("BookOrder not found in orders. ref={}", bookOrder);
		}
		BookOrder removed = MatchUtils.orderCache().removeById(bookOrder.getId());
		if (removed == null) {
			log.warn("Removed null. ref={}", bookOrder);
		}
		log.debug("Canceling order over. this={}", this);
	}

	@Override
	public String toString() {
		BigDecimal totalQuantity = getTotalQuantity();
		return String.format("BookEntry [price=%s, totalQuantity=%s, orders=%s]", price, totalQuantity, orders);
	}

	private BigDecimal getTotalQuantity() {
		BigDecimal totalQuantity = BigDecimal.ZERO;
		for (BookOrder order : orders) {
			totalQuantity = totalQuantity.add(order.getRemainingQuantity());
		}
		return totalQuantity;
	}

	public String toOrderBoardStr(int priceLength, int qtyLength, Side side) {
		String priceStr = Strings.padStart(String.valueOf(this.price), priceLength, ' ');
		String qtyStr = Strings.padStart(String.valueOf(this.getTotalQuantity()), qtyLength, ' ');
		String space = Strings.padStart("", priceLength, ' ');
		switch (side) {
		case BUY:
			return String.format("%s %s %s", priceStr, qtyStr, space);
		case SELL:
			return String.format("%s %s %s", space, qtyStr, priceStr);
		default:
			throw new IllegalArgumentException(String.valueOf(side));
		}
	}
	
}
