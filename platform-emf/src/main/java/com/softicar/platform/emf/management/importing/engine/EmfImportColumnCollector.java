package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.field.foreign.row.EmfForeignRowAttribute;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;
import java.util.ArrayList;
import java.util.List;

public class EmfImportColumnCollector<R extends IEmfTableRow<R, P>, P, S> {

	private final IEmfTable<R, P, S> table;

	public EmfImportColumnCollector(IEmfTable<R, P, S> table) {

		this.table = table;
	}

	public List<IDisplayString> collect() {

		List<IDisplayString> columns = new ArrayList<>();

		for (IDbField<R, ?> field: table.getAllFields()) {

			IEmfAttribute<R, ?> attribute = table.getAttribute(field);

//			System.out.println("\n" + field + " :");

			if (attribute instanceof EmfForeignRowAttribute) {
				EmfForeignRowAttribute<R, ?> foreignRowAttribute = (EmfForeignRowAttribute<R, ?>) attribute;

				System.out.println(field.getTitle() + " (FK)");

				// NPE:
//				var emfObject = CastUtils.castOrNull(foreignRowAttribute.getTargetTable().getClass(), IEmfObject.class);
//				new FkResolver<>(emfObject.table()).resolve();

				IEmfTable<?, ?, ?> emfTable = foreignRowAttribute.getTargetTable();
				new FkResolver<>(emfTable).resolve(1);

//				Class<?> clazz = foreignRowAttribute.getTargetTable().getClass();
//
//				IEmfObject<?> emfObject = (IEmfObject<?>) clazz;
//
//				System.out.println("\t" + emfObject.table());
//				new FkResolver<>(emfObject.table()).resolve();

			} else {
				columns.add(field.getTitle());
			}
		}

		return columns;
	}
}
