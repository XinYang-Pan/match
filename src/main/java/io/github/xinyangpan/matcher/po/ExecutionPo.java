package io.github.xinyangpan.matcher.po;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ExecutionPo {
	
	private Long id;
	private BigDecimal price;
	private BigDecimal quantity;
	private BigDecimal amount;
	private long makerId;
	private long takerId;
	private Long executedTime;
	
}
