package com.softicar.platform.dom.refresh.bus;

import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.dom.node.IDomNode;

public interface IDomRefreshBusListener extends IDomNode {

	default void invalidateCachedData(IDomRefreshBusEvent event) {

		// nothing by default
		DevNull.swallow(event);
	}

	void refresh(IDomRefreshBusEvent event);
}
