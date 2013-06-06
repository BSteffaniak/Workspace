package net.foxycorndog.jfoxylib.opengl;

import static org.lwjgl.opengl.GL11.GL_ALL_ATTRIB_BITS;
import static org.lwjgl.opengl.GL11.GL_ALPHA_TEST;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_CURRENT_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ENABLE_BIT;
import static org.lwjgl.opengl.GL11.GL_FOG_BIT;
import static org.lwjgl.opengl.GL11.GL_GREATER;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_LIGHTING_BIT;
import static org.lwjgl.opengl.GL11.GL_LINE_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_MODULATE;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PERSPECTIVE_CORRECTION_HINT;
import static org.lwjgl.opengl.GL11.GL_POINT_BIT;
import static org.lwjgl.opengl.GL11.GL_POLYGON_BIT;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_BIT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV_MODE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;
import static org.lwjgl.opengl.GL11.glAlphaFunc;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexEnvi;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.gluPerspective;
import static org.lwjgl.opengl.GL11.glScissor;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.FloatBuffer;
import java.util.ArrayList;


import net.foxycorndog.jfoxylib.opengl.texture.Texture;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL43;

/**
 * Class that has several static methods used for manipulating
 * OpenGL capabilities and other things.
 * 
 * @author	Braden Steffaniak
 * @since	Feb 15, 2013 at 11:43:44 PM
 * @since	v0.1
 * @version	May 23, 2013 at 2:28:44 PM
 * @version	v0.2
 */
public class GL
{
	private static boolean	inited;
	
	private static float	zClose, zFar;
	private static float	fov;
	private static float	textureTolerance, vertexTolerance;
	
	private static int		textureScaleMinMethod, textureScaleMagMethod, textureWrapSMethod, textureWrapTMethod;
	
	public static final int	POINTS = GL11.GL_POINTS, LINES = GL11.GL_LINES, TRIANGLES = GL11.GL_TRIANGLES;//, QUADS = GL11.GL_QUADS;
	
	public static final int ALL_ATTRIB_BITS = GL_ALL_ATTRIB_BITS, ENABLE_BIT = GL_ENABLE_BIT, FOG_BIT = GL_FOG_BIT,
			LIGHTING_BIT = GL_LIGHTING_BIT, LINE_BIT = GL_LINE_BIT, POINT_BIT = GL_POINT_BIT,
			POLYGON_BIT = GL_POLYGON_BIT, TEXTURE_BIT = GL_TEXTURE_BIT,
			COLOR_BUFFER_BIT = GL_COLOR_BUFFER_BIT, CURRENT_BIT = GL_CURRENT_BIT;
	
	public static final int LINEAR = GL11.GL_LINEAR, NEAREST = GL11.GL_NEAREST, REPEAT = GL11.GL_REPEAT, CLAMP = GL11.GL_CLAMP;
	
	/**
	 * Initialize all of the OpenGL states that are required before
	 * the use of the functions.
	 */
	private static void initGLStates()
	{
		glEnable(GL_TEXTURE_2D);
		
		GL11.glLightModeli(GL12.GL_LIGHT_MODEL_COLOR_CONTROL, GL12.GL_SEPARATE_SPECULAR_COLOR);
		
		glEnable(GL_DEPTH_TEST); // Enables Depth Testing
		glDepthFunc(GL_LEQUAL); // The Type Of Depth Testing To Do
		
		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.1f); 
		
		GL11.glFrontFace(GL11.GL_CCW);
		
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		
		setTextureWrapSMethod(GL.REPEAT);
		setTextureWrapTMethod(GL.REPEAT);
		
		setTextureTolerance(0.0001f);
		setVertexTolerance(0.001f);
		
		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);//GL_DECAL);
		
		glEnable(GL_BLEND);
