package com.softicar.platform.core.module.daemon.watchdog.state;

import com.softicar.platform.common.container.data.table.DataTableIdentifier;
import com.softicar.platform.common.container.data.table.IDataTableColumn;
import com.softicar.platform.common.container.data.table.in.memory.AbstractInMemoryDataTable;
import com.softicar.platform.common.core.clock.CurrentClock;
import com.softicar.platform.common.core.i18n.IDisplayString;
import com.softicar.platform.common.date.DayTime;
import com.softicar.platform.core.module.CoreI18n;
import com.softicar.platform.core.module.CoreImages;
import com.softicar.platform.core.module.daemon.watchdog.DaemonWatchdogControllerSingleton;
import com.softicar.platform.core.module.daemon.watchdog.log.DaemonWatchdogLogEntry;
import com.softicar.platform.core.module.daemon.watched.runnable.WatchedDaemonState;
import com.softicar.platform.dom.element.DomElementTag;
import com.softicar.platform.dom.elements.DomColorEnum;
import com.softicar.platform.dom.elements.DomDiv;
import com.softicar.platform.dom.elements.bar.DomActionBar;
import com.softicar.platform.dom.elements.button.DomButton;
import com.softicar.platform.dom.elements.label.DomLabelGrid;
import com.softicar.platform.dom.styles.CssFontWeight;
import com.softicar.platform.emf.EmfImages;
import com.softicar.platform.emf.data.table.EmfDataTableDivBuilder;
import com.softicar.platform.emf.data.table.IEmfDataTableCell;
import com.softicar.platform.emf.data.table.IEmfDataTableDiv;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTablePreformattedLabelColumnHandler;
import com.softicar.platform.emf.data.table.column.handler.EmfDataTableValueBasedColumnHandler;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.time.DurationFormatUtils;

public class DaemonWatchdogStatePageDiv extends DomDiv {

	private final DetailsDisplay watchdogThreadStatusTable;
	private final LogMessageDataTable logMessageDataTable;
	private final IEmfDataTableDiv<?> logMessageDataTableDiv;
	private final WatchedDaemonsDataTable watchedDaemonsDataTable;
	private final IEmfDataTableDiv<WatchedDaemonState> watchedDaemonsDataTableDiv;

	public DaemonWatchdogStatePageDiv() {

		this.watchdogThreadStatusTable = new DetailsDisplay();

		this.logMessageDataTable = new LogMessageDataTable();
		this.logMessageDataTableDiv = new EmfDataTableDivBuilder<>(logMessageDataTable)//
			.setColumnHandler(logMessageDataTable.getTextColumn(), new EmfDataTablePreformattedLabelColumnHandler())
			.build();

		this.watchedDaemonsDataTable = new WatchedDaemonsDataTable();
		this.watchedDaemonsDataTableDiv = new EmfDataTableDivBuilder<>(watchedDaemonsDataTable)//
			.setColumnHandler(watchedDaemonsDataTable.getThreadExistsColumn(), new BooleanDisplayColumnHandler())
			.setColumnHandler(watchedDaemonsDataTable.getThreadAliveColumn(), new BooleanDisplayColumnHandler())
			.build();

		appendChild(new DomActionBar(new UpdateWatchdogStateButton(), new StartWatchdogButton(), new StopWatchdogButton()));
		appendNewChild(DomElementTag.H3).appendText(CoreI18n.THREAD_STATUS.concatColon());
		appendChild(watchdogThreadStatusTable);
		appendNewChild(DomElementTag.H3).appendText(CoreI18n.LOG_MESSAGES.concatColon());
		appendChild(logMessageDataTableDiv);
		appendNewChild(DomElementTag.H3).appendText(CoreI18n.WATCHED_DAEMONS.concatColon());
		appendChild(watchedDaemonsDataTableDiv);

		updateState();
	}

