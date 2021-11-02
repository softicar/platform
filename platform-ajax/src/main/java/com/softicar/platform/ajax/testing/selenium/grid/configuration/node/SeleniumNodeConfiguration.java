package com.softicar.platform.ajax.testing.selenium.grid.configuration.node;

import com.softicar.platform.common.core.properties.IProperty;
import java.math.BigDecimal;

public class SeleniumNodeConfiguration implements ISeleniumNodeConfiguration {

	private String namePrefix;
	private String imageName;
	private BigDecimal factor;

	@Override
	public String getNamePrefix() {

		return namePrefix;
	}

	public SeleniumNodeConfiguration setNamePrefix(String namePrefix) {

		this.namePrefix = namePrefix;
		return this;
	}

	public SeleniumNodeConfiguration setNamePrefix(IProperty<String> namePrefix) {

		return setNamePrefix(namePrefix.getValue());
	}

	@Override
	public String getImageName() {

		return imageName;
	}

	public SeleniumNodeConfiguration setImageName(String imageName) {

		this.imageName = imageName;
		return this;
	}

	public SeleniumNodeConfiguration setImageName(IProperty<String> imageName) {

		return setImageName(imageName.getValue());
	}

	@Override
	public BigDecimal getFactor() {

		return factor;
	}

	public SeleniumNodeConfiguration setFactor(BigDecimal factor) {

		this.factor = factor;
		return this;
	}

	public SeleniumNodeConfiguration setFactor(IProperty<BigDecimal> factor) {

		return setFactor(factor.getValue());
	}
}
