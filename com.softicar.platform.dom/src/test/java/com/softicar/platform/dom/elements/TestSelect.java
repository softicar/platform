package com.softicar.platform.dom.elements;

import com.softicar.platform.common.container.pair.Pair;
import com.softicar.platform.common.core.i18n.IDisplayString;

class TestSelect extends AbstractDomValueSelect<Pair<Integer, IDisplayString>> {

	@Override
	protected IDisplayString getValueDisplayString(Pair<Integer, IDisplayString> value) {

		return value.getSecond();
	}

	@Override
	protected Integer getValueId(Pair<Integer, IDisplayString> value) {

		return value.getFirst();
	}
}
