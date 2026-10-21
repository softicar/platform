package com.softicar.platform.core.module;

import com.softicar.platform.common.core.properties.IProperty;
import com.softicar.platform.common.core.properties.PropertyFactory;
import com.softicar.platform.common.core.properties.SofticarConfiguration;
import java.io.File;

public interface CoreProperties {

	PropertyFactory FACTORY = SofticarConfiguration.createPropertyFactory(CoreProperties.class.getPackageName());

	IProperty<File> DEFAULT_THEME = FACTORY.createFileProperty("default.theme", null);

	/**
	 * The proxies from which the <i>X-Forwarded-For</i> header is accepted, as
	 * comma separated IPv4 addresses or networks in CIDR notation.
	 * <p>
	 * Defaults to the loopback and private networks. An empty value disables
	 * the evaluation of the <i>X-Forwarded-For</i> header.
	 *
	 * @see com.softicar.platform.core.module.web.servlet.ClientAddressDeterminer
	 */
	IProperty<String> TRUSTED_PROXIES = FACTORY.createStringProperty("trusted.proxies", "127.0.0.0/8, 10.0.0.0/8, 172.16.0.0/12, 192.168.0.0/16");
}
