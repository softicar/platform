package com.softicar.platform.core.module.license;

/**
 * An enumeration of well-known licenses.
 *
 * @author Alexander Schmidt
 */
public enum License {

	APACHE_2_0("Apache v2.0"),
	BOUNCY_CASTLE("Bouncy Castle"),
	BSD_3_CLAUSE("BSD 3-Clause"),
	BSD_4_CLAUSE("BSD 4-Clause"),
	CDDL_1_0("CDDL v1.0"),
	EPL_1_0("EPL v1.0"),
	EPL_2_0("EPL v2.0"),
	JJ_2000("JJ2000"),
	LGPL_2_1("LGPL v2.1"),
	MIT("MIT"),
	MIT_0("MIT-0"),
	OFL_1_1("OFL v1.1"),
	//
	;

	private final String name;

	private License(String name) {

		this.name = name;
	}

	/**
	 * Returns the name of the license.
	 *
	 * @return the license name (never <i>null</i>)
	 */
	public String getName() {

		return name;
	}

	@Override
	public String toString() {

		return getName();
	}
}
