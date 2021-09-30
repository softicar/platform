package com.softicar.platform.emf.data.table.export.element;

import com.softicar.platform.dom.elements.DomChildElementFinder;
import com.softicar.platform.dom.elements.DomHeaderCell;
import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.dom.elements.DomTBody;
import com.softicar.platform.dom.elements.DomTHead;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.tables.DomDataTable;
import com.softicar.platform.dom.elements.tables.pageable.DomPageableTable;
import com.softicar.platform.emf.data.table.export.util.TableExportLib;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TableExportChildElementFetcher {

	public static int getTotalNumberOfRows(DomTable table) {

		TableExportLib.assertPageableIfScrollable(table);

		if (table instanceof DomPageableTable) {
			return ((DomPageableTable) table).getTotalRowCount();
		}

		else {
			return TableExportChildElementFetcher.getHeaderRows(table).size() + TableExportChildElementFetcher.getBodyRows(table).size();
		}
	}

	public static List<DomRow> getHeaderRows(DomTable table) {

		DomTHead tHead = getTableHeadOrNull(table);
		if (tHead != null) {
			return DomChildElementFinder.getChildrenByClass(tHead, DomRow.class, createHeaderRowSkipIndexs(table), null);
		} else {
			return new ArrayList<>();
		}
	}

	public static List<DomRow> getBodyRows(DomTable table) {

		DomTBody tBody = null;
		if (table instanceof DomDataTable) {
			tBody = ((DomDataTable) table).getBody();
		}
		if (tBody != null) {
			return DomChildElementFinder.getChildrenByClass(tBody, DomRow.class, null, null);
		} else {
			return new ArrayList<>();
		}
	}

	// TODO: this is really nasty
	private static Collection<Integer> createHeaderRowSkipIndexs(DomTable table) {

		DomTHead head = getTableHeadOrNull(table);
		if (head != null && head.getChildCount() > 0 && head.getChild(0) instanceof DomRow) {
			DomRow row = (DomRow) head.getChild(0);
			if (row.getChildCount() > 0 && !(row.getChild(0) instanceof DomHeaderCell)) {
				return Collections.singleton(0);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	private static DomTHead getTableHeadOrNull(DomTable table) {

		if (table instanceof DomDataTable) {
			return ((DomDataTable) table).getHead();
		} else {
			return null;
		}
	}
}
