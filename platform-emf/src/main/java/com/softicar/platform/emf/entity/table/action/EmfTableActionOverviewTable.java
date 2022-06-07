package com.softicar.platform.emf.entity.table.action;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.io.resource.IResource;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.action.AbstractEmfAggregatingAction;
import com.softicar.platform.emf.action.IEmfAction;
import com.softicar.platform.emf.action.IEmfCommonAction;
import com.softicar.platform.emf.permission.EmfPermissionWrapper;
import com.softicar.platform.emf.predicate.EmfPredicateWrapper;
import com.softicar.platform.emf.table.IEmfTable;
import java.util.ArrayList;
import java.util.List;

public class EmfTableActionOverviewTable extends AbstractInMemoryDataTable<IEmfAction<?>> {

	private final IEmfTable<?, ?, ?> table;
	private final IDataTableColumn<IEmfAction<?>, IResource> iconColumn;
	private final IDataTableColumn<IEmfAction<?>, String> titleColumn;
	private final IDataTableColumn<IEmfAction<?>, IDisplayString> descriptionColumn;
	private final IDataTableColumn<IEmfAction<?>, EmfPredicateWrapper> preconditionColumn;
	private final IDataTableColumn<IEmfAction<?>, EmfPermissionWrapper> requiredPermissionColumn;

	public EmfTableActionOverviewTable(IEmfTable<?, ?, ?> table) {

		this.table = table;
		this.iconColumn = newColumn(IResource.class)//
			.setGetter(action -> action.getIcon())
			.setTitle(EmfI18n.ICON)
			.addColumn();
		this.titleColumn = newColumn(String.class)//
			.setGetter(action -> action.getClass().getSimpleName())
			.setTitle(EmfI18n.TITLE)
			.addColumn();
		this.descriptionColumn = newColumn(IDisplayString.class)//
			.setGetter(IEmfAction::getDescription)
			.setTitle(EmfI18n.DESCRIPTION)
			.addColumn();
		this.preconditionColumn = newColumn(EmfPredicateWrapper.class)//
			.setGetter(action -> new EmfPredicateWrapper(action.getPrecondition()))
			.setTitle(EmfI18n.PRECONDITION)
			.addColumn();
		this.requiredPermissionColumn = newColumn(EmfPermissionWrapper.class)//
			.setGetter(action -> new EmfPermissionWrapper(action.getRequiredPermission()))
			.setTitle(EmfI18n.REQUIRED_PERMISSION)
			.addColumn();
	}

	public IDataTableColumn<IEmfAction<?>, IResource> getIconColumn() {

		return iconColumn;
	}

	public IDataTableColumn<IEmfAction<?>, String> getTitleColumn() {

		return titleColumn;
	}

	public IDataTableColumn<IEmfAction<?>, IDisplayString> getDescriptionColumn() {

		return descriptionColumn;
	}

	public IDataTableColumn<IEmfAction<?>, EmfPredicateWrapper> getPreconditionColumn() {

		return preconditionColumn;
	}

	public IDataTableColumn<IEmfAction<?>, EmfPermissionWrapper> getRequiredPermissionColumn() {

		return requiredPermissionColumn;
	}

	@Override
	protected Iterable<IEmfAction<?>> getTableRows() {

		List<IEmfAction<?>> actionList = new ArrayList<>();
		table.getPrimaryActions().forEach(action -> actionList.add(action));
		table.getCommonActions().forEach(action -> addActionAndCheckForAggregatingActions(action, actionList));
		return actionList;
	}

	private void addActionAndCheckForAggregatingActions(IEmfCommonAction<?> action, List<IEmfAction<?>> actionList) {

		actionList.add(action);
		if (action instanceof AbstractEmfAggregatingAction<?>) {
			actionList.addAll(((AbstractEmfAggregatingAction<?>) action).getActions());
		}
	}
}
