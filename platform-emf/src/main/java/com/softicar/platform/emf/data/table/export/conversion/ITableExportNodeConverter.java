package com.softicar.platform.emf.data.table.export.conversion;

import com.softicar.platform.dom.node.IDomNode;

/**
 * Implementations convert the content of the given {@link IDomNode} to an
 * object of the given type.
 * 
 * @param <CT>
 *            the type to which the content of a node is to be converted
 * @author Alexander Schmidt
 */
public interface ITableExportNodeConverter<CT> {

	/**
	 * @param node
	 * @return A Pair of extracted node content and an integer number of lines
	 *         this content comprises.
	 */
	NodeConverterResult<CT> convertNode(IDomNode node);

	// ----

	public static class NodeConverterResult<CT> {

		private final CT content;
		private final int contentLineCount;

		public NodeConverterResult(CT content, int contentLineCount) {

			this.content = content;
			this.contentLineCount = contentLineCount;
		}

		public CT getContent() {

			return content;
		}

		public int getContentLineCount() {

			return contentLineCount;
		}
	}
}
