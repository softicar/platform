package com.softicar.platform.dom.elements.prompt;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.DomI18n;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * The specialized configuration used by {@link DomPromptButtonBuilder}.
 *
 * @author Alexander Schmidt
 */
class DomPromptButtonConfiguration {

	private IResource icon = null;
	private IDisplayString label = null;
	private IDisplayString title = null;
	private Consumer<String> promptCallback = null;
	private Supplier<IDisplayString> promptMessageSupplier = () -> DomI18n.PLEASE_ENTER_A_TEXT;
	private Supplier<String> defaultValueSupplier = () -> "";

	public IResource getIcon() {

		return icon;
	}

	public void setIcon(IResource icon) {

		this.icon = icon;
	}

	public IDisplayString getLabel() {

		return label;
	}

	public void setLabel(IDisplayString label) {

		this.label = label;
	}

	public IDisplayString getTitle() {

		return title;
	}

	public void setTitle(IDisplayString title) {

		this.title = title;
	}

	public Consumer<String> getPromptCallback() {

		return promptCallback;
	}

	public void setPromptCallback(Consumer<String> promptCallback) {

		this.promptCallback = promptCallback;
	}

	public Supplier<IDisplayString> getPromptMessageSupplier() {

		return promptMessageSupplier;
	}

	public void setPromptMessageSupplier(Supplier<IDisplayString> promptMessageSupplier) {

		this.promptMessageSupplier = promptMessageSupplier;
	}

	public Supplier<String> getDefaultValueSupplier() {

		return defaultValueSupplier;
	}

	public void setDefaultValueSupplier(Supplier<String> defaultValueSupplier) {

		this.defaultValueSupplier = defaultValueSupplier;
	}
}
