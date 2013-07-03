package net.foxycorndog.jfoxylib.util;

import net.foxycorndog.jfoxylib.opengl.GL;

/**
 * Class used to manipulate how a perspective is viewed.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 11:17:22 PM
 * @since	v0.1
 * @version	Jul 2, 2013 at 6:41:22 PM
 * @version	v0.2
 */
public class Camera implements Cloneable
{
	private					int		cameraMode;
	
	private					float	yaw, maxYaw, minYaw;
	private					float	roll, maxRoll, minRoll;
	private					float	pitch, maxPitch, minPitch;
	
	// 3d vector to store the camera's position in
	private					Point3f	location;
	
	public	static	final	int		FORWARD = 0, BACKWARD = 1, LEFT = 2, RIGHT = 3, UP = 4, DOWN = 5;
	
	public	static	final	int		FREE = 1, XZ_ONLY = 2;
	
	/**
	 * Create a Camera instance at the location (0, 0, 0).
	 */
	public Camera()
	{
		this(0, 0, 0);
	}
	
	/**
	 * Constructor that takes the starting x, y, z location of the camera
	 * 
	 * @param x The horizontal location of the Camera.
	 * @param y The vertical location of the Camera.
	 * @param z The depth (oblique) location of the Camera.
	 */
	public Camera(float x, float y, float z)
	{
		location = new Point3f(x, y, z);
		
		this.cameraMode = FREE;
		
		maxYaw   = Integer.MAX_VALUE;
		maxPitch = Integer.MAX_VALUE;
		maxRoll  = Integer.MAX_VALUE;
		minYaw   = Integer.MIN_VALUE;
		minPitch = Integer.MIN_VALUE;
		minRoll  = Integer.MIN_VALUE;
	}
	
	/**
	 * Set the minimum value that the yaw can reach.
	 * 
	 * @param minYaw The minimum value that the yaw can reach.
	 */
	public void setMinYaw(float minYaw)
	{
		this.minYaw = minYaw;
	}
	
	/**
	 * Set the maximum value that the yaw can reach.
	 * 
	 * @param maxYaw The maximum value that the yaw can reach.
	 */
	public void setMaxYaw(float maxYaw)
	{
		this.maxYaw = maxYaw;
	}
	
	/**
	 * Set the minimum value that the pitch can reach.
	 * 
	 * @param minPitch The minimum value that the pitch can reach.
	 */
	public void setMinPitch(float minPitch)
	{
		this.minPitch = minPitch;
	}

	/**
	 * Set the maximum value that the pitch can reach.
	 * 
	 * @param maxPitch The maximum value that the pitch can reach.
	 */
	public void setMaxPitch(float maxPitch)
	{
		this.maxPitch = maxPitch;
	}
	
	/**
	 * Set the minimum value that the roll can reach.
	 * 
	 * @param minRoll The minimum value that the roll can reach.
	 */
	public void setMinRoll(float minRoll)
	{
		this.minRoll = minRoll;
	}
	
	/**
	 * Set the maximum value that the roll can reach.
	 * 
	 * @param maxRoll The maximum value that the roll can reach.
	 */
	public void setMaxRoll(float maxRoll)
	{
		this.maxRoll = maxRoll;
	}
	
	/**
	 * Get the current yaw value of the Camera.
	 * 
	 * @return The current yaw of the Camera.
	 */
	public float getYaw()
	{
		return yaw;
	}
	
	/**
	 * Set the new yaw value of the Camera.
	 *  
	 * @param yaw The new yaw value.
	 */
	public void setYaw(float yaw)
	{
		yaw %= 360;
		
		if (yaw > maxYaw)
		{
			yaw = maxYaw;
		}
		else if (yaw < minYaw)
		{
			yaw = minYaw;
		}
		
		this.yaw = yaw;
	}
	
