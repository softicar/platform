package com.softicar.platform.dom.elements.wiki;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.ui.wiki.element.IWikiParentElement;
import com.softicar.platform.common.ui.wiki.element.IWikiVisitor;
import com.softicar.platform.common.ui.wiki.element.block.WikiDocument;
import com.softicar.platform.common.ui.wiki.element.block.WikiHeadline;
import com.softicar.platform.common.ui.wiki.element.block.WikiList;
import com.softicar.platform.common.ui.wiki.element.block.WikiListItem;
import com.softicar.platform.common.ui.wiki.element.block.WikiListType;
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
import com.softicar.platform.common.ui.wiki.element.tag.WikiRawTextType;
import com.softicar.platform.common.ui.wiki.parser.WikiParser;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.element.DomSimpleElement;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.DomAnchor;
import com.softicar.platform.dom.elements.DomCell;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.elements.DomHeaderCell;
import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.dom.elements.DomTable;
import com.softicar.platform.dom.elements.wiki.box.DomWikiBox;
import com.softicar.platform.dom.parent.IDomParentElement;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

public class DomWikiDivBuilder implements IWikiBuilderMethods<DomWikiDivBuilder> {

	private final Map<String, IDomElement> elementMap;
	private final IDomParentElement rootElement;
	private final StringBuilder builder;

	public DomWikiDivBuilder() {

		this(new DomDiv());
	}

	public DomWikiDivBuilder(IDomParentElement rootElement) {

		this.elementMap = new TreeMap<>();
		this.rootElement = rootElement;
		this.builder = new StringBuilder();
	}

	public DomWikiDivBuilder registerElement(String key, IDomElement element) {

		elementMap.put(key, element);
		return this;
	}

	@Override
	public DomWikiDivBuilder addText(IDisplayString text) {

		builder.append(text);
		return this;
	}

	public DomWikiDivBuilder addEmptyLine() {

		builder.append("\n\n");
		return this;
	}

	public DomWikiDivBuilder addLineBreak() {

		builder.append("\\\\ ");
		return this;
	}

	public IDomElement build() {

		ElementsBuilder visitor = new ElementsBuilder();
		WikiDocument document = new WikiParser().parse(builder.toString());
		document.accept(visitor);
		return rootElement;
	}

	public void buildAndAppendTo(IDomParentElement parentElement) {

		parentElement.appendChild(build());
	}

	private class ElementsBuilder implements IWikiVisitor {

		private final Stack<IDomParentElement> stack;

		public ElementsBuilder() {

			this.stack = new Stack<>();
		}

		@Override
		public void visit(WikiBold bold) {

			appendElementAndChildren(DomElementTag.B, bold);
		}

		@Override
		public void visit(WikiBox box) {

			DomWikiBox domWikiBox = new DomWikiBox(box.getBoxType());
			appendElement(domWikiBox);
			appendChildren(domWikiBox.getContentDiv(), box);
		}

		@Override
		public void visit(WikiDocument document) {

			appendChildren(rootElement, document);
		}

		@Override
		public void visit(WikiHeadline headline) {

			appendElementAndChildren(DomWikiHeadlines.createHeadline(headline), headline);
		}

		@Override
		public void visit(WikiItalic italic) {

			appendElementAndChildren(DomElementTag.I, italic);
		}

		@Override
		public void visit(WikiLineBreak lineBreak) {

			stack.peek().appendNewChild(DomElementTag.BR);
		}

		@Override
		public void visit(WikiList list) {

			DomElementTag tag = list.getListType() == WikiListType.ORDERED? DomElementTag.OL : DomElementTag.UL;
			appendElementAndChildren(tag, list);
		}

		@Override
		public void visit(WikiListItem listItem) {

			appendElementAndChildren(DomElementTag.LI, listItem);
		}

		@Override
		public void visit(WikiMonospace monospace) {

			DomDiv text = new DomDiv();
			text.addCssClass(DomElementsCssClasses.DOM_WIKI_MONOSPACE);
			appendElementAndChildren(text, monospace);
		}

		@Override
		public void visit(WikiParagraph paragraph) {

			appendElementAndChildren(DomElementTag.P, paragraph);
		}

		@Override
		public void visit(WikiResource resource) {

			IDomElement element = elementMap.get(resource.getUrl());
			if (element != null) {
				appendElement(element);
			}
		}

		@Override
		public void visit(WikiTable table) {

			appendElementAndChildren(new DomTable(), table);
		}

		@Override
		public void visit(WikiTableCell tableCell) {

			IDomParentElement cell = tableCell.isHeader()? new DomHeaderCell() : new DomCell();
			cell.appendText(tableCell.getText());
			appendElement(cell);
		}

		@Override
		public void visit(WikiTableRow tableRow) {

			appendElementAndChildren(new DomRow(), tableRow);
		}

		@Override
		public void visit(WikiRawText rawText) {

			IDomParentElement element = new DomDiv();
			element.addCssClass(DomElementsCssClasses.DOM_WIKI_RAW_TEXT);
			if (!rawText.isBlockElement()) {
				element.addCssClass(DomElementsCssClasses.DOM_WIKI_RAW_TEXT_INLINE);
			}
			element.appendText(rawText.getText());
			appendElement(element);

			if (rawText.getType() == WikiRawTextType.CODE) {
				element.addCssClass(DomElementsCssClasses.DOM_WIKI_RAW_TEXT_CODE);
				if (rawText.isBlockElement()) {
					element.addCssClass(DomElementsCssClasses.DOM_WIKI_RAW_TEXT_CODE_BLOCK);
				}
			}
		}

		@Override
		public void visit(WikiText text) {

			stack.peek().appendText(text.getText());
		}

		@Override
		public void visit(WikiUnderline underline) {

			DomDiv text = new DomDiv();
			text.addCssClass(DomElementsCssClasses.DOM_WIKI_UNDERLINE);
			appendElementAndChildren(text, underline);
		}

		@Override
		public void visit(WikiUrl url) {

			DomAnchor anchor = new DomAnchor();
			anchor.setHRef(url.getUrl());
			anchor.setAttribute("target", "_blank");
			anchor.appendText(url.getLabel().isEmpty()? url.getUrl() : url.getLabel());
			appendElement(anchor);
		}

		private void appendElementAndChildren(DomElementTag elementTag, IWikiParentElement wikiElement) {

			appendElementAndChildren(new DomSimpleElement(elementTag), wikiElement);
		}

		private void appendElementAndChildren(IDomParentElement domElement, IWikiParentElement wikiElement) {

			appendElement(domElement);
			appendChildren(domElement, wikiElement);
		}

		private void appendElement(IDomElement element) {

			stack.peek().appendChild(element);
		}

		private void appendChildren(IDomParentElement domElement, IWikiParentElement wikiElement) {

			stack.push(domElement);
			visitChildren(wikiElement);
			stack.pop();
		}
	}
}
