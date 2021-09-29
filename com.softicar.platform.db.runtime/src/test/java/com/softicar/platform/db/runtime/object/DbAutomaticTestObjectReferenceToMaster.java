package com.softicar.platform.db.runtime.object;

import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.logic.IDbObjectTable;

public class DbAutomaticTestObjectReferenceToMaster extends AbstractDbObject<DbAutomaticTestObjectReferenceToMaster> {

	// @formatter:off
		public static final DbObjectTableBuilder<DbAutomaticTestObjectReferenceToMaster, DbAutomaticTestObjectReferenceToMaster> BUILDER = new DbObjectTableBuilder<>("database", "automaticReferenceToMaster", DbAutomaticTestObjectReferenceToMaster::new, DbAutomaticTestObjectReferenceToMaster.class);
		public static final IDbIdField<DbAutomaticTestObjectReferenceToMaster> ID_FIELD = BUILDER.addIdField("id", o->o.id, (o,v)->o.id=v);
		public static final IDbForeignField<DbAutomaticTestObjectReferenceToMaster, DbAutomaticTestObjectMaster> FOREIGN_FIELD = BUILDER.addForeignField("masterFk", o->o.foreign, (o,v)->o.foreign=v, DbAutomaticTestObjectMaster.ID_FIELD).setNullable();
		public static final DbObjectTable<DbAutomaticTestObjectReferenceToMaster> TABLE = new DbObjectTable<>(BUILDER);
		// @formatter:on

	private Integer id;
	private DbAutomaticTestObjectMaster foreign;

	@Override
	public IDbObjectTable<DbAutomaticTestObjectReferenceToMaster> table() {

		return TABLE;
	}
}
