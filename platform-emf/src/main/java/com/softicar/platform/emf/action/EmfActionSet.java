package com.softicar.platform.emf.action;

import com.softicar.platform.emf.action.factory.IEmfCommonActionFactory;
import com.softicar.platform.emf.action.factory.IEmfManagementActionFactory;
import com.softicar.platform.emf.action.factory.IEmfPrimaryActionFactory;
import com.softicar.platform.emf.editor.EmfDeactivateAction;
import com.softicar.platform.emf.editor.EmfEditAction;
import com.softicar.platform.emf.editor.EmfViewAction;
import com.softicar.platform.emf.form.refresh.EmfFormRefreshAction;
import com.softicar.platform.emf.log.viewer.EmfLogAction;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class EmfActionSet<R extends IEmfTableRow<R, ?>, S> {

	private final Collection<IEmfScopeAction<S>> scopeActions;
	private final Collection<IEmfPrimaryAction<R>> primaryActions;
	private final Collection<IEmfCommonAction<R>> commonActions;
	private final Collection<IEmfManagementAction<R>> managementActions;
	private final Collection<IEmfPrimaryActionFactory<R>> primaryActionFactories;
	private final Collection<IEmfCommonActionFactory<R>> commonActionFactories;
	private final Collection<IEmfManagementActionFactory<R>> managementActionFactories;

	public EmfActionSet(IEmfTable<R, ?, S> table) {

		this.scopeActions = new ArrayList<>();
		this.primaryActions = new ArrayList<>();
		this.commonActions = new ArrayList<>();
		this.managementActions = new ArrayList<>();
		this.primaryActionFactories = new ArrayList<>();
		this.commonActionFactories = new ArrayList<>();
		this.managementActionFactories = new ArrayList<>();

		// add standard common actions
		addCommonAction(new EmfFormRefreshAction<>());
		addCommonAction(new EmfEditAction<>(table));
		addCommonAction(new EmfLogAction<>(table));

		// add standard management actions
		addManagementAction(new EmfViewAction<>(table));
		addManagementAction(new EmfEditAction<>(table));
		addManagementAction(new EmfDeactivateAction<>(table));
	}

	public Collection<IEmfScopeAction<S>> getScopeActions() {

		return scopeActions;
	}

	public Collection<IEmfPrimaryAction<R>> getPrimaryActions() {

		return primaryActions;
	}

	public Collection<IEmfCommonAction<R>> getCommonActions() {

		return commonActions;
	}

	public Collection<IEmfManagementAction<R>> getManagementActions() {

		return managementActions;
	}

	public Collection<IEmfPrimaryActionFactory<R>> getPrimaryActionFactories() {

		return primaryActionFactories;
	}

	public Collection<IEmfCommonActionFactory<R>> getCommonActionFactories() {

		return commonActionFactories;
	}

	public Collection<IEmfManagementActionFactory<R>> getManagementActionFactories() {

		return managementActionFactories;
	}

	public EmfActionSet<R, S> addScopeAction(IEmfScopeAction<S> action) {

		scopeActions.add(Objects.requireNonNull(action));
		return this;
	}

	public EmfActionSet<R, S> addPrimaryAction(IEmfPrimaryAction<R> action) {

		primaryActions.add(Objects.requireNonNull(action));
		return this;
	}

	public EmfActionSet<R, S> addCommonAction(IEmfCommonAction<R> action) {

		commonActions.add(Objects.requireNonNull(action));
		return this;
	}

	public EmfActionSet<R, S> addManagementAction(IEmfManagementAction<R> action) {

		managementActions.add(Objects.requireNonNull(action));
		return this;
	}

	public EmfActionSet<R, S> addPrimaryActionFactory(IEmfPrimaryActionFactory<R> actionFactory) {

		primaryActionFactories.add(Objects.requireNonNull(actionFactory));
		return this;
	}

	public EmfActionSet<R, S> addCommonActionFactory(IEmfCommonActionFactory<R> actionFactory) {

		commonActionFactories.add(Objects.requireNonNull(actionFactory));
		return this;
	}

	public EmfActionSet<R, S> addManagementActionFactory(IEmfManagementActionFactory<R> actionFactory) {

		managementActionFactories.add(Objects.requireNonNull(actionFactory));
		return this;
	}
}
