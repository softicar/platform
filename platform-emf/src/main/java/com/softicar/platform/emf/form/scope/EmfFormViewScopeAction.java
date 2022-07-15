package com.softicar.platform.emf.form.scope;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.CastUtils;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.dom.elements.popup.manager.DomPopupManager;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.action.IEmfCommonAction;
import com.softicar.platform.emf.attribute.field.foreign.row.IEmfForeignRowAttribute;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.emf.mapper.IEmfTableRowMapper;
import com.softicar.platform.emf.permission.EmfMappedPermission;
import com.softicar.platform.emf.permission.EmfPermissions;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.EmfPredicate;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class EmfFormViewScopeAction<R extends IEmfTableRow<R, ?>, F extends IEmfTableRow<F, ?>> implements IEmfCommonAction<R> {

	private final IEmfTable<R, ?, ?> table;
	private final IEmfTable<F, ?, ?> scopeTable;

	public EmfFormViewScopeAction(IEmfTable<R, ?, ?> table) {

		this.table = table;
		this.scopeTable = table//
			.getScopeField()
			.map(this::getScopeTable)
			.orElse(null);
	}

	@Override
	public IResource getIcon() {

		return EmfImages.VIEW_SCOPE.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return scopeTable != null//
				? EmfI18n.VIEW_ARG1.toDisplay(scopeTable.getTitle())
				: EmfI18n.VIEW_SCOPE;
	}

	@Override
	public IEmfPredicate<R> getPrecondition() {

		return new EmfPredicate<>(//
			EmfI18n.SCOPE_PRESENT,
			it -> it.table().getScopeField().isPresent());
	}

	@Override
	public IEmfPermission<R> getRequiredPermission() {

		if (scopeTable != null) {
			return new EmfMappedPermission<>(//
				scopeTable.getAuthorizer().getViewPermission(),
				IEmfTableRowMapper.<R, F> nonOptional(EmfI18n.SCOPE, this::getScope));
		} else {
			return EmfPermissions.never();
		}
	}

	@Override
	public void handleClick(IEmfFormBody<R> formBody) {

		R scopeRow = CastUtils.cast(table.getScope(formBody.getTableRow()).get());
		DomPopupManager//
			.getInstance()
			.getPopup(scopeRow, EmfFormPopup.class, EmfFormPopup::new)
			.open();
	}

	private IEmfTable<F, ?, ?> getScopeTable(ISqlForeignRowField<R, ?, ?> scopeField) {

		IEmfForeignRowAttribute<R, F> row = CastUtils.cast(table.getAttribute(scopeField));
		return row.getTargetTable();
	}

	private F getScope(R row) {

		return CastUtils.cast(table.getScope(row).orElse(null));
	}
}
