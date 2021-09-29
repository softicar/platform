package com.softicar.platform.core.module.email.transport;

public interface IEmailTransportServer {

	String getAddress();

	Integer getPort();

	String getUsername();

	String getPassword();
}
