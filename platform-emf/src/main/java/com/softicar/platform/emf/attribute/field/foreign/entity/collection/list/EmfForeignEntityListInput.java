package com.softicar.platform.emf.attribute.field.foreign.entity.collection.list;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.number.parser.IntegerParser;
import com.softicar.platform.common.math.Clamping;
import com.softicar.platform.common.ui.wiki.element.tag.WikiBoxType;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomCell;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.elements.input.DomIntegerInput;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.prompt.DomPromptButtonBuilder;
import com.softicar.platform.dom.elements.tables.DomDataTable;
import com.softicar.platform.dom.elements.wiki.box.DomWikiBoxShadow;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.attribute.field.foreign.entity.collection.EmfUnsavedChangedMessageDiv;
import com.softicar.platform.emf.attribute.field.foreign.entity.collection.set.EmfForeignEntitySetInput;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInputEngine;
import com.softicar.platform.emf.attribute.input.AbstractEmfInputDiv;
import com.softicar.platform.emf.attribute.input.EmfInputException;
import com.softicar.platform.emf.collection.list.IEmfEntityList;
import com.softicar.platform.emf.data.table.empty.EmfDataTableEmptyTablePlaceholderRow;
import com.softicar.platform.emf.data.table.util.ListShifter;
import com.softicar.platform.emf.entity.IEmfEntity;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * TODO reduce redundancy to {@link EmfForeignEntitySetInput}
 */
