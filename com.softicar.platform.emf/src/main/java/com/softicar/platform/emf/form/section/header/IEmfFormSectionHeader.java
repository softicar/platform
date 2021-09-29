package com.softicar.platform.emf.form.section.header;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import java.util.Optional;
import java.util.function.Supplier;

public interface IEmfFormSectionHeader {

	Optional<IResource> getIcon();

	IDisplayString getDisplayString();

	default Optional<Supplier<IDisplayString>> getPlaceholder() {

		return Optional.empty();
	}
}
