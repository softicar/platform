package com.softicar.platform.emf.editor;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.db.core.transaction.DbTransaction;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.action.IEmfManagementAction;
import com.softicar.platform.emf.deactivation.IEmfTableRowDeactivationStrategy;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;
import java.util.function.Supplier;

public class EmfDeactivateAction<R extends IEmfTableRow<R, ?>> implements IEmfManagementAction<R> {

	private final IEmfTable<R, ?, ?> table;
	private final IEmfTableRowDeactivationStrategy<R> deactivationStrategy;

	public EmfDeactivateAction(IEmfTable<R, ?, ?> table) {

		this.table = table;
		this.deactivationStrategy = table//
			.getEmfTableConfiguration()
			.getDeactivationStrategy();
	}

	@Override
	public IResource getIcon() {

		return EmfImages.ENTITY_DEACTIVATE.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return EmfI18n.DEACTIVATE;
	}

	@Override
	public IDisplayString getDescription() {

		return EmfI18n.DEACTIVATES_THE_GIVEN_ENTRY;
	}

	@Override
	public IEmfPredicate<R> getPrecondition() {

		return table//
			.getEmfTableConfiguration()
			.getEditPredicate()
			.and(EmfPredicates.deactivationSupported())
			.and(new EmfPredicate<>(EmfI18n.DEACTIVATION_AVAILABLE, deactivationStrategy::isDeactivationAvailable))
			.and(new EmfPredicate<>(EmfI18n.ACTIVE, deactivationStrategy::isActive));
	}

	@Override
	public IEmfPermission<R> getRequiredPermission() {

		return table.getAuthorizer().getEditPermission();
	}

	@Override
	public Optional<Supplier<IDisplayString>> getConfirmationMessageSupplier(R object) {

		return Optional.of(() -> EmfI18n.ARE_YOU_SURE_QUESTION);
	}

	@Override
	public void handleClick(R tableRow) {

		try (DbTransaction transaction = new DbTransaction()) {
			tableRow.reloadForUpdate();
			if (tableRow.table().getEmfTableConfiguration().getDeactivationStrategy().isDeactivationAvailable(tableRow)) {
				deactivationStrategy.setActive(tableRow, false);
			}
			tableRow.saveIfNecessary();
			transaction.commit();
		}
	}
}
