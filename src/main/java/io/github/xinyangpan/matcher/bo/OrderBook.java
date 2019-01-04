package io.github.xinyangpan.matcher.bo;

import java.math.BigDecimal;
import java.util.Map.Entry;

import io.github.xinyangpan.matcher.enums.OrderType;
import io.github.xinyangpan.matcher.enums.Side;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class OrderBook {
	private final OrderBookConfig orderBookConfig;
	private final SideBook sellBook; // ask
	private final SideBook buyBook; // bid

	public OrderBook(OrderBookConfig orderBookConfig) {
		this.orderBookConfig = orderBookConfig;
		this.sellBook = new SideBook(Side.SELL, orderBookConfig);
		this.buyBook = new SideBook(Side.BUY, orderBookConfig);
	}

	public void place(PlaceOrder placeOrder) {
		placeOrder.setOrderBookConfig(orderBookConfig);
		SideBook sideBook = this.otherSideBook(placeOrder.getSide());
		sideBook.take(placeOrder);
		if (placeOrder.isCompleted()) {
			return;
		}
		if (placeOrder.getOrderType() == OrderType.LIMIT) {
			// rest
			sideBook = this.sameSideBook(placeOrder.getSide());
			sideBook.rest(placeOrder.buildBookOrder());
		}
	}

	public BookOrder cancel(long orderId) {
		BookOrder bookOrder = orderBookConfig.getOrderCache().findById(orderId);
		if (bookOrder == null) {
			log.info("BookOrder not found for orderId={}", orderId);
			return null;
		}
		SideBook sideBook = this.sameSideBook(bookOrder.getSide());
		sideBook.cancel(bookOrder);
		return bookOrder;
	}

	private SideBook otherSideBook(Side side) {
		switch (side) {
		case SELL:
			return this.buyBook;
		case BUY:
			return this.sellBook;
		default:
			throw new IllegalArgumentException(side.name());
		}
	}

	private SideBook sameSideBook(Side side) {
		switch (side) {
		case SELL:
			return this.sellBook;
		case BUY:
			return this.buyBook;
		default:
			throw new IllegalArgumentException(side.name());
		}
	}

	public String toOrderBoardStr() {
		int priceLength = 7;
		int qtyLength = 8;
		StringBuilder sb = new StringBuilder();
		for (Entry<BigDecimal, BookEntry> e : sellBook.getEntryMap().entrySet()) {
			sb.insert(0, System.lineSeparator()).insert(0, e.getValue().toOrderBoardStr(priceLength, qtyLength, Side.SELL));
		}
		for (Entry<BigDecimal, BookEntry> e : buyBook.getEntryMap().entrySet()) {
			sb.append(e.getValue().toOrderBoardStr(priceLength, qtyLength, Side.BUY)).append(System.lineSeparator());
		}
		sb.insert(0, System.lineSeparator()).insert(0, "****** Order Board ******");
		sb.append("*************************");
		return sb.toString();
	}

}