	/**
	 * Increment the camera's current yaw rotation.
	 * 
	 * @param amount The amount to increment the yaw by.
	 */
	public void yaw(float amount)
	{
		//increment the yaw by the amount param
		yaw += amount;
		
		yaw %= 360;
		
		if (yaw > maxYaw)
		{
			yaw = maxYaw;
		}
		else if (yaw < minYaw)
		{
			yaw = minYaw;
		}
		
//		yaw = yaw < 0 ? 360 + yaw : yaw;
	}
	
	/**
	 * Get the current pitch value of the Camera.
	 * 
	 * @return The current pitch of the Camera.
	 */
	public float getPitch()
	{
		return pitch;
	}
	
	/**
	 * Set the new pitch value of the Camera.
	 *  
	 * @param pitch The new pitch value.
	 */
	public void setPitch(float pitch)
	{
		pitch %= 360;
		
		if (pitch > maxPitch)
		{
			pitch = maxPitch;
		}
		else if (pitch < minPitch)
		{
			pitch = minPitch;
		}
		
		this.pitch = pitch;
	}
	 
	/**
	 * Increment the camera's current pitch rotation.
	 * 
	 * @param amount The amount to increment the pitch by.
	 */
	public void pitch(float amount)
	{
		// increment the pitch by the amount param
		pitch += amount;
		
		pitch %= 360;
		
		if (pitch > maxPitch)
		{
			pitch = maxPitch;
		}
		else if (pitch < minPitch)
		{
			pitch = minPitch;
		}
		
//		pitch = pitch < 0 ? 360 + pitch : pitch;
	}
	
	/**
	 * Get the current roll value of the Camera.
	 * 
	 * @return The current roll of the Camera.
	 */
	public float getRoll()
	{
		return roll;
	}
	
	/**
	 * Set the new pitch value of the Camera.
	 *  
	 * @param pitch The new pitch value.
	 */
	public void setRoll(float roll)
	{
		roll %= 360;
		
		if (roll > maxRoll)
		{
			roll = maxRoll;
		}
		else if (roll < minRoll)
		{
			roll = minRoll;
		}
		
		this.roll = roll;
	}
	
	/**
	 * Increment the camera's current roll rotation.
	 * 
	 * @param amount The amount to increment the roll by.
	 */
	public void roll(float amount)
	{
		//increment the roll by the amount param
		roll += amount;
		
		roll %= 360;
		
		if (roll > maxRoll)
		{
			roll = maxRoll;
		}
		else if (roll < minRoll)
		{
			roll = minRoll;
		}
		
//		roll = roll < 0 ? 360 + roll : roll;
	}
	
	/**
	 * Rotate the camera the specified amount for the pitch, yaw, and
	 * roll.
	 * 
	 * @param pitch The amount to rotate around the x axis.
	 * @param yaw The amount to rotate around the y axis.
	 * @param roll The amount to rotate around teh z axis.
	 */
	public void rotate(float pitch, float yaw, float roll)
	{
		this.pitch += pitch;
		
		this.pitch %= 360;
		
		if (this.pitch > maxPitch)
		{
			this.pitch = maxPitch;
		}
		else if (this.pitch < minPitch)
		{
			this.pitch = minPitch;
		}
		
		this.yaw += yaw;
		
		this.yaw %= 360;
		
		if (this.yaw > maxYaw)
		{
			this.yaw = maxYaw;
		}
		else if (this.yaw < minYaw)
		{
			this.yaw = minYaw;
		}
		
		this.roll += roll;
		
		this.roll %= 360;
		
		if (this.roll > maxRoll)
		{
			this.roll = maxRoll;
		}
		else if (this.roll < minRoll)
		{
			this.roll = minRoll;
		}
	}
	
	/**
	 * Set the location of the Camera in the scene.
	 * 
	 * @param x The horizontal location of the Camera.
	 * @param y The vertical location of the Camera.
	 * @param z The depth (oblique) location of the Camera.
	 */
	public void setLocation(float x, float y, float z)
	{
		location.set(x, y, z);
		
//		location.x = -x;
//		location.y = -y;
//		location.z = -z;
	}
	
