package com.softicar.platform.emf.test.simple.list;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.db.runtime.field.IDbByteArrayField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;

public class EmfTestObjectList extends AbstractEmfTestObjectList {

	// @formatter:off
	private static final DbObjectTableBuilder<EmfTestObjectList, EmfTestObjectList> BUILDER = new DbObjectTableBuilder<>("Test", "EntityList", EmfTestObjectList::new, EmfTestObjectList.class);
	static {
		BUILDER.setTitle(IDisplayString.create("Test Object List"));
		BUILDER.setTitle(IDisplayString.create("Test Object Lists"));
	}
	public static final IDbIdField<EmfTestObjectList> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v);
	public static final IDbByteArrayField<EmfTestObjectList> HASH = BUILDER.addByteArrayField("hash", o->o.m_hash, (o,v)->o.m_hash=v).setDefault(new byte[0]);
	public static final EmfTestObjectListTable TABLE = new EmfTestObjectListTable(BUILDER);
	// @formatter:on

	@Override
	public EmfTestObjectListTable table() {

		return TABLE;
	}

	private Integer m_id;
	private byte[] m_hash;
}