public class EmfForeignEntityListInput<R extends IEmfTableRow<R, ?>, L extends IEmfEntityList<L, F>, F extends IEmfEntity<F, ?>>
		extends AbstractEmfInputDiv<L> {

	private final EmfForeignEntityListAttribute<R, L, F> attribute;
	private final R tableRow;
	private final EmfEntityInputEngine<F> inputEngine;
	private final ListTable table;
	private final EmfUnsavedChangedMessageDiv unsavedChangedMessageDiv;
	private boolean mandatory;

	public EmfForeignEntityListInput(EmfForeignEntityListAttribute<R, L, F> attribute, R tableRow) {

		this.attribute = attribute;
		this.tableRow = tableRow;
		this.inputEngine = new EmfEntityInputEngine<>(tableRow, attribute.getCollectionTable().getElementTable(), attribute.getValidator());
		this.table = new ListTable();
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
	public L getValueOrThrow() throws EmfInputException {

		return attribute.getCollectionTable().getOrInsert(table.getEntities());
	}

	@Override
	public void setValue(L entityList) {

		if (entityList != null) {
			table.setEntities(entityList.getElements());
		} else {
			table.setEntities(new ArrayList<>());
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

		return Optional//
			.ofNullable(
				attribute//
					.getCollectionTable()
					.getElementTable()
					.getTitle())
			.orElse(IDisplayString.EMPTY);
	}

	private class ListTable extends DomDataTable {

		private List<F> entities;
		private boolean changed;

		public ListTable() {

			this.entities = null;
			this.changed = false;

			DomRow headerRow = getHead().appendRow();
			headerRow.appendHeaderCell(EmfI18n.ACTIONS);
			headerRow.appendHeaderCell(EmfI18n.INDEX);
			headerRow.appendHeaderCell(getElementTableTitle().toString());
		}

		public List<F> getEntities() {

			return entities;
		}

		public void setEntities(List<F> entities) {

			this.entities = new ArrayList<>(entities);
			this.changed = false;
			refresh();
		}

		public boolean contains(F entity) {

			return entities.contains(entity);
		}

		public int size() {

			return Optional.ofNullable(entities).map(it -> it.size()).orElse(0);
		}

		public void refresh() {

			getBody().removeChildren();

			for (int i = 0; i < entities.size(); i++) {
				F entity = entities.get(i);
				getBody().appendChild(new ListRow(entity, i));
			}

			if (entities.isEmpty()) {
				getBody().appendChild(new EmfDataTableEmptyTablePlaceholderRow(3));
			}

			refreshUnsavedChangesHighlight(changed);
		}

		public void addEntity(int index, F entity) {

			if (!contains(entity)) {
				int sizeBefore = entities.size();
				entities.add(clampIndexForAdding(index), entity);
				if (entities.size() > sizeBefore) {
					this.changed = true;
					refresh();
				}
			} else {
				throw new SofticarUserException(EmfI18n.THIS_ENTRY_IS_ALREADY_CONTAINED_IN_THE_LIST);
			}
		}

		public void removeEntity(F entity) {

			if (entities.remove(entity)) {
				this.changed = true;
				refresh();
			}
		}

		public void moveEntity(Integer fromIndex, Integer toIndex) {

			ListShifter.shiftList(entities, clampIndexForMoving(fromIndex), clampIndexForMoving(toIndex));
			this.changed = true;
			refresh();
		}

		private Integer clampIndexForAdding(int index) {

			return Clamping.clamp(0, entities.size(), index);
		}

		private Integer clampIndexForMoving(int index) {

			return Clamping.clamp(0, entities.size() - 1, index);
		}

		private class ListRow extends DomRow {

			private final F foreignEntity;
			private final int index;

			public ListRow(F foreignEntity, int index) {

				this.foreignEntity = foreignEntity;
				this.index = index;

				DomCell actionCell = appendCell();
				actionCell
					.appendChild(
						new DomButton()//
							.setClickCallback(() -> moveEntity(index, index - 1))
							.setIcon(DomElementsImages.MOVE_UP.getResource())
							.setTitle(EmfI18n.MOVE_UP));
				actionCell
					.appendChild(
						new DomButton()//
							.setClickCallback(() -> moveEntity(index, index + 1))
							.setIcon(DomElementsImages.MOVE_DOWN.getResource())
							.setTitle(EmfI18n.MOVE_DOWN));
				actionCell
					.appendChild(
						new DomPromptButtonBuilder()//
							.setIcon(DomElementsImages.MOVE_VERTICAL.getResource())
							.setTitle(EmfI18n.MOVE_TO_POSITION)
							.setPromptMessage(EmfI18n.ENTER_TARGET_INDEX)
							.setPromptCallback(this::move)
							.build());
				actionCell
					.appendChild(
						new DomButton()//
							.setClickCallback(this::remove)
							.setIcon(DomElementsImages.ENTRY_REMOVE.getResource())
							.setTitle(EmfI18n.REMOVE_ENTRY)
							.setConfirmationMessage(EmfI18n.ARE_YOU_SURE_QUESTION));
				appendCell(index);
				appendCell(foreignEntity != null? foreignEntity.toDisplay() : DisplayString.EMPTY);
			}

			private void move(String indexString) {

				int toIndex = IntegerParser//
					.parse(indexString)
					.orElseThrow(() -> new SofticarUserException(EmfI18n.PLEASE_SPECIFY_A_VALID_INDEX));
				moveEntity(index, toIndex);
			}

			private void remove() {

				removeEntity(foreignEntity);
			}
		}
	}

	private class EntityPopup extends DomPopup {

		private final DomIntegerInput indexInput;
		private final EmfEntityInput<F> entityInput;

		public EntityPopup() {

			this.indexInput = new DomIntegerInput(table.size());
			this.entityInput = new EmfEntityInput<>(inputEngine);
			this.entityInput.setMandatory(mandatory);

			setCaption(EmfI18n.ADD_ENTRY);

			appendChild(
				new DomLabelGrid()//
					.add(EmfI18n.INDEX, indexInput)
					.add(getElementTableTitle(), entityInput));
			appendNewChild(DomElementTag.HR);
			appendChild(
				new DomButton()//
					.setClickCallback(this::save)
					.setIcon(EmfImages.ENTITY_SAVE.getResource())
					.setLabel(EmfI18n.SAVE));
			appendCancelButton();
		}

		private void save() {

			Integer index = indexInput.getInteger();
			if (index == null) {
				throw new SofticarUserException(EmfI18n.PLEASE_SPECIFY_A_VALID_INDEX);
			}

			F entity = entityInput.getValueOrThrow();
			if (entity == null) {
				throw new SofticarUserException(EmfI18n.PLEASE_SELECT_A_VALID_ENTRY);
			}

			table.addEntity(index, entity);
			hide();
		}
	}
}
