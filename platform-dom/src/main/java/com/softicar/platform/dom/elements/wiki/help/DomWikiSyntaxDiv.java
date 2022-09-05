package com.softicar.platform.dom.elements.wiki.help;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.elements.wiki.DomWikiDivBuilder;
import com.softicar.platform.dom.elements.wiki.DomWikiI18n;

public class DomWikiSyntaxDiv extends DomDiv {

	public DomWikiSyntaxDiv() {

		addCssClass(DomCssClasses.DOM_WIKI_SYNTAX_DIV);
		new DomWikiDivBuilder(this)//
			.addHeadline("======", DomWikiI18n.SOFTICAR_WIKI_SYNTAX)
			.addLine(DomWikiI18n.THE_SOFTICAR_WIKI_SYNTAX_IS_VERY_SIMILAR_TO_THE_ARG1_DOKUWIKI_SYNTAX.toDisplay("https://www.dokuwiki.org/wiki:syntax"))

			// basic text formatting
			.addHeadline("=====", DomWikiI18n.BASIC_TEXT_FORMATTING)
			.addLine(DomWikiI18n.THE_SOFTICAR_WIKI_SYNTAX_SUPPORTS_BOLD)
			.addLine(DomWikiI18n.ITALIC_UNDERLINE_AND_MONOSPACED_TEXT)
			.addLine(DomWikiI18n.OF_COURSE_YOU_CAN_COMBINE_ALL_THESE)
			.beginCode()
			.addLine(DomWikiI18n.THE_SOFTICAR_WIKI_SYNTAX_SUPPORTS_BOLD)
			.addLine(DomWikiI18n.ITALIC_UNDERLINE_AND_MONOSPACED_TEXT)
			.addLine(DomWikiI18n.OF_COURSE_YOU_CAN_COMBINE_ALL_THESE)
			.endCode()
			.addEmptyLine()
			.addLine(DomWikiI18n.PARAGRAPHS_ARE_CREATED_FROM_BLANK_LINES)
			.addLine(DomWikiI18n.IF_YOU_WANT_TO_FORCE_A_NEWLINE_WITHOUT_A_PARAGRAPH_YOU_CAN_USE_TWO_BACKSLASHES_FOLLOWED_BY_A_WHITESPACE_OR_THE_END_OF_LINE)

			// headlines
			.addHeadline("=====", DomWikiI18n.HEADLINES)
			.addLine(DomWikiI18n.HEADLINES_ARE_CREATED_BY_USING_TWO_OR_MORE_EQUALS_SIGNS_ARG1.toDisplay("<code>==</code>"))
			.addLine(DomWikiI18n.THERE_ARE_FIVE_DIFFERENT_LEVELS_OF_HEADLINES)
			.beginCode()
			.addLine(DomWikiI18n.HEADLINE.enclose("====== ", " #1 ======"))
			.addLine(DomWikiI18n.HEADLINE.enclose("===== ", " #2 ====="))
			.addLine(DomWikiI18n.HEADLINE.enclose("==== ", " #3 ===="))
			.addLine(DomWikiI18n.HEADLINE.enclose("=== ", " #4 ==="))
			.addLine(DomWikiI18n.HEADLINE.enclose("== ", " #5 =="))
			.endCode()

			// lists
			.addHeadline("=====", DomWikiI18n.LISTS)
			// ordered lists
			.addEmptyLine()
			.addLine(DomWikiI18n.YOU_CAN_CREATE_ORDERED_LISTS_BY_STARTING_A_LINE_WITH_ARG1.toDisplay("<code>  -</code>"))
			.addLine(IDisplayString.create("  - ").concat(DomWikiI18n.ORDERED_LIST_ITEM))
			.addLine(IDisplayString.create("  - ").concat(DomWikiI18n.ORDERED_LIST_ITEM))
			.addLine(IDisplayString.create("    - ").concat(DomWikiI18n.ORDERED_LIST_ITEM))
			.addLine(IDisplayString.create("    - ").concat(DomWikiI18n.ORDERED_LIST_ITEM))
			.addEmptyLine()
			.beginCode()
			.addLine(IDisplayString.create("  - ").concat(DomWikiI18n.ORDERED_LIST_ITEM))
			.addLine(IDisplayString.create("  - ").concat(DomWikiI18n.ORDERED_LIST_ITEM))
			.addLine(IDisplayString.create("    - ").concat(DomWikiI18n.ORDERED_LIST_ITEM))
			.addLine(IDisplayString.create("    - ").concat(DomWikiI18n.ORDERED_LIST_ITEM))
			.endCode()
			// unordered lists
			.addEmptyLine()
			.addLine(DomWikiI18n.YOU_CAN_CREATE_UNORDERED_LISTS_BY_STARTING_A_LINE_WITH_ARG1.toDisplay("<code>  *</code>"))
			.addLine(IDisplayString.create("  * ").concat(DomWikiI18n.UNORDERED_LIST_ITEM))
			.addLine(IDisplayString.create("  * ").concat(DomWikiI18n.UNORDERED_LIST_ITEM))
			.addLine(IDisplayString.create("    * ").concat(DomWikiI18n.UNORDERED_LIST_ITEM))
			.addLine(IDisplayString.create("    * ").concat(DomWikiI18n.UNORDERED_LIST_ITEM))
			.addEmptyLine()
			.beginCode()
			.addLine(IDisplayString.create("  * ").concat(DomWikiI18n.UNORDERED_LIST_ITEM))
			.addLine(IDisplayString.create("  * ").concat(DomWikiI18n.UNORDERED_LIST_ITEM))
			.addLine(IDisplayString.create("    * ").concat(DomWikiI18n.UNORDERED_LIST_ITEM))
			.addLine(IDisplayString.create("    * ").concat(DomWikiI18n.UNORDERED_LIST_ITEM))
			.endCode()

			// tables
			.addHeadline("=====", DomWikiI18n.TABLES)
			.addLine(DomWikiI18n.YOU_CAN_DEFINE_TABLES_BY_USING_ARG1_AND_ARG2.toDisplay("<code>^</code>", "<code>|</code>"))
			.addLine(IDisplayString.create("  * ").concat(DomWikiI18n.USE_ARG1_FOR_TABLE_HEADER_CELLS.toDisplay("<code>^</code>")))
			.addLine(IDisplayString.create("  * ").concat(DomWikiI18n.USE_ARG1_FOR_TABLE_BODY_CELLS.toDisplay("<code>|</code>")))
			.addEmptyLine()
			.addLine(IDisplayString.format("^ %s ^ %s ^", DomWikiI18n.HEADER, DomWikiI18n.HEADER))
			.addLine(IDisplayString.format("| %s   | %s   |", DomWikiI18n.CELL, DomWikiI18n.CELL))
			.addEmptyLine()
			.beginCode()
			.addLine(IDisplayString.format("^ %s ^ %s ^", DomWikiI18n.HEADER, DomWikiI18n.HEADER))
			.addLine(IDisplayString.format("| %s   | %s   |", DomWikiI18n.CELL, DomWikiI18n.CELL))
			.endCode()

			// boxes
			.addHeadline("=====", DomWikiI18n.BOXES)
			.addLine(DomWikiI18n.DIFFERENT_TYPES_OF_BOXES_ARG1_ARG2_AND_ARG3.toDisplay("<code><info></code>", "<code><warning></code>", "<code><error></code>"))
			.addLine(DomWikiI18n.THIS_IS_AN_INFO_BOX_WITH_A_LIST_FOO_BAR.enclose("<info>", "</info>"))
			.addLine(DomWikiI18n.THIS_IS_A_WARNING_BOX.enclose("<warning>", "</warning>"))
			.addLine(DomWikiI18n.THIS_IS_AN_ERROR_BOX.enclose("<error>", "</error>"))
			.addEmptyLine()
			.beginCode()
			.addLine(DomWikiI18n.THIS_IS_AN_INFO_BOX_WITH_A_LIST_FOO_BAR.enclose("<info>", "</info>"))
			.addLine(DomWikiI18n.THIS_IS_A_WARNING_BOX.enclose("<warning>", "</warning>"))
			.addLine(DomWikiI18n.THIS_IS_AN_ERROR_BOX.enclose("<error>", "</error>"))
			.endCode()

			// links
			.addHeadline("=====", DomWikiI18n.LINKS)
			.addLine(DomWikiI18n.YOU_CAN_DEFINE_EXTERNAL_LINKS_WITH_AND_WITHOUT_LABEL)
			.addLine(IDisplayString.create("  * [[http://www.example.com]]"))
			.addLine(IDisplayString.format("  * [[http://www.example.com|%s]]", DomWikiI18n.WITH_LABEL))
			.addEmptyLine()
			.beginCode()
			.addLine(IDisplayString.create("  * [[http://www.example.com]]"))
			.addLine(IDisplayString.format("  * [[http://www.example.com|%s]]", DomWikiI18n.WITH_LABEL))
			.endCode()

			// resources
			.addHeadline("=====", DomWikiI18n.RESOURCES)
			.registerElement("info", new DomImage(DomImages.INFO.getResource()))
			.addLine(DomWikiI18n.YOU_CAN_ALSO_ADD_RESOURCES_LIKE_IMAGES_ARG1.toDisplay("{{info}}"))
			.addEmptyLine()
			.beginCode()
			.addLine(DomWikiI18n.YOU_CAN_ALSO_ADD_RESOURCES_LIKE_IMAGES_ARG1.toDisplay("{{info}}"))
			.endCode()
			.addEmptyLine()
			.addLine(DomWikiI18n.CURRENTLY_RESOURCES_ARE_ONLY_SUPPORTED_FOR_WIKI_ELEMENTS_THAT_ARE_DEFINED_IN_JAVA.enclose("//"))

			.build();
	}
}
