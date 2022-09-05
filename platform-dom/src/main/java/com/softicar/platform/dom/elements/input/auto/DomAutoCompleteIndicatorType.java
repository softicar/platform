package com.softicar.platform.dom.elements.input.auto;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.dom.DomCssClasses;
import com.softicar.platform.dom.DomI18n;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.style.ICssClass;
import java.util.Optional;

public enum DomAutoCompleteIndicatorType {

	AMBIGUOUS(DomImages.EMBLEM_AUTO_COMPLETE_VALUE_AMBIGUOUS, DomI18n.AMBIGUOUS_INPUT, DomCssClasses.DOM_AUTO_COMPLETE_INDICATOR_AMBIGUOUS),
	ILLEGAL(DomImages.EMBLEM_AUTO_COMPLETE_VALUE_ILLEGAL, DomI18n.ILLEGAL_INPUT, DomCssClasses.DOM_AUTO_COMPLETE_INDICATOR_ILLEGAL);

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

	public Optional<DomAutoCompleteIndicatorType> asOptional() {

		return Optional.of(this);
	}
}
