package com.softicar.platform.demo.core.module;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.db.runtime.field.IDbBaseField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.object.sub.AbstractDbSubObject;
import com.softicar.platform.db.runtime.object.sub.DbSubObjectTableBuilder;
import com.softicar.platform.demo.DemoI18n;

/**
 * This is the automatically generated class AGDemoCoreModuleInstance for
 * database table <i>Demo.DemoCoreModuleInstance</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGDemoCoreModuleInstanceGenerated extends AbstractDbSubObject<AGDemoCoreModuleInstance, AGModuleInstance> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbSubObjectTableBuilder<AGDemoCoreModuleInstance, AGDemoCoreModuleInstanceGenerated, AGModuleInstance, Integer> BUILDER = new DbSubObjectTableBuilder<>("Demo", "DemoCoreModuleInstance", AGDemoCoreModuleInstance::new, AGDemoCoreModuleInstance.class);
	static {
		BUILDER.setTitle(DemoI18n.DEMO_CORE_MODULE_INSTANCE);
		BUILDER.setPluralTitle(DemoI18n.DEMO_CORE_MODULE_INSTANCES);
	}

	public static final IDbBaseField<AGDemoCoreModuleInstance, AGModuleInstance, Integer> MODULE_INSTANCE = BUILDER.addBaseField("moduleInstance", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGModuleInstance.TABLE).setTitle(DemoI18n.MODULE_INSTANCE);
	public static final IDbStringField<AGDemoCoreModuleInstance> TITLE = BUILDER.addStringField("title", o->o.m_title, (o,v)->o.m_title=v).setTitle(DemoI18n.TITLE).setDefault("").setMaximumLength(255);
	public static final IDbKey<AGDemoCoreModuleInstance> UK_TITLE = BUILDER.addUniqueKey("title", TITLE);
	public static final AGDemoCoreModuleInstanceTable TABLE = new AGDemoCoreModuleInstanceTable(BUILDER);
	// @formatter:on

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final AGModuleInstance getModuleInstance() {

		return pk();
	}

	public final String getTitle() {

		return getValue(TITLE);
	}

	public final AGDemoCoreModuleInstance setTitle(String value) {

		return setValue(TITLE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGDemoCoreModuleInstanceTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGModuleInstance m_moduleInstance;
	private String m_title;
}
