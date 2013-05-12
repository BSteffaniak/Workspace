package net.foxycorndog.jfoxylib.events;

public interface KeyListener
{
	public void keyDown(KeyEvent event);
	
	public void keyPressed(KeyEvent event);
	
	public void keyReleased(KeyEvent event);
	
	public void keyTyped(KeyEvent event);
}
