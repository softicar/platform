package com.softicar.platform.common.string.imploder;

import com.softicar.platform.common.core.item.IBasicItem;

public class BasicItemImploder<T extends IBasicItem> implements IObjectImploder<T> {

	@Override
	public String implode(T obj) {

		return obj.getItemId() + "";
	}
}
