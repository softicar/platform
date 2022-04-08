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

	// Caution:
	// Unless we reuse the base context upon consecutive file/entry operations, Microsoft file servers will enforce a new connection
	// upon each operation, which is a huge waste of time. This is not an issue with Samba based file servers.
	// This field is static, as a blunt means of reusing the base context.
	private static final BaseContext BASE_CONTEXT = createBaseContext();

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
		return BASE_CONTEXT.withCredentials(authenticator);
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

			// Caution:
			// The "BCAST" resolver is omitted because it slows down SMB connection creation during unit tests of Docker-in-Docker based builds.
			// The "WINS" resolver is omitted because it causes errors being printed upon connection attempts.
			// The "LMHOSTS" resolver is omitted because nobody seems to use that anyway.
			// This leaves us with "DNS" only.
			properties.setProperty("jcifs.resolveOrder", "DNS");

			// Caution:
			// We enforce at least SMB 2.1 ("SMB210") here - see enum "jcifs.DialectVersion" for possible values.
			properties.setProperty("jcifs.smb.client.minVersion", "SMB210");

			return new BaseContext(new PropertyConfiguration(properties));
		} catch (CIFSException exception) {
			throw new SmbIOException(exception);
		}
	}
}
