package net.foxycorndog.gitfoxy.event;

public interface CommandListener
{
	public void onResultReceived(CommandEvent event);
	
	public void onOutputReceived(CommandEvent event);
	
	public void onErrorReceived(CommandEvent event);
	
	public void onCommandStarted(CommandEvent event);
}