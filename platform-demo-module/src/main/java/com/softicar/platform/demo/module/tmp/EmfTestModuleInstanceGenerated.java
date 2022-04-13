package com.softicar.platform.demo.module.tmp;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;
import com.softicar.platform.demo.module.DemoI18n;

/**
 * This is the automatically generated class AGModuleInstance for
 * database table <i>Test.ModuleInstance</i>.
 */
@Generated
@SuppressWarnings("all")
public class EmfTestModuleInstanceGenerated extends AbstractDbObject<EmfTestModuleInstance> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<EmfTestModuleInstance, EmfTestModuleInstanceGenerated> BUILDER = new DbObjectTableBuilder<>("Test", "ModuleInstance", EmfTestModuleInstance::new, EmfTestModuleInstance.class);
	static {
		BUILDER.setTitle(DemoI18n.MODULE_INSTANCE);
		BUILDER.setPluralTitle(DemoI18n.MODULE_INSTANCES);
	}

	public static final IDbIdField<EmfTestModuleInstance> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(DemoI18n.ID);
	public static final IDbForeignField<EmfTestModuleInstance, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(DemoI18n.TRANSACTION).setCascade(false, true);
	public static final IDbBooleanField<EmfTestModuleInstance> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(DemoI18n.ACTIVE).setDefault(true);
	public static final IDbForeignField<EmfTestModuleInstance, AGUuid> MODULE_UUID = BUILDER.addForeignField("moduleUuid", o->o.m_moduleUuid, (o,v)->o.m_moduleUuid=v, AGUuid.ID).setTitle(DemoI18n.MODULE_UUID);
	public static final EmfTestModuleInstanceTable TABLE = new EmfTestModuleInstanceTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<EmfTestModuleInstance> createSelect() {

		return TABLE.createSelect();
	}

	public static EmfTestModuleInstance get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final EmfTestModuleInstance setTransaction(AGTransaction value) {

		return setValue(TRANSACTION, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final EmfTestModuleInstance setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getModuleUuidID() {

		return getValueId(MODULE_UUID);
	}

	public final AGUuid getModuleUuid() {

		return getValue(MODULE_UUID);
	}

	public final EmfTestModuleInstance setModuleUuid(AGUuid value) {

		return setValue(MODULE_UUID, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final EmfTestModuleInstanceTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGTransaction m_transaction;
	private Boolean m_active;
	private AGUuid m_moduleUuid;
}

