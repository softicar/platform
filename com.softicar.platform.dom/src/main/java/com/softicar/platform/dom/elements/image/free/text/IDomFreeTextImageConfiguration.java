package com.softicar.platform.dom.elements.image.free.text;

import com.softicar.platform.common.ui.color.RgbColor;

public interface IDomFreeTextImageConfiguration {

	boolean isRotate();

	boolean isAntiAliasing();

	int getFontSize();

	int getAwtFontStyle();

	String getFontFamily();

	RgbColor getFontColor();

	int getFontAlpha();

	String getText();
}
