package main;

import gameObjects.Player;
import gui.Gui;
import map.Room;
import resources.SpriteContainer;

public abstract class GameAPI {
	private static final Room room = new Room (); //Makes the static instance of the room object
	private static final SpriteContainer sprites = new SpriteContainer (); //Makes the static instance of the sprite container object
	private static final Gui gui = new Gui ();
	private static final Player player = new Player ();
	public boolean keyCheck (int keyCode) {
		//Returns true if the key with an ASCII code of keyCode is pressed down
		return MainLoop.getWindow ().keyCheck (keyCode);
	}
	public boolean keyPressed (int keyCode) {
		//Returns true if the key with an ASCII code of keyCode was pressed down this frame
		return MainLoop.getWindow ().keyPressed (keyCode);
	}
	public boolean keyReleased (int keyCode) {
		//Returns true if the key with an ASCII code of keyCode was released this frame
		return MainLoop.getWindow ().keyReleased (keyCode);
	}
	public boolean keyCheck (char key) {
		//Returns true if the key with an ASCII code of keyCode is pressed down
		return MainLoop.getWindow ().keyCheck ((int)key);
	}
	public boolean keyPressed (char key) {
		//Returns true if the key with an ASCII code of keyCode was pressed down this frame
		return MainLoop.getWindow ().keyPressed ((int)key);
	}
	public boolean keyReleased (char key) {
		//Returns true if the key with an ASCII code of keyCode was released this frame
		return MainLoop.getWindow ().keyReleased ((int)key);
	}
	public GameObject getObject (int x, int y) {
		//Gets the object from the object matrix with the position (x, y)
		return MainLoop.getObjectMatrix ().get (x, y);
	}
	public int getTypeId (String objectName) {
		//Gets the x index for the object matrix corresponding to a specific object name
		return MainLoop.getObjectMatrix ().getTypeId (objectName);
	}
	public int getMouseX () {
		//Get the current mouse x-coordinate on the screen
		return MainLoop.getWindow ().getMouseX ();
	}
	public int getMouseY () {
		//Get the current mouse y-coordinate on the screen
		return MainLoop.getWindow ().getMouseY ();
	}
	public int[] getClick () {
		//Returns the coordinates of the most recent click in the format [x, y]. Returns null if there was not a click this frame.
		return MainLoop.getWindow ().getClick ();
	}
	public boolean mouseClicked () {
		//Returns true if the mouse was clicked this frame
		if (MainLoop.getWindow ().getClick () == null) {
			return false;
		}
		return true;
	}
	public static Room getRoom () {
		return room;
	}
	public static SpriteContainer getSprites () {
		return sprites;
	}
	public static Gui getGui () {
		return gui;
	}
	public static Player getPlayer () {
		return player;
	}
}