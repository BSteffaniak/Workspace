package net.foxycorndog.thedigginggame.tile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import javax.imageio.ImageIO;

import net.foxycorndog.jfoxylib.opengl.texture.SpriteSheet;
import net.foxycorndog.thedigginggame.TheDiggingGame;

/**
 * Class that holds information for a Tile that is used in the terrain.
 * 
 * @author	Braden Steffaniak
 * @since	Feb 22, 2013 at 4:23:23 AM
 * @since	v0.2
 * @version Feb 22, 2013 at 4:23:24 AM
 * @version	v0.2
 */
public class Tile01 implements Serializable
{
	private boolean							collidable;
	
	private int								x, y;
	private int								cols, rows;
	
	private float							transparency;
	
	private static int						tileSize;
	
	private static SpriteSheet				terrainSprites;
	
	private static HashMap<String, Tile01>	tiles;
	
	static
	{
		int cols = 16;
		int rows = 16;
		
		BufferedImage spriteSheet = null;
		
		try
		{
			spriteSheet = ImageIO.read(new File(TheDiggingGame.getResourcesLocation() + "res/images/texturepacks/16/minecraft/terrain.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		terrainSprites = new SpriteSheet(spriteSheet, cols, rows);
		
		tileSize = 16;
		
		tiles = new HashMap<String, Tile01>();
		
		int index = 0;
		
		tiles.put("Stone",				new Tile01(1,  0, 1, 1, 0, true));
		tiles.put("Dirt",				new Tile01(2,  0, 1, 1, 0, true));
		tiles.put("Grass",				new Tile01(3,  0, 1, 1, 0, true));
		tiles.put("Wooden Planks",		new Tile01(4,  0, 1, 1, 0, true));
		tiles.put("Double Stone Slab",	new Tile01(5,  0, 1, 1, 0, true));
		tiles.put("Stone Slab",			new Tile01(6,  0, 1, 1, 0, true));
		tiles.put("Bricks",				new Tile01(7,  0, 1, 1, 0, true));
		tiles.put("TNT",				new Tile01(8,  0, 1, 1, 0, true));
		tiles.put("Cobblestone",		new Tile01(0,  1, 1, 1, 0, true));
		tiles.put("Bedrock",			new Tile01(1,  1, 1, 1, 0, true));
		tiles.put("Sand",				new Tile01(2,  1, 1, 1, 0, true));
		tiles.put("Gravel",				new Tile01(3,  1, 1, 1, 0, true));
		tiles.put("Log", 				new Tile01(4,  1, 1, 1, 0, true));
		tiles.put("Iron Block",			new Tile01(6,  1, 1, 1, 0, true));
		tiles.put("Gold Block",			new Tile01(7,  1, 1, 1, 0, true));
		tiles.put("Diamond Block",		new Tile01(8,  1, 1, 1, 0, true));
		tiles.put("Emerald Block",		new Tile01(9,  1, 1, 1, 0, true));
		tiles.put("Gold Ore",			new Tile01(0,  2, 1, 1, 0, true));
		tiles.put("Iron Ore",			new Tile01(1,  2, 1, 1, 0, true));
		tiles.put("Coal Ore",			new Tile01(2,  2, 1, 1, 0, true));
		tiles.put("Bookshelf",			new Tile01(3,  2, 1, 1, 0, true));
		tiles.put("Mossy Cobblestone",	new Tile01(4,  2, 1, 1, 0, true));
		tiles.put("Obsidian",			new Tile01(5,  2, 1, 1, 0, true));
		tiles.put("Furnace",			new Tile01(12, 2, 1, 1, 0, true));
		tiles.put("Dispenser",			new Tile01(14, 2, 1, 1, 0, true));
		tiles.put("Sponge",				new Tile01(0,  3, 1, 1, 0, true));
		tiles.put("Glass",				new Tile01(1,  3, 1, 1, 0.05f, true));
		tiles.put("Diamond Ore",		new Tile01(2,  3, 1, 1, 0, true));
		tiles.put("Redstone Ore",		new Tile01(3,  3, 1, 1, 0, true));
		tiles.put("Leaves",				new Tile01(5,  3, 1, 1, 0, true));
		tiles.put("White Wool",			new Tile01(0,  4, 1, 1, 0, true));
		tiles.put("Snow Block",			new Tile01(2,  4, 1, 1, 0, true));
		tiles.put("Ice Block",			new Tile01(3,  4, 1, 1, 0, true));
		tiles.put("Snowy Grass",		new Tile01(4,  4, 1, 1, 0, true));
		tiles.put("Cactus",				new Tile01(6,  4, 1, 1, 0, true));
		tiles.put("Sugar Cane",			new Tile01(9,  4, 1, 1, 0, true));
		tiles.put("Record Player",		new Tile01(10, 4, 1, 1, 0, true));
		tiles.put("Torch",				new Tile01(0,  5, 1, 1, 1, false));
		tiles.put("Wooden Door",		new Tile01(1,  5, 1, 2, 0, true));
		tiles.put("Iron Door",			new Tile01(2,  5, 1, 2, 0, true));
		tiles.put("Ladder",				new Tile01(3,  5, 1, 1, 0, true));
		tiles.put("Trap Door",			new Tile01(4,  5, 1, 1, 0, true));
		tiles.put("Lever",				new Tile01(0,  6, 1, 1, 0, true));
	}
	
	/**
	 * Construct a Tile with the specified location and size.
	 * 
	 * @param x The horizontal offset in the SpriteSheet.
	 * @param y The vertical offset in the SpriteSheet.
	 * @param cols The amount of columns the Tile takes up on the
	 * 		SpriteSheet.
	 * @param rows The amount of rows the Tile takes up on the
	 * 		SpriteSheet.
	 * @param transparency The value of transparency from (0 - 1).
	 * @param collidable Whether or not the Tile collides with Actors.
	 */
	public Tile01(int x, int y, int cols, int rows, float transparency, boolean collidable)
	{
		this.x            = x;
		this.y            = y;
		this.cols         = cols;
		this.rows         = rows;
		
		this.transparency = transparency;
		
		this.collidable   = collidable;
	}
	
	/**
	 * @return Whether or not the Tile collides with Actors.
	 */
	public boolean isCollidable()
	{
		return collidable;
	}
	
	/**
	 * @return The horizontal offset in the SpriteSheet.
	 */
	public int getX()
	{
		return x;
	}
	
	/**
	 * @return The vertical offset in the SpriteSheet.
	 */
	public int getY()
	{
		return y;
	}
	
	/**
	 * @return The amount of columns the Tile takes up on the SpriteSheet.
	 */
	public int getCols()
	{
		return cols;
	}

	/**
	 * @return The amount of rows the Tile takes up on the SpriteSheet.
	 */
	public int getRows()
	{
		return rows;
	}
	
	/**
	 * @return The value of transparency from (0 - 1).
	 */
	public float getTransparency()
	{
		return transparency;
	}
	
	/**
	 * Get an instance of a Tile with the specified name.
	 * 
	 * @param name The name of the Tile to get.
	 * @return An instance of the Tile with the specified Name.
	 */
	public static Tile01 getTile(String name)
	{
		return tiles.get(name);
	}
	
	/**
	 * @return The size of a Tile in pixels.
	 */
	public static int getTileSize()
	{
		return tileSize;
	}
	
	/**
	 * @return The SpriteSheet used for rendering the terrain.
	 */
	public static SpriteSheet getTerrainSprites()
	{
		return terrainSprites;
	}
}