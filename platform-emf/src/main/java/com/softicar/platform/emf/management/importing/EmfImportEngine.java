package com.softicar.platform.emf.management.importing;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmfImportEngine<R extends IEmfTableRow<R, P>, P, S> {

	private final IEmfTable<R, P, S> table;
	private Optional<S> scope;

	public EmfImportEngine(IEmfTable<R, P, S> table) {

		this.table = table;
		this.scope = Optional.empty();
	}

	public EmfImportEngine<R, P, S> setScope(S scope) {

		this.scope = Optional.of(scope);
		return this;
	}

	public IDisplayString getFieldTitle(IDbField<R, ?> field) {

		return table.getAttribute(field).getTitle();
	}

	public Collection<IDbField<R, ?>> getFieldsToImport() {

		return table//
			.getAllFields()
			.stream()
			.filter(this::filterScopeField)
			.collect(Collectors.toList());
	}

	private boolean filterScopeField(IDbField<R, ?> field) {

		return scope.isEmpty() || !isScopeField(field);
	}

	private boolean isScopeField(IDbField<R, ?> field) {

		return table//
			.getScopeField()
			.map(scopeField -> field == scopeField)
			.orElse(false);
	}
}
