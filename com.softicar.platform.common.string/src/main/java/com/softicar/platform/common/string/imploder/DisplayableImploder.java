package com.softicar.platform.common.string.imploder;

import com.softicar.platform.common.core.i18n.IDisplayable;

public class DisplayableImploder<T extends IDisplayable> implements IObjectImploder<T> {

	@Override
	public String implode(T obj) {

		return obj.toDisplay().toString();
	}
}
