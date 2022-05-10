package com.softicar.platform.demo.module.business.unit;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.db.runtime.field.IDbBaseField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.object.sub.AbstractDbSubObject;
import com.softicar.platform.db.runtime.object.sub.DbSubObjectTableBuilder;
import com.softicar.platform.demo.module.DemoI18n;

/**
 * This is the automatically generated class AGDemoBusinessUnitModuleInstance for
 * database table <i>Demo.DemoBusinessUnitModuleInstance</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGDemoBusinessUnitModuleInstanceGenerated extends AbstractDbSubObject<AGDemoBusinessUnitModuleInstance, AGModuleInstance> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbSubObjectTableBuilder<AGDemoBusinessUnitModuleInstance, AGDemoBusinessUnitModuleInstanceGenerated, AGModuleInstance, Integer> BUILDER = new DbSubObjectTableBuilder<>("Demo", "DemoBusinessUnitModuleInstance", AGDemoBusinessUnitModuleInstance::new, AGDemoBusinessUnitModuleInstance.class);
	static {
		BUILDER.setTitle(DemoI18n.DEMO_BUSINESS_UNIT_MODULE_INSTANCE);
		BUILDER.setPluralTitle(DemoI18n.DEMO_BUSINESS_UNIT_MODULE_INSTANCES);
	}

	public static final IDbBaseField<AGDemoBusinessUnitModuleInstance, AGModuleInstance, Integer> MODULE_INSTANCE = BUILDER.addBaseField("moduleInstance", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGModuleInstance.TABLE).setTitle(DemoI18n.MODULE_INSTANCE);
	public static final IDbStringField<AGDemoBusinessUnitModuleInstance> TITLE = BUILDER.addStringField("title", o->o.m_title, (o,v)->o.m_title=v).setTitle(DemoI18n.TITLE).setDefault("").setMaximumLength(255);
	public static final IDbKey<AGDemoBusinessUnitModuleInstance> UK_TITLE = BUILDER.addUniqueKey("title", TITLE);
	public static final AGDemoBusinessUnitModuleInstanceTable TABLE = new AGDemoBusinessUnitModuleInstanceTable(BUILDER);
	// @formatter:on

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final AGModuleInstance getModuleInstance() {

		return pk();
	}

	public final String getTitle() {

		return getValue(TITLE);
	}

	public final AGDemoBusinessUnitModuleInstance setTitle(String value) {

		return setValue(TITLE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGDemoBusinessUnitModuleInstanceTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGModuleInstance m_moduleInstance;
	private String m_title;
}

