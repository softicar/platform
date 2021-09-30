package com.softicar.platform.dom.element;

import com.softicar.platform.dom.element.DomElement.HierarchyType;

/**
 * Represents a list of all allowed HTML tags.
 *
 * @author Oliver Richers
 */
public enum DomElementTag {

	A("a", HierarchyType.PARENT),
	ABBR("abbr", HierarchyType.PARENT),
	ACRONYM("acronym", HierarchyType.PARENT),
	ADDRESS("address", HierarchyType.PARENT),
	APPLET("applet", HierarchyType.PARENT),
	AREA("area", HierarchyType.PARENT),
	AUDIO("audio", HierarchyType.PARENT),
	B("b", HierarchyType.PARENT),
	BASE("base", HierarchyType.PARENT),
	BDI("bdi", HierarchyType.PARENT),
	BDO("bdo", HierarchyType.PARENT),
	BLOCKQUOTE("blockquote", HierarchyType.PARENT),
	BODY("body", HierarchyType.PARENT),
	BR("br", HierarchyType.LEAF),
	BUTTON("button", HierarchyType.PARENT),
	CANVAS("canvas", HierarchyType.PARENT),
	CAPTION("caption", HierarchyType.PARENT),
	CITE("cite", HierarchyType.PARENT),
	CODE("code", HierarchyType.PARENT),
	COL("col", HierarchyType.PARENT),
	COLGROUP("colgroup", HierarchyType.PARENT),
	DD("dd", HierarchyType.PARENT),
	DIV("div", HierarchyType.PARENT),
	DL("dl", HierarchyType.PARENT),
	DT("dt", HierarchyType.PARENT),
	FIELDSET("fieldset", HierarchyType.PARENT),
	FORM("form", HierarchyType.PARENT),
	H1("h1", HierarchyType.PARENT),
	H2("h2", HierarchyType.PARENT),
	H3("h3", HierarchyType.PARENT),
	H4("h4", HierarchyType.PARENT),
	H5("h5", HierarchyType.PARENT),
	H6("h6", HierarchyType.PARENT),
	HEAD("head", HierarchyType.PARENT),
	HR("hr", HierarchyType.LEAF),
	I("i", HierarchyType.PARENT),
	IFRAME("iframe", HierarchyType.PARENT),
	IMG("img", HierarchyType.LEAF),
	INPUT("input", HierarchyType.LEAF),
	LABEL("label", HierarchyType.LEAF),
	LEGEND("legend", HierarchyType.LEAF),
	LI("li", HierarchyType.PARENT),
	LINK("link", HierarchyType.LEAF),
	MAP("map", HierarchyType.PARENT),
	OBJECT("object", HierarchyType.PARENT),
	OL("ol", HierarchyType.PARENT),
	OPTGROUP("optgroup", HierarchyType.PARENT),
	OPTION("option", HierarchyType.PARENT),
	P("p", HierarchyType.PARENT),
	PARAM("param", HierarchyType.LEAF),
	PRE("pre", HierarchyType.PARENT),
	SCRIPT("script", HierarchyType.PARENT),
	SELECT("select", HierarchyType.PARENT),
	SOURCE("source", HierarchyType.LEAF),
	SPAN("span", HierarchyType.PARENT),
	SUB("sub", HierarchyType.LEAF),
	SUP("sup", HierarchyType.LEAF),
	TABLE("table", HierarchyType.PARENT),
	TBODY("tbody", HierarchyType.PARENT),
	TD("td", HierarchyType.PARENT),
	TEXTAREA("textarea", HierarchyType.PARENT),
	TFOOT("tfoot", HierarchyType.PARENT),
	TH("th", HierarchyType.PARENT),
	THEAD("thead", HierarchyType.PARENT),
	TR("tr", HierarchyType.PARENT),
	UL("ul", HierarchyType.PARENT),
	VIDEO("video", HierarchyType.PARENT);

	public String getName() {

		return m_name;
	}

	public HierarchyType getBlockType() {

		return m_blockType;
	}

	private DomElementTag(String name, HierarchyType blockType) {

		m_name = name;
		m_blockType = blockType;
	}

	private final String m_name;
	private final HierarchyType m_blockType;
}
