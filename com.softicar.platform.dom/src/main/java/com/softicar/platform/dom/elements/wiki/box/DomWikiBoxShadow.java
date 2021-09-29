package com.softicar.platform.dom.elements.wiki.box;

import com.softicar.platform.common.core.exceptions.SofticarUnknownEnumConstantException;
import com.softicar.platform.common.ui.color.IColor;
import com.softicar.platform.common.ui.wiki.element.tag.WikiBoxType;
import com.softicar.platform.dom.elements.DomColorEnum;
import com.softicar.platform.dom.style.CssPixel;
import com.softicar.platform.dom.style.ICssLength;
import com.softicar.platform.dom.styles.CssBoxShadow;

// TODO Should be deleted and done via css
public class DomWikiBoxShadow extends CssBoxShadow {

	public static final ICssLength SIZE = CssPixel.FIVE;

	public DomWikiBoxShadow(WikiBoxType boxType) {

		super(ICssLength.ZERO, ICssLength.ZERO, SIZE, ICssLength.ZERO, getShadowColor(boxType));
	}

	private static IColor getShadowColor(WikiBoxType boxType) {

		switch (boxType) {
		case ERROR:
			return DomColorEnum.MESSAGE_RED_DARK;
		case INFO:
			return DomColorEnum.MESSAGE_BLUE_DARK;
		case WARNING:
			return DomColorEnum.MESSAGE_YELLOW_DARKER;
		}
		throw new SofticarUnknownEnumConstantException(boxType);
	}
}
