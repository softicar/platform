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
 * This is the automatically generated class AGModuleInstanceBase for
 * database table <i>Core.ModuleInstanceBase</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGModuleInstanceBaseGenerated extends AbstractDbObject<AGModuleInstanceBase> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbObjectTableBuilder<AGModuleInstanceBase, AGModuleInstanceBaseGenerated> BUILDER = new DbObjectTableBuilder<>("Core", "ModuleInstanceBase", AGModuleInstanceBase::new, AGModuleInstanceBase.class);
	static {
		BUILDER.setTitle(CoreI18n.MODULE_INSTANCE_BASE);
		BUILDER.setPluralTitle(CoreI18n.MODULE_INSTANCE_BASES);
	}

	public static final IDbIdField<AGModuleInstanceBase> ID = BUILDER.addIdField("id", o->o.m_id, (o,v)->o.m_id=v).setTitle(CoreI18n.ID);
	public static final IDbForeignField<AGModuleInstanceBase, AGTransaction> TRANSACTION = BUILDER.addForeignField("transaction", o->o.m_transaction, (o,v)->o.m_transaction=v, AGTransaction.ID).setTitle(CoreI18n.TRANSACTION).setCascade(false, true);
	public static final IDbBooleanField<AGModuleInstanceBase> ACTIVE = BUILDER.addBooleanField("active", o->o.m_active, (o,v)->o.m_active=v).setTitle(CoreI18n.ACTIVE).setDefault(true);
	public static final IDbForeignField<AGModuleInstanceBase, AGUuid> MODULE_UUID = BUILDER.addForeignField("moduleUuid", o->o.m_moduleUuid, (o,v)->o.m_moduleUuid=v, AGUuid.ID).setTitle(CoreI18n.MODULE_UUID);
	public static final AGModuleInstanceBaseTable TABLE = new AGModuleInstanceBaseTable(BUILDER);
	// @formatter:on

	// -------------------------------- STATIC FUNCTIONS -------------------------------- //

	public static ISqlSelect<AGModuleInstanceBase> createSelect() {

		return TABLE.createSelect();
	}

	public static AGModuleInstanceBase get(Integer id) {

		return TABLE.get(id);
	}

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final Integer getTransactionID() {

		return getValueId(TRANSACTION);
	}

	public final AGTransaction getTransaction() {

		return getValue(TRANSACTION);
	}

	public final AGModuleInstanceBase setTransaction(AGTransaction value) {

		return setValue(TRANSACTION, value);
	}

	public final Boolean isActive() {

		return getValue(ACTIVE);
	}

	public final AGModuleInstanceBase setActive(Boolean value) {

		return setValue(ACTIVE, value);
	}

	public final Integer getModuleUuidID() {

		return getValueId(MODULE_UUID);
	}

	public final AGUuid getModuleUuid() {

		return getValue(MODULE_UUID);
	}

	public final AGModuleInstanceBase setModuleUuid(AGUuid value) {

		return setValue(MODULE_UUID, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGModuleInstanceBaseTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private Integer m_id;
	private AGTransaction m_transaction;
	private Boolean m_active;
	private AGUuid m_moduleUuid;
}

