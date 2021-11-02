package com.softicar.platform.emf.attribute.field.foreign.entity.collection.set;

import com.softicar.platform.common.core.entity.IEntity;
import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.ui.wiki.element.tag.WikiBoxType;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomCell;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputEngine;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.tables.DomDataTable;
import com.softicar.platform.dom.elements.wiki.box.DomWikiBoxShadow;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.attribute.field.foreign.entity.collection.EmfUnsavedChangedMessageDiv;
import com.softicar.platform.emf.attribute.field.foreign.entity.collection.list.EmfForeignEntityListInput;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInputEngine;
import com.softicar.platform.emf.attribute.input.AbstractEmfInputDiv;
import com.softicar.platform.emf.collection.set.IEmfEntitySet;
import com.softicar.platform.emf.data.table.EmfDataTableEmptyRow;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * TODO reduce redundancy to {@link EmfForeignEntityListInput}
 */
public class EmfForeignEntitySetInput<R extends IEmfTableRow<R, ?>, S extends IEmfEntitySet<S, F>, F extends IEmfEntity<F, ?>> extends AbstractEmfInputDiv<S> {

	private final EmfForeignEntitySetAttribute<R, S, F> attribute;
	private final R tableRow;
	private final IDomAutoCompleteInputEngine<F> inputEngine;
	private final SetTable table;
	private final EmfUnsavedChangedMessageDiv unsavedChangedMessageDiv;
	private boolean mandatory;

	public EmfForeignEntitySetInput(EmfForeignEntitySetAttribute<R, S, F> attribute, R tableRow) {

		this.attribute = attribute;
		this.tableRow = tableRow;
		this.inputEngine = new EmfEntityInputEngine<>(tableRow, attribute.getCollectionTable().getElementTable(), attribute.getValidator());
		this.table = new SetTable();
		this.unsavedChangedMessageDiv = new EmfUnsavedChangedMessageDiv().hide();
		this.mandatory = false;

		appendChild(
			new DomPopupButton()//
				.setPopupFactory(() -> new EntityPopup())
				.setIcon(DomElementsImages.ENTRY_ADD.getResource())
				.setLabel(EmfI18n.ADD_ENTRY));
		appendChild(table);
		appendChild(unsavedChangedMessageDiv);
	}

	@Override
	public S getValueOrThrow() {

		return attribute.getCollectionTable().getOrInsert(table.getEntities());
	}

	@Override
	public void setValue(S entitySet) {

		if (entitySet != null) {
			table.setEntities(entitySet.getElements());
		} else {
			table.setEntities(new TreeSet<>());
		}
	}

	@Override
	public void executePostSaveHook() {

		setValue(attribute.getValue(tableRow));
	}

	@Override
	public void setMandatory(boolean mandatory) {

		this.mandatory = mandatory;
	}

	private void refreshUnsavedChangesHighlight(boolean unsaved) {

		if (unsaved) {
			table.setStyle(new DomWikiBoxShadow(WikiBoxType.WARNING));
			unsavedChangedMessageDiv.show();
		} else {
			table.unsetStyle(CssStyle.BOX_SHADOW);
			unsavedChangedMessageDiv.hide();
		}
	}

	private IDisplayString getElementTableTitle() {

		return Optional.ofNullable(attribute.getCollectionTable().getElementTable().getTitle()).orElse(IDisplayString.EMPTY);
	}

	private class SetTable extends DomDataTable {

		private Set<F> entities;
		private boolean changed;

		public SetTable() {

			this.entities = null;
			this.changed = false;

			DomRow headerRow = getHead().appendRow();
			headerRow.appendHeaderCell(EmfI18n.ACTIONS);
			headerRow.appendHeaderCell(getElementTableTitle().toString());
		}

		public void setEntities(Set<F> entities) {

			this.entities = new TreeSet<>(entities);
			this.changed = false;
			refresh();
		}

		public Set<F> getEntities() {

			return entities;
		}

		public void refresh() {

			getBody().removeChildren();

			for (F entity: entities//
				.stream()
				.sorted(Comparator.comparing(IEntity::toDisplay))
				.collect(Collectors.toList())) {
				getBody().appendChild(new ListRow(entity));
			}

			if (entities.isEmpty()) {
				getBody().appendChild(new EmfDataTableEmptyRow(2));
			}

			refreshUnsavedChangesHighlight(changed);
		}

		public void addEntity(F entity) {

			if (entities.add(entity)) {
				this.changed = true;
				refresh();
			}
		}

		public void removeEntity(F entity) {

			if (entities.remove(entity)) {
				this.changed = true;
				refresh();
			}
		}

		private class ListRow extends DomRow {

			private final F foreignEntity;

			public ListRow(F foreignEntity) {

				this.foreignEntity = foreignEntity;

				DomCell actionCell = appendCell();
				actionCell
					.appendChild(
						new DomButton()//
							.setClickCallback(this::remove)
							.setIcon(DomElementsImages.ENTRY_REMOVE.getResource())
							.setTitle(EmfI18n.REMOVE_ENTRY)
							.setConfirmationMessage(EmfI18n.ARE_YOU_SURE_QUESTION));

				appendCell(foreignEntity != null? foreignEntity.toDisplay() : DisplayString.EMPTY);
			}

			private void remove() {

				removeEntity(foreignEntity);
			}
		}
	}

	private class EntityPopup extends DomPopup {

		private final EmfEntityInput<F> entityInput;

		public EntityPopup() {

			this.entityInput = new EmfEntityInput<>(inputEngine);
			this.entityInput.setMandatory(mandatory);

			setCaption(EmfI18n.ADD_ENTRY);

			appendChild(new DomLabelGrid().add(getElementTableTitle(), entityInput));
			appendNewChild(DomElementTag.HR);
			appendChild(
				new DomButton()//
					.setClickCallback(this::save)
					.setIcon(EmfImages.ENTITY_SAVE.getResource())
					.setLabel(EmfI18n.SAVE));
			appendCancelButton();
		}

		private void save() {

			F entity = entityInput.getValueOrThrow();
			if (entity == null) {
				throw new SofticarUserException(EmfI18n.PLEASE_SELECT_A_VALID_ENTRY);
			}

			table.addEntity(entity);
			hide();
		}
	}
}