	/**
	 * Move the Camera the specified amount in the direction of
	 * (dx, dy, dz) in the scene.
	 * 
	 * @param dx The amount to move the Camera in the horizontal
	 * 		direction.
	 * @param dy The amount to move the Camera in the vertical
	 * 		direction.
	 * @param dz The amount to move the Camera in the oblique (depth)
	 * 		direction.
	 */
	public void move(float dx, float dy, float dz)
	{
		location.move(dx, dy, dz);
		
//		location.x -= dx;
//		location.y -= dy;
//		location.z -= dz;
	}
	
	/**
	 * Move the Camera along the specified direction (dx, dy, dz).
	 * The movement is based upon the yaw, pitch, and roll. The movement
	 * moves along the slope that these values have set.<br>
	 * <br>
	 * For example:<br>
	 * If the pitch of the Camera is -90 (facing down) and
	 * moveDirection(1, 0, 0) is called, you will move 1 unit
	 * righward. Likewise, if you called moveDirection(0, 1, 0),
	 * you would move forward one (because you are facing downward).
	 * 
	 * @param dx The amount to move forward by.
	 * @param dy The amount to move upward by.
	 * @param dz The amount to move rightward by.
	 */
	public void moveDirection(float dx, float dy, float dz)
	{
//		float zOff = -distZ * (float)Math.cos(Math.toRadians(yaw));
//		
//		location.x += distZ * (float)Math.sin(Math.toRadians(yaw));
//		
//		if (cameraMode == FREE)
//		{
////			location.y -= distZ * (float)Math.tan(Math.toRadians(pitch));
////			location.y += distX * (float)Math.tan(Math.toRadians(pitch + 180));
//			location.y -= distZ * Math.sin(Math.toRadians(pitch));
//			zOff -= distZ * Math.sin(Math.toRadians(pitch));
//			System.out.println(Math.sin(Math.toRadians(pitch)) + ", " + zOff);
//		}
//		else
//		{
////			location.y -= distY;
//		}
//
//		location.x -= distX * (float)Math.sin(Math.toRadians(yaw + 90));
//		location.z += distX * (float)Math.cos(Math.toRadians(yaw + 90));
//		
//		location.z += zOff;
		
		float xOff = -dx * (float)Math.sin(Math.toRadians(yaw + 90)) + dz * (float)Math.sin(Math.toRadians(yaw)); 
		float yOff = 0;
		float zOff = -dz * (float)Math.cos(Math.toRadians(yaw))      + dx * (float)Math.cos(Math.toRadians(yaw + 90));
		
		if (cameraMode == FREE)
		{
			float slope = (float)Math.cos(Math.toRadians(pitch));
			
			xOff *= slope;
//			location.y -= distY * (float)Math.sin(Math.toRadians(pitch));
			location.move(0, -dy * (float)Math.sin(Math.toRadians(pitch)), 0);
			zOff *= slope;
		}
		else
		{
//			location.y -= distY;
			location.move(0, -dy, 0);
		}
		
//		location.x += xOff;
//		location.y += yOff;
//		location.z += zOff;
		location.move(-xOff, -yOff, -zOff);
	}
	
