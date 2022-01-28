package com.softicar.platform.core.module.file.smb;

import com.softicar.platform.common.core.singleton.Singleton;
import com.softicar.platform.core.module.file.smb.jcifsng.JcifsNgSmbClient;
import java.util.Objects;

/**
 * Provides the {@link ISmbClient} singleton which is currently in use.
 *
 * @author Alexander Schmidt
 */
public class CurrentSmbClient {

	private static final Singleton<ISmbClient> VALUE = new Singleton<>(JcifsNgSmbClient::new);

	public static ISmbClient get() {

		return VALUE.get();
	}

	public static void set(ISmbClient api) {

		VALUE.set(Objects.requireNonNull(api));
	}
}
