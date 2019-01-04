package io.github.xinyangpan.matcher.po;

import java.math.BigDecimal;

import io.github.xinyangpan.matcher.enums.OrderType;
import io.github.xinyangpan.matcher.enums.Side;
import lombok.Data;

@Data
public class OrderPo {
	
	private Long id;
	private BigDecimal orderQuantity;
	private BigDecimal price;
	private BigDecimal amount;
	private Side side;
	private OrderType orderType;
	// 
	private String symbol;
	private String clientOrderId;
	private Integer brokerId;
	private Long clientId;
	// 
	private BigDecimal filledQuantity;
	private Long orderTime;
	
}
