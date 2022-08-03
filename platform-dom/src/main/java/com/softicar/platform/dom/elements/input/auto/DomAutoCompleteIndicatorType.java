package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.elements.DomElementsCssClasses;
import com.softicar.platform.dom.style.ICssClass;

public enum DomAutoCompleteIndicatorType {

	// TODO remove unnecessary types
	AMBIGUOUS(DomImages.EMBLEM_AUTO_COMPLETE_VALUE_AMBIGUOUS, DomI18n.AMBIGUOUS_INPUT, DomElementsCssClasses.DOM_AUTO_COMPLETE_INDICATOR_AMBIGUOS),
	DEFAULT(DomImages.EMBLEM_AUTO_COMPLETE_GENERIC, DomI18n.AUTO_COMPLETE_INPUT_ELEMENT, DomElementsCssClasses.DOM_AUTO_COMPLETE_INDICATOR_DEFAULT),
	ILLEGAL(DomImages.EMBLEM_AUTO_COMPLETE_VALUE_ILLEGAL, DomI18n.ILLEGAL_INPUT, DomElementsCssClasses.DOM_AUTO_COMPLETE_INDICATOR_ILLEGAL),
	MISSING(DomImages.EMBLEM_AUTO_COMPLETE_VALUE_MISSING, DomI18n.INPUT_REQUIRED, DomElementsCssClasses.DOM_AUTO_COMPLETE_INDICATOR_MISSING),
	NOT_OKAY(DomImages.EMBLEM_AUTO_COMPLETE_NOT_OKAY, DomI18n.INVALID_INPUT, DomElementsCssClasses.DOM_AUTO_COMPLETE_INDICATOR_NOT_OKAY),
	VALID(DomImages.EMBLEM_AUTO_COMPLETE_VALUE_VALID, DomI18n.VALID_INPUT, DomElementsCssClasses.DOM_AUTO_COMPLETE_INDICATOR_VALID);

	private final IResourceSupplier image;
	private final IDisplayString title;
	private final ICssClass cssClass;

	private DomAutoCompleteIndicatorType(IResourceSupplier image, IDisplayString title, ICssClass cssClass) {

		this.image = image;
		this.title = title;
		this.cssClass = cssClass;
	}

	public IResource getImage() {

		return image.getResource();
	}

	public IDisplayString getTitle() {

		return title;
	}

	public ICssClass getCssClass() {

		return cssClass;
	}
}
