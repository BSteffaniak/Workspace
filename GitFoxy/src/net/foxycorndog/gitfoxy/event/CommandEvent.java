package net.foxycorndog.gitfoxy.event;

import net.foxycorndog.gitfoxy.command.Command;

public class CommandEvent extends Event
{
	private	int		exitValue;
	
	private	String	output;
	
	private	Command	command;
	
	public CommandEvent(String output, int exitValue, Command command)
	{
		this.output    = output;
		this.exitValue = exitValue;
		this.command   = command;
	}
	
	public int getExitValue()
	{
		return exitValue;
	}
	
	public String getOutput()
	{
		return output;
	}
	
	public Command getCommand()
	{
		return command;
	}
}