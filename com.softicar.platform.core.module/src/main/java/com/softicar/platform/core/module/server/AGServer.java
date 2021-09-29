package com.softicar.platform.core.module.server;

import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.core.module.email.mailbox.IMailbox;
import com.softicar.platform.core.module.email.transport.IEmailTransportServer;
import com.softicar.platform.emf.object.IEmfObject;

public class AGServer extends AGServerGenerated implements IEmfObject<AGServer>, IEmailTransportServer {

	@Override
	public IDisplayString toDisplayWithoutId() {

		return IDisplayString.create(getName());
	}

	public IMailbox toImapsMailbox() {

		return new IMailbox() {

			@Override
			public String getProtocol() {

				return "imaps";
			}

			@Override
			public String getServer() {

				return getAddress();
			}

			@Override
			public String getUsername() {

				return AGServer.this.getUsername();
			}

			@Override
			public String getPassword() {

				return AGServer.this.getPassword();
			}
		};
	}
}