	/**
	 * Move the Camera in the specified direction the specified amount
	 * of units.<br>
	 * Options for the direction includes:<br>
	 * 	<ul>
	 * 		<li>FORWARD</li>
	 * 		<li>BACKWARD</li>
	 * 		<li>LEFT</li>
	 * 		<li>RIGHT</li>
	 * 		<li>UP</li>
	 * 		<li>DOWN</li>
	 * 	</ul>
	 * 
	 * @param direction The direction in which to move the Camera.
	 * @param distance The amount of units to move the Camera.
	 */
	public void move(int direction, float distance)
	{
		if (direction == FORWARD)
		{
//			location.x -= distance * (float)Math.sin(Math.toRadians(yaw));
//			location.z += distance * (float)Math.cos(Math.toRadians(yaw));
			location.move
				(
					-distance * (float)Math.sin(Math.toRadians(yaw)),
					0,
					distance * (float)Math.cos(Math.toRadians(yaw))
				);
			
			if (cameraMode == FREE)
			{
//				location.y += distance * (float)Math.tan(Math.toRadians(pitch));
				location.move
					(
						0,
						distance * (float)Math.tan(Math.toRadians(pitch)),
						0
					);
			}
		}
		else if (direction == BACKWARD)
		{
//			location.x += distance * (float)Math.sin(Math.toRadians(yaw));
//			location.z -= distance * (float)Math.cos(Math.toRadians(yaw));
			location.move
				(
					distance * (float)Math.sin(Math.toRadians(yaw)),
					0,
					-distance * (float)Math.cos(Math.toRadians(yaw))
				);

			if (cameraMode == FREE)
			{
//				location.y -= distance * (float)Math.tan(Math.toRadians(pitch));
				location.move
					(
						0,
						-distance * (float)Math.tan(Math.toRadians(pitch)),
						0
					);
			}
		}
		else if (direction == LEFT)
		{
//			location.x -= distance * (float)Math.sin(Math.toRadians(yaw - 90));
//			location.z += distance * (float)Math.cos(Math.toRadians(yaw - 90));
			location.move
				(
					-distance * (float)Math.sin(Math.toRadians(yaw - 90)),
					0,
					distance * (float)Math.cos(Math.toRadians(yaw - 90))
				);
		}
		else if (direction == RIGHT)
		{
//			location.x -= distance * (float)Math.sin(Math.toRadians(yaw + 90));
//			location.z += distance * (float)Math.cos(Math.toRadians(yaw + 90));
			location.move
				(
					-distance * (float)Math.sin(Math.toRadians(yaw + 90)),
					0,
					distance * (float)Math.cos(Math.toRadians(yaw + 90))
				);
		}
		else if (direction == UP)
		{
//			location.y -= distance;
			location.move
				(
					0,
					-distance,
					0
				);
		}
		else if (direction == DOWN)
		{
//			location.y += distance;
			location.move
				(
					0,
					distance,
					0
				);
		}
	}
	
	/**
	 * Translates and rotate the matrix so that it looks through the camera.
	 * This does basically what gluLookAt() does.
	 */
	public void lookThrough()
	{
		GL.rotate(pitch, yaw, roll);
		// translate to the position vector's location
		GL.translate(-location.getX(), -location.getY(), -location.getZ());
	}
	
	/**
	 * Get the Point3f instance that hold the location of the Camera at
	 * the current time.
	 * 
	 * @return The current Location Point3f instance.
	 */
	public Point3f getLocation()
	{
		return new Point3f(getX(), getY(), getZ());
	}
	
	/**
	 * Get the current horizontal location of the Camera.
	 * 
	 * @return The current horizontal location of the Camera.
	 */
	public float getX()
	{
		return location.getX();
	}
	
	/**
	 * Get the current vertical location of the Camera.
	 * 
	 * @return The current vertical location of the Camera.
	 */
	public float getY()
	{
		return location.getY();
	}
	
	/**
	 * Get the current oblique (depth) location of the Camera.
	 * 
	 * @return The current oblique (depth) location of the Camera.
	 */
	public float getZ()
	{
		return location.getZ();
	}
	
	/**
	 * Set the Camera movement method.<br>
	 * Options include:<br>
	 * 	<ul>
	 * 		<li>FREE: Allow the Camera to move in all directions.</li>
	 * 		<li>
	 * 			XZ_ONLY: Restrict the Camera movement to the horizontal
	 * 			and oblique (depth) axiis.
	 * 		</li>
	 * 	</ul>
	 * 
	 * @param mode The mode to set the Camera movement as.
	 */
	public void setCameraMode(int mode)
	{
		this.cameraMode = mode;
	}
	
	/**
	 * Generate a String representation of the Camera by showing
	 * the location of the Camera.
	 * 
	 * @return The String representation of the Camera instance.
	 */
	public String toString()
	{
		return this.getClass().getSimpleName() + " { " + getX() + ", " + getY() + ", " + getZ() + " }";
	}
}

