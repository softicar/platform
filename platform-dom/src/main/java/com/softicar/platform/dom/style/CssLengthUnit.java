package com.softicar.platform.dom.style;

/**
 * Enumeration of all valid length units defined by the CSS standard.
 *
 * @author Oliver Richers
 */
public enum CssLengthUnit {

	// font-relative units
	EM("em"),
	EX("ex"),
	CH("ch"),
	REM("rem"),

	// viewport-percentage units
	VH("vh"),
	VW("vw"),
	VMIN("vmin"),
	VMAX("vmax"),

	// absolute units
	PX("px"),
	MM("mm"),
	Q("q"),
	IN("in"),
	PT("pt"),
	PC("pc"),

	// special units
	PERCENT("%");

	private String name;

	private CssLengthUnit(String name) {

		this.name = name;
	}

	@Override
	public String toString() {

		return name;
	}
}
