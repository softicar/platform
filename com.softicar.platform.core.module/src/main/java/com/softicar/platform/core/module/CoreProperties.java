package com.softicar.platform.core.module;

import com.softicar.platform.common.core.properties.IProperty;
import com.softicar.platform.common.core.properties.PropertyFactory;
import com.softicar.platform.common.core.properties.SofticarConfiguration;
import java.io.File;

public interface CoreProperties {

	PropertyFactory FACTORY = SofticarConfiguration.createPropertyFactory(CoreProperties.class.getPackageName());

	IProperty<File> DEFAULT_THEME = FACTORY.createFileProperty("default.theme", null);
}
