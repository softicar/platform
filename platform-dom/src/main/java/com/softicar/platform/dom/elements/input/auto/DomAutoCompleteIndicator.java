package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.dom.elements.DomImage;
import com.softicar.platform.dom.style.CssPixel;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.styles.CssCursor;
import com.softicar.platform.dom.styles.CssPosition;

public class DomAutoCompleteIndicator extends DomImage {

	public DomAutoCompleteIndicator(DomAutoCompleteIndicatorType type) {

		super(type.getImage());

		addCssClass(type.getCssClass());

		setStyle(CssPosition.ABSOLUTE);
		setStyle(CssCursor.DEFAULT);
		setStyle(CssStyle.FILTER, "drop-shadow(0px 0px 1px #000)");
		setStyle(CssStyle.FILTER, "drop-shadow(0px 0px 1px #000)");
		setStyle(CssStyle.WIDTH, new CssPixel(10));
		setStyle(CssStyle.HEIGHT, new CssPixel(10));
		setStyle(CssStyle.LEFT, new CssPixel(-4));
		setStyle(CssStyle.TOP, new CssPixel(-4));

		setTitle(type.getTitle());
	}
}
