package com.softicar.platform.core.module.module.instance;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.transaction.AGTransaction;
import com.softicar.platform.core.module.uuid.AGUuid;
import com.softicar.platform.db.runtime.field.IDbBooleanField;
import com.softicar.platform.db.runtime.field.IDbForeignField;
import com.softicar.platform.db.runtime.field.IDbIdField;
import com.softicar.platform.db.runtime.logic.AbstractDbObject;
import com.softicar.platform.db.runtime.object.DbObjectTableBuilder;
import com.softicar.platform.db.sql.statement.ISqlSelect;

/**
 * This is the automatically generated class AGModuleInstance for
 * database table <i>Core.ModuleInstance</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGModuleInstanceGenerated extends AbstractDbObject<AGModuleInstance> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGModuleInstance, AGModuleInstanceGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "ModuleInstance", AGModuleInstance::new, AGModuleInstance.class);
	static {
		BUILDER.setTitle(CoreI18n.MODULE_INSTANCE);
		BUILDER.setPluralTitle(CoreI18n.MODULE_INSTANCES);
	}

	public static final IDbIdField<AGModuleInstance> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGModuleInstance, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setCascade(false, true);
	public static final IDbBooleanField<AGModuleInstance> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setDefault(true);
	public static final IDbForeignField<AGModuleInstance, AGUuid> MODULE_UUID = BUILDER.addForeignField("moduleUuid", o->o.m_moduleUuid, (o,v)->o.m_moduleUuid=v, AGUuid.ID).setTitle(CoreI18n.MODULE_UUID);
	public static final AGModuleInstanceTable TABLE = new AGModuleInstanceTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGModuleInstance> createSelect() {

		return TABLE.createSelect();
	}

	public static AGModuleInstance get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final AGModuleInstance setTransaction(AGTransaction value) {

		return setValue(TRANSACTION, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGModuleInstance setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getModuleUuidID() {

		return getValueId(MODULE_UUID);
	}

	public final AGUuid getModuleUuid() {

		return getValue(MODULE_UUID);
	}

	public final AGModuleInstance setModuleUuid(AGUuid value) {

		return setValue(MODULE_UUID, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGModuleInstanceTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGTransaction m_transaction;
	private Boolean m_active;
	private AGUuid m_moduleUuid;
}

