package com.softicar.platform.dom.elements.message.style;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.style.ICssClass;
import java.util.Objects;

public enum DomMessageType {

	ERROR(DomElementsImages.DIAGNOSTIC_ERROR, DomCssPseudoClasses.ERROR),
	INFO(DomElementsImages.DIAGNOSTIC_INFO, DomCssPseudoClasses.INFO),
	SUCCESS(DomElementsImages.DIAGNOSTIC_SUCCESS, DomCssPseudoClasses.SUCCESS),
	WARNING(DomElementsImages.DIAGNOSTIC_WARNING, DomCssPseudoClasses.WARNING)
	//
	;

	private final IResourceSupplier iconSupplier;
	private final ICssClass styleClass;

	private DomMessageType(IResourceSupplier iconSupplier, ICssClass styleClass) {

		this.iconSupplier = Objects.requireNonNull(iconSupplier);
		this.styleClass = Objects.requireNonNull(styleClass);
	}

	public IResource getIcon() {

		return iconSupplier.getResource();
	}

	public ICssClass getStyleClass() {

		return styleClass;
	}
}
