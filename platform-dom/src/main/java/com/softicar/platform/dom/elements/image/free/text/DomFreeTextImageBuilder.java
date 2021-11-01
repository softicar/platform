package com.softicar.platform.dom.elements.image.free.text;

import com.softicar.platform.common.ui.color.RgbColor;
import com.softicar.platform.dom.elements.DomColorEnum;
import java.awt.Font;

public class DomFreeTextImageBuilder implements IDomFreeTextImageConfiguration {

	private String text;
	private boolean rotate;
	private boolean antiAliasing;
	private int fontSize;
	private RgbColor fontColor;
	private int fontAlpha;
	private int awtFontStyle;
	private String fontFamily;

	public DomFreeTextImageBuilder() {

		this.rotate = false;
		this.antiAliasing = true;
		this.fontSize = 12;
		this.fontColor = DomColorEnum.BLACK.getRgbColor();
		this.fontAlpha = 255;
		this.awtFontStyle = Font.PLAIN;
		this.fontFamily = "Helvetica";
	}

	public DomFreeTextImageBuilder setText(String text) {

		this.text = text;
		return this;
	}

	public DomFreeTextImageBuilder setRotate(boolean rotate) {

		this.rotate = rotate;
		return this;
	}

	public DomFreeTextImageBuilder setAntiAliasing(boolean antiAliasing) {

		this.antiAliasing = antiAliasing;
		return this;
	}

	public DomFreeTextImageBuilder setFontSize(int fontSize) {

		this.fontSize = fontSize;
		return this;
	}

	public DomFreeTextImageBuilder setFontColor(RgbColor fontColor) {

		this.fontColor = fontColor;
		return this;
	}

	public DomFreeTextImageBuilder setFontAlpha(int fontAlpha) {

		this.fontAlpha = fontAlpha;
		return this;
	}

	public DomFreeTextImageBuilder setAwtFontStyle(int awtFontStyle) {

		this.awtFontStyle = awtFontStyle;
		return this;
	}

	public DomFreeTextImageBuilder setFontFamily(String fontFamily) {

		this.fontFamily = fontFamily;
		return this;
	}

	public DomFreeTextImage build() {

		return new DomFreeTextImage(this);
	}

	@Override
	public String getText() {

		return text;
	}

	@Override
	public boolean isRotate() {

		return rotate;
	}

	@Override
	public boolean isAntiAliasing() {

		return antiAliasing;
	}

	@Override
	public int getFontSize() {

		return fontSize;
	}

	@Override
	public int getAwtFontStyle() {

		return awtFontStyle;
	}

	@Override
	public String getFontFamily() {

		return fontFamily;
	}

	@Override
	public RgbColor getFontColor() {

		return fontColor;
	}

	@Override
	public int getFontAlpha() {

		return fontAlpha;
	}
}
