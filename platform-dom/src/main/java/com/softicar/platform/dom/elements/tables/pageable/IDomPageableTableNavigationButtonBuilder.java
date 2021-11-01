package com.softicar.platform.dom.elements.tables.pageable;

import com.softicar.platform.dom.elements.button.DomButton;

public interface IDomPageableTableNavigationButtonBuilder {

	DomButton build();

	default boolean isAuthorized() {

		return true;
	}
}
