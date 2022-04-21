package com.softicar.platform.core.module.module.page;

import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.core.utils.DevNull;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.emf.EmfI18n;
import com.softicar.platform.emf.authorization.role.EmfRoleWrapper;
import com.softicar.platform.emf.module.IEmfModule;
import com.softicar.platform.emf.page.EmfPagePath;
import com.softicar.platform.emf.page.EmfPages;
import com.softicar.platform.emf.page.IEmfPage;
import java.util.ArrayList;
import java.util.UUID;

public class ModulePagesTable extends AbstractInMemoryDataTable<IEmfPage<?>> {

	private final IEmfModule<?> module;
	private final IDataTableColumn<IEmfPage<?>, EmfRoleWrapper> authorizedRoleColumn;

	public ModulePagesTable(IEmfModule<?> module) {

		this.module = module;
		newColumn(String.class)//
			.setGetter(page -> page.getClass().getSimpleName())
			.setTitle(EmfI18n.NAME)
			.addColumn();
		newColumn(IDisplayString.class)//
			.setGetter(this::getPageTitle)
			.setTitle(EmfI18n.TITLE)
			.addColumn();
		authorizedRoleColumn = newColumn(EmfRoleWrapper.class)//
			.setGetter(page -> getRoleFromPage(page))
			.setTitle(EmfI18n.AUTHORIZED_ROLE)
			.addColumn();
		newColumn(String.class)//
			.setGetter(this::getPagePath)
			.setTitle(EmfI18n.PAGE_PATH)
			.addColumn();
		newColumn(UUID.class)//
			.setGetter(IEmfPage::getAnnotatedUuid)
			.setTitle(EmfI18n.UUID)
			.addColumn();
	}

	public IDataTableColumn<IEmfPage<?>, EmfRoleWrapper> getAuthorizedRoleColumn() {

		return authorizedRoleColumn;
	}

	@Override
	protected Iterable<IEmfPage<?>> getTableRows() {

		return new ArrayList<>(EmfPages.getPages(module));
	}

	private EmfRoleWrapper getRoleFromPage(IEmfPage<?> page) {

		return new EmfRoleWrapper(page.getAuthorizedRole());
	}

	private String getPagePath(IEmfPage<?> definition) {

		return definition//
			.getPagePath(new EmfPagePath().append("...").append(module.toDisplay()))
			.getCanonicalPath(" -> ");
	}

	private IDisplayString getPageTitle(IEmfPage<?> page) {

		try {
			return page.getTitle(null);
		} catch (NullPointerException exception) {
			DevNull.swallow(exception);
			return IDisplayString//
				.create(page.getClass().getSimpleName())
				.concatSpace()
				.concatInParentheses(CoreI18n.FAILED_TO_DETERMINE_PAGE_TITLE);
		}
	}
}
