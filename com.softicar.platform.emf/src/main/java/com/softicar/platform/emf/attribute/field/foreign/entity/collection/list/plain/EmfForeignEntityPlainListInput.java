package com.softicar.platform.emf.attribute.field.foreign.entity.collection.list.plain;

import com.softicar.platform.common.core.exceptions.SofticarUserException;
import com.softicar.platform.common.core.i18n.DisplayString;
import com.softicar.platform.common.core.number.parser.IntegerParser;
import com.softicar.platform.common.ui.wiki.element.tag.WikiBoxType;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomCell;
import com.softicar.platform.dom.elements.DomElementsImages;
import com.softicar.platform.dom.elements.DomRow;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.elements.input.DomIntegerInput;
import com.softicar.platform.dom.elements.input.auto.IDomAutoCompleteInputEngine;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.elements.prompt.DomPromptButtonBuilder;
import com.softicar.platform.dom.elements.tables.DomDataTable;
import com.softicar.platform.dom.elements.wiki.box.DomWikiBoxShadow;
import com.softicar.platform.dom.style.CssStyle;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.attribute.field.foreign.entity.collection.EmfUnsavedChangedMessageDiv;
import com.softicar.platform.emf.attribute.field.foreign.entity.input.EmfEntityInput;
import com.softicar.platform.emf.attribute.input.AbstractEmfInputDiv;
import com.softicar.platform.emf.object.IEmfObject;
import com.softicar.platform.emf.object.table.IEmfObjectTable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmfForeignEntityPlainListInput<O extends IEmfObject<O>, S> extends AbstractEmfInputDiv<String> {

	private final IEmfObjectTable<O, S> entityTable;
	private final IDomAutoCompleteInputEngine<O> inputEngine;
	private final EmfUnsavedChangedMessageDiv unsavedChangedMessageDiv;
	private final ListTable listTable;
	private boolean mandatory;

	public EmfForeignEntityPlainListInput(IEmfObjectTable<O, S> entityTable, IDomAutoCompleteInputEngine<O> inputEngine) {

		this.entityTable = entityTable;
		this.inputEngine = inputEngine;
		this.listTable = new ListTable();
		this.unsavedChangedMessageDiv = new EmfUnsavedChangedMessageDiv().hide();
		this.mandatory = false;

		appendChild(
			new DomPopupButton()//
				.setPopupFactory(() -> new EntityPopup())
				.setIcon(DomElementsImages.ENTRY_ADD.getResource())
				.setLabel(EmfI18n.ADD_ENTRY));
		appendChild(listTable);
		appendChild(unsavedChangedMessageDiv);
	}

	@Override
	public String getValueOrThrow() {

		return listTable//
			.getIdList()
			.stream()
			.map(Object::toString)
			.collect(Collectors.joining(","));
	}

	@Override
	public void setValue(String idListString) {

		listTable.setIdList(idListString);
	}

	@Override
	public void executePostSaveHook() {

		listTable.setUnchangedAndRefresh();
	}

	@Override
	public void setMandatory(boolean mandatory) {

		this.mandatory = mandatory;
	}

	private void refreshUnsavedChangesHighlight(boolean unsaved) {

		if (unsaved) {
			listTable.setStyle(new DomWikiBoxShadow(WikiBoxType.WARNING));
			unsavedChangedMessageDiv.show();
		} else {
			listTable.unsetStyle(CssStyle.BOX_SHADOW);
			unsavedChangedMessageDiv.hide();
		}
	}

	private class ListTable extends DomDataTable {

		private List<Integer> idList;
		private Map<Integer, O> entityMap;
		private boolean changed;

		public ListTable() {

			this.idList = null;
			this.entityMap = null;
			this.changed = false;

			DomRow headerRow = getHead().appendRow();
			headerRow.appendHeaderCell(EmfI18n.ACTIONS);
			headerRow.appendHeaderCell(EmfI18n.INDEX);
			headerRow.appendHeaderCell(entityTable.getTitle().toString());
		}

		public List<Integer> getIdList() {

			return idList;
		}

		public void setIdList(String idListString) {

			this.idList = new EmfForeignEntityIdPlainListParser(idListString).parseToIdList();
			this.entityMap = entityTable.getAllAsMap(idList);
			setUnchangedAndRefresh();
		}

		public boolean contains(O object) {

			return idList//
				.stream()
				.map(entityMap::get)
				.filter(it -> it.equals(object))
				.findAny()
				.isPresent();
		}

		public void setUnchangedAndRefresh() {

			this.changed = false;
			refresh();
		}

		public void refresh() {

			getBody().removeChildren();

			int index = 0;
			for (Integer entityId: idList) {
				ListRow row = new ListRow(index, entityMap.get(entityId));
				getBody().appendChild(row);
				index += 1;
			}

			refreshUnsavedChangesHighlight(changed);
		}

		public void addEntity(Integer index, O object) {

			if (!contains(object)) {
				index = clampIndex(index);
				idList.add(index, object.getId());
				entityMap.put(object.getId(), object);
				this.changed = true;
				refresh();
			} else {
				throw new SofticarUserException(EmfI18n.THIS_ENTRY_IS_ALREADY_CONTAINED_IN_THE_LIST);
			}
		}

		public void removeEntity(Integer index) {

			idList.remove((int) index);
			this.changed = true;
			refresh();
		}

		public void moveEntity(Integer fromIndex, Integer toIndex, O object) {

			idList.remove((int) fromIndex);
			idList.add(clampIndex(toIndex), object.getId());
			this.changed = true;
			refresh();
		}

		private Integer clampIndex(Integer index) {

			index = Math.max(index, 0);
			index = Math.min(index, idList.size());
			return index;
		}

		private class ListRow extends DomRow {

			private final int index;
			private final O object;

			public ListRow(int index, O object) {

				this.index = index;
				this.object = object;

				DomCell actionCell = appendCell();
				actionCell
					.appendChild(
						new DomButton()//
							.setClickCallback(() -> moveEntity(index, index - 1, object))
							.setIcon(DomElementsImages.MOVE_UP.getResource())
							.setTitle(EmfI18n.MOVE_UP));
				actionCell
					.appendChild(
						new DomButton()//
							.setClickCallback(() -> moveEntity(index, index + 1, object))
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
							.setIcon(DomElementsImages.ENTRY_REMOVE.getResource())
							.setTitle(EmfI18n.REMOVE_ENTRY)
							.setConfirmationMessage(EmfI18n.ARE_YOU_SURE_QUESTION)
							.setClickCallback(this::remove));

				appendCell(index);

				appendCell(object != null? object.toDisplayWithoutId() : DisplayString.EMPTY);
			}

			private void move(String indexString) {

				int toIndex = IntegerParser//
					.parse(indexString)
					.orElseThrow(() -> new SofticarUserException(EmfI18n.PLEASE_SPECIFY_A_VALID_INDEX));
				moveEntity(index, toIndex, object);
			}

			private void remove() {

				removeEntity(index);
			}
		}
	}

	private class EntityPopup extends DomPopup {

		private final DomIntegerInput indexInput;
		private final EmfEntityInput<O> entityInput;

		public EntityPopup() {

			this.indexInput = new DomIntegerInput(listTable.getIdList().size());
			this.entityInput = new EmfEntityInput<>(inputEngine);
			this.entityInput.setMandatory(mandatory);

			setCaption(EmfI18n.ADD_ENTRY);

			appendChild(
				new DomLabelGrid()//
					.add(EmfI18n.INDEX, indexInput)
					.add(entityTable.getTitle(), entityInput));
			appendNewChild(DomElementTag.HR);
			appendChild(
				new DomButton()//
					.setIcon(EmfImages.ENTITY_SAVE.getResource())
					.setLabel(EmfI18n.SAVE)
					.setClickCallback(this::save));
			appendCancelButton();
		}

		private void save() {

			Integer index = indexInput.getInteger();
			if (index == null) {
				throw new SofticarUserException(EmfI18n.PLEASE_SPECIFY_A_VALID_INDEX);
			}

			O object = entityInput.getValueOrThrow();
			if (object == null) {
				throw new SofticarUserException(EmfI18n.PLEASE_SELECT_A_VALID_ENTRY);
			}

			listTable.addEntity(index, object);
			hide();
		}
	}
}
