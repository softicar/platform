package com.softicar.platform.core.module.user.password.quality;

import com.softicar.platform.core.module.user.password.policy.IPasswordPolicy;
import com.softicar.platform.core.module.user.password.quality.criteria.IPasswordQualityCriterion;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.message.DomMessageDiv;
import com.softicar.platform.dom.elements.message.style.DomMessageType;

/**
 * This element displays the quality of a given password with respect to the
 * password policy.
 *
 * @see IPasswordPolicy
 * @see IPasswordQualityCriterion
 * @author Oliver Richers
 */
public class UserPasswordQualityDiv extends DomDiv {

	private final IPasswordPolicy passwordPolicy;
	private final String password;
	private final boolean policyFulfilled;

	public UserPasswordQualityDiv(IPasswordPolicy passwordPolicy, String password) {

		this.passwordPolicy = passwordPolicy;
		this.password = password;
		this.policyFulfilled = passwordPolicy.isFulfilled(password);
		appendPasswordCriteriaTable();
	}

	private void appendPasswordCriteriaTable() {

		DomMessageType messageType = policyFulfilled? DomMessageType.SUCCESS : DomMessageType.ERROR;
		appendChild(new DomMessageDiv(messageType, new UserPasswordQualityCriteriaTable(passwordPolicy, password)));

	}
}
