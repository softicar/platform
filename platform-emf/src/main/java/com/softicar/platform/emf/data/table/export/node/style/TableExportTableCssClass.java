package com.softicar.platform.emf.data.table.export.node.style;

import com.softicar.platform.dom.elements.DomTable;
import java.util.Optional;

/**
 * Represents the CSS class of the exported {@link DomTable}.
 */
public class TableExportTableCssClass {

	private final String className;

	public TableExportTableCssClass(DomTable table) {

		this.className = Optional.ofNullable(table.getAttribute("class").getValue()).orElse("");
	}

	public String getCssClassName() {

		return className;
	}
}
