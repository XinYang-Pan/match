package io.github.xinyangpan.matcher.vo;

import lombok.Data;

@Data
public class CancelRequest {

	private String symbol;
	private String clientOrderId;
	private Integer brokerId;
	private Long orderId;

}
