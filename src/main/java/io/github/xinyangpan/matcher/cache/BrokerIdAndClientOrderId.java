package io.github.xinyangpan.matcher.cache;

import lombok.Data;

@Data
public class BrokerIdAndClientOrderId {
	private final int brokerId;
	private final String clientOrderId;
	
}
