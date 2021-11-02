package com.softicar.platform.core.module.user.password.quality.criteria;

import java.util.Collection;

public interface IComposedPasswordQualityCriterion extends IPasswordQualityCriterion {

	Collection<IPasswordQualityCriterion> getSubCriteria();
}
