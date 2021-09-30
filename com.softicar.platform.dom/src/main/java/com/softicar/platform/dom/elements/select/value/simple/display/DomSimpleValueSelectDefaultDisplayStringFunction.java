package com.softicar.platform.dom.elements.select.value.simple.display;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.i18n.IDisplayable;
import java.util.function.Function;

public class DomSimpleValueSelectDefaultDisplayStringFunction<V> implements Function<V, IDisplayString> {

	@Override
	public IDisplayString apply(V value) {

		if (value instanceof IDisplayable) {
			return ((IDisplayable) value).toDisplay();
		} else {
			return IDisplayString.create(value + "");
		}
	}
}
