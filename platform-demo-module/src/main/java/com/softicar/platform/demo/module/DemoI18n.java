package com.softicar.platform.demo.module;

import com.softicar.platform.common.core.i18n.I18n0;
import com.softicar.platform.workflow.module.WorkflowI18n;

public interface DemoI18n extends WorkflowI18n {

	I18n0 DEMO = new I18n0("Demo")//
		.de("Demo");
	I18n0 DEMO_MODULE_INSTANCE = new I18n0("Demo Module Instance")//
		.de("Demo-Modulinstanz");
	I18n0 DEMO_MODULE_INSTANCES = new I18n0("Demo Module Instances")//
		.de("Demo-Modulinstanzen");
}