//		glBlendFunc(GL_ONE, GL11.GL_ONE);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//		glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE_MINUS_CONSTANT_COLOR);
//		GL11.glDisable(GL_BLEND);
//		GL11.glDepthMask(true);
	}
	
	/**
	 * Initialize the orthographic view mode. This mode is used
	 * for 2 dimensional viewing.
	 * 
	 * @param width The width of each OpenGL unit per pixel.
	 * @param height The height of each OpenGL unit per pixel.
	 */
	public static void viewOrtho(int width, int height)
	{
		initGLStates();
		
		glViewport(0, 0, width, height);
		glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
		glLoadIdentity(); // Reset The Projection Matrix

		// Calculate The Aspect Ratio Of The Window
		glOrtho(0, width, 0, height, -99999, 99999);
		glMatrixMode(GL_MODELVIEW); // Select The Modelview Matrix
	}
	
	/**
	 * Initialize the OpenGL perspective viewing mode.
	 * 
	 * @param width The width of the Frame of reference.
	 * @param height The height of the Frame of reference.
	 * @param zClose The closest index to the screen possible for
	 * 		the zed axis.
	 * @param zFar The farthest index to the screen possible for
	 * 		the zed axis.
	 */
	public static void viewPerspective(int width, int height, float zClose, float zFar)
	{
		fov = 55.0f;
		
		initGLStates();
		
//		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
//		glEnable(GL_TEXTURE_2D);
		
//		glEnable(GL_CULL_FACE);
		
//		glShadeModel(GL_SMOOTH); // Enable Smooth Shading
		glClearDepth(1.0); // Depth Buffer Setup
		
		glViewport(0, 0, width, height);
		glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
		glLoadIdentity(); // Reset The Projection Matrix

		// Calculate The Aspect Ratio Of The Window
		gluPerspective(fov, (float)width / height, zClose, zFar);
//		glOrtho(1, 1, 1, 1, -1, 1);
		glMatrixMode(GL_MODELVIEW); // Select The Modelview Matrix

		// Really Nice Perspective Calculations
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
	}
	
	/**
	 * Get the integer variable that represents the method of
	 * minification for OpenGL Textures.
	 * 
	 * @return The integer variable that represents the method of
	 * minification for OpenGL Textures.
	 */
	public static int getTextureScaleMinMethod()
	{
		return textureScaleMinMethod;
	}
	
	/**
	 * Get the integer variable that represents the method of
	 * magnification for OpenGL Textures.
	 * 
	 * @return The integer variable that represents the method of
	 * magnification for OpenGL Textures.
	 */
	public static int getTextureScaleMagMethod()
	{
		return textureScaleMagMethod;
	}
	
	/**
	 * Set the Texture minification method when rendering Textures.
	 * Possible methods include:<br>
	 * NEAREST - Minify the Texture exactly as it is, with loss of pixels.<br>
	 * LINEAR - Minify the Texture with estimation.<br>
	 * 
	 * @param method The method to set the minification to.
	 */
	public static void setTextureScaleMinMethod(int method)
	{
		GL.textureScaleMinMethod = method;
		
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, method);
	}
	
	/**
	 * Set the Texture magification method when rendering Textures.
	 * Possible methods include:<br>
	 * NEAREST - Magnify the Texture exactly as it is, without estimation.<br>
	 * LINEAR - Magnify the Texture with estimation.
	 * 
	 * @param method The method to set the magnification to.
	 */
	public static void setTextureScaleMagMethod(int method)
	{
		GL.textureScaleMagMethod = method;
		
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, method);
	}
	
	/**
	 * Get the method that is used wrap Textures when they are rendered.
	 * This only effects the horizontal view of the Texture.<br>
	 * Possible methods include:<br>
	 * CLAMP - When the object the Texture is rendered to is too large
	 * 		for the Texture, clamp the Texture by continuing the
	 * 		colors closest to the edge onward.<br>
	 * REPEAT - When the object the Texture is rendered to is too large
	 * 		for the Texture, repeat the Texture to fill the empty
	 * 		space.
	 * 
	 * @return The method that is used wrap Textures when they are rendered
	 * 		horizontally.
	 */
	public static int getTextureWrapSMethod()
	{
		return textureWrapSMethod;
	}
	
	/**
	 * Get the method that is used wrap Textures when they are rendered.
	 * This only effects the vertical view of the Texture.<br>
	 * Possible methods include:<br>
	 * CLAMP - When the object the Texture is rendered to is too large
	 * 		for the Texture, clamp the Texture by continuing the
	 * 		colors closest to the edge onward.<br>
	 * REPEAT - When the object the Texture is rendered to is too large
	 * 		for the Texture, repeat the Texture to fill the empty
	 * 		space.
	 * 
	 * @return The method that is used wrap Textures when they are rendered
	 * 		vertically.
	 */
	public static int getTextureWrapTMethod()
	{
		return textureWrapTMethod;
	}
	
	/**
	 * Set the method to wrap Textures when they are rendered. This
	 * only effects the horizontal view of the Texture.<br>
	 * Possible methods include:<br>
	 * CLAMP - When the object the Texture is rendered to is too large
	 * 		for the Texture, clamp the Texture by continuing the
	 * 		colors closest to the edge onward.<br>
	 * REPEAT - When the object the Texture is rendered to is too large
	 * 		for the Texture, repeat the Texture to fill the empty
	 * 		space.
	 * 
	 * @param method The method to set the horizontal Texture wrap
	 * 		method.
	 */
	public static void setTextureWrapSMethod(int method)
	{
		GL.textureWrapSMethod = method;
		
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, method);
	}
	
	/**
	 * Set the method to wrap Textures when they are rendered. This
	 * only effects the vertical view of the Texture.<br>
	 * Possible methods include:<br>
	 * CLAMP - When the object the Texture is rendered to is too large
	 * 		for the Texture, clamp the Texture by continuing the
	 * 		colors closest to the edge onward.<br>
	 * REPEAT - When the object the Texture is rendered to is too large
	 * 		for the Texture, repeat the Texture to fill the empty
	 * 		space.
	 * 
	 * @param method The method to set the vertical Texture wrap
	 * 		method.
	 */
	public static void setTextureWrapTMethod(int method)
	{
		GL.textureWrapTMethod = method;
		
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, method);
	}
	
	/**
	 * Enable the use of clipping the rendering to the screen.
	 */
	private static void enableClipping()
	{
		glEnable(GL_SCISSOR_TEST);
	}
	
	/**
	 * Clip the area that will be rendered inside of to the screen. The
	 * difference between this method and
	 * beginFrameClipping(int, int, int, int) is that this method takes into
	 * account the current scale amount and amount translated.
	 * 
	 * @param x The horizonal location to make the clip.
	 * @param y The vertical location to make to clip.
	 * @param width The horizontal size of the area to clip.
	 * @param height The vertical size of the area to clip.
	 */
	public static void beginClipping(int x, int y, int width, int height)
	{
		float scale[] = getAmountScaled();
		
		x      *= scale[0];
		y      *= scale[1];
		width  *= scale[0];
		height *= scale[1];
		
		float trans[] = getAmountTranslated();
		
		x      += trans[0];
		y      += trans[1];
		
		beginFrameClipping(x, y, width, height);
	}
	
	/**
	 * Stop clipping what is rendered to the screen.
	 */
	public static void endClipping()
	{
		endFrameClipping();
	}
	
	/**
	 * Clip the area that will be rendered inside of to the screen. The
	 * difference between this method and
	 * beginClipping(int, int, int, int) is that this method does not
	 * takes into account the current scale amount, and is based on pixels.
	 * It also takes the absolute position from the screen.
	 * 
	 * @param x The horizonal location to make the clip.
	 * @param y The vertical location to make to clip.
	 * @param width The horizontal size of the area to clip.
	 * @param height The vertical size of the area to clip.
	 */
	public static void beginFrameClipping(int x, int y, int width, int height)
	{
		enableClipping();
		
		glScissor(x, y, width, height);
	}

	/**
	 * Stop clipping what is rendered to the screen.
	 */
	public static void endFrameClipping()
	{
		glDisable(GL_SCISSOR_TEST);
	}
	
	/**
	 * Get the current version of OpenGL.
	 * 
	 * @return The current version of OpenGL.
	 */
	public static String getVersion()
	{
		return GL11.glGetString(GL11.GL_VERSION);
	}
	
	/**
	 * Method used to push the specified object onto the stack.
	 * 
	 * @param bit The bit to push onto the stack.
	 */
	public static void pushAttrib(int bit)
	{
		GL11.glPushAttrib(bit);
	}
	
	/**
	 * Pop the last added bit off of the stack and return to
	 * the second latest mode.
	 */
	public static void popAttrib()
	{
		GL11.glPopAttrib();
	}
	
	/**
	 * Push the current matrix configuration to the stack.
	 */
	public static void pushMatrix()
	{
		GL11.glPushMatrix();
	}
	
	/**
	 * Pop the last matrix off of the stack. Returns the matrix
	 * configuration to the seconf last configuration available on the
	 * stack.
	 */
	public static void popMatrix()
	{
		GL11.glPopMatrix();
	}
	
	/**
	 * Rotates everything that is rendered after this method call
	 * the specified x, y, and z amounts.<br>
	 * Rotates the current matrix configuration the specified amounts.
	 * 
	 * @param x How many degrees to rotate around the x axis.
	 * @param y How many degrees to rotate around the y axis.
	 * @param z How many degrees to rotate around the z axis.
	 */
	public static void rotate(float x, float y, float z)
	{
		GL11.glRotatef(x, 1, 0, 0);
		GL11.glRotatef(y, 0, 1, 0);
		GL11.glRotatef(z, 0, 0, 1);
	}
	
	/**
	 * Translate everything that is rendered after this method call
	 * the specified x, y, and z amounts.<br>
	 * Translates the current matrix configuration the specified amounts.
	 * 
	 * @param x The amount to translate along the x (horizontal) axis.
	 * @param y The amount to translate along the y (vertical) axis.
	 * @param z The amount to translate along the z (onlique) axis.
	 */
	public static void translate(float x, float y, float z)
	{
		GL11.glTranslatef(x, y, z);
	}
	
	/**
	 * Scale everything that is rendered after this method call
	 * the specified amount int the x, y, and z axis.<br>
	 * Scales the current matrix configuration the specified amounts.
	 * 
	 * @param x The amount to scale horizontally what is rendered.
	 * @param y The amount to scale vertically what is rendered.
	 * @param z The amount to scale obliquely what is rendered.
	 */
	public static void scale(float x, float y, float z)
	{
		GL11.glScalef(x, y, z);
	}
	
	/**
	 * Get the amount that the current matrix has been scaled up to
	 * this method call.
	 * 
	 * @return A float array containing three values that correspond
	 * 		to the (x, y, z) amount scaled.
	 */
	public static float[] getAmountScaled()
	{
		float scaled[] = new float[3];
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, buffer);
		
		scaled[0] = buffer.get(0);
		scaled[1] = buffer.get(1 + 1 * 4);
		scaled[2] = buffer.get(2 + 2 * 4);
		
		return scaled;
	}
	
	/**
	 * Get the amount that the current matrix has been translated by up
	 * to this method call.
	 * 
	 * @return A float array containing three values that correspond
	 * 		to the (x, y, z) amount translated.
	 */
	public static float[] getAmountTranslated()
	{
		float translated[] = new float[3];
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, buffer);
		
		translated[0] = buffer.get(0 + 3 * 4);
		translated[1] = buffer.get(1 + 3 * 4);
		translated[2] = buffer.get(2 + 3 * 4);
		
		return translated;
	}
	
	/**
	 * Get the tolerance in which the Textures will  be generated with.
	 * This determines how far to keep from overlapping textures on
	 * a SpriteSheet or image.
	 * 
	 * @return The tolerance in which the Textures will be generated
	 * 		with.
	 */
	public static float getTextureTolerance()
	{
		return textureTolerance;
	}
	
	/**
	 * Set the tolerance in which the Textures will  be generated with.
	 * This determines how far to keep from overlapping textures on
	 * a SpriteSheet or image.
	 * 
	 * @param textureTolerance The tolerance in which the Textures
	 * 		will be generated with.
	 */
	public static void setTextureTolerance(float textureTolerance)
	{
		GL.textureTolerance = textureTolerance;
	}
	
	/**
	 * Set the tolerance in which the Vertices will  be generated with.
	 * This determines how much of a buffer to make for the vertices to
	 * make sure that no empty space is left in between any adjacent
	 * polygons.
	 * 
	 * @return The tolerance in which the Vertices will  be generated
	 * 		with.
	 */
	public static float getVertexTolerance()
	{
		return vertexTolerance;
	}
	
	/**
	 * Get the tolerance in which the Vertices will  be generated with.
	 * This determines how much of a buffer to make for the vertices to
	 * make sure that no empty space is left in between any adjacent
	 * polygons.
	 * 
	 * @return The tolerance in which the Vertices will  be generated
	 * 		with.
	 */
	public static void setVertexTolerance(float vertexTolerance)
	{
		GL.vertexTolerance = vertexTolerance;
	}
	