	private void updateState() {

		var state = DaemonWatchdogControllerSingleton.get().getState();
		watchdogThreadStatusTable.update(state);
		logMessageDataTable.update(state);
		logMessageDataTableDiv.refresh();
		watchedDaemonsDataTable.update(state);
		watchedDaemonsDataTableDiv.refresh();
	}

	private void startWatchdog() {

		DaemonWatchdogControllerSingleton.get().start();
		updateState();
	}

	private void stopWatchdog() {

		DaemonWatchdogControllerSingleton.get().stop();
		updateState();
	}

	private String formatDurationAsStringHHMMSS(Duration duration) {

		return DurationFormatUtils.formatDuration(duration.toMillis(), "HH:mm:ss");
	}

	private String formatInstantAsString(Instant instant) {

		return Optional//
			.ofNullable(instant)
			.map(DayTime::fromInstant)
			.map(DayTime::toString)
			.orElse("-");
	}

	private class UpdateWatchdogStateButton extends DomButton {

		public UpdateWatchdogStateButton() {

			setLabel(CoreI18n.REFRESH);
			setIcon(EmfImages.REFRESH.getResource());
			setClickCallback(() -> updateState());
		}
	}

	private class StartWatchdogButton extends DomButton {

		public StartWatchdogButton() {

			setLabel(CoreI18n.START_WATCHDOG);
			setIcon(CoreImages.MEDIA_PLAY.getResource());
			setClickCallback(() -> startWatchdog());
		}
	}

	private class StopWatchdogButton extends DomButton {

		public StopWatchdogButton() {

			setLabel(CoreI18n.STOP_WATCHDOG);
			setIcon(CoreImages.MEDIA_STOP.getResource());
			setClickCallback(() -> stopWatchdog());
		}
	}

	private class DetailsDisplay extends DomLabelGrid {

		public void update(DaemonWatchdogState state) {

			removeChildren();
			add(CoreI18n.THREAD_EXISTS, new BooleanDisplayElement(state.isThreadExists()));
			add(CoreI18n.THREAD_ACTIVE, new BooleanDisplayElement(state.isThreadAlive()));
			add(CoreI18n.STARTED_AT, new InstantDisplayElement(state.getStartedAt()));
			add(CoreI18n.STOPPED_AT, new InstantDisplayElement(state.getStoppedAt()));
		}
	}

	private class LogMessageDataTable extends AbstractInMemoryDataTable<DaemonWatchdogLogEntry> {

		private final List<DaemonWatchdogLogEntry> rows;
		private final IDataTableColumn<DaemonWatchdogLogEntry, String> textColumn;

		public LogMessageDataTable() {

			this.rows = new ArrayList<>();
			newColumn(DayTime.class)//
				.setGetter(this::getTimeAsDayTime)
				.setTitle(CoreI18n.TIME)
				.addColumn();
			this.textColumn = newColumn(String.class)//
				.setGetter(DaemonWatchdogLogEntry::getText)
				.setTitle(CoreI18n.TEXT)
				.addColumn();
		}

		@Override
		public DataTableIdentifier getIdentifier() {

			return new DataTableIdentifier("c6fd7f6b-8ab2-4411-880f-a661cd0bdaf9");
		}

		@Override
		protected Iterable<DaemonWatchdogLogEntry> getTableRows() {

			return rows;
		}

		public IDataTableColumn<DaemonWatchdogLogEntry, String> getTextColumn() {

			return textColumn;
		}

		public void update(DaemonWatchdogState state) {

			rows.clear();
			rows.addAll(getReversed(state.getLogEntries()));
		}

		private DayTime getTimeAsDayTime(DaemonWatchdogLogEntry entry) {

			return DayTime.fromInstant(entry.getTime());
		}

		private List<DaemonWatchdogLogEntry> getReversed(List<DaemonWatchdogLogEntry> logEntries) {

			var entries = new ArrayList<>(logEntries);
			Collections.reverse(entries);
			return entries;
		}
	}

