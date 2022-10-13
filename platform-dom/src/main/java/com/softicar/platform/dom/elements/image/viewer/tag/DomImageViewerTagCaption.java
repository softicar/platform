package com.softicar.platform.dom.elements.image.viewer.tag;

import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomTestMarker;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.image.viewer.DomImageViewerRotation;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.dom.style.CssStyleAttribute;
import java.util.Collection;
import java.util.List;

class DomImageViewerTagCaption extends DomDiv {

	public DomImageViewerTagCaption(String text) {

		addMarker(DomTestMarker.IMAGE_VIEWER_TAG_CAPTION);
		addCssClass(DomCssClasses.DOM_IMAGE_VIEWER_TAG_CAPTION);
		appendText(text);
	}

	public void applyRotation(DomImageViewerRotation rotation) {

		getRotationStyles(rotation).forEach(this::setStyle);
	}

	private Collection<CssStyleAttribute> getRotationStyles(DomImageViewerRotation rotation) {

		return switch (rotation) {
		case _0:
		case _90:
		case _270:
			yield List
				.of(//
					new CssStyleAttribute(CssStyle.INSET, "unset"),
					new CssStyleAttribute(CssStyle.TRANSFORM_ORIGIN, "unset"),
					new CssStyleAttribute(CssStyle.TRANSFORM, "translate(0, -100%) translate(2px, 0)"));
		case _180:
			yield List
				.of(//
					new CssStyleAttribute(CssStyle.INSET, "unset"),
					new CssStyleAttribute(CssStyle.BOTTOM, "0"),
					new CssStyleAttribute(CssStyle.RIGHT, "0"),
					new CssStyleAttribute(CssStyle.TRANSFORM_ORIGIN, "right bottom"),
					new CssStyleAttribute(CssStyle.TRANSFORM, "rotate(180deg) translate(100%, 0) translate(2px, 0)"));
		};
	}
}
