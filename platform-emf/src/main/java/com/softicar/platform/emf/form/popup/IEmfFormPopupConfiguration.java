package com.softicar.platform.emf.form.popup;

import com.softicar.platform.dom.elements.popup.configuration.DomPopupConfiguration;
import java.util.Collection;
import java.util.function.Consumer;

public interface IEmfFormPopupConfiguration {

	Collection<Consumer<DomPopupConfiguration>> getConfigurationModifiers();
}
