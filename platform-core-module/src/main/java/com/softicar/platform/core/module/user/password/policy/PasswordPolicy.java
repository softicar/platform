package com.softicar.platform.core.module.user.password.policy;

import com.softicar.platform.core.module.user.password.quality.UserPasswordQuality;
import com.softicar.platform.core.module.user.password.quality.criteria.IPasswordQualityCriterion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PasswordPolicy implements IPasswordPolicy {

	private final List<IPasswordQualityCriterion> qualityCriteria;

	public PasswordPolicy() {

		this.qualityCriteria = new ArrayList<>();
	}

	@Override
	public Collection<IPasswordQualityCriterion> getQualityCriteria() {

		return qualityCriteria;
	}

	@Override
	public boolean isFulfilled(String password) {

		for (IPasswordQualityCriterion criterion: qualityCriteria) {
			if (!criterion.isFulfilled(password)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public UserPasswordQuality getQuality(String password) {

		double qualitySum = 0.0;
		if (!password.isEmpty()) {
			for (IPasswordQualityCriterion criterion: qualityCriteria) {
				qualitySum += criterion.getQuality(password).getValue();
			}
		}
		return new UserPasswordQuality(qualitySum / qualityCriteria.size());
	}

	public void addQualityCriterion(IPasswordQualityCriterion criterion) {

		this.qualityCriteria.add(criterion);
	}
}
