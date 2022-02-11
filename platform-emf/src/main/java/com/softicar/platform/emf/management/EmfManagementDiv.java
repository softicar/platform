package com.softicar.platform.emf.management;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.interfaces.IRefreshable;
import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.db.runtime.cache.DbTableRowCaches;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.dom.document.CurrentDomDocument;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.button.popup.DomPopupButton;
import com.softicar.platform.dom.elements.checkbox.DomCheckbox;
import com.softicar.platform.dom.elements.popup.DomPopup;
import com.softicar.platform.dom.node.IDomNode;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusEvent;
import com.softicar.platform.dom.refresh.bus.IDomRefreshBusListener;
import com.softicar.platform.emf.EmfCssClasses;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.action.IEmfScopeAction;
import com.softicar.platform.emf.action.marker.EmfScopeActionMarker;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableDiv;
import com.softicar.platform.emf.management.importing.EmfImportPopup;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EmfManagementDiv<R extends IEmfTableRow<R, P>, P, S> extends DomDiv implements IDomRefreshBusListener {

	private final IEmfTable<R, P, S> entityTable;
	private final S scopeEntity;
	private EmfManagementDataTable<R, P, S> dataTable;
	private IEmfDataTableDiv<R> tableDiv;
	private boolean showInactive;
	private Optional<IRefreshable> refreshable;
	private Optional<Set<R>> prefilteredEntities;
	private Optional<ISqlBooleanExpression<R>> additionalFilterExpression;
	private Consumer<EmfDataTableDivBuilder<R>> dataTableDivCustomizer;
	private ScopeActionBar scopeActionBar;
	private ActionBar actionBar;

	public EmfManagementDiv(IEmfTable<R, P, S> entityTable, S scopeEntity) {

		this(new EmfManagementDivBuilder<>(entityTable, scopeEntity));
	}

	protected EmfManagementDiv(EmfManagementDivBuilder<R, P, S> builder) {

		this.entityTable = builder.getEntityTable();
		this.scopeEntity = builder.getScopeEntity();
		this.dataTable = null;
		this.tableDiv = null;
		this.showInactive = false;
		this.prefilteredEntities = builder.getEntityFilter();
		this.additionalFilterExpression = builder.getAdditionalFilterExpression();
		this.dataTableDivCustomizer = builder.getDataTableDivCustomizer();
		this.scopeActionBar = new ScopeActionBar();
		this.actionBar = new ActionBar();
		this.refreshable = Optional.empty();

		setCssClass(EmfCssClasses.EMF_MANAGEMENT_DIV);
		appendChild(scopeActionBar);
		appendChild(actionBar);
		rebuildTable();
	}

	public IEmfTable<R, P, S> getTable() {

		return entityTable;
	}

	private void rebuildTable() {

		Optional//
			.ofNullable(tableDiv)
			.ifPresent(IDomNode::disappend);

		this.dataTable = new EmfManagementDataTable<>(entityTable, scopeEntity, showInactive)
			.setPrefilteredEntities(prefilteredEntities)
			.setAdditionalFilterExpression(additionalFilterExpression);
		this.tableDiv = new EmfManagementDataTableDivBuilder<>(entityTable, dataTable).setDataTableDivCustomizer(dataTableDivCustomizer).build();

		appendChild(tableDiv);
	}

	public EmfManagementDiv<R, P, S> setRefreshable(IRefreshable refreshable) {

		this.refreshable = Optional.of(refreshable);
		return this;
	}

	public IEmfDataTableDiv<R> getDataTableDiv() {

		return tableDiv;
	}

	@Override
	public void refresh(IDomRefreshBusEvent event) {

		if (event.isAnyObjectChanged(entityTable.getValueClass())) {
			tableDiv.refresh();
			refreshable.ifPresent(IRefreshable::refresh);
		}
		if (isScopeChanged(event)) {
			this.scopeActionBar = replaceChild(new ScopeActionBar(), scopeActionBar);
			this.actionBar = replaceChild(new ActionBar(), actionBar);
		}
	}

	private boolean isScopeChanged(IDomRefreshBusEvent event) {

		Object scope = scopeEntity;
		while (scope != null) {
			if (event.isChanged(scope)) {
				return true;
			}
			scope = CastUtils//
				.tryCast(scope, IEmfTableRow.class)
				.map(row -> row.table().getScope(row))
				.orElse(null);
		}
		return false;
	}

	private void toggleShowInactive(boolean showInactive) {

		this.showInactive = showInactive;
		rebuildTable();
	}

	private DomPopup createNewEntityPopup() {

		return entityTable.getTableSpecialization().createNewTableRowPopup(scopeEntity);
	}

	// TODO This element will be appended even if there are no scope actions. This causes a vertical offset for all elements below.
	private class ScopeActionBar extends DomActionBar {

		public ScopeActionBar() {

			IBasicUser user = CurrentBasicUser.get();

			entityTable//
				.getScopeActions()
				.stream()
				.filter(action -> action.isAvailable(scopeEntity, user))
				.collect(Collectors.toList())
				.forEach(this::appendAction);
		}

		private void appendAction(IEmfScopeAction<S> action) {

			var button = new DomButton()//
				.setClickCallback(() -> action.handleClick(scopeEntity))
				.setIcon(action.getIcon())
				.setLabel(action.getTitle())
				.setMarker(new EmfScopeActionMarker(action));
			action.getConfirmationMessageSupplier(scopeEntity).ifPresent(button::setConfirmationMessageSupplier);
			appendChild(button);
		}
	}

	private class ActionBar extends DomActionBar {

		public ActionBar() {

			appendChild(
				new DomButton()//
					.setClickCallback(this::invalidateCachesAndRefreshAll)
					.setIcon(EmfImages.REFRESH.getResource())
					.setLabel(EmfI18n.REFRESH)
					.setMarker(EmfManagementMarker.REFRESH_BUTTON));
			appendChild(
				new DomPopupButton()//
					.setPopupFactory(() -> createNewEntityPopup())
					.setIcon(EmfImages.ENTITY_CREATE.getResource())
					.setLabel(EmfI18n.CREATE)
					.setMarker(EmfManagementMarker.CREATE_BUTTON)
					.setEnabled(isCreationAllowed())
					.setTitle(getCreationPredicateTitle()));
			appendChild(
				new DomPopupButton()//
					.setPopupFactory(() -> new EmfImportPopup<>(entityTable, scopeEntity))
					.setIcon(EmfImages.ENTITY_IMPORT.getResource())
					.setLabel(EmfI18n.IMPORT)
					.setMarker(EmfManagementMarker.IMPORT_BUTTON)
					.setEnabled(isCreationAllowed())
					.setTitle(getCreationPredicateTitle()));

			if (entityTable.getEmfTableConfiguration().getDeactivationStrategy().isDeactivationSupported()) {
				appendActiveCheckbox();
			}
		}

		private void invalidateCachesAndRefreshAll() {

			DbTableRowCaches.invalidateAll();
			CurrentDomDocument.get().getRefreshBus().setAllChanged();
		}

		private boolean isCreationAllowed() {

			if (scopeEntity != null) {
				return entityTable.isCreationPossible(scopeEntity) && entityTable.isCreationAllowed(scopeEntity);
			} else {
				return CurrentBasicUser.get().isSuperUser();
			}
		}

		private IDisplayString getCreationPredicateTitle() {

			IEmfPredicate<S> creationPredicate = entityTable.getEmfTableConfiguration().getCreationPredicate();
			if (!creationPredicate.test(scopeEntity)) {
				return creationPredicate.getTitle();
			}
			return null;
		}

		private void appendActiveCheckbox() {

			appendChild(
				new DomCheckbox()//
					.setLabel(EmfI18n.SHOW_INACTIVE)
					.setChecked(showInactive)
					.setChangeCallback(showInactive -> toggleShowInactive(showInactive))
					.setMarker(EmfManagementMarker.SHOW_INACTIVE_CHECKBOX));
		}
	}
}
