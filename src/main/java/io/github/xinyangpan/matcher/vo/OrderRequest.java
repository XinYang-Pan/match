package io.github.xinyangpan.matcher.vo;

import java.math.BigDecimal;

import io.github.xinyangpan.matcher.enums.OrderType;
import io.github.xinyangpan.matcher.enums.Side;
import lombok.Data;

@Data
public class OrderRequest {

	private String symbol;
	private String clientOrderId;
	private Side side;
	private OrderType type;
	private BigDecimal quantity;
	private BigDecimal amount;
	private BigDecimal price;
	// 
	private Integer brokerId;
	private Long clientId;

}
