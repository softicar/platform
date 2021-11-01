package com.softicar.platform.common.io.hash;

import com.softicar.platform.common.string.hexadecimal.Hexadecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Represents an SHA-1 hash string.
 *
 * @author Oliver Richers
 */
public class Sha1Hash implements Comparable<Sha1Hash> {

	private static final String CHARACTERS = "0123456789abcdef";
	private static final Set<Character> CHARACTER_SET = new HashSet<>(getCharacters());
	private final String hash;

	public Sha1Hash(String hash) {

		this.hash = validateAndNormalize(hash);
	}

	public Sha1Hash(byte[] hash) {

		this(Hexadecimal.getHexStringLC(hash));
	}

	@Override
	public boolean equals(Object other) {

		if (other == this) {
			return true;
		} else if (other instanceof Sha1Hash) {
			return Objects.equals(hash, ((Sha1Hash) other).hash);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {

		return hash.hashCode();
	}

	@Override
	public int compareTo(Sha1Hash other) {

		return hash.compareTo(other.hash);
	}

	@Override
	public String toString() {

		return hash;
	}

	private static String validateAndNormalize(String hash) {

		hash = Objects.requireNonNull(hash, "SHA-1 hash string was null.").toLowerCase();

		if (hash.length() != 40) {
			throw new RuntimeException(String.format("Illegal length of SHA-1 hash string: '%s'", hash));
		}

		for (int i = 0; i < hash.length(); i++) {
			char c = hash.charAt(i);
			if (!CHARACTER_SET.contains(c)) {
				throw new RuntimeException(String.format("Illegal character '%s' at index %s of SHA-1 hash string: '%s'", c, i, hash));
			}
		}

		return hash;
	}

	private static List<Character> getCharacters() {

		List<Character> characters = new ArrayList<>();
		for (int i = 0; i < CHARACTERS.length(); i++) {
			characters.add(CHARACTERS.charAt(i));
		}
		return characters;
	}
}
