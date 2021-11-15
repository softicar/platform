package com.softicar.platform.emf.attribute.configuration;

import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.data.table.EmfAttributeDataTableStrategy;
import com.softicar.platform.emf.attribute.data.table.IEmfAttributeDataTableStrategy;
import com.softicar.platform.emf.attribute.display.IEmfAttributeFieldValueDisplayFactory;
import com.softicar.platform.emf.attribute.display.IEmfAttributeTableRowDisplayFactory;
import com.softicar.platform.emf.attribute.display.IEmfAttributeValueDisplayFactory;
import com.softicar.platform.emf.attribute.input.IEmfInput;
import com.softicar.platform.emf.attribute.input.IEmfInputFactory;
import com.softicar.platform.emf.data.table.column.handler.IEmfDataTableRowBasedColumnHandler;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.function.Supplier;

public class EmfAttributeConfiguration<R extends IEmfTableRow<R, ?>, V> {

	private final IEmfAttribute<R, V> attribute;
	private IEmfInputFactory<R, V> inputFactory;
	private IEmfAttributeTableRowDisplayFactory<R> displayFactory;
	private Supplier<IEmfAttributeDataTableStrategy<R>> dataTableStrategyFactory;
	private Supplier<IEmfDataTableRowBasedColumnHandler<R, V>> columnHandlerFactory;

	public EmfAttributeConfiguration(IEmfAttribute<R, V> attribute) {

		this.attribute = attribute;
		this.inputFactory = null;
		this.displayFactory = null;
		this.dataTableStrategyFactory = () -> new EmfAttributeDataTableStrategy<>(attribute);
		this.columnHandlerFactory = () -> new EmfAttributeColumnHandler<>(attribute);
	}

	// ------------------------------ input factory ------------------------------ //

	public IEmfInputFactory<R, V> getInputFactory() {

		return inputFactory;
	}

	public void setInputFactoryByEntity(IEmfInputFactory<R, V> inputFactory) {

		this.inputFactory = inputFactory;
	}

	public void setInputFactoryByValue(Supplier<IEmfInput<V>> inputFactory) {

		setInputFactoryByEntity(entity -> inputFactory.get());
	}

	// ------------------------------ display factory ------------------------------ //

	public IEmfAttributeTableRowDisplayFactory<R> getDisplayFactory() {

		return displayFactory;
	}

	public void setDisplayFactoryByEntity(IEmfAttributeTableRowDisplayFactory<R> displayFactory) {

		this.displayFactory = displayFactory;
	}

	public void setDisplayFactoryByEntityFieldValue(IEmfAttributeFieldValueDisplayFactory<R, V> displayFactory) {

		setDisplayFactoryByEntity(entity -> displayFactory.createDisplay(attribute.getField(), entity));
	}

	public void setDisplayFactoryByValue(IEmfAttributeValueDisplayFactory<V> displayFactory) {

		setDisplayFactoryByEntity(new EmfValueToTableRowDisplayFactory<>(attribute, displayFactory));
	}

	// ------------------------------ data table strategy factory ------------------------------ //

	public Supplier<IEmfAttributeDataTableStrategy<R>> getDataTableStrategyFactory() {

		return dataTableStrategyFactory;
	}

	public void setDataTableStrategyFactory(Supplier<IEmfAttributeDataTableStrategy<R>> dataTableStrategyFactory) {

		this.dataTableStrategyFactory = dataTableStrategyFactory;
	}

	// ------------------------------ column handler factory ------------------------------ //

	public Supplier<IEmfDataTableRowBasedColumnHandler<R, V>> getColumnHandlerFactory() {

		return columnHandlerFactory;
	}

	public void setColumnHandlerFactory(Supplier<IEmfDataTableRowBasedColumnHandler<R, V>> columnHandlerFactory) {

		this.columnHandlerFactory = columnHandlerFactory;
	}
}
