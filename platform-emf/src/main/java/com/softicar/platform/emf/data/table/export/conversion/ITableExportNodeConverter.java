package com.softicar.platform.emf.data.table.export.conversion;

import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.emf.data.table.export.node.TableExportTypedNodeValue;

/**
 * Implementations convert the content of the given {@link IDomNode} to an
 * object of the given type.
 *
 * @author Alexander Schmidt
 */
public interface ITableExportNodeConverter {

	/**
	 * @param node
	 * @return A Pair of extracted node content and an integer number of lines
	 *         this content comprises.
	 */
	NodeConverterResult convertNode(IDomNode node);

	// ----

	public static class NodeConverterResult {

		private final TableExportTypedNodeValue content;
		private final int contentLineCount;

		public NodeConverterResult(TableExportTypedNodeValue content, int contentLineCount) {

			this.content = content;
			this.contentLineCount = contentLineCount;
		}

		public TableExportTypedNodeValue getContent() {

			return content;
		}

		public int getContentLineCount() {

			return contentLineCount;
		}
	}
}
