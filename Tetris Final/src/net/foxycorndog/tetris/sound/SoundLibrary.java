package net.foxycorndog.tetris.sound;

import java.util.HashMap;

import net.foxycorndog.jfoxylib.openal.Sound;

/**
 * Class used to store the Sounds used in the game.
 * 
 * @author	Braden Steffaniak
 * @since	May 13, 2013 at 6:12:48 PM
 * @since	v0.1
 * @version	May 13, 2013 at 6:12:48 PM
 * @version	v0.1
 */
public class SoundLibrary
{
	private HashMap<String, Sound>	sounds;
	
	/**
	 * Instantiate a SoundLibrary.
	 */
	public SoundLibrary()
	{
		sounds = new HashMap<String, Sound>();
	}
	
	/**
	 * Get the Sound instance that is linked to the specified key.
	 * 
	 * @param key The String key to the Sound.
	 * @return The Sound that is linked to the specified key.
	 */
	public Sound getSound(String key)
	{
		return sounds.get(key);
	}
	
	/**
	 * Add the specified sound to the Library in reference with the
	 * specified key.
	 * 
	 * @param key The key to the Sound.
	 * @param sound The Sound to add.
	 */
	public void addSound(String key, Sound sound)
	{
		sounds.put(key, sound);
	}
	
	/**
	 * Play the Sound with the specified key.
	 * 
	 * @param key The key that references the Sound.
	 */
	public void playSound(String key)
	{
		getSound(key).play();
	}
	
	/**
	 * Loop the Sound with the specified key.
	 * 
	 * @param key The key that references the Sound.
	 */
	public void loopSound(String key)
	{
		getSound(key).loop();
	}
	
	/**
	 * Stop the Sound with the specified key.
	 * 
	 * @param key The key that references the Sound.
	 */
	public void stopSound(String key)
	{
		getSound(key).stop();
	}
	
	/**
	 * Mute all of the Sounds in the library.
	 */
	public void mute()
	{
		Sound sounds[] = this.sounds.values().toArray(new Sound[0]);
		
		for (Sound sound : sounds)
		{
			sound.setVolume(0);
		}
	}
	
	/**
	 * Unute all of the Sounds in the library.
	 */
	public void unmute()
	{
		Sound sounds[] = this.sounds.values().toArray(new Sound[0]);
		
		for (Sound sound : sounds)
		{
			sound.setVolume(1);
		}
	}
}