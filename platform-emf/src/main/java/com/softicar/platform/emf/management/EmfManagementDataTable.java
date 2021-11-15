package com.softicar.platform.emf.management;

import com.softicar.platform.common.container.data.table.DataTableIdentifier;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.data.table.IEmfAttributeDataTableStrategy;
import com.softicar.platform.emf.authorization.role.IEmfRole;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class EmfManagementDataTable<R extends IEmfTableRow<R, P>, P, S> extends AbstractInMemoryDataTable<R> {

	private final IEmfTable<R, P, S> entityTable;
	private final S scopeEntity;
	private final boolean showInactive;
	private final List<IEmfAttributeDataTableStrategy<R>> fieldStrategiesList;
	private final Map<IEmfAttribute<R, ?>, IEmfAttributeDataTableStrategy<R>> fieldStrategiesMap;
	private Optional<Set<R>> prefilteredEntities;
	private Optional<ISqlBooleanExpression<R>> additionalFilterExpression;

	public EmfManagementDataTable(IEmfTable<R, P, S> entityTable, S scopeEntity, boolean showInactive) {

		this.entityTable = entityTable;
		this.scopeEntity = scopeEntity;
		this.showInactive = showInactive;
		this.fieldStrategiesList = new ArrayList<>();
		this.fieldStrategiesMap = new IdentityHashMap<>();
		this.prefilteredEntities = Optional.empty();

		// create field strategies
		entityTable//
			.getAttributes()
			.stream()
			.filter(this::showAttribute)
			.forEach(this::addStrategy);

		// create data columns
		fieldStrategiesList.forEach(strategy -> strategy.addDataColumns(this));
	}

	@Override
	public DataTableIdentifier getIdentifier() {

		return new DataTableIdentifier(entityTable.getFullName().getCanonicalName());
	}

	public EmfManagementDataTable<R, P, S> setPrefilteredEntities(Optional<Set<R>> prefilteredEntities) {

		this.prefilteredEntities = prefilteredEntities;
		return this;
	}

	public EmfManagementDataTable<R, P, S> setAdditionalFilterExpression(Optional<ISqlBooleanExpression<R>> additionalFilterExpression) {

		this.additionalFilterExpression = additionalFilterExpression;
		return this;
	}

	@Override
	public Iterable<R> getTableRows() {

		ISqlSelect<R> select = entityTable.createSelect();

		// filter by scope
		Optional<ISqlForeignRowField<R, S, ?>> scopeField = entityTable.getScopeField();
		if (scopeField.isPresent()) {
			select.where(scopeField.get().isEqual(scopeEntity));
		}

		// filter by pre-filtered entities
		if (prefilteredEntities.isPresent()) {
			if (prefilteredEntities.get().isEmpty()) {
				return Collections.emptyList();
			} else {
				select = select.where(entityTable.getPrimaryKey().isIn(getPrefilterEntityPrimaryKeys()));
			}
		}

		// filter by additional expression
		if (additionalFilterExpression.isPresent()) {
			select = select.where(additionalFilterExpression.get());
		}

		// filter by active
		if (!showInactive) {
			entityTable.getEmfTableConfiguration().getDeactivationStrategy().addWhereActive(select);
		}

		return getAuthorizedEntities(select);
	}

	private Set<P> getPrefilterEntityPrimaryKeys() {

		return prefilteredEntities//
			.get()//
			.stream()//
			.map(entity -> entity.pk())//
			.collect(Collectors.toSet());
	}

	private Iterable<R> getAuthorizedEntities(ISqlSelect<R> select) {

		IBasicUser currentUser = CurrentBasicUser.get();
		List<R> allEntities = select.list();
		IEmfRole<R> viewRole = entityTable.getAuthorizer().getViewRole().prefetchData(allEntities);

		return allEntities.stream().filter(entity -> viewRole.test(entity, currentUser)).collect(Collectors.toList());
	}

	public void setColumnHandlers(EmfDataTableDivBuilder<R> tableDivBuilder) {

		fieldStrategiesList.forEach(strategy -> strategy.setColumnHandlers(tableDivBuilder));
	}

	public <V> Optional<IEmfAttributeDataTableStrategy<R>> getFieldStrategy(IEmfAttribute<R, V> attribute) {

		return Optional.ofNullable(fieldStrategiesMap.get(attribute));
	}

	private <V> boolean showAttribute(IEmfAttribute<R, V> attribute) {

		if (attribute.isConcealed()) {
			return false;
		} else if (!showInactive && isActiveAttribute(attribute)) {
			return false;
		} else {
			return true;
		}
	}

	private <V> void addStrategy(IEmfAttribute<R, V> attribute) {

		IEmfAttributeDataTableStrategy<R> strategy = attribute.createDataTableStrategy();
		fieldStrategiesList.add(strategy);
		fieldStrategiesMap.put(attribute, strategy);
	}

	private <V> boolean isActiveAttribute(IEmfAttribute<R, V> attribute) {

		return entityTable//
			.getEmfTableConfiguration()
			.getDeactivationStrategy()
			.isActiveAttribute(attribute);
	}

	@Override
	public void invalidateCaches() {

		entityTable.getCache().getTransientValueCache().invalidateAll();
		super.invalidateCaches();
	}
}
