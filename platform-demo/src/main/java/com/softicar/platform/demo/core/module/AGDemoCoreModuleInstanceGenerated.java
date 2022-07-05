package com.softicar.platform.demo.core.module;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.module.instance.AGModuleInstanceBase;
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
public class AGDemoCoreModuleInstanceGenerated extends AbstractDbSubObject<AGDemoCoreModuleInstance, AGModuleInstanceBase> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbSubObjectTableBuilder<AGDemoCoreModuleInstance, AGDemoCoreModuleInstanceGenerated, AGModuleInstanceBase, Integer> BUILDER = new DbSubObjectTableBuilder<>("Demo", "DemoCoreModuleInstance", AGDemoCoreModuleInstance::new, AGDemoCoreModuleInstance.class);
	static {
		BUILDER.setTitle(DemoI18n.DEMO_CORE_MODULE_INSTANCE);
		BUILDER.setPluralTitle(DemoI18n.DEMO_CORE_MODULE_INSTANCES);
	}

	public static final IDbBaseField<AGDemoCoreModuleInstance, AGModuleInstanceBase, Integer> BASE = BUILDER.addBaseField("base", o->o.m_base, (o,v)->o.m_base=v, AGModuleInstanceBase.TABLE).setTitle(DemoI18n.BASE);
	public static final IDbStringField<AGDemoCoreModuleInstance> TITLE = BUILDER.addStringField("title", o->o.m_title, (o,v)->o.m_title=v).setTitle(DemoI18n.TITLE).setDefault("").setMaximumLength(255);
	public static final IDbKey<AGDemoCoreModuleInstance> UK_TITLE = BUILDER.addUniqueKey("title", TITLE);
	public static final AGDemoCoreModuleInstanceTable TABLE = new AGDemoCoreModuleInstanceTable(BUILDER);
	// @formatter:on

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final AGModuleInstanceBase getBase() {

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

	private AGModuleInstanceBase m_base;
	private String m_title;
}

