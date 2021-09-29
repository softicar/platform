package com.softicar.platform.emf.data.table.export.element;

import com.softicar.platform.common.container.matrix.IMatrixTraits;
import com.softicar.platform.common.core.exceptions.SofticarNotImplementedYetException;
import com.softicar.platform.common.string.unicode.UnicodeEnum;
import com.softicar.platform.dom.elements.AbstractDomCell;
import java.io.Serializable;

public class TableExportNamedDomCell {

	private final String name;
	private final AbstractDomCell cell;

	public TableExportNamedDomCell(String name, AbstractDomCell cell) {

		this.name = name;
		this.cell = cell;
	}

	public String getName() {

		return name;
	}

	public AbstractDomCell getCell() {

		return cell;
	}

	public boolean isEmptyName() {

		return name == null || trimNoBreakSpace(name).isEmpty();
	}

	private String trimNoBreakSpace(String str) {

		return str.replace(UnicodeEnum.NO_BREAK_SPACE.toChar(), ' ').trim();
	}

	// ----

	public static class NamedDomCellMatrixTraits implements Serializable, IMatrixTraits<TableExportNamedDomCell> {

		@Override
		public TableExportNamedDomCell getDefaultValue() {

			return null;
		}

		@Override
		public TableExportNamedDomCell plus(TableExportNamedDomCell q1, TableExportNamedDomCell q2) {

			if (q1 != null && q2 != null) {
				throw new SofticarNotImplementedYetException();
			} else {
				if (q1 != null) {
					return q1;
				} else {
					return q2;
				}
			}
		}
	}
}
