package com.softicar.platform.core.module.component.external.license.rule;

import com.softicar.platform.common.core.singleton.Singleton;
import com.softicar.platform.core.module.component.external.type.ExternalComponentType;
import com.softicar.platform.core.module.license.License;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Provides built-in {@link ExternalComponentLicenseRule} definitions.
 *
 * @author Alexander Schmidt
 */
public class ExternalComponentLicensePredefinedRules {

	private static final Singleton<ExternalComponentLicensePredefinedRules> INSTANCE = new Singleton<>(ExternalComponentLicensePredefinedRules::new);

	private final List<ExternalComponentLicenseRule> list;

	private ExternalComponentLicensePredefinedRules() {

		this.list = new ArrayList<>();

		// Artifact does not contain license information.
		// License defined at:
		// https://www.bouncycastle.org/licence.html
		addLibrary("bcprov-jdk15on", ".*", License.BOUNCY_CASTLE);

		// Artifact does not contain license information.
		// License defined at:
		// https://github.com/google/guava/blob/master/COPYING
		addLibrary("listenablefuture", ".*", License.APACHE_2_0);

		// Dual-Licensed (MPL 2.0 and EPL 1.0) - we choose EPL 1.0.
		// License defined at:
		// http://h2database.com/html/license.html
		addLibrary("h2", ".*", License.EPL_1_0);

		// Artifact does not contain license information.
		// License defined at:
		// https://github.com/AgNO3/jcifs-ng/blob/master/LICENSE
		addLibrary("jcifs-ng", ".*", License.LGPL_2_1);

		// Artifact does not contain license information.
		// License defined at:
		// https://github.com/cbeust/jcommander/blob/master/license.txt
		addLibrary("jcommander", ".*", License.APACHE_2_0);

		// Artifact does not contain license information.
		// License defined at:
		// https://github.com/findbugsproject/findbugs/blob/master/findbugs/licenses/LICENSE-jsr305.txt
		addLibrary("jsr305", ".*", License.BSD_3_CLAUSE);

		// Leptonica library, in a C++-to-Java wrapper.
		// The wrapper license (Apache 2.0) is more restrictive than the library license (BSD 2-Clause).
		// License defined at:
		// https://github.com/bytedeco/javacpp-presets/blob/master/LICENSE.txt (C++ wrapper; Apache 2.0)
		// http://www.leptonica.org/about-the-license.html (Leptonica library; BSD 2-Clause)
		addLibrary("leptonica(-platform)?", ".*", License.APACHE_2_0);

		// Artifacts do not contain license information.
		// License defined at:
		// https://github.com/open-telemetry/opentelemetry-java/blob/main/LICENSE
		addLibrary("opentelemetry-.*", ".*", License.APACHE_2_0);

		// Artifact does not contain license information.
		// License defined at:
		// https://github.com/reactive-streams/reactive-streams-jvm/blob/master/LICENSE
		addLibrary("reactive-streams", ".*", License.MIT_0);

		// Artifacts do not contain license information.
		// License defined at:
		// https://github.com/SeleniumHQ/selenium/blob/trunk/LICENSE
		addLibrary("selenium-.*", ".*", License.APACHE_2_0);

		// Artifact does not contain license information.
		// License defined at:
		// https://github.com/qos-ch/slf4j/blob/master/LICENSE.txt
		addLibrary("slf4j-api", ".*", License.MIT);

		// Tesseract library, in a C++-to-Java wrapper.
		// License defined at:
		// https://github.com/bytedeco/javacpp-presets/blob/master/tesseract/LICENSE (C++ wrapper; Apache 2.0)
		// https://github.com/tesseract-ocr/tesseract/blob/master/LICENSE (Tesseract library; Apache 2.0)
		addLibrary("tesseract(-platform)?", ".*", License.APACHE_2_0);
	}

	private void addLibrary(String namePattern, String versionPattern, License license) {

		add(ExternalComponentType.LIBRARY, namePattern, versionPattern, license);
	}

	private void add(ExternalComponentType type, String namePattern, String versionPattern, License license) {

		var rule = new ExternalComponentLicenseRule(//
			type,
			"^" + namePattern + "$",
			versionPattern,
			license);
		list.add(rule);
	}

	/**
	 * Returns all built-in {@link ExternalComponentLicenseRule} definitions.
	 *
	 * @return the {@link ExternalComponentLicenseRule} definitions (never
	 *         <i>null</i>)
	 */
	public static Collection<ExternalComponentLicenseRule> getAll() {

		return Collections.unmodifiableList(INSTANCE.get().list);
	}
}
