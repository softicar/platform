package com.softicar.platform.emf.attribute.configuration;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.dom.elements.wiki.DomWikiDivBuilder;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.display.EmfDummyDisplay;
import com.softicar.platform.emf.attribute.display.IEmfAttributeFieldValueDisplayFactory;
import com.softicar.platform.emf.attribute.display.IEmfAttributeTableRowDisplayFactory;
import com.softicar.platform.emf.attribute.display.IEmfAttributeValueDisplayFactory;
import com.softicar.platform.emf.attribute.input.EmfDummyInput;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.attribute.input.IEmfInputFactory;
import com.softicar.platform.emf.data.table.column.handler.IEmfDataTableRowBasedColumnHandler;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class EmfAttributeConfiguration<R extends IEmfTableRow<R, ?>, V> {

	private final IEmfAttribute<R, V> attribute;
	private IEmfInputFactory<R, V> inputFactory;
	private IEmfAttributeTableRowDisplayFactory<R> displayFactory;
	private Supplier<IEmfDataTableRowBasedColumnHandler<R, V>> columnHandlerFactory;
	private Supplier<IDomElement> helpDisplayFactory;

	public EmfAttributeConfiguration(IEmfAttribute<R, V> attribute) {

		this.attribute = attribute;
		this.inputFactory = EmfDummyInput::new;
		this.displayFactory = EmfDummyDisplay::new;
		this.columnHandlerFactory = () -> new EmfAttributeColumnHandler<>(attribute);
		this.helpDisplayFactory = null;
	}

	// ------------------------------ input factory ------------------------------ //

	public IEmfInputFactory<R, V> getInputFactory() {

		return inputFactory;
	}

	public void setInputFactoryByEntity(IEmfInputFactory<R, V> inputFactory) {

		this.inputFactory = Objects.requireNonNull(inputFactory);
	}

	public void setInputFactoryByValue(Supplier<IEmfInput<V>> inputFactory) {

		setInputFactoryByEntity(entity -> inputFactory.get());
	}

	// ------------------------------ display factory ------------------------------ //

	public IEmfAttributeTableRowDisplayFactory<R> getDisplayFactory() {

		return displayFactory;
	}

	public void setDisplayFactoryByEntity(IEmfAttributeTableRowDisplayFactory<R> displayFactory) {

		this.displayFactory = Objects.requireNonNull(displayFactory);
	}

	public void setDisplayFactoryByEntityFieldValue(IEmfAttributeFieldValueDisplayFactory<R, V> displayFactory) {

		setDisplayFactoryByEntity(entity -> displayFactory.createDisplay(attribute.getField(), entity));
	}

	public void setDisplayFactoryByValue(IEmfAttributeValueDisplayFactory<V> displayFactory) {

		setDisplayFactoryByEntity(new EmfValueToTableRowDisplayFactory<>(attribute, displayFactory));
	}

	// ------------------------------ column handler factory ------------------------------ //

	public Supplier<IEmfDataTableRowBasedColumnHandler<R, V>> getColumnHandlerFactory() {

		return columnHandlerFactory;
	}

	public void setColumnHandlerFactory(Supplier<IEmfDataTableRowBasedColumnHandler<R, V>> columnHandlerFactory) {

		this.columnHandlerFactory = columnHandlerFactory;
	}

	// ------------------------------ help display factory ------------------------------ //

	public Optional<Supplier<IDomElement>> getHelpDisplayFactory() {

		return Optional.ofNullable(helpDisplayFactory);
	}

	public void setHelpDisplayFactory(Supplier<IDomElement> helpDisplayFactory) {

		this.helpDisplayFactory = helpDisplayFactory;
	}

	public void setHelpDisplay(IDisplayString text) {

		setHelpDisplayFactory(() -> new EmfAttributeHelpTextElement(text, false));
	}

	public void setHelpDisplay(Supplier<DomWikiDivBuilder> wikiDivBuilderFactory) {

		setHelpDisplayFactory(() -> new EmfAttributeHelpTextElement(wikiDivBuilderFactory));
	}

	public void setHelpDisplayByWikiText(IDisplayString wikiText) {

		setHelpDisplayFactory(() -> new EmfAttributeHelpTextElement(wikiText, true));
	}
}
