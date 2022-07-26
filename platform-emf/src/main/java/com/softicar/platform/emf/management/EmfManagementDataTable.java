package com.softicar.platform.emf.management;

import com.softicar.platform.common.container.comparator.OrderDirection;
import com.softicar.platform.common.container.data.table.DataTableIdentifier;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.common.core.user.CurrentBasicUser;
import com.softicar.platform.common.core.user.IBasicUser;
import com.softicar.platform.db.sql.ISqlBooleanExpression;
import com.softicar.platform.db.sql.field.ISqlForeignRowField;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.permission.IEmfPermission;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
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
	private final Map<IEmfAttribute<R, ?>, AttributeColumn<?>> columnMap;
	private Optional<Set<R>> prefilteredEntities;
	private Optional<ISqlBooleanExpression<R>> additionalFilterExpression;

	public EmfManagementDataTable(IEmfTable<R, P, S> entityTable, S scopeEntity, boolean showInactive) {

		this.entityTable = entityTable;
		this.scopeEntity = scopeEntity;
		this.showInactive = showInactive;
		this.columnMap = new IdentityHashMap<>();
		this.prefilteredEntities = Optional.empty();

		entityTable//
			.getAttributes()
			.stream()
			.filter(this::showAttribute)
			.forEach(this::addDataColumn);
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

	@Override
	public void invalidateCaches() {

		entityTable.getCache().getTransientValueCache().invalidateAll();
		super.invalidateCaches();
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
		IEmfPermission<R> viewPermission = entityTable.getAuthorizer().getViewPermission().prefetchData(allEntities);

		return allEntities.stream().filter(entity -> viewPermission.test(entity, currentUser)).collect(Collectors.toList());
	}

	void setColumnHandlers(EmfDataTableDivBuilder<R> builder) {

		columnMap.values().forEach(column -> column.setColumnHandler(builder));
	}

	void addColumnMakers(EmfDataTableDivBuilder<R> builder) {

		columnMap.values().forEach(column -> column.addColumnMarker(builder));
	}

	void addOrderBy(EmfDataTableDivBuilder<R> builder, IEmfAttribute<R, ?> attribute, OrderDirection direction) {

		Optional//
			.ofNullable(columnMap.get(attribute))
			.ifPresent(column -> column.addOrderBy(builder, direction));
	}

	private <V> boolean showAttribute(IEmfAttribute<R, V> attribute) {

		if (attribute.isConcealed() || attribute.isScope()) {
			return false;
		} else if (!showInactive && isActiveAttribute(attribute)) {
			return false;
		} else {
			return true;
		}
	}

	private <V> void addDataColumn(IEmfAttribute<R, V> attribute) {

		IDataTableColumn<R, V> column = newColumn(attribute.getValueClass())//
			.setComparator(attribute.getValueComparator())
			.setGetter(attribute::getValue)
			.setTitle(attribute.getTitle())
			.addColumn();
		columnMap.put(attribute, new AttributeColumn<>(attribute, column));
	}

	private <V> boolean isActiveAttribute(IEmfAttribute<R, V> attribute) {

		return entityTable//
			.getEmfTableConfiguration()
			.getDeactivationStrategy()
			.isActiveAttribute(attribute);
	}

	private class AttributeColumn<V> {

		private final IEmfAttribute<R, V> attribute;
		private final IDataTableColumn<R, V> column;

		public AttributeColumn(IEmfAttribute<R, V> attribute, IDataTableColumn<R, V> column) {

			this.attribute = attribute;
			this.column = column;
		}

		public void setColumnHandler(EmfDataTableDivBuilder<R> builder) {

			builder.setColumnHandler(column, attribute.createColumnHandler());
		}

		public void addColumnMarker(EmfDataTableDivBuilder<R> builder) {

			builder.addColumnMarker(column, attribute.getTestMarker());
		}

		public void addOrderBy(EmfDataTableDivBuilder<R> builder, OrderDirection direction) {

			builder.addOrderBy(column, direction);
		}
	}
}
