package io.github.xinyangpan.matcher.bo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import io.github.xinyangpan.matcher.util.MatchUtils;
import lombok.Data;

@Data
public class Execution {
	private final BigDecimal price;
	private final BigDecimal quantity;
	private final BigDecimal amount;
	private final long makerId;
	private final long takerId;
	private final BookOrder makerOrder;
	private final PlaceOrder takerOrder;

	public Execution(BigDecimal price, BigDecimal quantity, BookOrder makerOrder, PlaceOrder takerOrder) {
		this.price = price;
		this.quantity = quantity;
		this.amount = this.price.multiply(this.quantity).setScale(MatchUtils.amountScale(), RoundingMode.DOWN);
		this.makerOrder = Objects.requireNonNull(makerOrder);
		this.takerOrder = Objects.requireNonNull(takerOrder);
		this.makerId = makerOrder.getId();
		this.takerId = takerOrder.getId();
	}

	@Override
	public String toString() {
		return String.format("Execution [price=%s, quantity=%s, amount=%s, makerId=%s, takerId=%s]", price, quantity, amount, makerId, takerId);
	}

}
