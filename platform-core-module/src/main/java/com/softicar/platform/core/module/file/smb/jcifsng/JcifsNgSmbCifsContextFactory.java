package com.softicar.platform.core.module.file.smb.jcifsng;

import com.softicar.platform.core.module.file.smb.SmbCredentials;
import com.softicar.platform.core.module.file.smb.SmbIOException;
import java.util.Objects;
import java.util.Properties;
import jcifs.CIFSContext;
import jcifs.CIFSException;
import jcifs.config.PropertyConfiguration;
import jcifs.context.BaseContext;
import jcifs.smb.NtlmPasswordAuthenticator;

/**
 * A factory for {@link CIFSContext} instances.
 *
 * @author Alexander Schmidt
 * @author Daniel Klose
 */
class JcifsNgSmbCifsContextFactory {

	/**
	 * Derives a {@link CIFSContext} from the given {@link SmbCredentials}.
	 *
	 * @param credentials
	 *            the credentials for an SMB share (never <i>null</i>)
	 * @return a new {@link CIFSContext}, derived from the given
	 *         {@link SmbCredentials} (never <i>null</i>)
	 */
	public static CIFSContext create(SmbCredentials credentials) {

		var authenticator = createAuthenticator(credentials);
		return createBaseContext().withCredentials(authenticator);
	}

	private static NtlmPasswordAuthenticator createAuthenticator(SmbCredentials credentials) {

		Objects.requireNonNull(credentials);
		return new NtlmPasswordAuthenticator(//
			credentials.getDomain(),
			credentials.getUsername(),
			credentials.getPassword());
	}

	private static BaseContext createBaseContext() {

		try {
			var properties = new Properties();
			// fast
//			properties.setProperty("jcifs.resolveOrder", "DNS");

			// slow
//			properties.setProperty("jcifs.resolveOrder", "DNS,LMHOSTS,WINS,BCAST");

			// testing
			properties.setProperty("jcifs.resolveOrder", "DNS,LMHOSTS,BCAST");
			return new BaseContext(new PropertyConfiguration(properties));
		} catch (CIFSException exception) {
			throw new SmbIOException(exception);
		}
	}
}
