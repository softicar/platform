package com.softicar.platform.emf.test.simple.set;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbByteArrayField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

public class EmfTestObjectSet extends AbstractEmfTestObjectSet {

	// @formatter:off
	private static final DbObjectTableBuilder<EmfTestObjectSet, EmfTestObjectSet> BUILDER = new DbObjectTableBuilder<>("Test", "EntitySet", EmfTestObjectSet::new, EmfTestObjectSet.class);
	static {
		BUILDER.setTitle(IDisplayString.create("Test Object Set"));
		BUILDER.setTitle(IDisplayString.create("Test Object Sets"));
	}
	public static final IDbIdField<EmfTestObjectSet> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v);
	public static final IDbByteArrayField<EmfTestObjectSet> HASH = BUILDER.addByteArrayField("hash", o->o.m_hash, (o,v)->o.m_hash=v).setDefault(new byte[0]);
	public static final EmfTestObjectSetTable TABLE = new EmfTestObjectSetTable(BUILDER);
	// @formatter:on

	@Override
	public EmfTestObjectSetTable table() {

		return TABLE;
	}

	private Integer m_id;
	private byte[] m_hash;
}
