package io.github.xinyangpan.matcher.bo;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import io.github.xinyangpan.matcher.enums.Side;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class SideBook {
	private final Side side;
	private final NavigableMap<BigDecimal, BookEntry> entryMap;

	public SideBook(Side side) {
		this.side = side;
		switch (side) {
		case BUY:
			entryMap = new TreeMap<>(Comparator.reverseOrder());
			break;
		case SELL:
			entryMap = new TreeMap<>();
			break;
		default:
			throw new IllegalArgumentException(side.name());
		}
	}

	public void take(PlaceOrder taker) {
		Entry<BigDecimal, BookEntry> firstEntry = null;
		while ((firstEntry = entryMap.firstEntry()) != null) {
			BigDecimal price = firstEntry.getKey();
			BookEntry bookEntry = firstEntry.getValue();
			if (taker.isMatchablePrice(price)) {
				// 
				bookEntry.take(taker);
				if (bookEntry.isEmpty()) {
					entryMap.remove(price);
				}
				if (taker.isCompleted()) {
					return;
				}
			} else {
				break;
			}
		}
	}

	public void rest(BookOrder bookOrder) {
		BookEntry bookEntry = entryMap.get(bookOrder.getPrice());
		if (bookEntry == null) {
			bookEntry = new BookEntry(bookOrder.getPrice());
			entryMap.put(bookEntry.getPrice(), bookEntry);
		}
		bookEntry.rest(bookOrder);
	}

	public void cancel(BookOrder bookOrder) {
		BookEntry bookEntry = entryMap.get(bookOrder.getPrice());
		if (bookEntry == null) {
			log.warn("BookEntry not found for {}", bookOrder);
			return;
		}
		bookEntry.cancel(bookOrder);
		if (bookEntry.isEmpty()) {
			entryMap.remove(bookEntry.getPrice());
		}
	}

}
