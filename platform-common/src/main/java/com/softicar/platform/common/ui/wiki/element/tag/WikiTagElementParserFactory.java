package com.softicar.platform.common.ui.wiki.element.tag;

import com.softicar.platform.common.ui.wiki.element.parser.IWikiElementParser;
import com.softicar.platform.common.ui.wiki.token.WikiTag;
import com.softicar.platform.common.ui.wiki.token.WikiTagToken;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

public class WikiTagElementParserFactory {

	private static final WikiTagElementParserFactory INSTANCE = new WikiTagElementParserFactory();
	private final Map<WikiTag, Function<WikiTagToken, IWikiElementParser<?>>> factories;

	public WikiTagElementParserFactory() {

		factories = new TreeMap<>();
		factories.put(WikiTag.CODE, token -> new WikiRawTextParser(WikiRawTextType.CODE));
		factories.put(WikiTag.ERROR, token -> new WikiBoxParser(new WikiBox(WikiBoxType.ERROR)));
		factories.put(WikiTag.INFO, token -> new WikiBoxParser(new WikiBox(WikiBoxType.INFO)));
		factories.put(WikiTag.PRE, token -> new WikiRawTextParser(WikiRawTextType.PRE));
		factories.put(WikiTag.WARNING, token -> new WikiBoxParser(new WikiBox(WikiBoxType.WARNING)));
	}

	public static WikiTagElementParserFactory getInstance() {

		return INSTANCE;
	}

	public IWikiElementParser<?> createParser(WikiTagToken token) {

		return factories.get(token.getTag()).apply(token);
	}
}
