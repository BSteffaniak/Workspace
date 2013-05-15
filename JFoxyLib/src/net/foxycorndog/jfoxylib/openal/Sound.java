package net.foxycorndog.jfoxylib.openal;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

import static org.lwjgl.openal.AL10.alGenBuffers;
import static org.lwjgl.openal.AL10.alGenSources;
import static org.lwjgl.openal.AL10.alBufferData;
import static org.lwjgl.openal.AL10.AL_BUFFER;
import static org.lwjgl.openal.AL10.AL_POSITION;
import static org.lwjgl.openal.AL10.AL_VELOCITY;
import static org.lwjgl.openal.AL10.AL_GAIN;
import static org.lwjgl.openal.AL10.alSourcei;
import static org.lwjgl.openal.AL10.alSourcef;
import static org.lwjgl.openal.AL10.alSource3f;
import static org.lwjgl.openal.AL10.alSourcePlay;
import static org.lwjgl.openal.AL10.alSourceStop;
import static org.lwjgl.openal.AL10.alSourcePause;
import static org.lwjgl.openal.AL10.alSourceRewind;

/**
 * Class used to hold information for a Sound, as well as play the
 * Sound.
 * 
 * @author	Braden Steffaniak
 * @since	May 9, 2013 at 7:54:33 PM
 * @since	v0.2
 * @version	May 9, 2013 at 7:54:33 PM
 * @version	v0.2
 */
public class Sound
{
	private	int						id;
	private	int						source;
	
	private	WaveData				data;
	
	private static boolean			initialized;
	
	private static ArrayList<Sound>	sounds;
	
	/**
	 * Try to create the AL instance.
	 */
	private static void init()
	{
		if (initialized)
		{
			return;
		}
		
		try
		{
			AL.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		
		sounds = new ArrayList<Sound>();
		
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()
			{
				while (sounds.size() > 0)
				{
					Sound sound = sounds.remove(0);
					
					AL10.alDeleteSources(sound.source);
					AL10.alDeleteBuffers(sound.id);
					
					sound.data.dispose();
				}
				
				AL.destroy();
			}
		});
		
		initialized = true;
	}
	
	/**
	 * Create a Sound instance at the specified location.
	 * 
	 * @param location The location of the sound file to use.
	 * @throws FileNotFoundException Thrown when the specified sound
	 * 		file could not be found.
	 */
	public Sound(String location) throws FileNotFoundException
	{
		init();
		
		sounds.add(this);
		
		data = WaveData.create(new BufferedInputStream(new FileInputStream(location)));
		
		id = alGenBuffers();

		alBufferData(id, data.format, data.data, data.samplerate);

		//alDeleteBuffers(id);

		source = alGenSources();

		alSourcei(source, AL_BUFFER, id);
		alSource3f(source, AL_POSITION, 0, 0, 0);
		alSource3f(source, AL_VELOCITY, 0, 0, 0);
		alSourcef(source, AL_GAIN, 1f);
	}
	
	/**
	 * Play the Sound file's audio.
	 */
	public void play()
	{
		alSourcePlay(source);
	}
	
	/**
	 * Loops the audio file indefinitely until it is stopped.
	 */
	public void loop()
	{
		AL10.alSourcei(source, AL10.AL_LOOPING, AL10.AL_TRUE);
		
		play();
	}
	
	/**
	 * Pause the Sound file's audio.
	 */
	public void pause()
	{
		alSourcePause(source);
	}
	
	/**
	 * Stop playing the Sound file's audio if it is playing.
	 */
	public void stop()
	{
		AL10.alSourcei(source, AL10.AL_LOOPING, AL10.AL_FALSE);
		
		alSourceStop(source);
	}
	
	/**
	 * Rewind the Sound file's audio if paused.
	 */
	public void rewind()
	{
		alSourceRewind(source);
	}
	
	/**
	 * Set the volume of the sound. Value is from 0f-1f.
	 * 
	 * @param volume The float value of the new volume.
	 */
	public void setVolume(float volume)
	{
		alSourcef(source, AL_GAIN, volume);
	}
}