//	/**
//	 * 
//	 * 
//	 * @param x
//	 * @param y
//	 * @param width
//	 * @param height
//	 */
//	public static void drawRect(int x, int y, int width, int height)
//	{
//		GL11.glBegin(GL11.GL_QUADS);
//		{
//			GL11.glVertex2f(x, y);
//			GL11.glVertex2f(x + width, y);
//			GL11.glVertex2f(x + width, y + height);
//			GL11.glVertex2f(x, y + height);
//		}
//		GL11.glEnd();
//	}
	
//	/**
//	 * 
//	 * 
//	 * @param x
//	 * @param y
//	 * @param width
//	 * @param height
//	 * @param texture
//	 */
//	public static void drawRect(int x, int y, int width, int height, Texture texture)
//	{
//		texture.bind();
//		float offsets[] = texture.getImageOffsets();
//		
//		GL11.glBegin(GL11.GL_QUADS);
//		{
//			GL11.glVertex2f(x, y);
//			GL11.glTexCoord2f(offsets[0], offsets[1]);
//			GL11.glVertex2f(x + width, y);
//			GL11.glTexCoord2f(offsets[0], offsets[3]);
//			GL11.glVertex2f(x + width, y + height);
//			GL11.glTexCoord2f(offsets[2], offsets[3]);
//			GL11.glVertex2f(x, y + height);
//			GL11.glTexCoord2f(offsets[2], offsets[1]);
//		}
//		GL11.glEnd();
//	}
	
	/**
	 * Get the current color that is being used by OpenGL.
	 * 
	 * @return A float array describing the float values for the
	 * 		(r, g, b, a) color in the current state.
	 */
	public static float[] getColor()
	{
		float color[] = new float[4];
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		
		GL11.glGetFloat(GL11.GL_CURRENT_COLOR, buffer);
		
		color[0] = buffer.get(0);
		color[1] = buffer.get(1);
		color[2] = buffer.get(2);
		color[3] = buffer.get(3);
		
		return color;
	}
	
	/**
	 * Set the color that everything rendered will be affected by.
	 * 
	 * @param r The red component (0.0 - 1.0).
	 * @param g The green component (0.0 - 1.0).
	 * @param b The blue component (0.0 - 1.0).
	 * @param a The alpha component (0.0 - 1.0).
	 */
	public static void setColor(float r, float g, float b, float a)
	{
		GL11.glColor4f(r, g, b, a);
	}
	
	/**
	 * Set the color that the Frame will clear the scene with.
	 * 
	 * @param r The red component (0.0 - 1.0).
	 * @param g The green component (0.0 - 1.0).
	 * @param b The blue component (0.0 - 1.0).
	 * @param a The alpha component (0.0 - 1.0).
	 */
	public static void setClearColor(float r, float g, float b, float a)
	{
		GL11.glClearColor(r, g, b, a);
	}
	
	/**
	 * Reset the matrix of all translations and scales.
	 */
	public static void resetMatrix()
	{
		GL11.glLoadIdentity();
	}
	
	/**
	 * Get the float value of the Field of View.
	 * 
	 * @return The float value of the Field of View.
	 */
	public static float getFOV()
	{
		return fov;
	}
	
	/**
	 * Set the float value of the Field of View.
	 * 
	 * @param FOV The float value of the Field of View.
	 */
	public static void setFOV(float FOV)
	{
		GL.fov = FOV;
	}
	
	/**
	 * Generate a float array containing values that describe a
	 * 2 dimensional rectangle's vertices.
	 * 
	 * @param x The horizontal location of the rectangle.
	 * @param y The vertical location of the rectangle.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 * @return The generated float array containing the values.
	 */
	public static float[] genRectVerts(float x, float y, float width, float height)
	{
		float array[] = new float[3 * 2 * 2];
		
		int index = 0;
		
		x      -= vertexTolerance;
		width  += vertexTolerance * 2;
		y      -= vertexTolerance;
		height += vertexTolerance * 2;
		
//		Old QUAD method.
//		// Front
//		array[index++] = x;
//		array[index++] = y;
//		
//		array[index++] = x + width;
//		array[index++] = y;
//		
//		array[index++] = x + width;
//		array[index++] = y + height;
//		
//		array[index++] = x;
//		array[index++] = y + height;
		
		
//		New TRIANGLE method.
		array[index++] = x;
		array[index++] = y;

		array[index++] = x + width;
		array[index++] = y + height;

		array[index++] = x;
		array[index++] = y + height;
		
		
		array[index++] = x;
		array[index++] = y;

		array[index++] = x + width;
		array[index++] = y;

		array[index++] = x + width;
		array[index++] = y + height;
		
		return array;
	}
	
	/**
	 * Generate a float array containing values that describe a
	 * 2 dimensional rectangle's vertices with depth.
	 * 
	 * @param x The horizontal location of the rectangle.
	 * @param y The vertical location of the rectangle.
	 * @param z The oblique location of the rectangle (depth).
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 * @return The generated float array containing the values.
	 */
	public static float[] genRectVerts(float x, float y, float z, float width, float height)
	{
		float array[] = new float[4 * 3];
		
		int index = 0;
		
		// Front
		array[index++] = x;
		array[index++] = y;
		array[index++] = z;
		
		array[index++] = x + width;
		array[index++] = y;
		array[index++] = z;
		
		array[index++] = x + width;
		array[index++] = y + height;
		array[index++] = z;
		
		array[index++] = x;
		array[index++] = y + height;
		array[index++] = z;
		
		return array;
	}
	
	/**
	 * Generate a float array containing values that describe a
	 * 2 dimensional rectangle's texture coordinates to map on
	 * vertices.
	 * 
	 * @param texture The texture to generate the values for.
	 * @return The generated float array containing the values.
	 */
	public static float[] genRectTextures(Texture texture)
	{
		return genRectTextures(texture.getImageOffsets());
	}
	
	/**
	 * Generate a float array containing values that describe a
	 * 2 dimensional rectangle's texture coordinates to map on
	 * vertices. The texture will be repeated the specified amount on
	 * each of the axiis.
	 * 
	 * @param texture The texture to generate the values for.
	 * @param rx The amount of times to repeat the Texture horizontally.
	 * @param ry The amount of times to repeat the Texture vertically.
	 * @return The generated float array containing the values.
	 */
	public static float[] genRectTextures(Texture texture, int rx, int ry)
	{
		return genRectTextures(texture.getImageOffsets(), rx, ry);
	}
	
	/**
	 * Generate a float array containing values that describe a
	 * 2 dimensional rectangle's texture coordinates to map on
	 * vertices. The texture can be mirrored horizontally and/or
	 * vertically depending on the values passed to the parameters.
	 * 
	 * @param texture The texture to generate the values for.
	 * @param mirrorHorizontal Whether or not to mirror the Texture
	 * 		horizontally.
	 * @param mirrorVertical Whether or not to mirror the Texture
	 * 		vertically.
	 * @return The generated float array containing the values.
	 */
	public static float[] genRectTextures(Texture texture, boolean mirrorHorizontal, boolean mirrorVertical)
	{
		return genRectTextures(texture.getImageOffsets(), mirrorHorizontal, mirrorVertical);
	}
	
	/**
	 * Generate a float array containing values that describe a
	 * 2 dimensional rectangle's texture coordinates to map on
	 * vertices.
	 * 
	 * @param offsets The offsets of the Texture that will be used to
	 * 		generate the float array.
	 * @return The generated float array containing the values.
	 */
	public static float[] genRectTextures(float offsets[])
	{
		return genRectTextures(offsets, 1, 1, false, false);
	}
	
	/**
	 * Generate a float array containing values that describe a
	 * 2 dimensional rectangle's texture coordinates to map on
	 * vertices.
	 * 
	 * @param offsets The offsets of the Texture that will be used to
	 * 		generate the float array.
	 * @param rx The amount of times to repeat the Texture horizontally.
	 * @param ry The amount of times to repeat the Texture vertically.
	 * @return The generated float array containing the values.
	 */
	public static float[] genRectTextures(float offsets[], int rx, int ry)
	{
		return genRectTextures(offsets, rx, ry, false, false);
	}
	
	/**
	 * Generate a float array containing values that describe a
	 * 2 dimensional rectangle's texture coordinates to map on
	 * vertices.
	 * 
	 * @param offsets The offsets of the Texture that will be used to
	 * 		generate the float array.
	 * @param mirrorHorizontal Whether or not to mirror the Texture
	 * 		horizontally.
	 * @param mirrorVertical Whether or not to mirror the Texture
	 * 		vertically.
	 * @return The generated float array containing the values.
	 */
	public static float[] genRectTextures(float offsets[], boolean mirrorHorizontal, boolean mirrorVertical)
	{
		return genRectTextures(offsets, 1, 1, mirrorHorizontal, mirrorVertical);
	}
	
	/**
	 * Generate a float array containing values that describe a
	 * 2 dimensional rectangle's texture coordinates to map on
	 * vertices.
	 * 
	 * @param offsets The offsets of the Texture that will be used to
	 * 		generate the float array.
	 * @param rx The amount of times to repeat the Texture horizontally.
	 * @param ry The amount of times to repeat the Texture vertically.
	 * @param mirrorHorizontal Whether or not to mirror the Texture
	 * 		horizontally.
	 * @param mirrorVertical Whether or not to mirror the Texture
	 * 		vertically.
	 * @return The generated float array containing the values.
	 */
	public static float[] genRectTextures(float offsets[], int rx, int ry, boolean mirrorHorizontal, boolean mirrorVertical)
	{
		float array[] = new float[3 * 2 * 2];
		
//		Old QUAD method.
//		float tolerance = -0.00001f;
//		
//		if (mirrorHorizontal)
//		{
//			array[0] = rx * offsets[2] - tolerance * 2;
//			
//			array[2] = rx * offsets[0] + tolerance;
//			
//			array[4] = rx * offsets[0] + tolerance;
//
//			array[6] = rx * offsets[2] - tolerance * 2;
//		}
//		else
//		{
//			array[0] = rx * offsets[0] + tolerance;
//			
//			array[2] = rx * offsets[2] - tolerance * 2;
//			
//			array[4] = rx * offsets[2] - tolerance * 2;
//			
//			array[6] = rx * offsets[0] + tolerance;
//		}
//		
//		if (mirrorVertical)
//		{
//			array[1] = ry * offsets[3] - tolerance * 2;
//			
//			array[3] = ry * offsets[3] - tolerance * 2;
//			
//			array[5] = ry * offsets[1] + tolerance;
//			
//			array[7] = ry * offsets[1] + tolerance;
//		}
//		else
//		{
//			array[1] = ry * offsets[1] + tolerance;
//			
//			array[3] = ry * offsets[1] + tolerance;
//			
//			array[5] = ry * offsets[3] - tolerance * 2;
//			
//			array[7] = ry * offsets[3] - tolerance * 2;
//		}
		
//		New TRIANGLE method.
		int index = 0;
		
		if (mirrorHorizontal)
		{
			index = 0;
			
			array[index] = rx * offsets[2] - textureTolerance;// * 2;
			index += 2;
			
			array[index] = rx * offsets[0] + textureTolerance;
			index += 2;
			
			array[index] = rx * offsets[2] - textureTolerance;// * 2;
			index += 2;
			
			
			array[index] = rx * offsets[2] - textureTolerance;// * 2;
			index += 2;
			
			array[index] = rx * offsets[0] + textureTolerance;
			index += 2;
			
			array[index] = rx * offsets[0] + textureTolerance;
			index += 2;
		}
		else
		{
			array[index] = rx * offsets[0] + textureTolerance;
			index += 2;
			
			array[index] = rx * offsets[2] - textureTolerance;// * 2;
			index += 2;
			
			array[index] = rx * offsets[0] + textureTolerance;
			index += 2;
			

			array[index] = rx * offsets[0] + textureTolerance;
			index += 2;
			
			array[index] = rx * offsets[2] - textureTolerance;// * 2;
			index += 2;
			
			array[index] = rx * offsets[2] - textureTolerance;// * 2;
			index += 2;
		}
		
		if (mirrorVertical)
		{
			index = 1;
			
			array[index] = ry * offsets[3] - textureTolerance;// * 2;
			index += 2;
			
			array[index] = ry * offsets[1] + textureTolerance;
			index += 2;

			array[index] = ry * offsets[1] + textureTolerance;
			index += 2;
			
			
			array[index] = ry * offsets[3] - textureTolerance;// * 2;
			index += 2;
			
			array[index] = ry * offsets[3] - textureTolerance;// * 2;
			index += 2;
			
			array[index] = ry * offsets[1] + textureTolerance;
			index += 2;
		}
		else
		{
			index = 1;
			
			array[index] = ry * offsets[1] + textureTolerance;
			index += 2;

			array[index] = ry * offsets[3] - textureTolerance;// * 2;
			index += 2;
			
			array[index] = ry * offsets[3] - textureTolerance;// * 2;
			index += 2;
			
			
			array[index] = ry * offsets[1] + textureTolerance;
			index += 2;
			
			array[index] = ry * offsets[1] + textureTolerance;
			index += 2;
			
			array[index] = ry * offsets[3] - textureTolerance;// * 2;
			index += 2;
		}
		
		return array;
	}
	
	/**
	 * Generate a float array containing values that describe a
	 * 3 dimensional cube's vertices.
	 * 
	 * @param x The horizontal location of the cube.
	 * @param y The vertical location of the cube.
	 * @param z The oblique location of the cube.
	 * @param width The width of the cube.
	 * @param height The height of the cube.
	 * @param depth The depth of the cube.
	 * @return The generated float array containing the values.
	 */
	public static float[] genCubeVerts(float x, float y, float z, float width, float height, float depth)
	{
		float array[] = new float[4 * 6 * 3];
		
		int index = 0;
		
		
		// Back
		array[index++] = x;
		array[index++] = y;
		array[index++] = z + depth;
		
		array[index++] = x + width;
		array[index++] = y;
		array[index++] = z + depth;
		
		array[index++] = x + width;
		array[index++] = y + height;
		array[index++] = z + depth;
		
		array[index++] = x;
		array[index++] = y + height;
		array[index++] = z + depth;
		
		
		// Right
		array[index++] = x + width;
		array[index++] = y;
		array[index++] = z + depth;
		
		array[index++] = x + width;
		array[index++] = y;
		array[index++] = z;
		
		array[index++] = x + width;
		array[index++] = y + height;
		array[index++] = z;
		
		array[index++] = x + width;
		array[index++] = y + height;
		array[index++] = z + depth;
		
		// Front
		array[index++] = x + width;
		array[index++] = y;
		array[index++] = z;
		
		array[index++] = x;
		array[index++] = y;
		array[index++] = z;
		
		array[index++] = x;
		array[index++] = y + height;
		array[index++] = z;
		
		array[index++] = x + width;
		array[index++] = y + height;
		array[index++] = z;
		
		
		// Left
		array[index++] = x;
		array[index++] = y;
		array[index++] = z;
		
		array[index++] = x;
		array[index++] = y;
		array[index++] = z + depth;
		
		array[index++] = x;
		array[index++] = y + height;
		array[index++] = z + depth;
		
		array[index++] = x;
		array[index++] = y + height;
		array[index++] = z;
		
		
		// Top
		array[index++] = x + width;
		array[index++] = y + height;
		array[index++] = z;
		
		array[index++] = x;
		array[index++] = y + height;
		array[index++] = z;
		
		array[index++] = x;
		array[index++] = y + height;
		array[index++] = z + depth;
		
		array[index++] = x + width;
		array[index++] = y + height;
		array[index++] = z + depth;
		
		
		// Bottom
		array[index++] = x;
		array[index++] = y;
		array[index++] = z + depth;
		
		array[index++] = x;
		array[index++] = y;
		array[index++] = z;
		
		array[index++] = x + width;
		array[index++] = y;
		array[index++] = z;
		
		array[index++] = x + width;
		array[index++] = y;
		array[index++] = z + depth;
		
		return array;
	}
	
	/**
	 * Generate a float array containing values that describe an
	 * (r, g, b, a) color's values that are used for Color Buffers.
	 * 
	 * @param r The red component of the Color (0.0 - 1.0).
	 * @param g The green component of the Color (0.0 - 1.0).
	 * @param b The blue component of the Color (0.0 - 1.0).
	 * @param a The alpha component of the Color (0.0 - 1.0).
	 * @return The generated float array containing the values.
	 */
	public static float[] genRectColors(float r, float g, float b, float a)
	{
		float array[] = new float[4 * 4];
		
		int index = 0;
		
		array[index++] = r;
		array[index++] = g;
		array[index++] = b;
		array[index++] = a;
		
		array[index++] = r;
		array[index++] = g;
		array[index++] = b;
		array[index++] = a;
		
		array[index++] = r;
		array[index++] = g;
		array[index++] = b;
		array[index++] = a;
		
		array[index++] = r;
		array[index++] = g;
		array[index++] = b;
		array[index++] = a;
		
		return array;
	}
	
