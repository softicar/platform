package com.softicar.platform.dom.elements.image.free.text;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.elements.DomColorEnum;
import com.softicar.platform.dom.elements.DomImage;
import java.awt.Font;

public class DomFreeTextImage extends DomImage {

	private final String text;

	public DomFreeTextImage(IDomFreeTextImageConfiguration configuration) {

		super(new DomFreeTextImageResource(configuration));
		this.text = configuration.getText();
	}

	public static DomFreeTextImage getVerticalHeader(IDisplayString text) {

		return getVerticalHeader(text.toString());
	}

	public static DomFreeTextImage getVerticalHeader(String text) {

		return new DomFreeTextImageBuilder()//
			.setText(text)
			.setRotate(true)
			.setFontColor(DomColorEnum.BLACK.getRgbColor())
			.setAwtFontStyle(Font.BOLD)
			.build();
	}

	public String getText() {

		return text;
	}
}
