package com.softicar.platform.core.module.file.smb;

import com.softicar.platform.common.core.singleton.Singleton;
import com.softicar.platform.core.module.file.smb.jcifs.JcifsSmbApi;
import java.util.Objects;

public class CurrentSmbApi {

	private static final Singleton<ISmbApi> VALUE = new Singleton<>(JcifsSmbApi::new);

	public static ISmbApi get() {

		return VALUE.get();
	}

	public static void set(ISmbApi api) {

		VALUE.set(Objects.requireNonNull(api));
	}
}
