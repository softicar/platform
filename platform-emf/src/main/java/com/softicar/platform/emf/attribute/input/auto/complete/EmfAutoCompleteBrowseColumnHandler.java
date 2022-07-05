package com.softicar.platform.emf.attribute.input.auto.complete;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputEngine;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableRowBasedColumnHandler;
import java.util.function.Consumer;

class EmfAutoCompleteBrowseColumnHandler<T> extends EmfDataTableRowBasedColumnHandler<T, String> {

	private final IDomAutoCompleteInputEngine<T> inputEngine;
	private final Consumer<T> clickConsumer;

	public EmfAutoCompleteBrowseColumnHandler(IDomAutoCompleteInputEngine<T> inputEngine, Consumer<T> clickConsumer) {

		this.inputEngine = inputEngine;
		this.clickConsumer = clickConsumer;
	}

	@Override
	public void buildCell(IEmfDataTableCell<T, String> cell, T entity) {

		cell.appendChild(new ContentDiv(entity));
	}

	private class ContentDiv extends DomDiv implements IDomClickEventHandler {

		private final T entity;

		public ContentDiv(T entity) {

			this.entity = entity;
			addCssClass(EmfCssClasses.EMF_ENTITY_INPUT_BROWSE_POPOVER_CELL_CONTENT);
			appendText(inputEngine.getDisplayString(entity));
		}

		@Override
		public void handleClick(IDomEvent event) {

			clickConsumer.accept(entity);
		}
	}
}
