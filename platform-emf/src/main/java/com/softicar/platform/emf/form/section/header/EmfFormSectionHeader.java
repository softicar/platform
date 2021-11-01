package com.softicar.platform.emf.form.section.header;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import java.util.Optional;
import java.util.function.Supplier;

public class EmfFormSectionHeader implements IEmfFormSectionHeader {

	private IDisplayString caption;
	private Optional<IResource> icon;
	private Optional<Supplier<IDisplayString>> placeholder;

	public EmfFormSectionHeader() {

		this.caption = IDisplayString.EMPTY;
		this.icon = Optional.empty();
		this.placeholder = Optional.empty();
	}

	public EmfFormSectionHeader setCaption(IDisplayString caption) {

		this.caption = caption;
		return this;
	}

	public EmfFormSectionHeader setIcon(IResource icon) {

		this.icon = Optional.of(icon);
		return this;
	}

	public EmfFormSectionHeader setPlaceholder(Supplier<IDisplayString> placeholder) {

		this.placeholder = Optional.of(placeholder);
		return this;
	}

	@Override
	public IDisplayString getDisplayString() {

		return caption;
	}

	@Override
	public Optional<IResource> getIcon() {

		return icon;
	}

	@Override
	public Optional<Supplier<IDisplayString>> getPlaceholder() {

		return placeholder;
	}
}
