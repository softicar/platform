package com.softicar.platform.common.container.matrix.traits;

import com.softicar.platform.common.container.matrix.IMatrixTraits;

public class CharacterMatrixTraits implements IMatrixTraits<Character> {

	@Override
	public Character getDefaultValue() {

		return ' ';
	}

	@Override
	public Character plus(Character a, Character b) {

		throw new UnsupportedOperationException("You can't add characters.");
	}
}
