package io.github.xinyangpan.matcher.cache;

import java.util.HashMap;
import java.util.Map;

import io.github.xinyangpan.matcher.bo.BookOrder;
import lombok.Data;
import lombok.NonNull;

@Data
public class OrderCache {
	private final Map<Long, BookOrder> idToEntity = new HashMap<>();
	private final Map<BrokerIdAndClientOrderId, Long> index1 = new HashMap<>();

	public void put(BookOrder bookOrder) {
		long id = bookOrder.getId();
		idToEntity.put(id, bookOrder);
		BrokerIdAndClientOrderId brokerIdAndClientOrderId = this.toBrokerIdAndClientOrderId(bookOrder);
		if (brokerIdAndClientOrderId != null) {
			index1.put(brokerIdAndClientOrderId, id);
		}
	}

	public BookOrder removeById(long id) {
		BookOrder removed = idToEntity.remove(id);
		if (removed != null) {
			BrokerIdAndClientOrderId brokerIdAndClientOrderId = this.toBrokerIdAndClientOrderId(removed);
			if (brokerIdAndClientOrderId != null) {
				index1.remove(brokerIdAndClientOrderId);
			}
		}
		return removed;
	}

	public BookOrder findById(long id) {
		return idToEntity.get(id);
	}

	public Long getId(short brokerId, @NonNull String clientOrderId) {
		return index1.get(new BrokerIdAndClientOrderId(brokerId, clientOrderId));
	}
	
	private BrokerIdAndClientOrderId toBrokerIdAndClientOrderId(BookOrder bookOrder) {
		Short brokerId = bookOrder.getBrokerId();
		String clientOrderId = bookOrder.getClientOrderId();
		if (brokerId != null && clientOrderId != null && !clientOrderId.isEmpty()) {
			return new BrokerIdAndClientOrderId(brokerId, clientOrderId);
		} else {
			return null;
		}
	}

}
