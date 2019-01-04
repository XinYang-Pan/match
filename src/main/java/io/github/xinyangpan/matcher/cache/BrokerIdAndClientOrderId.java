package io.github.xinyangpan.matcher.cache;

import lombok.Data;

@Data
public class BrokerIdAndClientOrderId {
	private final short brokerId;
	private final String clientOrderId;
	
}
