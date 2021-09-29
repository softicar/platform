package com.softicar.platform.emf.management.section;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.emf.form.IEmfFormBody;
import com.softicar.platform.emf.form.section.EmfFormSectionDiv;
import com.softicar.platform.emf.form.section.header.EmfFormSectionHeader;
import com.softicar.platform.emf.management.EmfManagementDiv;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.function.Function;

public class EmfManagementSectionDiv<R extends IEmfTableRow<R, ?>> extends EmfFormSectionDiv<R> {

	public <S> EmfManagementSectionDiv(IEmfFormBody<R> formBody, IEmfTable<?, ?, S> table, Function<R, S> scopeMapper, IDisplayString title) {

		super(formBody, new EmfFormSectionHeader().setIcon(table.getIcon()).setCaption(title));
		addElement(() -> new EmfManagementDiv<>(table, scopeMapper.apply(formBody.getTableRow())));
	}
}
