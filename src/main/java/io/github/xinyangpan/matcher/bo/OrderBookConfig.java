package io.github.xinyangpan.matcher.bo;

import java.math.BigDecimal;
import java.math.RoundingMode;

import io.github.xinyangpan.matcher.cache.OrderCache;
import lombok.Data;
import lombok.NonNull;

@Data
public class OrderBookConfig {

	private final int quantityScale;
	private final int amountScale;
	private final OrderCache orderCache = new OrderCache();

	public BigDecimal calculateAmount(@NonNull BigDecimal price, @NonNull BigDecimal quantity) {
		return price.multiply(quantity).setScale(amountScale, RoundingMode.DOWN);
	}

}
