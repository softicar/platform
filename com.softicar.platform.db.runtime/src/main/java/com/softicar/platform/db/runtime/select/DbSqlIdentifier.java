package com.softicar.platform.db.runtime.select;

import com.softicar.platform.db.core.connection.quirks.IDbServerQuirks;
import com.softicar.platform.db.sql.token.ISqlToken;
import com.softicar.platform.db.sql.token.SqlKeyword;

public class DbSqlIdentifier implements ISqlToken {

	private final IDbServerQuirks serverQuirks;
	private final String name;

	public DbSqlIdentifier(IDbServerQuirks serverQuirks, String name) {

		this.serverQuirks = serverQuirks;
		this.name = name;
	}

	public String getName() {

		return name;
	}

	@Override
	public String toString() {

		if (isQuotingNecessary()) {
			return serverQuirks.getQuotedIdentifier(name);
		} else {
			return name;
		}
	}

	private boolean isQuotingNecessary() {

		return serverQuirks.isQuotingMandatory() || SqlKeyword.isKeyword(name) || !isOnlyValidCharacters();
	}

	private boolean isOnlyValidCharacters() {

		if (isValidStartCharacter(name.charAt(0))) {
			int length = name.length();
			for (int i = 1; i < length; i++) {
				if (!isValidCharacter(name.charAt(i))) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	private static boolean isValidStartCharacter(char c) {

		return Character.isLetter(c);
	}

	private static boolean isValidCharacter(char c) {

		return Character.isLetter(c) || Character.isDigit(c) || c == '$';
	}
}
