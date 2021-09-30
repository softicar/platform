package com.softicar.platform.db.runtime.object;

import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.logic.DbObjectTable;
import com.softicar.platform.db.runtime.logic.IDbObjectTable;

public class DbAutomaticTestObjectReference extends AbstractDbObject<DbAutomaticTestObjectReference> {

	// @formatter:off
	public static final DbObjectTableBuilder<DbAutomaticTestObjectReference, DbAutomaticTestObjectReference> BUILDER = new DbObjectTableBuilder<>("database", "automaticReference", DbAutomaticTestObjectReference::new, DbAutomaticTestObjectReference.class);
	public static final IDbIdField<DbAutomaticTestObjectReference> ID_FIELD = BUILDER.addIdField("id", o->o.id, (o,v)->o.id=v);
	public static final IDbForeignField<DbAutomaticTestObjectReference, DbAutomaticTestObjectReferenceToMaster> FOREIGN_FIELD = BUILDER.addForeignField("referenceToMasterFk", o->o.foreign, (o,v)->o.foreign=v, DbAutomaticTestObjectReferenceToMaster.ID_FIELD).setNullable();
	public static final DbObjectTable<DbAutomaticTestObjectReference> TABLE = new DbObjectTable<>(BUILDER);
	// @formatter:on

	private Integer id;
	private DbAutomaticTestObjectReferenceToMaster foreign;

	@Override
	public IDbObjectTable<DbAutomaticTestObjectReference> table() {

		return TABLE;
	}
}
