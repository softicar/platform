package com.softicar.platform.common.network.address;

import com.softicar.platform.common.core.number.parser.IntegerParser;
import java.util.Objects;

/**
 * Represents an IPv4 network address.
 * <p>
 * An IPv4 network address consist of an {@link Ipv4Address} and a network mask,
 * defining how many bits belong to the network address.
 *
 * @author Oliver Richers
 */
public class Ipv4NetworkAddress {

	private final Ipv4Address address;
	private final int bits;

	public Ipv4NetworkAddress(String addressAndBits) {

		this.address = parseAddressPart(addressAndBits);
		this.bits = parseBitsPart(addressAndBits);
	}

	public Ipv4NetworkAddress(Ipv4Address address, int mask) {

		this.address = address;
		this.bits = mask;
	}

	public Ipv4NetworkAddress(int address, int mask) {

		this.address = new Ipv4Address(address);
		this.bits = mask;
	}

	public Ipv4Address getAddress() {

		return address;
	}

	public int getBits() {

		return bits;
	}

	/**
	 * Checks whether the given address belongs to the network, described by
	 * this network address.
	 *
	 * @param address
	 *            the address to check (never null)
	 * @return <i>true</i> if the given address belongs to this network
	 */
	public boolean contains(Ipv4Address address) {

		int mask = getIntegerMask();
		int networkIntegerValue = this.address.getIntegerValue();
		int addressIntegerValue = address.getIntegerValue();
		return Objects.equals(networkIntegerValue & mask, addressIntegerValue & mask);
	}

	public int getIntegerMask() {

		return bits > 0? 0xffffffff << (32 - bits) : 0;
	}

	@Override
	public int hashCode() {

		return Objects.hash(address, bits);
	}

	@Override
	public boolean equals(Object object) {

		if (object instanceof Ipv4NetworkAddress) {
			Ipv4NetworkAddress other = (Ipv4NetworkAddress) object;
			return Objects.equals(this.address, other.address) && Objects.equals(this.bits, other.bits);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {

		return address + "/" + bits;
	}

	// -------------------- parsing -------------------- //

	private Ipv4Address parseAddressPart(String addressAndMask) {

		return new Ipv4Address(getPart(addressAndMask, 0));
	}

	private int parseBitsPart(String addressAndMask) {

		Integer mask = IntegerParser.parseInteger(getPart(addressAndMask, 1));
		if (mask == null || mask < 0 || mask > 32) {
			throw new IllegalIpv4NetworkAddress(addressAndMask, "Illegal definition of bit count of network mask.");
		}
		return mask;
	}

	private String getPart(String addressAndMask, int index) {

		String[] parts = addressAndMask.split("/");
		if (parts.length != 2) {
			throw new IllegalIpv4NetworkAddress(addressAndMask, "Missing or illegal specification of network mask.");
		}
		return parts[index];
	}
}
