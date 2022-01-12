package com.softicar.platform.core.module.email;

import com.softicar.platform.common.core.properties.IProperty;
import com.softicar.platform.common.core.properties.PropertyFactory;
import com.softicar.platform.common.core.properties.SofticarConfiguration;
import java.io.File;

/**
 * Enumeration of all configuration properties of the email system.
 *
 * @author Oliver Richers
 */
public abstract class EmailSystemProperties {

	// TODO the property prefix should be adapted to match existing packages
	private static final PropertyFactory FACTORY = SofticarConfiguration.createPropertyFactory("com.softicar.common.mail");

	public static final IProperty<Boolean> DUMPING_ENABLED = FACTORY.createBooleanProperty("dumping.enabled", false);
	public static final IProperty<File> DUMPING_FOLDER = FACTORY.createFileProperty("dumping.folder", "~/.softicar/mail.dumps/");
	public static final IProperty<String> REDIRECTION_ADDRESS = FACTORY.createStringProperty("redirection.address", null);
	public static final IProperty<Boolean> SENDING_ENABLED = FACTORY.createBooleanProperty("sending.enabled", true);
}