//private static final float _90 = (float) Math.toRadians(90);
//private float heading = 0.0f;
//private float pitch = 0.0f;
//// angle values are got via input and used to create matrix
//private float cosa, cosb, cosz, sina, sinb, sinz;
//// camera roll is not implemented so set default values
//private float cosc = 1.0f;
//private float sinc = 0.0f;
//// forward/back and strafe
//private float x, z;
//
//public float y;
//// matrix is initialized with the identity matrix mat
//private float[] mat =
//{
//	1, 0, 0, 0,
//	0, 1, 0, 0,
//	0, 0, 1, 0,
//	0, 0, 0, 1
//};
//
//private FloatBuffer matrix;
//
//public Player() {
//	matrix = Buffer.createFloatBuffer(mat.length);
//	matrix.put(mat);
//	x = z = 0.0f;
//	y = -3.0f;
//}
//
///*
// * changes in mouse x values are added to heading. cosb and sinb values
// * affect rotations about y. cosZ and sinz affect motion forward and back.
// */
//public void setHeading(float amount) {
//	heading += amount;
//	cosb = (float) Math.cos(heading);
//	sinb = (float) Math.sin(heading);
//	cosz = (float) Math.cos(heading + _90);
//	sinz = (float) Math.sin(heading + _90);
//}
//
///*
// * changes in mouse y values are added to pitch. cosa and sina affect
// * rotations arounf x.
// */
//public void setPitch(float amount) {
//	pitch -= amount;
//	cosa = (float) Math.cos(pitch);
//	sina = (float) Math.sin(pitch);
//}
//
//public void setFord(float amount) {
//	x += cosz * amount;
//	z += sinz * amount;
//}
//
//public void setBack(float amount) {
//	x -= cosz * amount;
//	z -= sinz * amount;
//}
//
//public void setlStrafe(float amount) {
//	x += cosb * amount;
//	z += sinb * amount;
//}
//
//public void setrStrafe(float amount) {
//	x -= cosb * amount;
//	z -= sinb * amount;
//}
//
///*
// * after mouse and keyboard have been polled and set their respective
// * variables, set is called to construct the rotation matrix. This matrix is
// * multiplied with the x, y, z values that determine positions (up/down,
// * forward/back, strafe left or right). The values from the multiplication
// * are placed in the relevant positions in the overall view matrix.
// */
//public void set() {
//	matrix.put(0, cosc * cosb - sinc * sina * sinb);
//	matrix.put(1, sinc * cosb + cosc * sina * sinb);
//	matrix.put(2, -cosa * sinb);
//	matrix.put(4, -sinc * cosa);
//	matrix.put(5, cosc * cosa);
//	matrix.put(6, sina);
//	matrix.put(8, cosc * sinb + sinc * sina * cosb);
//	matrix.put(9, sinc * sinb - cosc * sina * cosb);
//	matrix.put(10, cosa * cosb);
//	matrix.put(12, matrix.get(0) * x + matrix.get(4) * y + matrix.get(8)
//			* z);
//	matrix.put(13, matrix.get(1) * x + matrix.get(5) * y + matrix.get(9)
//			* z);
//	matrix.put(14, matrix.get(2) * x + matrix.get(6) * y + matrix.get(10)
//			* z);
//}
//
///*
// * before any other objects are drawn the player/camera position is defined
// * here. Note loadIdentity need not be called because the matrix we build is
// * loaded directly into the openGL modelview matrix.
// */
//public void draw() {
//	// draw a square to represent a gun
//	GL.glColor3f(7.0f, 5.0f, 3.0f);
//	GL.glBegin(GL.QUADS);
//	GL.glVertex3f(1.0f, 1.0f, 0.0f);
//	GL.glVertex3f(2.0f, 1.0f, 0.0f);
//	GL.glVertex3f(2.0f, 1.0f, -5.0f);
//	GL.glVertex3f(1.0f, 1.0f, -5.0f);
//	GL.glEnd();
//	// rewind the float buffer then load it as the modelview matrix
//	matrix.rewind();
//	GL.glLoadMatrix(matrix);
//}