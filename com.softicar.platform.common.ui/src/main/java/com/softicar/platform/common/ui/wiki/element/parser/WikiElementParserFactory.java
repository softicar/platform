package com.softicar.platform.common.ui.wiki.element.parser;

import com.softicar.platform.common.ui.wiki.element.block.WikiHeadlineParser;
import com.softicar.platform.common.ui.wiki.element.block.WikiListItemParser;
import com.softicar.platform.common.ui.wiki.element.block.WikiParagraphParser;
import com.softicar.platform.common.ui.wiki.element.block.WikiTableRowParser;
import com.softicar.platform.common.ui.wiki.element.format.WikiBold;
import com.softicar.platform.common.ui.wiki.element.format.WikiFormatElementParser;
import com.softicar.platform.common.ui.wiki.element.format.WikiItalic;
import com.softicar.platform.common.ui.wiki.element.format.WikiMonospace;
import com.softicar.platform.common.ui.wiki.element.format.WikiUnderline;
import com.softicar.platform.common.ui.wiki.element.inline.WikiLineBreak;
import com.softicar.platform.common.ui.wiki.element.inline.WikiResource;
import com.softicar.platform.common.ui.wiki.element.inline.WikiText;
import com.softicar.platform.common.ui.wiki.element.inline.WikiUrl;
import com.softicar.platform.common.ui.wiki.element.tag.WikiTagElementParserFactory;
import com.softicar.platform.common.ui.wiki.token.WikiResourceToken;
import com.softicar.platform.common.ui.wiki.token.WikiTableRowToken;
import com.softicar.platform.common.ui.wiki.token.WikiTagToken;
import com.softicar.platform.common.ui.wiki.token.WikiToken;
import com.softicar.platform.common.ui.wiki.token.WikiTokenType;
import com.softicar.platform.common.ui.wiki.token.WikiUrlToken;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

public class WikiElementParserFactory {

	private static final WikiElementParserFactory INSTANCE = new WikiElementParserFactory();
	private final Map<WikiTokenType, Function<WikiToken, IWikiElementParser<?>>> factories;

	public WikiElementParserFactory() {

		factories = new TreeMap<>();
		factories.put(WikiTokenType.BOLD, token -> new WikiFormatElementParser<>(new WikiBold()));
		factories.put(WikiTokenType.HEADLINE, token -> new WikiHeadlineParser(token));
		factories.put(WikiTokenType.RESOURCE, token -> new WikiElementParser<>(new WikiResource((WikiResourceToken) token)));
		factories.put(WikiTokenType.ITALIC, token -> new WikiFormatElementParser<>(new WikiItalic()));
		factories.put(WikiTokenType.LINE_BREAK, token -> new WikiElementParser<>(new WikiLineBreak()));
		factories.put(WikiTokenType.LIST_ITEM, token -> new WikiListItemParser(token));
		factories.put(WikiTokenType.MONOSPACE, token -> new WikiFormatElementParser<>(new WikiMonospace()));
		factories.put(WikiTokenType.PARAGRAPH, token -> new WikiParagraphParser());
		factories.put(WikiTokenType.TABLE_ROW, token -> new WikiTableRowParser((WikiTableRowToken) token));
		factories.put(WikiTokenType.TAG, this::createTagElementParser);
		factories.put(WikiTokenType.TEXT, token -> new WikiElementParser<>(new WikiText(token)));
		factories.put(WikiTokenType.UNDERLINE, token -> new WikiFormatElementParser<>(new WikiUnderline()));
		factories.put(WikiTokenType.URL, token -> new WikiElementParser<>(new WikiUrl((WikiUrlToken) token)));
	}

	public static WikiElementParserFactory getInstance() {

		return INSTANCE;
	}

	public IWikiElementParser<?> createParser(WikiToken token) {

		return factories.get(token.getTokenType()).apply(token);
	}

	private IWikiElementParser<?> createTagElementParser(WikiToken token) {

		return WikiTagElementParserFactory.getInstance().createParser((WikiTagToken) token);
	}
}
