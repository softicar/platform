package com.softicar.platform.dom.element;

/**
 * Enumerates all supported HTML tags.
 *
 * @author Oliver Richers
 */
public enum DomElementTag {

	A("a"),
	ABBR("abbr"),
	ACRONYM("acronym"),
	ADDRESS("address"),
	APPLET("applet"),
	AREA("area"),
	AUDIO("audio"),
	B("b"),
	BASE("base"),
	BDI("bdi"),
	BDO("bdo"),
	BLOCKQUOTE("blockquote"),
	BODY("body"),
	BR("br"),
	BUTTON("button"),
	CANVAS("canvas"),
	CAPTION("caption"),
	CITE("cite"),
	CODE("code"),
	COL("col"),
	COLGROUP("colgroup"),
	DD("dd"),
	DIV("div"),
	DL("dl"),
	DT("dt"),
	FIELDSET("fieldset"),
	FORM("form"),
	H1("h1"),
	H2("h2"),
	H3("h3"),
	H4("h4"),
	H5("h5"),
	H6("h6"),
	HEAD("head"),
	HR("hr"),
	I("i"),
	IFRAME("iframe"),
	IMG("img"),
	INPUT("input"),
	LABEL("label"),
	LEGEND("legend"),
	LI("li"),
	LINK("link"),
	MAP("map"),
	OBJECT("object"),
	OL("ol"),
	OPTGROUP("optgroup"),
	OPTION("option"),
	P("p"),
	PARAM("param"),
	PRE("pre"),
	SCRIPT("script"),
	SELECT("select"),
	SOURCE("source"),
	SPAN("span"),
	SUB("sub"),
	SUP("sup"),
	TABLE("table"),
	TBODY("tbody"),
	TD("td"),
	TEXTAREA("textarea"),
	TFOOT("tfoot"),
	TH("th"),
	THEAD("thead"),
	TR("tr"),
	UL("ul"),
	VIDEO("video");

	private final String name;

	private DomElementTag(String name) {

		this.name = name;
	}

	public String getName() {

		return name;
	}
}
