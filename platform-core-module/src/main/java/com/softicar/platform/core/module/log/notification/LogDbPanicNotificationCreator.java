package com.softicar.platform.core.module.log.notification;

import com.softicar.platform.common.code.java.CodePrinter;
import com.softicar.platform.core.module.log.message.AGLogMessage;
import com.softicar.platform.core.module.log.process.AGLogProcess;
import java.util.stream.Collectors;

class LogDbPanicNotificationCreator {

	private final AGLogMessage message;
	private final AGLogProcess process;

	public LogDbPanicNotificationCreator(AGLogMessage message) {

		this.message = message;
		this.process = message.getProcess();

	}

	public String create() {

		CodePrinter printer = new CodePrinter();
		printer.println("class name:    %s", process.getClassName());
		printer.println("log level:     %s", message.getLevel());
		printer.println("log time:      %s", message.getLogTime());
		printer.println("message id:    %s", message.getId());
		printer.println("process id:    %s", process.getId());
		printer.println("process start: %s", process.getStartTime());
		printer.println("host:          %s", process.getServerIp());
		printer.println("log text:");
		printer.println(message.getLogText());
		return printer.getCodeLines().stream().collect(Collectors.joining("\r\n"));
	}
}
