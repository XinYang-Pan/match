package io.github.xinyangpan.matcher.bo;

import java.math.BigDecimal;

import io.github.xinyangpan.matcher.enums.Side;
import lombok.Data;
import lombok.NonNull;

@Data
public class BookOrder {

	private final long id;
	private @NonNull BigDecimal orderQuantity;
	// worst price for Market Order
	private @NonNull BigDecimal price;
	private @NonNull Side side;
	private @NonNull BigDecimal filledQuantity;
	// 
	private String symbol;
	private String clientOrderId;
	private Integer brokerId;
	private Long clientId;

	public BookOrder(long id, @NonNull BigDecimal orderQuantity, @NonNull BigDecimal price, @NonNull Side side, @NonNull BigDecimal filledQuantity) {
		super();
		this.id = id;
		this.orderQuantity = orderQuantity;
		this.price = price;
		this.side = side;
		this.filledQuantity = filledQuantity;
	}
	
	public BigDecimal getRemainingQuantity() {
		return orderQuantity.subtract(filledQuantity);
	}

	public void addFilledQuantity(BigDecimal fillingQuantity) {
		this.filledQuantity = this.filledQuantity.add(fillingQuantity);
	}

	public boolean isDone() {
		return orderQuantity.compareTo(filledQuantity) == 0;
	}

}
