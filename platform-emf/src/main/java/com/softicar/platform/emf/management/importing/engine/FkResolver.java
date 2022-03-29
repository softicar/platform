package com.softicar.platform.emf.management.importing.engine;

import com.softicar.platform.db.runtime.field.IDbField;
import com.softicar.platform.emf.attribute.IEmfAttribute;
import com.softicar.platform.emf.attribute.field.foreign.row.EmfForeignRowAttribute;
import com.softicar.platform.emf.table.IEmfTable;
import com.softicar.platform.emf.table.row.IEmfTableRow;

public class FkResolver<R extends IEmfTableRow<R, P>, P, S> {

	private static int COUNT = 0;
	private final IEmfTable<R, P, S> table;

	public FkResolver(IEmfTable<R, P, S> table) {

		this.table = table;
	}

	public void resolve(int count) {

		String prefix = getPrefix(count);

		for (IDbField<R, ?> field: table.getBusinessKey().getFields()) {

			IEmfAttribute<R, ?> attribute = table.getAttribute(field);

			if (attribute instanceof EmfForeignRowAttribute) {

//				CastUtils.tryCast(attribute, EmfForeignRowAttribute.class);

				EmfForeignRowAttribute<R, ?> foreignRowAttribute = (EmfForeignRowAttribute<R, ?>) attribute;
				System.out.println(prefix + field.getTitle() + " (FK)");

				IEmfTable<?, ?, ?> emfTable = foreignRowAttribute.getTargetTable();
				new FkResolver<>(emfTable).resolve(count + 1);

			} else {
				System.out.println(prefix + field.getTitle());
			}
		}
	}

	private String getPrefix(int count) {

		String prefix = "";
		for (int i = 0; i < count; i++) {
			prefix += "\t";
		}
		return prefix;

	}

}
