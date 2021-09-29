package com.softicar.platform.core.module.user.password.quality;

import com.softicar.platform.core.module.CoreCssClasses;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.password.policy.IPasswordPolicy;
import com.softicar.platform.core.module.user.password.quality.criteria.IComposedPasswordQualityCriterion;
import com.softicar.platform.core.module.user.password.quality.criteria.IPasswordQualityCriterion;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.label.DomPreformattedLabel;

public class UserPasswordQualityCriteriaTable extends DomDiv {

	private final String password;
	private final boolean policyFulfilled;

	public UserPasswordQualityCriteriaTable(IPasswordPolicy passwordPolicy, String password) {

		this.password = password;
		this.policyFulfilled = passwordPolicy.isFulfilled(password);
		for (IPasswordQualityCriterion criterion: passwordPolicy.getQualityCriteria()) {
			appendChild(new CriterionDiv(criterion, false));
			if (criterion instanceof IComposedPasswordQualityCriterion) {
				IComposedPasswordQualityCriterion parentCriterion = (IComposedPasswordQualityCriterion) criterion;
				for (IPasswordQualityCriterion subCriterion: parentCriterion.getSubCriteria()) {
					appendChild(new CriterionDiv(subCriterion, true));
				}
			}
		}
	}

	private class CriterionDiv extends DomDiv {

		public CriterionDiv(IPasswordQualityCriterion criterion, boolean addIndent) {

			addCssClass(CoreCssClasses.USER_PASSWORD_QUALITY_CRITERION_ROW);
			appendChild(new CriterionLabelDiv(criterion, addIndent));
			appendChild(new CriterionValueDiv(criterion));

			if (criterion.isFulfilled(password)) {
				addCssClass(CoreCssClasses.USER_PASSWORD_QUALITY_CRITERION_FULFILLED);
			} else if (policyFulfilled) {
				addCssClass(CoreCssClasses.USER_PASSWORD_QUALITY_CRITERION_POLICY_FULFILLED);
			} else {
				addCssClass(CoreCssClasses.USER_PASSWORD_QUALITY_CRITERION_NOT_FULFILLED);
			}
		}
	}

	private class CriterionLabelDiv extends DomDiv {

		public CriterionLabelDiv(IPasswordQualityCriterion criterion, boolean addIndent) {

			DomPreformattedLabel span = appendChild(new DomPreformattedLabel());
			if (addIndent) {
				span.appendText("\t");
			}
			span.appendText(criterion.getDescription());
		}
	}

	private class CriterionValueDiv extends DomDiv {

		public CriterionValueDiv(IPasswordQualityCriterion criterion) {

			addCssClass(CoreCssClasses.USER_PASSWORD_QUALITY_CRITERION_VALUE);
			if (criterion.isFulfilled(password)) {
				appendText(CoreI18n.YES);
			} else {
				appendText(CoreI18n.NO);
			}
		}
	}
}
