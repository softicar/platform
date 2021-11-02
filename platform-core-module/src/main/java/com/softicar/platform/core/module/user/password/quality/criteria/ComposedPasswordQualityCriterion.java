package com.softicar.platform.core.module.user.password.quality.criteria;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.user.password.quality.UserPasswordQuality;
import java.util.ArrayList;
import java.util.List;

public class ComposedPasswordQualityCriterion implements IComposedPasswordQualityCriterion {

	private final List<IPasswordQualityCriterion> subCriteria;
	private int minimumSubCriteriaCount;

	public ComposedPasswordQualityCriterion() {

		this.subCriteria = new ArrayList<>();
	}

	@Override
	public IDisplayString getDescription() {

		return CoreI18n.THE_PASSWORD_FULFILLS_AT_LEAST_ARG1_SUB_CRITERIA.toDisplay(minimumSubCriteriaCount);
	}

	@Override
	public boolean isFulfilled(String password) {

		int count = 0;
		for (IPasswordQualityCriterion criterion: subCriteria) {
			if (criterion.isFulfilled(password)) {
				++count;
			}
		}
		return count >= minimumSubCriteriaCount;
	}

	@Override
	public List<IPasswordQualityCriterion> getSubCriteria() {

		return subCriteria;
	}

	@Override
	public UserPasswordQuality getQuality(String password) {

		double qualitySum = 0.0;
		for (IPasswordQualityCriterion criterion: subCriteria) {
			qualitySum += criterion.getQuality(password).getValue();
		}
		return new UserPasswordQuality(qualitySum / subCriteria.size());
	}

	public void addSubCriterion(IPasswordQualityCriterion subCriterion) {

		subCriteria.add(subCriterion);
	}

	public void setMinimumSubCriteriaCount(int minimumSubCriteriaCount) {

		this.minimumSubCriteriaCount = minimumSubCriteriaCount;
	}
}
