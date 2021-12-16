package com.softicar.platform.demo.module;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.db.runtime.field.IDbBaseField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.object.sub.AbstractDbSubObject;
import com.softicar.platform.db.runtime.object.sub.DbSubObjectTableBuilder;

/**
 * This is the automatically generated class AGDemoModuleInstance for
 * database table <i>Demo.DemoModuleInstance</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGDemoModuleInstanceGenerated extends AbstractDbSubObject<AGDemoModuleInstance, AGModuleInstance> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbSubObjectTableBuilder<AGDemoModuleInstance, AGDemoModuleInstanceGenerated, AGModuleInstance, Integer> BUILDER = new DbSubObjectTableBuilder<>("Demo", "DemoModuleInstance", AGDemoModuleInstance::new, AGDemoModuleInstance.class);
	static {
		BUILDER.setTitle(DemoI18n.DEMO_MODULE_INSTANCE);
		BUILDER.setPluralTitle(DemoI18n.DEMO_MODULE_INSTANCES);
	}

	public static final IDbBaseField<AGDemoModuleInstance, AGModuleInstance, Integer> MODULE_INSTANCE = BUILDER.addBaseField("moduleInstance", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGModuleInstance.TABLE).setTitle(DemoI18n.MODULE_INSTANCE);
	public static final IDbStringField<AGDemoModuleInstance> TITLE = BUILDER.addStringField("title", o->o.m_title, (o,v)->o.m_title=v).setTitle(DemoI18n.TITLE).setDefault("").setMaximumLength(255);
	public static final AGDemoModuleInstanceTable TABLE = new AGDemoModuleInstanceTable(BUILDER);
	// @formatter:on

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final AGModuleInstance getModuleInstance() {

		return pk();
	}

	public final String getTitle() {

		return getValue(TITLE);
	}

	public final AGDemoModuleInstance setTitle(String value) {

		return setValue(TITLE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGDemoModuleInstanceTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGModuleInstance m_moduleInstance;
	private String m_title;
}

