package io.github.xinyangpan.matcher.bo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import io.github.xinyangpan.matcher.enums.OrderType;
import io.github.xinyangpan.matcher.enums.Side;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class PlaceOrder {

	private long id;
	private BigDecimal orderQuantity; // 可空, Market BUY 可空
	// worst price for Market Order
	private BigDecimal price; // Market 注文可空
	// for Market Order
	private BigDecimal amount; // Market BUY 可不空
	private Side side;
	private OrderType orderType;
	// 
	private String symbol;
	private String clientOrderId;
	private Integer brokerId;
	private Long clientId;
	// 
	private BigDecimal filledQuantity = BigDecimal.ZERO;
	private boolean completed;
	// Result
	private final List<Execution> executions = new ArrayList<>();
	private OrderBookConfig orderBookConfig;
	
	private BigDecimal getFillableQuantity(BigDecimal price) {
		if (side == Side.SELL || orderType == OrderType.LIMIT) {
			return orderQuantity.subtract(filledQuantity);
		}
		// MARKET-BUY
		if (amount == null) {
			return orderQuantity.subtract(filledQuantity);
		}
		BigDecimal maxQuantity = this.amount.divide(price, orderBookConfig.getQuantityScale(), RoundingMode.FLOOR);
		return maxQuantity.subtract(filledQuantity);
	}

	public boolean isMatchablePrice(@NonNull BigDecimal targetPrice) {
		if (this.price == null) {
			return true;
		}
		switch (side) {
		case BUY:
			return targetPrice.compareTo(this.price) <= 0;
		case SELL:
			return targetPrice.compareTo(this.price) >= 0;
		default:
			throw new IllegalArgumentException(side.name());
		}
	}

	public void match(BookOrder bookOrder) {
		@NonNull
		BigDecimal price = bookOrder.getPrice();
		BigDecimal makerQuantity = bookOrder.getRemainingQuantity();
		if (makerQuantity.compareTo(BigDecimal.ZERO) <= 0) {
			log.warn("makerQuantity is less or equal to 0. ref={}", bookOrder);
			return;
		}
		BigDecimal takerQuantity = this.getFillableQuantity(price);
		if (takerQuantity.compareTo(BigDecimal.ZERO) <= 0) {
			log.warn("takerQuantity is less or equal to 0. maker={}", bookOrder);
			return;
		}
		// Filling
		BigDecimal fillingQuantity = makerQuantity.min(takerQuantity);
		this.filledQuantity = this.filledQuantity.add(fillingQuantity);
		bookOrder.addFilledQuantity(fillingQuantity);
		BigDecimal executedAmount = orderBookConfig.calculateAmount(price, fillingQuantity);
		Execution execution = new Execution(price, fillingQuantity, executedAmount, bookOrder, this);
		executions.add(execution);
		// 
		takerQuantity = this.getFillableQuantity(price);
		if (takerQuantity.compareTo(BigDecimal.ZERO) <= 0) {
			log.info("takerQuantity is less or equal to 0. maker={}", bookOrder);
			this.completed = true;
			return;
		}
	}

	public BookOrder buildBookOrder() {
		Assert.state(this.orderType == OrderType.LIMIT, "Must be limit order.");
		Assert.state(!this.completed, "Shall be uncompleted.");
		Assert.state(orderQuantity.compareTo(filledQuantity) > 0, "orderQuantity should be greater than filledQuantity.");
		// 
		BookOrder bookOrder = new BookOrder(id, orderQuantity, price, side, orderType, filledQuantity);
		bookOrder.setSymbol(symbol);
		bookOrder.setClientId(clientId);
		bookOrder.setBrokerId(brokerId);
		bookOrder.setClientOrderId(clientOrderId);
		return bookOrder;
	}

}
