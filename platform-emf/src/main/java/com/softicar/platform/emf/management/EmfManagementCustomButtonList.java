package com.softicar.platform.emf.management;

import com.softicar.platform.common.core.interfaces.Predicates;
import com.softicar.platform.dom.element.IDomElement;
import com.softicar.platform.emf.data.table.IEmfDataTableActionCell;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

public class EmfManagementCustomButtonList<R extends IEmfTableRow<R, ?>> {

	private final Collection<ButtonDefinition> buttonDefinitions;

	public EmfManagementCustomButtonList() {

		this.buttonDefinitions = new ArrayList<>();
	}

	public void addButton(Function<R, IDomElement> factory) {

		buttonDefinitions.add(new ButtonDefinition(factory));
	}

	public void addButton(Predicate<R> predicate, Function<R, IDomElement> factory) {

		buttonDefinitions.add(new ButtonDefinition(predicate, factory));
	}

	public void appendButtonsToCell(IEmfDataTableActionCell<R> cell, R tableRow) {

		buttonDefinitions.forEach(definition -> definition.appendButton(cell, tableRow));
	}

	private class ButtonDefinition {

		private final Predicate<R> predicate;
		private final Function<R, IDomElement> factory;

		public ButtonDefinition(Function<R, IDomElement> factory) {

			this.predicate = Predicates.always();
			this.factory = factory;
		}

		public ButtonDefinition(Predicate<R> predicate, Function<R, IDomElement> factory) {

			this.predicate = predicate;
			this.factory = factory;
		}

		public void appendButton(IEmfDataTableActionCell<R> cell, R tableRow) {

			if (predicate.test(tableRow)) {
				cell.appendChild(factory.apply(tableRow));
			}
		}
	}
}
