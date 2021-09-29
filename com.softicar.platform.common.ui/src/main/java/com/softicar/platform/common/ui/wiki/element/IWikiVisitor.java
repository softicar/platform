package com.softicar.platform.common.ui.wiki.element;

import com.softicar.platform.common.ui.wiki.element.block.WikiDocument;
import com.softicar.platform.common.ui.wiki.element.block.WikiHeadline;
import com.softicar.platform.common.ui.wiki.element.block.WikiList;
import com.softicar.platform.common.ui.wiki.element.block.WikiListItem;
import com.softicar.platform.common.ui.wiki.element.block.WikiParagraph;
import com.softicar.platform.common.ui.wiki.element.block.WikiTable;
import com.softicar.platform.common.ui.wiki.element.block.WikiTableCell;
import com.softicar.platform.common.ui.wiki.element.block.WikiTableRow;
import com.softicar.platform.common.ui.wiki.element.format.WikiBold;
import com.softicar.platform.common.ui.wiki.element.format.WikiItalic;
import com.softicar.platform.common.ui.wiki.element.format.WikiMonospace;
import com.softicar.platform.common.ui.wiki.element.format.WikiUnderline;
import com.softicar.platform.common.ui.wiki.element.inline.WikiLineBreak;
import com.softicar.platform.common.ui.wiki.element.inline.WikiResource;
import com.softicar.platform.common.ui.wiki.element.inline.WikiText;
import com.softicar.platform.common.ui.wiki.element.inline.WikiUrl;
import com.softicar.platform.common.ui.wiki.element.tag.WikiBox;
import com.softicar.platform.common.ui.wiki.element.tag.WikiRawText;

public interface IWikiVisitor {

	void visit(WikiBold bold);

	void visit(WikiBox box);

	void visit(WikiDocument document);

	void visit(WikiHeadline headline);

	void visit(WikiItalic italic);

	void visit(WikiLineBreak lineBreak);

	void visit(WikiList list);

	void visit(WikiListItem listItem);

	void visit(WikiMonospace monospace);

	void visit(WikiParagraph paragraph);

	void visit(WikiRawText rawText);

	void visit(WikiResource resource);

	void visit(WikiTable table);

	void visit(WikiTableCell tableCell);

	void visit(WikiTableRow tableRow);

	void visit(WikiText text);

	void visit(WikiUnderline underline);

	void visit(WikiUrl url);

	/**
	 * Visits all children of the given element.
	 * <p>
	 * If the given element is not an {@link IWikiParentElement}, this method
	 * does nothing.
	 *
	 * @param element
	 *            the element of which to visit the children
	 */
	default void visitChildren(IWikiElement element) {

		if (element instanceof IWikiParentElement) {
			((IWikiParentElement) element)//
				.getChildren()
				.forEach(child -> child.accept(this));
		}
	}
}
