package com.softicar.platform.dom.elements.prompt;

import com.softicar.platform.dom.elements.button.DomButton;
import java.util.Optional;

class DomPromptButton extends DomButton {

	private final DomPromptButtonConfiguration configuration;

	public DomPromptButton(DomPromptButtonConfiguration configuration) {

		this.configuration = configuration;
		Optional//
			.ofNullable(configuration.getIcon())
			.ifPresent(this::setIcon);
		Optional//
			.ofNullable(configuration.getLabel())
			.ifPresent(this::setLabel);
		Optional//
			.ofNullable(configuration.getTitle())
			.ifPresent(this::setTitle);
		setClickCallback(this::handleClick);
	}

	private void handleClick() {

		executePrompt(//
			configuration.getPromptCallback()::accept,
			configuration.getPromptMessageSupplier().get(),
			configuration.getDefaultValueSupplier().get());
	}
}