	private class WatchedDaemonsDataTable extends AbstractInMemoryDataTable<WatchedDaemonState> {

		private final List<WatchedDaemonState> rows;
		private final IDataTableColumn<WatchedDaemonState, Boolean> threadExistsColumn;
		private final IDataTableColumn<WatchedDaemonState, Boolean> threadAliveColumn;

		public WatchedDaemonsDataTable() {

			this.rows = new ArrayList<>();
			newColumn(IDisplayString.class)//
				.setGetter(WatchedDaemonState::getName)
				.setTitle(CoreI18n.NAME)
				.addColumn();
			this.threadExistsColumn = newColumn(Boolean.class)//
				.setGetter(WatchedDaemonState::isWorkerThreadPresent)
				.setTitle(CoreI18n.THREAD_EXISTS)
				.addColumn();
			this.threadAliveColumn = newColumn(Boolean.class)//
				.setGetter(WatchedDaemonState::isWorkerThreadAlive)
				.setTitle(CoreI18n.THREAD_ACTIVE)
				.addColumn();
			newColumn(String.class)//
				.setGetter(this::getLastHeartbeatAsString)
				.setTitle(CoreI18n.LAST_HEARTBEAT)
				.addColumn();
			newColumn(String.class)//
				.setGetter(this::getTimeSinceHeartbeatAsString)
				.setTitle(CoreI18n.TIME_SINCE_HEARTBEAT)
				.addColumn();
			newColumn(String.class)//
				.setGetter(this::getKillTimeoutAsString)
				.setTitle(CoreI18n.TIMEOUT)
				.addColumn();
		}

		@Override
		public DataTableIdentifier getIdentifier() {

			return new DataTableIdentifier("c4f9f2ae-4471-477e-b082-21d7d6cb8ab2");
		}

		@Override
		protected Iterable<WatchedDaemonState> getTableRows() {

			return rows;
		}

		public IDataTableColumn<WatchedDaemonState, Boolean> getThreadExistsColumn() {

			return threadExistsColumn;
		}

		public IDataTableColumn<WatchedDaemonState, Boolean> getThreadAliveColumn() {

			return threadAliveColumn;
		}

		public void update(DaemonWatchdogState state) {

			rows.clear();
			rows.addAll(state.getWatchedDaemonStates());
		}

		private String getKillTimeoutAsString(WatchedDaemonState state) {

			return formatDurationAsStringHHMMSS(state.getKillTimeout());
		}

		private String getLastHeartbeatAsString(WatchedDaemonState state) {

			return formatInstantAsString(state.getLastHeartbeat());
		}

		private String getTimeSinceHeartbeatAsString(WatchedDaemonState state) {

			Instant lastHeartbeat = state.getLastHeartbeat();
			if (lastHeartbeat != null) {
				Duration elapsedTime = Duration.between(lastHeartbeat, CurrentClock.get().instant());
				return formatDurationAsStringHHMMSS(elapsedTime);
			} else {
				return "-";
			}
		}
	}

	private class BooleanDisplayColumnHandler extends EmfDataTableValueBasedColumnHandler<Boolean> {

		@Override
		public void buildCell(IEmfDataTableCell<?, Boolean> cell, Boolean value) {

			cell.appendChild(new BooleanDisplayElement(value));
		}
	}

	private class BooleanDisplayElement extends DomDiv {

		public BooleanDisplayElement(boolean value) {

			setStyle(CssFontWeight.BOLD);
			if (value) {
				appendText(CoreI18n.YES);
				setColor(DomColorEnum.DARK_GREEN);
			} else {
				appendText(CoreI18n.NO);
				setColor(DomColorEnum.DARK_RED);
			}
		}
	}

	private class InstantDisplayElement extends DomDiv {

		public InstantDisplayElement(Instant instant) {

			appendText(formatInstantAsString(instant));
		}
	}
}
