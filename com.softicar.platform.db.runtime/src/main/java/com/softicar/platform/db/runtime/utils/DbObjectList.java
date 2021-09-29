package com.softicar.platform.db.runtime.utils;

import com.softicar.platform.common.core.item.IBasicItem;
import java.util.ArrayList;

public class DbObjectList<R extends IBasicItem> extends ArrayList<R> implements IDbObjectList<R> {

	private static final long serialVersionUID = 1L;
}
