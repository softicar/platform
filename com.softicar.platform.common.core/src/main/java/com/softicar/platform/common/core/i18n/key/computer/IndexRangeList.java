package com.softicar.platform.common.core.i18n.key.computer;

import java.util.ArrayList;

class IndexRangeList extends ArrayList<IndexRange> {

	public void add(int start, int end) {

		add(new IndexRange(start, end));
	}
}
