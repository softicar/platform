package com.softicar.platform.db.runtime.object;

import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.logic.IDbObjectTable;

public class DbAutomaticTestObjectMaster extends AbstractDbObject<DbAutomaticTestObjectMaster> {

	// @formatter:off
	public static final DbObjectTableBuilder<DbAutomaticTestObjectMaster, DbAutomaticTestObjectMaster> BUILDER = new DbObjectTableBuilder<>("database", "automaticMaster", DbAutomaticTestObjectMaster::new, DbAutomaticTestObjectMaster.class);
	public static final IDbIdField<DbAutomaticTestObjectMaster> ID_FIELD = BUILDER.addIdField("id", o->o.id, (o,v)->o.id=v);
	public static final IDbForeignField<DbAutomaticTestObjectMaster, DbAutomaticTestObjectReference> FOREIGN_FIELD = BUILDER.addForeignField("referenceFK", o->o.foreign, (o,v)->o.foreign=v, DbAutomaticTestObjectReference.ID_FIELD).setNullable();
	public static final DbObjectTable<DbAutomaticTestObjectMaster> TABLE = new DbObjectTable<>(BUILDER);
	// @formatter:on

	private Integer id;
	private DbAutomaticTestObjectReference foreign;

	@Override
	public IDbObjectTable<DbAutomaticTestObjectMaster> table() {

		return TABLE;
	}
}
