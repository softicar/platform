package com.softicar.platform.common.network.ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.softicar.platform.common.core.properties.SystemPropertyEnum;

public class JschUtils {

	public static void addPublicKeyIdentity(JSch jsch) {

		try {
			jsch.addIdentity(getPublicKeyFilePath());
		} catch (JSchException exception) {
			throw new RuntimeException(exception);
		}
	}

	private static String getPublicKeyFilePath() {

		return String.format("%s/.ssh/id_rsa", SystemPropertyEnum.USER_HOME.get());
	}
}
