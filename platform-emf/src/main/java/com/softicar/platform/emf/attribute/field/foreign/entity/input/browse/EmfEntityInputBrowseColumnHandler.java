package com.softicar.platform.emf.attribute.field.foreign.entity.input.browse;

import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.event.IDomClickEventHandler;
import com.softicar.platform.dom.event.IDomEvent;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableRowBasedColumnHandler;
import com.softicar.platform.emf.entity.IEmfEntity;
import java.util.function.Consumer;

class EmfEntityInputBrowseColumnHandler<E extends IEmfEntity<E, ?>> extends EmfDataTableRowBasedColumnHandler<E, String> {

	private final Consumer<E> clickConsumer;

	public EmfEntityInputBrowseColumnHandler(Consumer<E> clickConsumer) {

		this.clickConsumer = clickConsumer;
	}

	@Override
	public void buildCell(IEmfDataTableCell<E, String> cell, E entity) {

		cell.appendChild(new ContentDiv(entity));
	}

	private class ContentDiv extends DomDiv implements IDomClickEventHandler {

		private final E entity;

		public ContentDiv(E entity) {

			this.entity = entity;
			addCssClass(EmfCssClasses.EMF_ENTITY_INPUT_BROWSE_POPOVER_CELL_CONTENT);
			appendText(entity.toDisplay());
		}

		@Override
		public void handleClick(IDomEvent event) {

			clickConsumer.accept(entity);
		}
	}
}
