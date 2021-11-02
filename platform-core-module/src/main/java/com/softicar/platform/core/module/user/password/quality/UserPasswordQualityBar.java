package com.softicar.platform.core.module.user.password.quality;

import com.softicar.platform.core.module.user.password.policy.IPasswordPolicy;
import com.softicar.platform.dom.elements.DomColorEnum;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomPercentageMultiBar;

public class UserPasswordQualityBar extends DomDiv {

	private final IPasswordPolicy passwordPolicy;
	private final DomPercentageMultiBar qualityBar;

	public UserPasswordQualityBar(IPasswordPolicy passwordPolicy) {

		this.passwordPolicy = passwordPolicy;
		this.qualityBar = appendChild(new DomPercentageMultiBar());
	}

	public void update(String password) {

		boolean fulfilled = passwordPolicy.isFulfilled(password);
		UserPasswordQuality quality = passwordPolicy.getQuality(password);
		DomColorEnum color = getColor(fulfilled, quality);
		qualityBar.removeChildren();
		qualityBar.addPercentage(quality.getValue(), color, color);
	}

	private DomColorEnum getColor(boolean fulfilled, UserPasswordQuality quality) {

		if (fulfilled) {
			return DomColorEnum.DARK_GREEN;
		} else if (quality.getValue() < 0.5) {
			return DomColorEnum.RED;
		} else if (quality.getValue() < 0.75) {
			return DomColorEnum.ORANGE;
		} else {
			return DomColorEnum.YELLOW;
		}
	}
}
