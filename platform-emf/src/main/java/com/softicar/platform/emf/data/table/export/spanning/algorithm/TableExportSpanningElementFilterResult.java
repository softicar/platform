package com.softicar.platform.emf.data.table.export.spanning.algorithm;

import com.softicar.platform.emf.data.table.export.spanning.element.ITableExportSpanningElement;

public class TableExportSpanningElementFilterResult<OT extends ITableExportSpanningElement<?>> {

	private final OT spanningElement;
	private final int originalColumnIndex;

	public TableExportSpanningElementFilterResult(OT spanningElement, int originalColumnIndex) {

		this.spanningElement = spanningElement;
		this.originalColumnIndex = originalColumnIndex;
	}

	public OT getSpanningElement() {

		return spanningElement;
	}

	public int getOriginalColumnIndex() {

		return originalColumnIndex;
	}
}
