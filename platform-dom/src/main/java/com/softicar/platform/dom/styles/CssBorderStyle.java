package com.softicar.platform.dom.styles;

import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.ICssStyleAttribute;

public enum CssBorderStyle implements ICssStyleAttribute {
	NONE("none"),           // no border (invisible border)
	HIDDEN("hidden"),       // no border (invisible border)
	DOTTED("dotted"),       // dotted
	DASHED("dashed"),       // dashed
	SOLID("solid"),         // solid
	DOUBLE("double"),       // double solid
	GROOVE("groove"),       // 3D effect
	RIDGE("ridge"),         // 3D effect
	INSET("inset"),         // 3D effect
	OUTSET("outset");       // 3D effect

	@Override
	public CssStyle getStyle() {

		return CssStyle.BORDER_STYLE;
	}

	@Override
	public String getValue() {

		return name;
	}

	private CssBorderStyle(String name) {

		this.name = name;
	}

	private String name;
}
