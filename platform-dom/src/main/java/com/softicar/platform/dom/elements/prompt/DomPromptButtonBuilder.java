package com.softicar.platform.dom.elements.prompt;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.parent.IDomParentElement;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A builder to create a {@link DomButton} which prompts the user to enter a
 * text.
 *
 * @author Oliver Richers
 * @author Alexander Schmidt
 */
public class DomPromptButtonBuilder {

	private final DomPromptButtonConfiguration configuration;

	public DomPromptButtonBuilder() {

		this.configuration = new DomPromptButtonConfiguration();
	}

	public DomPromptButtonBuilder setLabel(IDisplayString label) {

		configuration.setLabel(label);
		return this;
	}

	public DomPromptButtonBuilder setTitle(IDisplayString title) {

		configuration.setTitle(title);
		return this;
	}

	public DomPromptButtonBuilder setIcon(IResource resource) {

		configuration.setIcon(resource);
		return this;
	}

	/**
	 * Defines the callback to execute when the user submits the entered text.
	 * <p>
	 * Calling this method is <b>mandatory</b>.
	 *
	 * @param promptCallback
	 *            the callback function
	 * @return this builder
	 */
	public DomPromptButtonBuilder setPromptCallback(Consumer<String> promptCallback) {

		configuration.setPromptCallback(promptCallback);
		return this;
	}

	/**
	 * Defines a {@link Supplier} for the prompt message.
	 *
	 * @param messageSupplier
	 *            the supplier to dynamically create the prompt message
	 * @return this builder
	 */
	public DomPromptButtonBuilder setPromptMessageSupplier(Supplier<IDisplayString> messageSupplier) {

		configuration.setPromptMessageSupplier(messageSupplier);
		return this;
	}

	/**
	 * Defines a literal prompt message.
	 *
	 * @param message
	 *            the prompt message to display
	 * @return this builder
	 */
	public DomPromptButtonBuilder setPromptMessage(IDisplayString message) {

		return setPromptMessageSupplier(() -> message);
	}

	/**
	 * Defines the {@link Supplier} for the default value.
	 *
	 * @param defaultValueSupplier
	 *            the supplier to dynamically create the default value
	 * @return this builder
	 */
	public DomPromptButtonBuilder setDefaultValueSupplier(Supplier<String> defaultValueSupplier) {

		configuration.setDefaultValueSupplier(defaultValueSupplier);
		return this;
	}

	/**
	 * Defines a static default value.
	 *
	 * @param defaultValue
	 *            the default value
	 * @return this builder
	 */
	public DomPromptButtonBuilder setDefaultValue(String defaultValue) {

		return setDefaultValueSupplier(() -> defaultValue);
	}

	/**
	 * Creates the new instance of the {@link DomButton}.
	 *
	 * @throws IllegalStateException
	 *             if no prompt callback has been defined by calling
	 *             {@link #setPromptCallback(Consumer)}
	 */
	public IDomElement build() {

		if (configuration.getPromptCallback() == null) {
			throw new IllegalStateException("No callback defined.");
		}

		return new DomPromptButton(configuration);
	}

	public IDomElement buildAndAppendTo(IDomParentElement element) {

		return element.appendChild(build());
	}
}
