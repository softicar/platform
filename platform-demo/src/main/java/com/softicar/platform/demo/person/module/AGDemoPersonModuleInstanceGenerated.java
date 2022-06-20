package com.softicar.platform.demo.person.module;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.module.instance.AGModuleInstance;
import com.softicar.platform.db.runtime.field.IDbBaseField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.object.sub.AbstractDbSubObject;
import com.softicar.platform.db.runtime.object.sub.DbSubObjectTableBuilder;
import com.softicar.platform.demo.DemoI18n;
import com.softicar.platform.demo.core.module.AGDemoCoreModuleInstance;

/**
 * This is the automatically generated class AGDemoPersonModuleInstance for
 * database table <i>Demo.DemoPersonModuleInstance</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGDemoPersonModuleInstanceGenerated extends AbstractDbSubObject<AGDemoPersonModuleInstance, AGModuleInstance> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbSubObjectTableBuilder<AGDemoPersonModuleInstance, AGDemoPersonModuleInstanceGenerated, AGModuleInstance, Integer> BUILDER = new DbSubObjectTableBuilder<>("Demo", "DemoPersonModuleInstance", AGDemoPersonModuleInstance::new, AGDemoPersonModuleInstance.class);
	static {
		BUILDER.setTitle(DemoI18n.DEMO_PERSON_MODULE_INSTANCE);
		BUILDER.setPluralTitle(DemoI18n.DEMO_PERSON_MODULE_INSTANCES);
	}

	public static final IDbBaseField<AGDemoPersonModuleInstance, AGModuleInstance, Integer> MODULE_INSTANCE = BUILDER.addBaseField("moduleInstance", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGModuleInstance.TABLE).setTitle(DemoI18n.MODULE_INSTANCE);
	public static final IDbForeignRowField<AGDemoPersonModuleInstance, AGDemoCoreModuleInstance, AGModuleInstance> DEMO_CORE_MODULE_INSTANCE = BUILDER.addForeignRowField("demoCoreModuleInstance", o->o.m_demoCoreModuleInstance, (o,v)->o.m_demoCoreModuleInstance=v, AGDemoCoreModuleInstance.MODULE_INSTANCE).setTitle(DemoI18n.DEMO_CORE_MODULE_INSTANCE);
	public static final IDbStringField<AGDemoPersonModuleInstance> TITLE = BUILDER.addStringField("title", o->o.m_title, (o,v)->o.m_title=v).setTitle(DemoI18n.TITLE).setDefault("").setMaximumLength(255);
	public static final IDbKey<AGDemoPersonModuleInstance> UK_DEMO_CORE_MODULE_INSTANCE = BUILDER.addUniqueKey("demoCoreModuleInstance", DEMO_CORE_MODULE_INSTANCE);
	public static final IDbKey<AGDemoPersonModuleInstance> UK_TITLE = BUILDER.addUniqueKey("title", TITLE);
	public static final AGDemoPersonModuleInstanceTable TABLE = new AGDemoPersonModuleInstanceTable(BUILDER);
	// @formatter:on

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final AGModuleInstance getModuleInstance() {

		return pk();
	}

	public final AGDemoCoreModuleInstance getDemoCoreModuleInstance() {

		return getValue(DEMO_CORE_MODULE_INSTANCE);
	}

	public final AGDemoPersonModuleInstance setDemoCoreModuleInstance(AGDemoCoreModuleInstance value) {

		return setValue(DEMO_CORE_MODULE_INSTANCE, value);
	}

	public final String getTitle() {

		return getValue(TITLE);
	}

	public final AGDemoPersonModuleInstance setTitle(String value) {

		return setValue(TITLE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGDemoPersonModuleInstanceTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGModuleInstance m_moduleInstance;
	private AGDemoCoreModuleInstance m_demoCoreModuleInstance;
	private String m_title;
}

