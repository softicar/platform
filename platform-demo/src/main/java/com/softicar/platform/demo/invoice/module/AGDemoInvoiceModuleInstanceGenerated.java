package com.softicar.platform.demo.invoice.module;

import com.softicar.platform.common.core.annotations.Generated;
import com.softicar.platform.core.module.access.module.instance.AGModuleInstance;
import com.softicar.platform.db.runtime.field.IDbBaseField;
import com.softicar.platform.db.runtime.field.IDbForeignRowField;
import com.softicar.platform.db.runtime.field.IDbStringField;
import com.softicar.platform.db.runtime.key.IDbKey;
import com.softicar.platform.db.runtime.object.sub.AbstractDbSubObject;
import com.softicar.platform.db.runtime.object.sub.DbSubObjectTableBuilder;
import com.softicar.platform.demo.DemoI18n;
import com.softicar.platform.demo.person.module.AGDemoPersonModuleInstance;

/**
 * This is the automatically generated class AGDemoInvoiceModuleInstance for
 * database table <i>Demo.DemoInvoiceModuleInstance</i>.
 */
@Generated
@SuppressWarnings("all")
public class AGDemoInvoiceModuleInstanceGenerated extends AbstractDbSubObject<AGDemoInvoiceModuleInstance, AGModuleInstance> {

	// -------------------------------- STATIC CONSTANTS -------------------------------- //

	// @formatter:off
	private static final DbSubObjectTableBuilder<AGDemoInvoiceModuleInstance, AGDemoInvoiceModuleInstanceGenerated, AGModuleInstance, Integer> BUILDER = new DbSubObjectTableBuilder<>("Demo", "DemoInvoiceModuleInstance", AGDemoInvoiceModuleInstance::new, AGDemoInvoiceModuleInstance.class);
	static {
		BUILDER.setTitle(DemoI18n.DEMO_INVOICE_MODULE_INSTANCE);
		BUILDER.setPluralTitle(DemoI18n.DEMO_INVOICE_MODULE_INSTANCES);
	}

	public static final IDbBaseField<AGDemoInvoiceModuleInstance, AGModuleInstance, Integer> MODULE_INSTANCE = BUILDER.addBaseField("moduleInstance", o->o.m_moduleInstance, (o,v)->o.m_moduleInstance=v, AGModuleInstance.TABLE).setTitle(DemoI18n.MODULE_INSTANCE);
	public static final IDbForeignRowField<AGDemoInvoiceModuleInstance, AGDemoPersonModuleInstance, AGModuleInstance> DEMO_PERSON_MODULE_INSTANCE = BUILDER.addForeignRowField("demoPersonModuleInstance", o->o.m_demoPersonModuleInstance, (o,v)->o.m_demoPersonModuleInstance=v, AGDemoPersonModuleInstance.MODULE_INSTANCE).setTitle(DemoI18n.DEMO_PERSON_MODULE_INSTANCE);
	public static final IDbStringField<AGDemoInvoiceModuleInstance> TITLE = BUILDER.addStringField("title", o->o.m_title, (o,v)->o.m_title=v).setTitle(DemoI18n.TITLE).setDefault("").setMaximumLength(255);
	public static final IDbKey<AGDemoInvoiceModuleInstance> UK_DEMO_PERSON_MODULE_INSTANCE = BUILDER.addUniqueKey("demoPersonModuleInstance", DEMO_PERSON_MODULE_INSTANCE);
	public static final IDbKey<AGDemoInvoiceModuleInstance> UK_TITLE = BUILDER.addUniqueKey("title", TITLE);
	public static final AGDemoInvoiceModuleInstanceTable TABLE = new AGDemoInvoiceModuleInstanceTable(BUILDER);
	// @formatter:on

	// -------------------------------- GETTERS AND SETTERS -------------------------------- //

	public final AGModuleInstance getModuleInstance() {

		return pk();
	}

	public final AGDemoPersonModuleInstance getDemoPersonModuleInstance() {

		return getValue(DEMO_PERSON_MODULE_INSTANCE);
	}

	public final AGDemoInvoiceModuleInstance setDemoPersonModuleInstance(AGDemoPersonModuleInstance value) {

		return setValue(DEMO_PERSON_MODULE_INSTANCE, value);
	}

	public final String getTitle() {

		return getValue(TITLE);
	}

	public final AGDemoInvoiceModuleInstance setTitle(String value) {

		return setValue(TITLE, value);
	}

	// -------------------------------- UTILS -------------------------------- //

	@Override
	public final AGDemoInvoiceModuleInstanceTable table() {

		return TABLE;
	}

	// -------------------------------- FIELD MEMBERS -------------------------------- //

	private AGModuleInstance m_moduleInstance;
	private AGDemoPersonModuleInstance m_demoPersonModuleInstance;
	private String m_title;
}

