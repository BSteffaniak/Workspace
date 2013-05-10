package net.foxycorndog.jfoxylib.opengl.lighting;

import net.foxycorndog.jfoxylib.Color;
import net.foxycorndog.jfoxylib.opengl.shader.Shader;

/**
 * Class that represents a light source using OpenGL shaders. There
 * are several dials and doodads to be toggled.
 * 
 * @author	Braden Steffaniak
 * @since	May 7, 2013 at 8:15:51 PM
 * @since	v0.2
 * @version	May 7, 2013 at 8:15:51 PM
 * @version	v0.2
 */
public class Light
{
	private	float	x, y, z;
	private float	intensity;
	
	private	Color	color;
	
	private Shader	lightShader;
	
	/**
	 * Create a Light instance.
	 */
	public Light()
	{
		
	}
	
	/**
	 * Set the location that the Light will be rendered at.
	 * 
	 * @param x The horizontal location to put the Light.
	 * @param y The vertical location to put the Light.
	 * @param z The depth location to put the Light.
	 */
	public void setLocation(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Render the Light to the scene at its specified location and its
	 * specified properties.
	 */
	public void render()
	{
		
	}
	
	/**
	 * Set the color that the Light will emit.
	 * 
	 * @param r The red component of the Color.
	 * @param g The green component of the Color.
	 * @param b The blue component of the Color.
	 */
	public void setColor(float r, float g, float b)
	{
		setColor((int)(r * 255),  (int)(g * 255), (int)(b * 255));
	}
	
	/**
	 * Set the color that the Light will emit.
	 * 
	 * @param r The red component of the Color.
	 * @param g The green component of the Color.
	 * @param b The blue component of the Color.
	 */
	public void setColor(int r, int g, int b)
	{
		Color color = new Color(r, g, b);
		
		setColor(color);
	}
	
	/**
	 * Set the color that the Light will emit.
	 * 
	 * @param color The Color instance to use to describe the color that
	 * 		the Light will emit.
	 */
	public void setColor(Color color)
	{
		this.color = color;
	}
}