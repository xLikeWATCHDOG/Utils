package cn.watchdog.events;

public interface Cancellable {
	boolean isCancelled();

	void setCancelled(boolean cancelled);
}
