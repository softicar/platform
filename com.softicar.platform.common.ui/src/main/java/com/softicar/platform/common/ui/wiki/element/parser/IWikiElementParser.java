package com.softicar.platform.common.ui.wiki.element.parser;

import com.softicar.platform.common.ui.wiki.element.IWikiElement;
import com.softicar.platform.common.ui.wiki.parser.WikiTokenStream;

public interface IWikiElementParser<E extends IWikiElement> {

	E parse(WikiTokenStream tokenStream);
}
