package com.softicar.platform.emf.form.popup;

import com.softicar.platform.dom.elements.popup.configuration.DomPopupConfiguration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;

public class EmfFormPopupConfiguration implements IEmfFormPopupConfiguration {

	private final Collection<Consumer<DomPopupConfiguration>> modifiers;

	public EmfFormPopupConfiguration() {

		this.modifiers = new ArrayList<>();
	}

	public void configure(Consumer<DomPopupConfiguration> configurationModifier) {

		Objects.requireNonNull(configurationModifier);
		this.modifiers.add(configurationModifier);
	}

	@Override
	public Collection<Consumer<DomPopupConfiguration>> getConfigurationModifiers() {

		return modifiers;
	}
}
