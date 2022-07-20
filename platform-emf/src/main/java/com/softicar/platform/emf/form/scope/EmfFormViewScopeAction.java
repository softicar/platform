package com.softicar.platform.emf.form.scope;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.dom.elements.popup.manager.DomPopupManager;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.action.IEmfCommonAction;
import com.softicar.platform.emf.attribute.field.foreign.row.IEmfForeignRowAttribute;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.form.popup.EmfFormPopup;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.predicate.EmfPredicates;
import com.softicar.platform.emf.predicate.IEmfPredicate;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;

/**
 * An {@link IEmfCommonAction} to open an {@link EmfFormPopup} for the scope
 * object of an {@link IEmfTableRow}.
 * <p>
 * This can only be instantiated for {@link IEmfTable} objects with a scope
 * object that implements {@link IEmfTableRow}.
 *
 * @author Oliver Richers
 */
public class EmfFormViewScopeAction<R extends IEmfTableRow<R, ?>, S extends IEmfTableRow<S, ?>> implements IEmfCommonAction<R> {

	private final IEmfForeignRowAttribute<R, S> scopeAttribute;

	private EmfFormViewScopeAction(IEmfForeignRowAttribute<R, S> scopeAttribute) {

		this.scopeAttribute = scopeAttribute;
	}

	/**
	 * Tries to create an {@link EmfFormViewScopeAction} for the given
	 * {@link IEmfTable}.
	 *
	 * @param <R>
	 *            the {@link IEmfTableRow} type
	 * @param table
	 *            the {@link IEmfTable} (never <i>null</i>)
	 * @return the optional {@link EmfFormViewScopeAction} as an
	 *         {@link IEmfCommonAction}
	 */
	public static <R extends IEmfTableRow<R, ?>> Optional<IEmfCommonAction<R>> create(IEmfTable<R, ?, ?> table) {

		return table//
			.getScopeAttribute()
			.flatMap(attribute -> attribute.asForeignRowAttribute())
			.map(EmfFormViewScopeAction::new);
	}

	@Override
	public IResource getIcon() {

		return EmfImages.VIEW_SCOPE.getResource();
	}

	@Override
	public IDisplayString getTitle() {

		return EmfI18n.VIEW_ARG1.toDisplay(scopeAttribute.getTitle());
	}

	@Override
	public IEmfPredicate<R> getPrecondition() {

		return EmfPredicates.always();
	}

	@Override
	public IEmfPermission<R> getRequiredPermission() {

		return scopeAttribute//
			.getTargetTable()
			.getAuthorizer()
			.getViewPermission()
			.of(scopeAttribute.getForeignRowField());
	}

	@Override
	public void handleClick(IEmfFormBody<R> formBody) {

		var scope = scopeAttribute.getValue(formBody.getTableRow());

		DomPopupManager//
			.getInstance()
			.getPopup(scope, EmfFormPopup.class, EmfFormPopup::new)
			.open();
	}
}
