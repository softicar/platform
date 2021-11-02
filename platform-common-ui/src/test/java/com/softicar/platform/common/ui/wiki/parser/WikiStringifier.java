package com.softicar.platform.common.ui.wiki.parser;

import com.softicar.platform.common.ui.wiki.element.IWikiElement;
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

class WikiStringifier implements IWikiVisitor {

	private StringBuilder builder;

	public String stringify(IWikiElement element) {

		this.builder = new StringBuilder();
		element.accept(this);
		return builder.toString();
	}

	@Override
	public void visit(WikiBold bold) {

		builder.append("B(");
		visitChildren(bold);
		builder.append(")");
	}

	@Override
	public void visit(WikiBox box) {

		builder.append(box.getBoxType());
		builder.append("(");
		visitChildren(box);
		builder.append(")");
	}

	@Override
	public void visit(WikiDocument document) {

		visitChildren(document);
	}

	@Override
	public void visit(WikiHeadline headline) {

		builder//
			.append("H")
			.append(headline.getLevel())
			.append("(");
		visitChildren(headline);
		builder.append(")");
	}

	@Override
	public void visit(WikiItalic italic) {

		builder.append("I(");
		visitChildren(italic);
		builder.append(")");
	}

	@Override
	public void visit(WikiLineBreak lineBreak) {

		builder.append("BR()");
	}

	@Override
	public void visit(WikiList list) {

		builder//
			.append(list.getListType() == WikiListType.ORDERED? "OL" : "UL")
			.append(list.getLevel())
			.append("(");
		visitChildren(list);
		builder.append(")");
	}

	@Override
	public void visit(WikiListItem listItem) {

		builder.append("LI(");
		visitChildren(listItem);
		builder.append(")");
	}

	@Override
	public void visit(WikiMonospace monospace) {

		builder.append("M(");
		visitChildren(monospace);
		builder.append(")");
	}

	@Override
	public void visit(WikiParagraph paragraph) {

		builder.append("P(");
		visitChildren(paragraph);
		builder.append(")");
	}

	@Override
	public void visit(WikiRawText rawText) {

		builder//
			.append(rawText.getType())
			.append("(")
			.append(rawText.getText())
			.append(")");
	}

	@Override
	public void visit(WikiResource resource) {

		builder//
			.append("RSC(")
			.append(resource.getUrl())
			.append("|")
			.append(resource.getLabel())
			.append(")");
	}

	@Override
	public void visit(WikiTable table) {

		builder.append("TAB(");
		visitChildren(table);
		builder.append(")");
	}

	@Override
	public void visit(WikiTableCell tableCell) {

		builder//
			.append(tableCell.isHeader()? "TH" : "TD")
			.append("(")
			.append(tableCell.getText())
			.append(")");
	}

	@Override
	public void visit(WikiTableRow tableRow) {

		builder.append("TR(");
		visitChildren(tableRow);
		builder.append(")");
	}

	@Override
	public void visit(WikiText text) {

		builder//
			.append("T(")
			.append(text.getText())
			.append(")");
	}

	@Override
	public void visit(WikiUnderline underline) {

		builder.append("U(");
		visitChildren(underline);
		builder.append(")");
	}

	@Override
	public void visit(WikiUrl url) {

		builder//
			.append("URL(")
			.append(url.getUrl())
			.append("|")
			.append(url.getLabel())
			.append(")");
	}
}
