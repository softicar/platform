package com.softicar.platform.dom.elements.message.style;

import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.common.io.resource.supplier.IResourceSupplier;
import com.softicar.platform.dom.DomCssPseudoClasses;
import com.softicar.platform.dom.DomImages;
import com.softicar.platform.dom.style.ICssClass;
import java.util.Objects;

public enum DomMessageType {

	ERROR(DomImages.DIAGNOSTIC_ERROR, DomCssPseudoClasses.ERROR),
	INFO(DomImages.DIAGNOSTIC_INFO, DomCssPseudoClasses.INFO),
	SUCCESS(DomImages.DIAGNOSTIC_SUCCESS, DomCssPseudoClasses.SUCCESS),
	WARNING(DomImages.DIAGNOSTIC_WARNING, DomCssPseudoClasses.WARNING)
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
