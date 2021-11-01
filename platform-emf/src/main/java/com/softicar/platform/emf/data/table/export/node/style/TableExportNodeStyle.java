package com.softicar.platform.emf.data.table.export.node.style;

import com.softicar.platform.common.ui.color.IColor;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.parent.IDomParentElement;
import com.softicar.platform.emf.data.table.export.implementation.excel.formatting.cell.TableExportCellAlignment;

public class TableExportNodeStyle {

	private TableExportCellAlignment alignment = null;
	private IColor backgroundColor = null;
	private IColor fontColor = null;
	private TableExportNodeFontWeight fontWeight = null;

	public TableExportNodeStyle setAlign(TableExportCellAlignment alignment) {

		this.alignment = alignment;
		return this;
	}

	public TableExportNodeStyle setBackgroundColor(IColor backgroundColor) {

		this.backgroundColor = backgroundColor;
		return this;
	}

	public TableExportNodeStyle setFontColor(IColor fontColor) {

		this.fontColor = fontColor;
		return this;
	}

	public TableExportNodeStyle setFontWeight(TableExportNodeFontWeight exportNodeFontWeight) {

		this.fontWeight = exportNodeFontWeight;
		return this;
	}

	public TableExportCellAlignment getAlignment() {

		return alignment;
	}

	public IColor getBackgroundColor() {

		return backgroundColor;
	}

	public IColor getFontColor() {

		return fontColor;
	}

	public TableExportNodeFontWeight getFontWeight() {

		return fontWeight;
	}

	// ----

	public static TableExportNodeStyle createFromNode(IDomNode node) {

		if (node != null) {
			if (node instanceof ITableExportStyledNode) {
				return ((ITableExportStyledNode) node).getExportNodeStyle();
			} else if (node instanceof IDomParentElement) {
				return getStyleFromChildren((IDomParentElement) node);
			}
		}
		return null;
	}

	private static TableExportNodeStyle getStyleFromChildren(IDomParentElement parent) {

		for (IDomNode child: parent.getChildren()) {
			if (child instanceof ITableExportStyledNode) {
				return ((ITableExportStyledNode) child).getExportNodeStyle();
			} else if (child instanceof IDomParentElement) {
				return getStyleFromChildren((IDomParentElement) child);
			}
		}
		return new TableExportNodeStyle();
	}
}
