package com.softicar.platform.core.module.log.process;

import com.softicar.platform.common.core.utils.DevNull;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

class LogDbIpFetcher {

	/**
	 * This method returns IP addresses of all network interfaces except Loop
	 * Back on Host Server
	 */
	public static String getHostIpAddresses() {

		String hostIPAddressesString = new String();

		try {
			String tmpIPAddress;

			ArrayList<String> hostIPAddresses = new ArrayList<>();

			Enumeration<?> e = NetworkInterface.getNetworkInterfaces();

			while (e.hasMoreElements()) {
				NetworkInterface netface = (NetworkInterface) e.nextElement();

				if (!netface.getName().equals("lo")) {
					Enumeration<?> e2 = netface.getInetAddresses();

					while (e2.hasMoreElements()) {
						InetAddress ip = (InetAddress) e2.nextElement();

						if (ip.toString().indexOf(":") == -1) {
							tmpIPAddress = ip.toString();

							tmpIPAddress = tmpIPAddress.substring(1);
							hostIPAddresses.add(tmpIPAddress);
						}
					}
				}
			}

			Integer tmpScore;
			for (int i = 0; i < hostIPAddresses.size(); i++) {
				hostIPAddressesString = hostIPAddressesString + hostIPAddresses.get(i);

				tmpScore = hostIPAddresses.size() - 1 - i;

				if (!tmpScore.equals(0)) {
					hostIPAddressesString = hostIPAddressesString + ", ";
				}
			}
		} catch (Exception exception) {
			DevNull.swallow(exception);
			hostIPAddressesString = "Unknown";
		}

		return hostIPAddressesString;
	}
}