//	public static void initBasicView(float zClose, float zFar)
//	{
//		glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
//		
//		GL.zClose = zClose;
//		GL.zFar   = zFar;
//		
//		glEnable(GL_BLEND);
//		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//		
//		if (render3D)
//		{
//			glEnable(GL11.GL_TEXTURE_2D);
//			
//			glEnable(GL_CULL_FACE);
//			
//			glClearColor(0.0f, 0.3f, 0.6f, 0.0f); // Blue Background
//			glClearDepth(1.0); // Depth Buffer Setup
//			glEnable(GL_DEPTH_TEST); // Enables Depth Testing
//			glDepthFunc(GL_LEQUAL); // The Type Of Depth Testing To Do
//			
//			glViewport(0, 0, Display.getWidth(), Display.getHeight());
//			glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
//			glLoadIdentity(); // Reset The Projection Matrix
//			
//			FOV = 55.0f;
//
//			// Calculate The Aspect Ratio Of The Window
//			gluPerspective(FOV, (float)Display.getWidth() / (float)Display.getHeight(), zClose, zFar);
//			glMatrixMode(GL_MODELVIEW);
//
//			// Really Nice Perspective Calculations
//			glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
//			
//			glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
//		}
//		else
//		{
//			glEnable(GL_TEXTURE_2D);
//			
//			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
//			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
//			
//			glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
//			
//			glEnable(GL_BLEND);
//			glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
//			
//			glEnable(GL_ALPHA_TEST);
//			glAlphaFunc(GL_GREATER, 0.1f); 
//			
//			glEnable(GL_DEPTH_TEST);
//			
//			glClearColor(0.0f, 0.3f, 0.6f, 0.0f); // Blue Background
//		}
//	}
}
