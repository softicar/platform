package com.softicar.platform.emf.management;

import com.softicar.platform.common.core.interfaces.Consumers;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.dom.parent.IDomParentElement;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class EmfManagementDivBuilder<R extends IEmfTableRow<R, P>, P, S> {

	private final IEmfTable<R, P, S> entityTable;
	private final S scopeEntity;
	private Optional<Set<R>> prefilteredEntities;
	private Optional<ISqlBooleanExpression<R>> additionalFilterExpression;
	private Consumer<EmfDataTableDivBuilder<R>> dataTableDivCustomizer;

	public EmfManagementDivBuilder(IEmfTable<R, P, S> entityTable, S scopeEntity) {

		this.entityTable = entityTable;
		this.scopeEntity = scopeEntity;
		this.prefilteredEntities = Optional.empty();
		this.additionalFilterExpression = Optional.empty();
		this.dataTableDivCustomizer = Consumers.noOperation();
	}

	public EmfManagementDivBuilder<R, P, S> setPrefilteredEntities(Set<R> prefilteredEntities) {

		this.prefilteredEntities = Optional.ofNullable(prefilteredEntities);
		return this;
	}

	public EmfManagementDivBuilder<R, P, S> setAdditionalFilterExpression(ISqlBooleanExpression<R> additionalFilterExpression) {

		this.additionalFilterExpression = Optional.ofNullable(additionalFilterExpression);
		return this;
	}

	public EmfManagementDivBuilder<R, P, S> setDataTableDivCustomizer(Consumer<EmfDataTableDivBuilder<R>> dataTableDivCustomizer) {

		this.dataTableDivCustomizer = dataTableDivCustomizer;
		return this;
	}

	public EmfManagementDiv<R, P, S> build() {

		return new EmfManagementDiv<>(this);
	}

	public EmfManagementDiv<R, P, S> buildAndAppendTo(IDomParentElement parentElement) {

		return parentElement.appendChild(build());
	}

	protected IEmfTable<R, P, S> getEntityTable() {

		return entityTable;
	}

	protected S getScopeEntity() {

		return scopeEntity;
	}

	protected Optional<Set<R>> getEntityFilter() {

		return prefilteredEntities;
	}

	protected Optional<ISqlBooleanExpression<R>> getAdditionalFilterExpression() {

		return additionalFilterExpression;
	}

	protected Consumer<EmfDataTableDivBuilder<R>> getDataTableDivCustomizer() {

		return dataTableDivCustomizer;
	}
}
