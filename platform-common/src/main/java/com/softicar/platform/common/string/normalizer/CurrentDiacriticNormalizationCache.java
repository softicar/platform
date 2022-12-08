package com.softicar.platform.common.string.normalizer;

import com.softicar.platform.common.core.singleton.Singleton;

/**
 * A {@link Singleton} that holds the currently-used
 * {@link DiacriticNormalizationCache}.
 *
 * @author Alexander Schmidt
 */
public class CurrentDiacriticNormalizationCache {

	private static final Singleton<DiacriticNormalizationCache> INSTANCE = new Singleton<>(DiacriticNormalizationCache::new);

	/**
	 * Returns the currently-used {@link DiacriticNormalizationCache}.
	 *
	 * @return the current {@link DiacriticNormalizationCache} (never
	 *         <i>null</i>)
	 */
	public static DiacriticNormalizationCache get() {

		return INSTANCE.get();
	}
}
